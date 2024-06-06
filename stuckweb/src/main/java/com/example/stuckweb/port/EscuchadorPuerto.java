package com.example.stuckweb.port;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EscuchadorPuerto extends Thread {

	private static Logger log = LoggerFactory.getLogger(EscuchadorPuerto.class);
	
	private final Socket s ;
	
	public EscuchadorPuerto(Socket s) {
		this.s = s;
	}
	
	@Override
	public void run() {
		try (InputStream i = s.getInputStream()) {
			log.info("iniciando hilo...");
			log.info("servidor:{}[{}] - destino: {}[{}]", s.getLocalAddress(), s.getLocalPort(), s.getInetAddress(), s.getPort());
			
			Thread.sleep(500); // damos tiempo para que se imprima los logs antes de imprimir el systemOut 
			
			System.out.println();
			int b = i.read();
			while ( b > -1) {
				char a = (char)b;
				System.out.print(a);
				b = i.read();
			}
			
			Thread.sleep(500); // damos tiempo para que se imprima los logs antes de imprimir el systemOut
			
			log.info("Conexión terminada por el cliente. Durmiendo 700 segundos...");
			Thread.sleep(700000);
			
		} catch (Exception e) {
			log.error("error en thread" ,e);
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				log.error("error al cerrar socket" ,e);
			}
		}
		
		log.info("terminando hilo");
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		log.info("escuchando puerto 12011...");
		try (ServerSocket so = new ServerSocket(12011);) {
			while (true) {
				Socket s = so.accept();
				log.info("conexion aceptada");
				
				EscuchadorPuerto escuchador = new EscuchadorPuerto(s);
				escuchador.start();
			}
		}
		
	}


	
	
}
