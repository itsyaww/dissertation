package backend.consumer;

import backend.model.Regulation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

public class MessageReceiverConfig {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {

        JsonDeserializer<Regulation> deserializer = new JsonDeserializer<>(Regulation.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kafka cluster
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        // allows a pool of processes to divide the work of consuming and processing records
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "paulos-backend");
        // automatically reset the offset to the earliest offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }

    /*@Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kafka cluster
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        // allows a pool of processes to divide the work of consuming and processing records
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "paulos");
        // automatically reset the offset to the earliest offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }*/

    @Bean
    public ConsumerFactory<String, Regulation> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<>(Regulation.class,false));
    }


    /*public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }*/

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Regulation>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Regulation> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public RegulationReceiver receiver() {
        return new RegulationReceiver();
    }
}
