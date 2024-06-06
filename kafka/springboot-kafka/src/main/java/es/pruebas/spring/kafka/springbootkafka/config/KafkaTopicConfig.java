package es.pruebas.spring.kafka.springbootkafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

//@Configuration
public class KafkaTopicConfig {
    
	private static Logger LOG = LoggerFactory.getLogger(KafkaTopicConfig.class);
	
    @Value(value = "${kafka.zookeeper}")
    private String zookeeperAddress;

    /*
     * kafkaAdmin crea colas de los topicos NewTopic
     * Si se genera, no se llama a los commandlinerunner ¿?
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
    	LOG.info("creando bean KafkaAdmin");
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, zookeeperAddress);
        KafkaAdmin ka = new KafkaAdmin(configs);
        LOG.info("--- tenemos KafkaAdmin");
        return ka;
    }
    
    @Bean
    public NewTopic topic1() {
    	LOG.info("creando bean NewTopic");
         return new NewTopic("baeldung", 1, (short) 1);
    }
}