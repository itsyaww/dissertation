package backend.consumer;

import backend.model.Regulation;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class RegulationReceiver {

    /*private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "regulation")
    public void receive(Regulation regulation) {
        System.out.println(regulation.toString());
        latch.countDown();
    }

    *//*@KafkaListener(topics = "regulation")
    public void receive(String payload) {
        System.out.println(payload);
        latch.countDown();
    }*/
}
