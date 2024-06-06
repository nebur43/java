package es.pruebas.kafka_helloworld;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public static String TOPIC_NAME_1 = "cola1"; 
	public static String TOPIC_NAME_2 = "cola2";
	

	
	
    static void runProducer() {
    	Producer<String, String> producer = ProducerCreator.createProducer();
        for (int index = 0; index < 10; index++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_NAME_2, // topic
            1, // partition
            "key", // key
            "New RECORD " + index//value
            );
            try {
            RecordMetadata metadata = producer.send(record).get();
                        System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
                        + " with offset " + metadata.offset());
                 } 
            catch (ExecutionException e) {
                     System.out.println("Error in sending record");
                     System.out.println(e);
                  } 
             catch (InterruptedException e) {
                      System.out.println("Error in sending record");
                      System.out.println(e);
                  }
         }
    }
	
    static void runConsumer() throws Exception {
        Consumer<String, String> consumer = ConsumerCreator.createConsumer(TOPIC_NAME_1);
        int noMessageFound = 0;
        while (true) {
          Map<String, ConsumerRecords<String,String>> x = consumer.poll(0);
          
          
          ConsumerRecords<String, String> consumerRecords = null;
          for (Entry<String,ConsumerRecords<String,String>> entry:x.entrySet()) {
        	  System.out.println("Key:" + entry.getKey());
        	  consumerRecords = entry.getValue();
          }
          
          
          // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
          noMessageFound++;
          if (noMessageFound > 10)
            // If no message found count is reached to threshold exit loop.  
            break;

          //print each record.
          List<ConsumerRecord<String,String>> records = consumerRecords.records(0); // partition 0
          
          for ( ConsumerRecord<String, String> record : records) {
        	  System.out.println("Record Key " + record.key());
              System.out.println("Record value " + record.value());
              System.out.println("Record partition " + record.partition());
              System.out.println("Record offset " + record.offset());
          }
          
          // commits the offset of record to broker. 
//           consumer.commitAsync();
           consumer.commit(true);
        }
    consumer.close();
    }
    
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
        runProducer();
//        runConsumer();
        
        
    }
}
