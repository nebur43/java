package es.pruebas.kafka_java8;

import java.time.Duration;
import java.util.List;
import java.util.Map.Entry;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;


/**
 * Hello world!
 *
 */
public class App 
{
	
	public static String TOPIC_NAME_1 = "cola1"; 
	public static String TOPIC_NAME_2 = "cola2";
	
	
    static void runProducer(String conexion, String topic, int partition) {
    	
    	try (Producer<String, String> producer = ProducerCreator.createProducer(conexion)) {
    		
            for (int index = 0; index < 10; index++) {
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, // topic
		                partition, // partition
		                String.valueOf(index), // key
		                "New -- RECORD " + index+ " partition:" + partition//value
		                );
                System.out.println("enviando record");
                RecordMetadata metadata = producer.send(record).get();
                System.out.println("Record sent with key " + index + " to partition " + metadata.partition() + " with offset " + metadata.offset());
             }
            
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    }
	
    static void runConsumer(String topic, int partition) throws Exception {
        Consumer<String, String> consumer = ConsumerCreator.createConsumer(topic);
        int noMessageFound = 0;
        while (true) {
        	ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
          
        	// 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecords.count() == 0) {
            	System.out.println("no encontramos mensajes en cola "+ topic);
                noMessageFound++;
                if (noMessageFound > 10) // esperamos 10 segundos antes de terminar
                  // If no message found count is reached to threshold exit loop.  
                  break;
                else
                    continue;
            }
        	
        	
          //print each record. 
            consumerRecords.
            	records(new TopicPartition(topic, partition)).
            	forEach(record -> {
	            	System.out.println("**** LEIDO REGISTRO ****");
	                System.out.println("Record Key " + record.key());
	                System.out.println("Record value " + record.value());
	                System.out.println("Record partition " + record.partition());
	                System.out.println("Record offset " + record.offset());
	                System.out.println();
             });
          
          
            // commits the offset of record to broker. (los marca como leidos)
             consumer.commitAsync();
        }
    consumer.close();
    }
	
    /**
     * listar colas: kafka-topics.sh --list --zookeeper zookeeper:2181 .
     * descripcion cola: kafka-topics.sh --describe --topic cola2 --zookeeper zookeeper:2181
     * crear cola: ./kafka-topics.sh --zookeeper zookeeper:2181 --topic cola1 --create --partitions 3 --replication-factor 1
     * ver mensajes en cola: kafka-console-consumer.sh --topic cola2 --bootstrap-server localhost:9092 --from-beginning
     * 
     * @param args
     * @throws Exception 
     */
    
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
//        runProducer("localhost:9092", TOPIC_NAME_1, 0);
        runConsumer(TOPIC_NAME_1,0);
        System.out.println( "FIN" );
    }
}
