package regulationService.consumer;

import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "regulation")
    public void receive(String payload) {
        System.out.println(payload);
        latch.countDown();
    }
}
