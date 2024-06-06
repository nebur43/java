package pruebas.java8_pruebas.hackerrank;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    // Complete the candies function below.
    static long candies(int n, int[] arr) {
    	long r=0;
    	int[] can = new int[n] ;
    	Arrays.fill(can, 1);
    	
    	
    	for (int i=1;i<n;i++) {
    		if (arr[i]>arr[i-1]) {
    			can[i]=can[i-1]+1;
    		} 
    	}	
    	
    	for (int i=n-2;i>=0;i--) {
    		if (arr[i]>arr[i+1] && can[i]<can[i+1]+1) {
    			can[i]=can[i+1]+1;
    		}
    	}
    	
    	
    	for (int j : can) {
    		r+=j;
    	}
    	
    	return r;
    }
    
	private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("c:/tmp/kk.txt"));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            int arrItem = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            arr[i] = arrItem;
        }

        long result = candies(n, arr);

        System.out.println(String.valueOf(result));
        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
