package pruebas.java_concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample implements Runnable{

	private CyclicBarrier cyclicBarrier;
	private String txt ; 
	
	public CyclicBarrierExample(CyclicBarrier barrier, String txt) {
		this.cyclicBarrier = barrier;
		this.txt = txt;
	}
	
	@Override
	public void run() {
		
		try {
			System.out.println(txt + " is waiting...");
			Thread.sleep(100);
			
			cyclicBarrier.await();
			
			System.out.println(txt + " paso intermedio");
			Thread.sleep(100);
			
			cyclicBarrier.await();
			
			System.out.println(txt + " is released");
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		
		System.out.println("EMPEZAMOS");
		
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("todas las tareas completadas"));
		
		Thread t1 = new Thread(new CyclicBarrierExample(cyclicBarrier, "t1"), "t1");
		Thread t2 = new Thread(new CyclicBarrierExample(cyclicBarrier, "t2"), "t2");
		Thread t3 = new Thread(new CyclicBarrierExample(cyclicBarrier, "t3"), "t3");
		
		if (!cyclicBarrier.isBroken()) {
			t1.start();
			t2.start();
			t3.start();
		}
		
		
		System.out.println("FIN");
	}


	
}
