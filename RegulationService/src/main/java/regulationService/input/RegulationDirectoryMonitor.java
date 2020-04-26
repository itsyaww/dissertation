package regulationService.input;

import regulationService.comprehend.RegulationProcessor;
import regulationService.kafka.consumer.RegulationReceiver;
import regulationService.kafka.publisher.MessagePublisher;
import regulationService.model.Message;
import regulationService.kafka.publisher.RegulationPublisher;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class RegulationDirectoryMonitor {

    private RegulationPublisher regulationPublisher;
    private MessagePublisher messagePublisher;
    private RegulationReceiver receiver;

    private RegulationFileReader regulationFileReader;
    private RegulationProcessor regulationProcessor;
    private String workingDirectory;
    private String completedDirectory;

    public static void main(String[] param) throws IOException {
        RegulationDirectoryMonitor monitor = new RegulationDirectoryMonitor("","");
        //String text = monitor.regulationFileReader.parseRegulationPDF("/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2019_83.pdf");
        String text = monitor.regulationFileReader.parseRegulationPDF("/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2010_59.pdf");
        Message message = monitor.regulationProcessor.createMessage(text);
    }

    public RegulationDirectoryMonitor(String workingDirectory, String completedDirectory, RegulationPublisher publisher)
    {
        this.workingDirectory = workingDirectory;
        this.completedDirectory = completedDirectory;
        this.regulationPublisher = publisher;
    }

    public RegulationDirectoryMonitor(String workingDirectory, String completedDirectory, RegulationPublisher publisher, MessagePublisher mPublisher)
    {
        this.workingDirectory = workingDirectory;
        this.completedDirectory = completedDirectory;
        this.regulationPublisher = publisher;
        this.messagePublisher = mPublisher;
    }

    public RegulationDirectoryMonitor(String workingDirectory, String completedDirectory){

        if(workingDirectory.equals(""))
        {
            this.workingDirectory = "/Users/paulfrimpong/RegulationFiles";
        }
        else{this.workingDirectory = workingDirectory;}

        if(completedDirectory.equals(""))
        {
            this.completedDirectory = "/Users/paulfrimpong/RegulationFiles/Processed";
        }
        else{this.completedDirectory = completedDirectory;}

        regulationFileReader = new RegulationFileReader();
        regulationProcessor = new RegulationProcessor();

    }

    public void monitorDirectory() throws IOException, InterruptedException {

        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(workingDirectory);

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

        String directory = this.workingDirectory;
        String fileName = event.context().toString();
        String filePath = directory + "/" + fileName;

        if(isPDF(filePath)){
            try {
                String regulationContent = regulationFileReader.parseRegulationPDF(filePath);

                if(regulationContent != null) //check file content successfully processed
                {
                    //Regulation enrichedRegulation = regulationProcessor.enrichRegulation(regulationContent);
                    Message enrichedMessage = regulationProcessor.createMessage(regulationContent);

                    //regulationPublisher.send(enrichedRegulation);
                    messagePublisher.send(enrichedMessage);
                }
                System.out.println("SLEEPING...");
                Thread.sleep(5000); //Prevent AWS from rejecting multiple calls


                String newFilePath = completedDirectory  + "/" + fileName;
                moveFile(filePath, newFilePath);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println(fileName + " is not a .pdf file. Skipping...");
        }
    }

    private void handleModified(WatchEvent<?> event) {
        handleCreated(event); //Treat update regulations as the same. Reprocess and publish to Kafka again.
    }

    private void handledDeleted(WatchEvent<?> event) {
        logEvent(event);
    }

    private boolean isPDF(String filePath) {
        return filePath.endsWith(".pdf");
    }

    private void moveFile(String filePath, String newFilePath) throws IOException {
        Path temp = Files.move(Paths.get(filePath), Paths.get(newFilePath));

        if(temp != null)
        {
            System.out.println("File renamed and moved successfully");
        }
        else
        {
            System.out.println("Failed to move the file");
        }
    }

    private void logEvent(WatchEvent<?> event)
    {
        System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
    }
}
