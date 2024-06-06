package com.java6;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DocumentController {
	
// llamada http://localhost:9090/prueba?arg=123
    @GetMapping("/prueba")
    @ResponseBody
    public String prueba(@RequestParam String arg)  {

    	System.out.println("se ha llamado al ws /prueba");
    	if (arg.equalsIgnoreCase("normadat")) {
    		SFTPSender.main(null);
    	} else if (arg.equalsIgnoreCase("ftps")) {
    		SFTPSender.usarClienteFtp();
    	} else if (arg.equalsIgnoreCase("ftp")) {
    		FTPSender.main(null);
    	}
        return "**** Fin: " + arg + "*** \n\n";
    }



}
