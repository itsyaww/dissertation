package regulationService.input;

import org.springframework.beans.factory.annotation.Autowired;
import regulationService.comprehend.RegulationProcessor;
import regulationService.publisher.RegulationPublisher;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class RegulationDirectoryMonitor {

    @Autowired
    private RegulationPublisher regulationPublisher;
    private RegulationFileReader regulationFileReader;
    private RegulationProcessor regulationProcessor;
    private String directory;

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
            String fileContents = regulationFileReader.parseRegulationPDF(event.context().toString());

            /*Regulation enrichedRegulation = regulationProcessor.enrichRegulation(fileContents);

            regulationPublisher.send(enrichedRegulation);*/

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
