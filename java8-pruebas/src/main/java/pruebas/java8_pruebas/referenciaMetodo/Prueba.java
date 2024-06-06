package pruebas.java8_pruebas.referenciaMetodo;

import java.util.Arrays;

/**
 * pruebas de referencia de método
 * 
 * @author 67760769
 *
 */
public class Prueba {
	
	public static void main(String[] args) {
		
		System.out.println("inicio");
		
		Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15").forEach(System.out::print);
		System.out.println("---");
		
		String[] c= new String[10];
		Arrays.fill(c, "p");
		Arrays.asList(c).forEach(System.out::print);
		System.out.println("---");
		
		Prueba p = new Prueba();
		Arrays.asList(c).forEach(p::concatena);
		System.out.println("---");
		Arrays.asList(c).forEach( e -> p.concatena(e) );
		System.out.println("---");
		
		Integer[] n= new Integer[10];
		Arrays.fill(n, 1);
//		Arrays.asList(n).forEach(p::concatena); error de compilacion
		
		
		System.out.println("fin");
		
		
	}
	
	public void concatena(String s) {
		System.out.print("concateno:"+s);
	}

}
