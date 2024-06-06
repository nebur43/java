package pruebas.java_concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureResult implements Runnable{

	@Override
	public void run() {
		
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<String> future = executorService.submit(() -> {
			System.out.println("ejecuntado task ");
			long j=2;
			for (int i=0; i<20 ; i++) {
				j=j*2;
				Thread.sleep(200);
			}
			System.out.println("salimos task " );
			return "FIN TAREA "+j ;
		});
		
		Thread.sleep(1000);
		if (future.cancel(true)) {
			System.out.println("cancelado correcta");
		} else {
			System.out.println("ya habia terminado");
		}
		
		executorService.shutdown();
//		executorService.awaitTermination(20, TimeUnit.SECONDS);
//		

		if (future.isDone() && !future.isCancelled()) {
			String result = future.get(10, TimeUnit.SECONDS);
			System.out.println("respuesta tarea:" + result);
		}
		
		
		System.out.println("sa cabo");
	}

	

}
