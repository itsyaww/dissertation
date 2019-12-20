package regulationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import regulationService.comprehend.RegulationProcessor;
import regulationService.publisher.Producer;

@SpringBootApplication
public class ServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Autowired
	Producer producer;

	@Override
	public void run(String... args) throws Exception {
		RegulationProcessor.runClient();
		//producer.send("TESTING REGULATION13");
		//producer.send("TESTING REGULATION14");
		//producer.send("TESTING REGULATION15");
	}

}
