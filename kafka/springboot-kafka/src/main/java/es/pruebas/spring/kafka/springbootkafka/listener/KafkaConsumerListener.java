package es.pruebas.spring.kafka.springbootkafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import es.pruebas.spring.kafka.springbootkafka.SpringbootKafkaApplication;
import es.pruebas.spring.kafka.springbootkafka.bean.Greeting;

@Component
public class KafkaConsumerListener {
	
	private static Logger LOG = LoggerFactory.getLogger(KafkaConsumerListener.class);
	
//	@KafkaListener(topics = SpringbootKafkaApplication.TOPIC_NAME)
	public void listener(String msg) {
		LOG.info("MENSAJE RECIBIDO:" + msg);
	}

//	@KafkaListener(topics = SpringbootKafkaApplication.TOPIC_NAME)
	public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		LOG.info("MENSAJE RECIBIDO con headers: " + message + "from partition: " + partition);
	}
	
	/**
	 * al ser el offset = 0, lee desde el principio siempre que se levante el cliente
	 * 
	 * @param message
	 * @param partition
	 */
//	@KafkaListener( topicPartitions = @TopicPartition(
//			topic = SpringbootKafkaApplication.TOPIC_NAME,
//			partitionOffsets = {@PartitionOffset(partition = "2",initialOffset = "0")} )
//			)
	@KafkaListener(topics = SpringbootKafkaApplication.TOPIC_NAME)
	public void listenToPartitionOffset0(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Header(KafkaHeaders.OFFSET) int offset) {
		LOG.info("offset: " + offset + " - partition: "+ partition+ " - mensaje: [" + message + "]");
	}

	
//	@KafkaListener( topicPartitions = @TopicPartition(
//			topic = SpringbootKafkaApplication.TOPIC_NAME,
//			partitions = {"0","1","2"})
//			)
	public void listenToPartition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		LOG.info("MENSAJE RECIBIDO con headers: " + message + "from partition: " + partition);
	}
	
//	@KafkaListener(topics = SpringbootKafkaApplication.TOPIC_NAME, containerFactory = "filter_2message_KafkaListenerContainerFactory")
	public void listenerWithFilter(String msg) {
		LOG.info("MENSAJE RECIBIDO:" + msg);
	}
	
//	@KafkaListener(topics = SpringbootKafkaApplication.TOPIC_NAME, containerFactory = "greetingKafkaListenerContainerFactory")
	public void listener(Greeting greeting) {
		LOG.info("Greeting RECIBIDO:" + greeting);
	}
	
}

