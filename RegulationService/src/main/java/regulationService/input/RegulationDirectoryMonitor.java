package regulationService.input;

import regulationService.comprehend.RegulationProcessor;
import regulationService.kafka.consumer.RegulationReceiver;
import regulationService.model.Regulation;
import regulationService.kafka.publisher.RegulationPublisher;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class RegulationDirectoryMonitor {

    private RegulationPublisher regulationPublisher;
    private RegulationReceiver receiver;

    private RegulationFileReader regulationFileReader;
    private RegulationProcessor regulationProcessor;
    private String directory;

    public static void main(String[] param) throws IOException {
        RegulationDirectoryMonitor monitor = new RegulationDirectoryMonitor("");
        String text = monitor.regulationFileReader.parseRegulationPDF("/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2019_83.pdf");
        Regulation regulation = monitor.regulationProcessor.enrichRegulation(text);
    }

    public RegulationDirectoryMonitor(String directory, RegulationPublisher publisher)
    {
        this(directory);
        this.regulationPublisher = publisher;
    }

    public RegulationDirectoryMonitor(String directory){

        if(directory.equals(""))
        {
            this.directory = "/Users/paulfrimpong/RegulationFiles";
        }
        else{this.directory = directory;}

        regulationFileReader = new RegulationFileReader();
        regulationProcessor = new RegulationProcessor();

    }

    public void monitorDirectory() throws IOException, InterruptedException {

        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(directory);

        path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

        WatchKey watchKey;
        while ((watchKey = watchService.take()) != null) {

            for (WatchEvent<?> event : watchKey.pollEvents()) {
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }

                if (event.kind().equals(ENTRY_CREATE))
                {
                    handleCreated(event);
                }
                else if (event.kind().equals(ENTRY_MODIFY))
                {
                    handleModified(event);
                }
                else
                {
                    handledDeleted(event);
                }

            }
            watchKey.reset(); //Required to add watchKey back to queue + monitor for multiple events
        }
    }

    private void handleCreated(WatchEvent<?> event) {
        logEvent(event);

        try {
            String regulationContent = regulationFileReader.parseRegulationPDF(this.directory + "/" + event.context().toString());

            if(regulationContent != null) //check file content successfully processed
            {
                Regulation enrichedRegulation = regulationProcessor.enrichRegulation(regulationContent);

                regulationPublisher.send(enrichedRegulation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleModified(WatchEvent<?> event) {
        handleCreated(event); //Treat update regulations as the same. Reprocess and publish to Kafka again.
    }

    private void handledDeleted(WatchEvent<?> event) {
        logEvent(event);
    }

    private void logEvent(WatchEvent<?> event)
    {
        System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
    }
}
