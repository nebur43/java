import java.util.Date;

public class SleepTest {

	
	public void test_sin_nada() {
		
		for (int i=0; i<100; i++) {
			Date d1 = new Date();
			Date d2 = new Date();
			System.out.println("ms:" +  (d2.getTime() - d1.getTime()));
		}
		
	}
	
	public void test_sleep(int mls) throws InterruptedException {
		
		for (int i=0; i<100; i++) {
			Date d1 = new Date();
			Thread.sleep(mls ); 
			Date d2 = new Date();
			System.out.println("ms:" +  (d2.getTime() - d1.getTime()));
		}
		
	}
	
	public void test_yield() {
		
		for (int i=0; i<100; i++) {
			Date d1 = new Date();
			Thread.yield(); 
			Date d2 = new Date();
			System.out.println("ms:" +  (d2.getTime() - d1.getTime()));
		}
		
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		
		SleepTest s = new SleepTest();
		
//		s.test_sin_nada();
//		s.test_sleep(100);
//		s.test_sleep(0);
		s.test_sleep(10);
		s.test_yield();
		
	}
	
}
