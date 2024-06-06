package pruebas.java8_pruebas.lambda;

public interface IAbstracto {

	default void concatena(String s) {
		
	}
	
	public default void concatena2(String s, String s2) {
		
	}
	
	public static void concatena3(String s, String s2) {
		
	}
	
	public default boolean concatena4(String s) {
		return true;
	}
	
	public int suma() ;
	
}
