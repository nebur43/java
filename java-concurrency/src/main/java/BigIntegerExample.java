import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * un minuto suma hasta: 5895818789 => 5.895.818.789
 * volatile mas lento:   5569204905
 * 
 * @author 67760769 - Rubén Aguado
 *
 */
public class BigIntegerExample {
	
	static BigInteger bigInteger = BigInteger.valueOf(1);
	static boolean fin = false;

	public static void main(String[] args) {

		Thread dSuma = new Thread(()-> {
			BigInteger suma = BigInteger.valueOf(2);
			while (!fin)
				bigInteger=bigInteger.add(suma);
		});
		
		ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.schedule(()->{
			fin = true;
			System.out.println(bigInteger);
			}, 60, TimeUnit.SECONDS);
		dSuma.start();
		scheduledExecutorService.shutdown();
	}

}
