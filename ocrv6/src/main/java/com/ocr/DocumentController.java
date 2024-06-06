package com.ocr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.codehaus.plexus.util.Base64;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocr.payload.Ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


@RestController
public class DocumentController {
	
	public String pathTess4j = "C:\\utilidades\\ocr\\Tess4J\\"; 

    @RequestMapping("/ocr")
    public Ocr ocr(@RequestBody Document document) throws IOException, TesseractException {

        InputStream in = new ByteArrayInputStream(Base64.decodeBase64(document.getContent().getBytes()));
		BufferedImage bImageFromConvert = ImageIO.read(in);


        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        setOcrProperties(instance, document);


        return new Ocr(document.getName(), instance.doOCR(bImageFromConvert).replaceAll("\n", ""));
    }

    private void setOcrProperties(ITesseract instance, Document document){

//    	String pathTess4j = "/logs/econtprep/KyC/Tess4J";
        instance.setDatapath(pathTess4j); // path to tessdata directory
        System.out.println("pathTess4j: "+pathTess4j);
        instance.setLanguage("eng");
        for (String prop: document.getOcrProperties().keySet()) {
            instance.setTessVariable(prop,document.getOcrProperties().get(prop));
        }
        if (document.getOutputFormat().equalsIgnoreCase("hocr")) {
            ((Tesseract) instance).setHocr(true);
        }
    }


}
