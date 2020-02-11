package regulationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import regulationService.comprehend.RegulationProcessor;
import regulationService.publisher.Producer;
import regulationService.storage.StorageProperties;
import regulationService.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Autowired
	Producer producer;

	@Override
	public void run(String... args) throws Exception {
		//RegulationProcessor.runClient();
		producer.send("TESTING REGULATION1");
		producer.send("TESTING REGULATION2");
		producer.send("TESTING REGULATION3");
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
