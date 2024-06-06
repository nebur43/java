package com.example.reactivehellowold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "mono")
public class ControladorHolaMono {

  @Autowired
  HolaServiceMono servicio;

  @RequestMapping("/hola")
  public Mono<String> hola() {
    
    return servicio.hola();
  }
  
  @RequestMapping("/hola2")
  public Mono<String> hola2() {
    
    return servicio.hola2();
  }
  
  @RequestMapping("/holas")
  public Flux<String> holas() {
    
    long t1= System.currentTimeMillis();
//    String texto=servicio.hola()+ servicio.hola2();
    
    Mono<String> mono1 = servicio.hola();
    Mono<String> mono2 = servicio.hola2();
    Flux<String> flujo = Flux.merge(mono1,mono2);
    
    long t2= System.currentTimeMillis();
    System.out.println(t2-t1);
    return flujo;
  }
}