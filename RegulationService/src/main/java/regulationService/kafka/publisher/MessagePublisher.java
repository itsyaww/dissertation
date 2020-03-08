package regulationService.kafka.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import regulationService.model.Message;
import regulationService.model.Regulation;

public class MessagePublisher {

    @Autowired
    private KafkaTemplate<String, Message> messageKafkaTemplate;

    /*@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String payload) {
        kafkaTemplate.send("regulation", payload);
    }*/

    public void send(Message message) {
        messageKafkaTemplate.send("message", message);
    }
}
