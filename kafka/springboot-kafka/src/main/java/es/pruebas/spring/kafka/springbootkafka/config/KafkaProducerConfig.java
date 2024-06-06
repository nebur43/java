package es.pruebas.spring.kafka.springbootkafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import es.pruebas.spring.kafka.springbootkafka.bean.Greeting;

@Configuration
public class KafkaProducerConfig {

	private static Logger LOG = LoggerFactory.getLogger(KafkaProducerConfig.class);
	
    @Value(value = "${kafka.bootstrapAddress}")
    private String kafkaAddress;
	
    @Bean
    public ProducerFactory<String, String> producerFactory() {
    	LOG.info("creando bean ProducerFactory");
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
    	LOG.info("creando bean KafkaTemplate");
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, Greeting> greetingProducerFactory() {
    	LOG.info("creando bean ProducerFactory");
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Greeting> greetingKafkaTemplate() {
    	LOG.info("creando bean KafkaTemplate");
        return new KafkaTemplate<>(greetingProducerFactory());
    }
    
}