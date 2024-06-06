package es.pruebas.spring.kafka.springbootkafka;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import es.pruebas.spring.kafka.springbootkafka.bean.Greeting;

@SpringBootApplication
public class SpringbootKafkaApplication implements CommandLineRunner{

	private static Logger LOG = LoggerFactory.getLogger(SpringbootKafkaApplication.class);
	
	public static final String TOPIC_NAME = "cola1";
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired 
	private KafkaTemplate<String, Greeting> greetingTemplate;
	
    /**
     * Consideraciones:
     * 		
     * 		kafka guarda todos los eventos. Leer de la cola no elimina el evento. Internamente tiene un limitie temporal (7 dias) y por tamaño.
     * 		Cada aplicacion debe conectarse al topico utilizando un groupID distinto. De esta forma cada aplicacion leerian los mismos mensajes.
     * 		Una cola se divide en 1 o más particiones. A cada nodo/aplicacion de un mismo groupID que se conecta a ella se le asigna una o varias particiones.
     * 		Si hay mas particiones(3) que nodos(2), un nodo escucharia de 1 particion y el otro de las otras 2. La asignacion la realiza el servidor kafka, salvo
     * 		que se indique programaticamente de cual escuchar.
     * 		Si hay mas nodos (4) que particiones (3), habria un nodo que no escucha el topico y los otros tres escucharían una particion cada uno.
     * 		Programaticamente podemos escuchar de una particion y a partir de un offset, lo cual implicaría eschcuar eventos que ya han ocurrido tiempo atrás. 
     * 
     * 
     * 
     * listar colas: kafka-topics.sh --list --zookeeper zookeeper:2181 .
     * descripcion cola: kafka-topics.sh --describe --topic cola2 --zookeeper zookeeper:2181
     * crear cola: ./kafka-topics.sh --zookeeper zookeeper:2181 --topic cola1 --create --partitions 3 --replication-factor 1
     * ver mensajes en cola: kafka-console-consumer.sh --topic cola2 --bootstrap-server localhost:9092 --from-beginning
     * 
     * @param args
     * @throws Exception 
     */

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("iniciando comando");
		
		Thread t = new Thread( () -> {
			int j=300;
			while ( j < 340 ) {
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for ( int i = 0 ; i < 10 ; i++) {
					sendMessage(j +" FRIKADA "+ new Date());
					j++;
				}
			}
			
		});
		t.start();
		
		for ( int i = 0 ; i < 30 ; i++) {
//			sendMessage(i +" hola-mundo "+ new Date());
//			sendGreeting(new Greeting("hola mundo " + new Date(), "paco"+i));
		}
		
		LOG.info("finalizando comando");
	}
	
	
	public void sendMessage(String message) {
		
		LOG.info("enviando mesaje.... [" + message + "]");
		
	    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_NAME, message);
	    		
	    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

	        @Override
	        public void onSuccess(SendResult<String, String> result) {
	        	LOG.debug("Sent message=[" + message + 
	              "] with offset=[" + result.getRecordMetadata().offset() + "]");
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	        	LOG.debug("Unable to send message=[" 
	              + message + "] due to : " + ex.getMessage());
	        }
	    });
	    
	}
	
	public void sendGreeting(Greeting greeting) {
		greetingTemplate.send(TOPIC_NAME, greeting);
	}

}
