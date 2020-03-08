package regulationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import regulationService.input.RegulationDirectoryMonitor;
import regulationService.kafka.consumer.RegulationReceiver;
import regulationService.kafka.publisher.MessagePublisher;
import regulationService.kafka.publisher.RegulationPublisher;
import regulationService.storage.StorageProperties;
import regulationService.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Autowired
	RegulationPublisher regulationPublisher;

	@Autowired
	MessagePublisher messagePublisher;

	@Autowired
	private RegulationReceiver receiver;

	@Override
	public void run(String... args) throws Exception {

		//regulationPublisher.send("TESTING REGULATION4");
		//regulationPublisher.send("TESTING REGULATION5");
		//regulationPublisher.send("TESTING REGULATION6");

		RegulationDirectoryMonitor directoryMonitor = new RegulationDirectoryMonitor("/Users/paulfrimpong/RegulationFiles",regulationPublisher, messagePublisher);
		directoryMonitor.monitorDirectory();

		/*new RegulationProcessor().runClient();*/
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
