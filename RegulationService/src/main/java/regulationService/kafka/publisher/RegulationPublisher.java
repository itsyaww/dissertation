package regulationService.kafka.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import regulationService.model.Regulation;

public class RegulationPublisher {
    @Autowired
    private KafkaTemplate<String, Regulation> kafkaTemplate;

    /*public void send(String payload) {
        kafkaTemplate.send("regulation", payload);
    }*/

    public void send(Regulation regulation) {
        kafkaTemplate.send("regulation", regulation);
    }
}
