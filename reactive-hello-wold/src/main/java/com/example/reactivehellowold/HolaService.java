package com.example.reactivehellowold;

import org.springframework.stereotype.Service;

@Service
public class HolaService {

  public String hola() {

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return "hola sincrono";

  }

  public String hola2() {

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  
    return "hola sincrono 2";

  }
}