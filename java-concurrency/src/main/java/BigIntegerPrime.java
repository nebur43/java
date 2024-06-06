import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

public class BigIntegerPrime {

	public static void main(String[] args) throws IOException {
		// el primo mas grande conocido:
		System.out.println(new Date() +" -> Empezamos");

		// https://es.wikipedia.org/wiki/Mayor_n%C3%BAmero_primo_conocido
		int expo = 82589933;
		BigInteger primo = BigInteger.valueOf(2).pow(expo).add(BigInteger.valueOf(-1));
		
		salvarAFichero("c:/tmp/M"+expo+".txt", primo.toString());
		
		System.out.println(new Date() +" -> terminamos");
	}
	
	public static void salvarAFichero(String path, String content) throws IOException {
		FileWriter writer = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(writer);
		bw.write(content);
		bw.close();
		writer.close();
	}

}
