package pruebas.java_concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileExample {

	/**
	 * Los procesadores de varios nucleos suelen e copiar los datos de acceso frecuente a su memoria caché 
	 * — una memoria interna propia de cada procesador, cuya lectura/escritura es mucho más rápida que la 
	 * RAM — dan lugar a la posibilidad de que ambos hilos estén operando sobre una copia local de la variable 
	 * y no sobre la variable del objeto.
	 * 
	 * Haciendo nuestras variable volátiles obligamos a los hilos a leerlas de la memoria principal, 
	 * y no del caché, garantizando que el valor leído siempre es el último valor escrito en ellas.
	 * 
	 */
	
//	private static volatile int number;
//	private static volatile boolean ready;
	private static int number;
	private static boolean ready;

	private static class Reader implements Runnable{

		@Override
		public void run() {
			while (!ready) {
//				Thread.yield();
			}
			int n = 0;
			n = number;
			System.out.println(n);
		}
	}

	public static void main(String[] args) {
		ExecutorService service= Executors.newFixedThreadPool(500);
		for (int i=0;i<500;i++) {
			service.submit(new Reader());
		}
		
//		new Reader().start();
		number = 42;
		ready = true;
		service.shutdown();
	}

}
