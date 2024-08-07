package com.nebur.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfContentsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfContentsTest.class);
	
	@Test
	public void mezclarPaginasScanner() throws IOException {
		LOGGER.debug("mezclamos páginas"); // no funciona el log
		
		byte[] contentImpar = readFile("C:\\Users\\XXXX\\Downloads\\informe1.pdf");
		byte[] contentPar = readFile("C:\\Users\\XXXX\\Downloads\\informe2.pdf");
		File fileOut = new File("C:\\tmp\\informe.pdf");
		
		try (PDDocument pdfImpar = PDDocument.load(contentImpar);
				PDDocument pdfPar = PDDocument.load(contentPar);
				PDDocument pdfOut = new PDDocument();) {
			
			int indexPar = pdfPar.getNumberOfPages()-1;
			for ( int i=0; i<pdfImpar.getNumberOfPages(); i++ ) {
				PDPage p1 = pdfImpar.getPage(i);
				pdfOut.addPage(p1);
				
				PDPage p2 = pdfPar.getPage(indexPar--);
				pdfOut.addPage(p2);
			}
			
			pdfOut.save(fileOut);
		}
		LOGGER.debug("fin");
	}

	@Test
	public void extraerTexto() {
		String path = "C:\\_PROYECTOS\\usigner\\20240520\\doc\\pdfs";
		File directorio = new File(path);
		File[] archivosPdf = directorio.listFiles((File dir, String name) -> name.endsWith(".pdf"));
		for (File archivoPdf : archivosPdf) {
			LOGGER.info("Archivo PDF encontrado: " + archivoPdf.getName());
			try (PDDocument pdf = PDDocument.load(archivoPdf)) {
				PDFTextStripper stripper = new PDFTextStripper();
				String texto = stripper.getText(pdf);
				File fileOut = new File(path+"\\"+archivoPdf.getName().replace(".pdf", ".txt"));
				try (FileOutputStream fos = new FileOutputStream(fileOut)) {
					fos.write(texto.getBytes());
				}
			} catch (IOException e) {
				LOGGER.error("Error al extraer texto del archivo PDF: " + archivoPdf.getName(), e);
				}
			}
	}
	
	
	public byte[] readFile(String path) throws IOException {
		
		File file = new File(path);		
		byte[] array = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		try {
			if (fis.read(array)<=0) {
				throw new IOException("Archivo vacío: "+path) ;
			}
		} finally {
			fis.close();
		}
		
		
		return array;		
	}
}
