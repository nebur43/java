package com.example.reactivehellowold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "simple")
public class ControladorHola {

  @Autowired
  HolaService servicio;

  @RequestMapping("/hola")
  public String hola() {
    
    return servicio.hola();
  }
  
  @RequestMapping("/hola2")
  public String hola2() {
    
    return servicio.hola2();
  }
  
  @RequestMapping("/holas")
  public String holas() {
    
    long t1= System.currentTimeMillis();
    String texto=servicio.hola()+ servicio.hola2();
    long t2= System.currentTimeMillis();
    System.out.println(t2-t1);
    return texto;
  }
}