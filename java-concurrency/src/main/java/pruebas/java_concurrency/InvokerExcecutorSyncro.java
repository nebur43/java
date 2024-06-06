package pruebas.java_concurrency;

import java.util.concurrent.Executor;

/**
 * 
 * @author 67760769 - Rubén Aguado
 *
 */
public class InvokerExcecutorSyncro implements Executor{

	@Override
	public void execute(Runnable command) {
		System.out.println("execute 1");
		command.run();
		System.out.println("execute 2");
	}
	
	public static void main(String[] args) {
		Executor executor = new InvokerExcecutorSyncro();
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("dentro del runnable 1");
				
			}
		};
		
		executor.execute(r);
		
		executor.execute(() -> {
			System.out.println("task to execute");
		});
		
	}

}
