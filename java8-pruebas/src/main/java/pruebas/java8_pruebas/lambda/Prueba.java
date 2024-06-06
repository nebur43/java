package pruebas.java8_pruebas.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.adictosaltrabajo.com/2015/12/04/expresiones-lambda-con-java-8/
 * https://www.adictosaltrabajo.com/2015/03/02/optional-java-8/
 * https://www.ecodeup.com/entendiendo-paso-a-paso-las-expresiones-lambda-en-java/
 * https://www.arquitecturajava.com/programacion-funcional-java-8-streams/
 * 
 * 
 * @author 67760769 - Rubén Aguado
 *
 */
public class Prueba {

	public static void main(String[] args) {

		ISoloUnMetodo m1 = inn -> !inn;
		
		boolean in = false;
		System.out.println( "entrada:"+ in + " salida:" +m1.test(in));
		
		IDosArgumentos xx = (a,b) -> 2;
		System.out.println( "Suma pega:" +xx.suma(22, 35));
		
		xx = (a,b) -> a+b;
		System.out.println( "Suma buena:" +xx.suma(22, 35));
		
		xx = (a,b) -> {
			a++;
			return a+b;
			};
		System.out.println( "Suma falsa (+1):" +xx.suma(22, 35));
		
		IArgumentoVacio yy = () -> "asdf";
		System.out.println("cadena:"+yy.test());
		
		List<String> aList = new ArrayList<>();
		aList.add("pepe");
		aList.add("francisco");
		aList.stream()
			 .findFirst()
			 .ifPresent(System.out::println);		
		
	}

}

