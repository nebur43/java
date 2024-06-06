package com.nebur.spring.spring5_base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bean1 {
	
	@Autowired
	private Bean2 bean2;
	
	public String getMensaje() {
		return "Hola " + bean2.nombre();
	}

}
