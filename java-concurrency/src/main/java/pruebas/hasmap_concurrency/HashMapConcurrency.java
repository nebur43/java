package pruebas.hasmap_concurrency;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * se puede hacer put, get o remove con multiples threads.
 * Falla al iterar sobre el hashmap (forEach)
 * 
 * @author 67760769
 *
 */
public class HashMapConcurrency implements Runnable{

	private String name;
	
	private static HashMap<String, Integer> hm = new HashMap<>();
	
	private static Integer semaforo= 1;
	
	
	public HashMapConcurrency(String name) {
		this.name = name;
	}
	
	
	@Override
	public void run() {
		for ( int i = 0 ; i < 50 ; i++) {
			String key = name + "_" + i; 
			System.out.println("tratando " + key);
			synchronized (semaforo) {
				hm.put(key , i);
			}
			
			Integer j = hm.get(key);
			
//			hm.keySet().forEach(System.out::println); esta linea si falla
//			hm.keySet().stream().count(); // esta linea falla tambien
			
			// hacemos un clon para que no falle
			HashMap<String, Integer> hmClone = null;
			synchronized (semaforo) {
				hmClone = (HashMap<String, Integer>)hm.clone();
			}
			
//			hmClone.keySet().forEach(System.out::println);
			System.out.println("size " + hmClone.keySet().size());
			
			
			synchronized (semaforo) {
				hm.remove(key);
			}
			
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		executorService.execute(new HashMapConcurrency("pepe"));
		executorService.execute(new HashMapConcurrency("ruben"));
		executorService.execute(new HashMapConcurrency("adrian"));
		executorService.execute(new HashMapConcurrency("alex"));
		executorService.execute(new HashMapConcurrency("jesus"));
		executorService.execute(new HashMapConcurrency("maria"));
		
		executorService.execute(new HashMapConcurrency("enero"));
		executorService.execute(new HashMapConcurrency("febrero"));
		executorService.execute(new HashMapConcurrency("marzo"));
		executorService.execute(new HashMapConcurrency("abril"));
		executorService.execute(new HashMapConcurrency("mayo"));
		executorService.execute(new HashMapConcurrency("junio"));
		executorService.execute(new HashMapConcurrency("julio"));
		executorService.execute(new HashMapConcurrency("agosto"));
		executorService.execute(new HashMapConcurrency("septiembre"));
		executorService.execute(new HashMapConcurrency("octubre"));
		executorService.execute(new HashMapConcurrency("noviembre"));
		executorService.execute(new HashMapConcurrency("diciembre"));
		
		
		executorService.shutdown();
		
		System.out.println("---------------------------");
	}

	
	
}
