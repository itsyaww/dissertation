package regulationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
		producer.send("TESTING REGULATION10");
		producer.send("TESTING REGULATION11");
		producer.send("TESTING REGULATION12");
	}

}
