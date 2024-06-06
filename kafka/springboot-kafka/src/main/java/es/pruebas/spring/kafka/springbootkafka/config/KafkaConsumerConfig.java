package es.pruebas.spring.kafka.springbootkafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import es.pruebas.spring.kafka.springbootkafka.bean.Greeting;

@Configuration
public class KafkaConsumerConfig {
	
	private static Logger LOG = LoggerFactory.getLogger(KafkaConsumerConfig.class);
	
    @Value(value = "${kafka.bootstrapAddress}")
    private String kafkaAddress;
    
    @Value(value = "${kafka.groupId}")
    private String groupId;
	
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
    	LOG.info("creando bean consumerFactory");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
    	LOG.info("creando bean kafkaListenerContainerFactory(ConsumerFactory");
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
        factory.setConsumerFactory( consumerFactory );
        return factory;
    }
	
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> filter_2message_KafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
    	LOG.info("creando bean filter_2message_KafkaListenerContainerFactory");
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
        factory.setConsumerFactory(consumerFactory); // reutilizamos el mismo bean que ya tenemos
        factory.setRecordFilterStrategy(
          record -> !	record.value().startsWith("2"));
        return factory;
    }
    
    @Bean
    public ConsumerFactory<String, Greeting> greetingConsumerFactory() {
    	LOG.info("creando bean greetingConsumerFactory");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Greeting.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Greeting> greetingKafkaListenerContainerFactory( ConsumerFactory<String, Greeting> greetingConsumerFactory ) {
    	LOG.info("creando bean greetingKafkaListenerContainerFactory");
        ConcurrentKafkaListenerContainerFactory<String, Greeting> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(greetingConsumerFactory);
        return factory;
    }

}
