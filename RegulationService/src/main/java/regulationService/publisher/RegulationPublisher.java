package regulationService.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class RegulationPublisher {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String payload) {
        kafkaTemplate.send("regulation", payload);
    }
    /*public void send(Regulation regulation) {
        kafkaTemplate.send("regulation", regulation);
    }*/
}
