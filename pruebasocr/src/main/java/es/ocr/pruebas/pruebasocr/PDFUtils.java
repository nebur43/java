package es.ocr.pruebas.pruebasocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(PDFUtils.class);

	public static void main(String[] args) {

		//		File[] files = new File("C:\\tmp\\extraccionProduccion").listFiles(new FilenameFilter() {
		//			
		//			@Override
		//			public boolean accept(File dir, String name) {
		//				return name.endsWith(".pdf");
		//			}
		//		});

		File[] files = {new File("C:\\tmp\\extraccionProduccion\\28081-MPV1.pdf")};


		for ( File file:files) {
			try {

				byte[] fb = PDFUtils.readFile(file);
				//				String text = PDFAnalizer.formatearTexto(PDFAnalizer.extractText(fb));
				String text = PDFUtils.extractText(fb);

				System.out.println("File:" + file.getName() + "\tPageCount:"+ PDFUtils.countPDFPages(fb) + 
						"\tcaracteresCount:"+text.length() + "\tContainsISBAN:" + text.contains("ES"));

				System.out.println(text);

			} catch (Exception e) {
				System.err.println("Error fichero "+ file);
				e.printStackTrace();
			}

		}



	}

	public static String extractText(byte[] pdfContent) throws IOException {
		
		try (PDDocument doc = PDDocument.load(pdfContent)) {
			PDFTextStripper pdfStripper = new PDFTextStripper();
			return pdfStripper.getText(doc);
		}
		
	}

	public static int countPDFPages(byte[] inputFile) throws IOException {
		PDDocument doc = PDDocument.load(inputFile);
		return doc.getNumberOfPages();
	}


	public static byte[] readFile(File file) throws IOException {

		byte[] array = new byte[(int) file.length()];
		
		try (FileInputStream fis = new FileInputStream(file)) {
			if (fis.read(array)<=0) {
				throw new IOException("Archivo vacío: " + file) ;
			}
		}

		return array;		
	}

	public static List<BufferedImage> extraerImagenes(byte[] pdfContent) throws IOException {
		ArrayList<BufferedImage> images = new ArrayList<>();
		
		LOGGER.debug("cargando pdf");
		try (PDDocument document = PDDocument.load(pdfContent)) {
			LOGGER.debug("pdf cargado");
			PDPageTree list = document.getPages();
			LOGGER.debug("paginas recuperadas");
			for (PDPage page : list) {
				PDResources pdResources = page.getResources();
				for (COSName name : pdResources.getXObjectNames()) {
					PDXObject o = pdResources.getXObject(name);
					if (o instanceof PDImageXObject) {
						PDImageXObject image = (PDImageXObject)o;
						images.add(image.getImage());
						//                        String filename = OUTPUT_DIR + "extracted-image-" + i + ".png";
						//                        ImageIO.write(image.getImage(), "png", new File(filename));
					}
				}
			}
		}
		LOGGER.debug("fin");
		return images;
	}


}
