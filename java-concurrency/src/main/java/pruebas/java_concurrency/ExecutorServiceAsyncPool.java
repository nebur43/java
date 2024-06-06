package pruebas.java_concurrency;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceAsyncPool implements Runnable{

	public String txt;
	public ExecutorServiceAsyncPool(String cadena) {
		txt = cadena;
	}
	
	@Override
	public void run() {
		for (int i =0; i  <20 ;i ++) {
			System.out.println(txt);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("Error cadena " + txt + " error: " + e.getMessage());
//				e.printStackTrace();
			}
		}	
		System.out.println("TERMINA " + txt);
	}
	
	@Override
	public String toString() {
		return "toString="+txt;
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		executorService.submit(new ExecutorServiceAsyncPool("cadena  1"));
		executorService.submit(new ExecutorServiceAsyncPool("cadena  2"));
		executorService.submit(new ExecutorServiceAsyncPool("cadena  3"));
		executorService.submit(new ExecutorServiceAsyncPool("cadena  4"));
		executorService.submit(new ExecutorServiceAsyncPool("cadena  5 ****"));
		executorService.submit(new ExecutorServiceAsyncPool("cadena  6 ------"));
		executorService.submit(new ExecutorServiceAsyncPool("cadena  7 @@@@@@@"));
		executorService.submit(new ExecutorServiceAsyncPool("cadena  8 ¿?¿?¿?¿?¿?"));
		executorService.submit(new ExecutorServiceAsyncPool("cadena  9 FIIIIIN"));

//		executorService.shutdown(); // previously submittedtasks are executed, but no new tasks will be accepted. 
//		List<Runnable>  tareas = executorService.shutdownNow(); // desecha las tareas en cola
//		for (Runnable r:tareas) {
//			System.out.println("Tareas no ejecutadas:" + r);
//		}
//		executorService.submit(new ExecutorServiceAsyncPool("cadena  0000 "));
		
		if (!executorService.awaitTermination(20, TimeUnit.SECONDS) ) { // se usa despues de un shutdown()
			System.err.println("Salto el timeout");
		}
		
		System.out.println("---------------------------");
	}



}
