package pruebas.java_concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample implements Runnable{

	static Semaphore semaphore = new Semaphore(2);
	private String txt;
	
	public SemaphoreExample(String txt) {
		this.txt = txt;
	}
	
	@Override
	public void run() {
		System.out.println(txt + " - Available permit:" + semaphore.availablePermits() + " - threads waiting to acquire:" + semaphore.getQueueLength());
	 
	    try {
			if (semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
			    try {
			        System.out.println(txt + " - doing task...");
			        Thread.sleep(500);
			        System.out.println(txt + " - finish task.");
			    } catch (InterruptedException e) {
					e.printStackTrace();
				}
			    finally {
			        semaphore.release();
			    }
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		
		Thread t1 = new Thread(new SemaphoreExample("t1"));
		Thread t2 = new Thread(new SemaphoreExample("t2"));
		Thread t3 = new Thread(new SemaphoreExample("t3"));
		Thread t4 = new Thread(new SemaphoreExample("t4"));
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();

	}

}
