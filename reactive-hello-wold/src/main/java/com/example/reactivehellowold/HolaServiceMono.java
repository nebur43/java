package com.example.reactivehellowold;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HolaServiceMono {
  public Mono<String> hola() {
    return Mono.just("hola asincrono").delayElement(Duration.ofSeconds(3));
  }
  public Mono<String> hola2() {
    
    return Mono.just("hola asincrono2").delayElement(Duration.ofSeconds(3));
  }
  
  public static void main(String[] args) {
	
	  Date d1 = new Date();
	  HolaServiceMono servicio = new HolaServiceMono();
	  Mono<String> m1 = servicio.hola();
	  Mono<String> m2 = servicio.hola2();
	  
	  List<String> l = Flux.merge(m1,m2).collectList().block();
	  
	  System.out.println("1[" + l.get(0) + "] 2[" + l.get(1)+"]");
	  Date d2 = new Date();
	  System.out.println("total time:" + (d2.getTime()- d1.getTime()) + " ms");
	  
	  
}
  
}
