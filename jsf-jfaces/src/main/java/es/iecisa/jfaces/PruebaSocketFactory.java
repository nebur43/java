package es.iecisa.jfaces;

public class PruebaSocketFactory {

	public static void main(String[] args) {
		System.setProperty("org.apache.axis.components.net.SecureSocketFactory","misuperclase.SecureSocektFactory");
		
		System.out.println(" propiedad:" + System.getProperty("org.apache.axis.components.net.SecureSocketFactory"));
		
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("FIN");
	}
	
}
