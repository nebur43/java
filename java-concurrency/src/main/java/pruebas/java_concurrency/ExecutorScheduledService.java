package pruebas.java_concurrency;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorScheduledService implements Runnable{

	public String txt;
	
	public ExecutorScheduledService(String txt) {
		this.txt = txt;
	}
	
	@Override
	public void run() {
		System.out.println( new Date() + " " +txt);
		
	}
	
	public static void main(String[] args) throws InterruptedException {

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
		scheduledExecutorService.schedule(new ExecutorScheduledService("tarea 1"), 3,TimeUnit.SECONDS);
		scheduledExecutorService.schedule(new ExecutorScheduledService("tarea 2"), 5,TimeUnit.SECONDS);
		scheduledExecutorService.schedule(new ExecutorScheduledService("tarea 3"), 2,TimeUnit.SECONDS);
		scheduledExecutorService.schedule(new ExecutorScheduledService("tarea 4"), 7,TimeUnit.SECONDS);
		System.out.println(new Date() + " " +"INICIO");
		scheduledExecutorService.shutdown();
		scheduledExecutorService.awaitTermination(60, TimeUnit.SECONDS);
		System.out.println(new Date() + " " +"FIN");
		
		/*Ejecuta tareas cada 4 1 y 2 segundos
		 * */
		final ScheduledExecutorService scheduledExecutorService2 = Executors.newScheduledThreadPool(4);
		scheduledExecutorService2.scheduleAtFixedRate(new ExecutorScheduledService("tarea 10"), 2, 4, TimeUnit.SECONDS);
		scheduledExecutorService2.scheduleAtFixedRate(new ExecutorScheduledService("tarea 20"), 1, 1, TimeUnit.SECONDS);
		scheduledExecutorService2.scheduleAtFixedRate(new ExecutorScheduledService("tarea 30"), 3, 2, TimeUnit.SECONDS);
		
		scheduledExecutorService2.schedule(()-> {
			System.out.println(new Date() + " " +"FINALIZAMOS DESPUES DE 15 SEGUNDOS ...");
			scheduledExecutorService2.shutdown();
			return true;
		},15, TimeUnit.SECONDS);
		
		scheduledExecutorService2.awaitTermination(40, TimeUnit.SECONDS);
		System.out.println(new Date() + " " +"SEGUNDO FIN");
	}

	

}
