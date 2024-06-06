package pruebas.java8_pruebas.streams;

import java.util.Arrays;
import java.util.List;

public class Prueba {
	
	
	public static void main(String[] args) {
		
		System.out.println("inicio");
		List<String> s = Arrays.asList("0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(","));
		
		s.stream().map( c -> {
			System.out.println(Thread.currentThread().getName()+ "-paso1-"+c);
			return c;
		}).map( c -> {
			System.out.println(Thread.currentThread().getName()+ "-paso2-"+c);
			return c;
		}).map( c -> {
			System.out.println(Thread.currentThread().getName()+ "-paso3-"+c);
			return c;
		}).count();
		
		System.out.println("--------------------");
		
		s.parallelStream().map( c -> {
			System.out.println(Thread.currentThread().getName()+ "-paso1-"+c);
			return c;
		}).map( c -> {
			System.out.println(Thread.currentThread().getName()+ "-paso2-"+c);
			return c;
		}).map( c -> {
			System.out.println(Thread.currentThread().getName()+ "-paso3-"+c);
			return c;
		}).count();
		System.out.println("fin");
	}

}
