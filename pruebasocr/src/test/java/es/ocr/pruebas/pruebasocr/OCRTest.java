package es.ocr.pruebas.pruebasocr;

import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ocr.pruebas.cif.CifAnalayzer;
import net.sourceforge.tess4j.ITesseract.RenderedFormat;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import nu.pattern.OpenCV;

public class OCRTest {
	
	private static final String TESSDATA_PATH = "C:\\utilidades\\ocr\\Tess4J\\tessdata_new\\";
	
	private static final String CIFS_PATH = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs\\";
	private static final String CIFS_OCTUBRE_PATH = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs_octubre\\";
	

	private static final Logger LOGGER = LoggerFactory.getLogger(OCRTest.class);
	
	private static final String [] CIF_JPG = {"url_10_0-ECONT-MPV1-231106-08120870_cif.jpeg","url_12_0-ECONT-MPV1-231107-08122207_cif.jpeg","url_13_0-ECONT-MPV1-231107-08122266_cif.jpeg","url_14_0-ECONT-MPV1-231107-08123176_cif.jpeg","url_16_0-ECONT-MPV1-231107-08125585_cif.jpeg","url_19_0-ECONT-MPV1-231107-08126804_cif.jpeg","url_1_0-ECONT-MPV1-231106-08101191_cif.jpeg","url_22_0-ECONT-MPV1-231107-08127731_cif.jpeg","url_23_0-ECONT-MPV1-231107-08127878_cif.jpeg","url_26_0-ECONT-MPV1-231107-08132617_cif.jpeg","url_29_0-ECONT-MPV1-231107-08140057_cif.jpeg","url_32_0-ECONT-MPV1-231108-08141125_cif.jpeg","url_33_0-ECONT-MPV1-231108-08141344_cif.jpeg","url_34_0-ECONT-MPV1-231108-08141374_cif.jpeg","url_35_0-ECONT-MPV1-231108-08142021_cif.jpeg","url_37_0-ECONT-MPV1-231108-08144372_cif.jpeg","url_38_0-ECONT-MPV1-231108-08144844_cif.jpeg","url_39_0-ECONT-MPV1-231108-08146681_cif.jpeg","url_3_0-ECONT-PANGEA-231106-08105054_tc.jpeg","url_40_0-ECONT-MPV1-231108-08147062_cif.jpeg","url_44_0-ECONT-MPV1-231108-08149277_cif.jpeg","url_48_0-ECONT-MPV1-231108-08153345_cif.jpeg","url_49_0-ECONT-MPV1-231108-08156019_cif.jpeg","url_4_0-ECONT-MPV1-231106-08110427_cif.jpeg","url_50_0-ECONT-MPV1-231108-08160322_cif.jpeg","url_52_0-ECONT-MPV1-231109-08161229_cif.jpeg","url_53_0-ECONT-MPV1-231109-08161742_cif.jpeg","url_54_0-ECONT-MPV1-231109-08161764_cif.jpeg","url_55_0-ECONT-MPV1-231109-08161772_cif.jpeg","url_56_0-ECONT-MPV1-231109-08162236_cif.jpeg","url_58_0-ECONT-MPV1-231109-08165292_cif.jpeg","url_59_0-ECONT-MPV1-231109-08165146_cif.jpeg","url_60_0-ECONT-MPV1-231109-08167555_cif.jpeg","url_61_0-ECONT-MPV1-231109-08168290_cif.jpeg","url_63_0-ECONT-MPV1-231109-08168867_cif.jpeg","url_64_0-ECONT-MPV1-231109-08170243_cif.jpeg","url_67_0-ECONT-MPV1-231109-08171840_cif.jpeg","url_6_0-ECONT-MPV1-231106-08113677_cif.jpeg","url_70_0-ECONT-MPV1-231109-08179965_cif.jpeg"};
	private static final String [] CIF_JPG_SI_MODE1 = {"url_10_0-ECONT-MPV1-231106-08120870_cif.jpeg","url_12_0-ECONT-MPV1-231107-08122207_cif.jpeg","url_13_0-ECONT-MPV1-231107-08122266_cif.jpeg","url_14_0-ECONT-MPV1-231107-08123176_cif.jpeg","url_1_0-ECONT-MPV1-231106-08101191_cif.jpeg","url_23_0-ECONT-MPV1-231107-08127878_cif.jpeg","url_26_0-ECONT-MPV1-231107-08132617_cif.jpeg","url_29_0-ECONT-MPV1-231107-08140057_cif.jpeg","url_39_0-ECONT-MPV1-231108-08146681_cif.jpeg","url_40_0-ECONT-MPV1-231108-08147062_cif.jpeg","url_48_0-ECONT-MPV1-231108-08153345_cif.jpeg","url_50_0-ECONT-MPV1-231108-08160322_cif.jpeg","url_52_0-ECONT-MPV1-231109-08161229_cif.jpeg","url_55_0-ECONT-MPV1-231109-08161772_cif.jpeg","url_59_0-ECONT-MPV1-231109-08165146_cif.jpeg","url_61_0-ECONT-MPV1-231109-08168290_cif.jpeg","url_63_0-ECONT-MPV1-231109-08168867_cif.jpeg"};
	private static final String [] CIF_JPG_NO_MODE1 = {"url_16_0-ECONT-MPV1-231107-08125585_cif.jpeg","url_19_0-ECONT-MPV1-231107-08126804_cif.jpeg","url_22_0-ECONT-MPV1-231107-08127731_cif.jpeg","url_32_0-ECONT-MPV1-231108-08141125_cif.jpeg","url_33_0-ECONT-MPV1-231108-08141344_cif.jpeg","url_34_0-ECONT-MPV1-231108-08141374_cif.jpeg","url_35_0-ECONT-MPV1-231108-08142021_cif.jpeg","url_37_0-ECONT-MPV1-231108-08144372_cif.jpeg","url_38_0-ECONT-MPV1-231108-08144844_cif.jpeg","url_3_0-ECONT-PANGEA-231106-08105054_tc.jpeg","url_44_0-ECONT-MPV1-231108-08149277_cif.jpeg","url_49_0-ECONT-MPV1-231108-08156019_cif.jpeg","url_4_0-ECONT-MPV1-231106-08110427_cif.jpeg","url_53_0-ECONT-MPV1-231109-08161742_cif.jpeg","url_54_0-ECONT-MPV1-231109-08161764_cif.jpeg","url_56_0-ECONT-MPV1-231109-08162236_cif.jpeg","url_58_0-ECONT-MPV1-231109-08165292_cif.jpeg","url_60_0-ECONT-MPV1-231109-08167555_cif.jpeg","url_64_0-ECONT-MPV1-231109-08170243_cif.jpeg","url_67_0-ECONT-MPV1-231109-08171840_cif.jpeg","url_6_0-ECONT-MPV1-231106-08113677_cif.jpeg","url_70_0-ECONT-MPV1-231109-08179965_cif.jpeg"};
	private static final String [] CIF_PDF = {"url_11_0-ECONT-MPV1-231107-08122071_cif.pdf","url_17_0-ECONT-MPV1-231107-08126248_cif.pdf","url_20_0-ECONT-MPV1-231107-08127509_cif.pdf","url_24_0-ECONT-MPV1-231107-08130173_cif.pdf","url_27_0-ECONT-MPV1-231107-08133624_cif.pdf","url_28_0-ECONT-PANGEA-231107-08137929_tc.pdf","url_2_0-ECONT-MPV1-231106-08102198_cif.pdf","url_30_0-ECONT-MPV1-231108-08141138_cif.pdf","url_31_0-ECONT-MPV1-231108-08141220_cif.pdf","url_41_0-ECONT-MPV1-231108-08147890_cif.pdf","url_42_0-ECONT-MPV1-231108-08147993_cif.pdf","url_43_0-ECONT-MPV1-231108-08148464_cif.pdf","url_45_0-ECONT-MPV1-231108-08150489_cif.pdf","url_46_0-ECONT-MPV1-231108-08150593_cif.pdf","url_51_0-ECONT-MPV1-231108-08160522_cif.pdf","url_57_0-ECONT-MPV1-231109-08162465_cif.pdf","url_5_0-ECONT-MPV1-231106-08113569_cif.pdf","url_65_0-ECONT-PANGEA-231109-08171471_tc.pdf","url_66_0-ECONT-MPV1-231109-08171809_cif.pdf","url_68_0-ECONT-PANGEA-231109-08175394_tc.pdf","url_71_0-ECONT-PANGEA-231109-08180439_tc.pdf","url_8_0-ECONT-PANGEA-231106-08116522_tc.pdf"};
	private static final String [] CIF_PDF_TEXT = {"url_24_0-ECONT-MPV1-231107-08130173_cif.pdf","url_27_0-ECONT-MPV1-231107-08133624_cif.pdf","url_28_0-ECONT-PANGEA-231107-08137929_tc.pdf","url_30_0-ECONT-MPV1-231108-08141138_cif.pdf","url_41_0-ECONT-MPV1-231108-08147890_cif.pdf","url_42_0-ECONT-MPV1-231108-08147993_cif.pdf","url_43_0-ECONT-MPV1-231108-08148464_cif.pdf","url_45_0-ECONT-MPV1-231108-08150489_cif.pdf","url_46_0-ECONT-MPV1-231108-08150593_cif.pdf","url_51_0-ECONT-MPV1-231108-08160522_cif.pdf","url_57_0-ECONT-MPV1-231109-08162465_cif.pdf","url_65_0-ECONT-PANGEA-231109-08171471_tc.pdf","url_71_0-ECONT-PANGEA-231109-08180439_tc.pdf"};
	private static final String [] CIF_PDF_IMAGE = {"url_2_0-ECONT-MPV1-231106-08102198_cif.pdf","url_5_0-ECONT-MPV1-231106-08113569_cif.pdf","url_8_0-ECONT-PANGEA-231106-08116522_tc.pdf","url_11_0-ECONT-MPV1-231107-08122071_cif.pdf","url_17_0-ECONT-MPV1-231107-08126248_cif.pdf","url_20_0-ECONT-MPV1-231107-08127509_cif.pdf","url_31_0-ECONT-MPV1-231108-08141220_cif.pdf","url_66_0-ECONT-MPV1-231109-08171809_cif.pdf","url_68_0-ECONT-PANGEA-231109-08175394_tc.pdf"};
	private static final String [] CIF_PDF_IMAGE_MODE1 = {};
	private static final String [] CIF_PDF_IMAGE_NO_MODE1 = {};
	
	private final App app ;
	private final CifAnalayzer cifAnalayzer = new CifAnalayzer("");
	
	
	public OCRTest() {
		app = new App();
	}
	
	
	@Test
	public void doOneImagen() throws TesseractException, IOException {
		
		String path = CIFS_OCTUBRE_PATH+ "se cuelga tesseract\\";
		String file = "url_184_0-ECONT-MPV1-231010-07688141_cif.jpeg";
		
		Tesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
        instance.setLanguage("spa"); 
        instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
//        instance.setVariable("user_defined_dpi", "370");
        instance.setVariable("tessedit_create_hocr", "0");
        
        
        byte[] jpgContent = EcontratoUtils.readImageFile(path+file);
//        byte[] jpgContent = EcontratoUtils.readImageFile("C:\\tmp\\Screenshot_2.jpg");
        
        String ocr = instance.doOCR(ImageIO.read(new ByteArrayInputStream(jpgContent)));
        printCifs(file, ocr);
        LOGGER.info("pageseg_mode {}=>\n[INICIO]{}[FIN]","1", ocr);
	}
	
	@Test
	public void doOnePdfImage() throws TesseractException, IOException {

		String path = CIFS_OCTUBRE_PATH;
		String file = "url_138_0-ECONT-MPV1-231004-07586707_cif.pdf";

		byte[] pdfContent = EcontratoUtils.readImageFile(path + file);
		List<BufferedImage> images = PDFUtils.extraerImagenes(pdfContent);
		
		ArrayList<String> cifs = new ArrayList<>();
		for (BufferedImage image:images) {
			Tesseract instance = new Tesseract();  // JNA Interface Mapping
			instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
			instance.setLanguage("spa"); 
			instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
			instance.setVariable("tessedit_create_hocr", "0");
			instance.setConfigs(Arrays.asList("quiet"));

			LOGGER.debug("imagen {}x{}", image.getWidth() , image.getHeight());
//			EcontratoUtils.writeImageFile(image, new File("c:\\tmp\\pdfImage-"+System.currentTimeMillis()+".jpg"));
			
			
			String ocr = instance.doOCR(image);
			cifs.addAll( cifAnalayzer.getCifs(ocr) );

		}
			
		LOGGER.info("Cifs detectados: {}", cifs);
	        

	}
	
	@Test
	public void cancelThreadOcr() throws InterruptedException, IOException {
		LOGGER.info("INICIO PROCESO");
		
		String path = CIFS_OCTUBRE_PATH+ "se cuelga tesseract\\";
		String file = "url_184_0-ECONT-MPV1-231010-07688141_cif.jpeg";
		
		OcrThread ocrThread = new OcrThread(EcontratoUtils.readImageFile(path+file));
//		ocrThread.start();
		Thread.sleep(15000);
//		ocrThread.join(15000);
		ocrThread.interrupt();
		
		if (ocrThread.getOcr() != null) {
			printCifs(file, ocrThread.getOcr());
	        LOGGER.info("pageseg_mode 1=>\n[INICIO]{}[FIN]",ocrThread.getOcr());
		}
		ocrThread = null;
		LOGGER.info("intermedio");
		Thread.sleep(15000);
		LOGGER.info("FIN PROCESO");

	}
	
	@Test
	// este si funciona
	public void cancelProcessOcr() throws InterruptedException, IOException {
		LOGGER.info("fin");
		Process process = Runtime.getRuntime().exec(new String[]{
				"C:\\utilidades3\\tesseract\\tesseract.exe",
//				"C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs_octubre\\url_01_0-ECONT-MPV1-231011-07700092_cif.jpeg",
				"C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs_octubre\\se cuelga tesseract\\url_184_0-ECONT-MPV1-231010-07688141_cif.jpeg",
				"c:\\tmp\\salida_tesseract_windows"});

		int count = 0;
		while (process.isAlive()) {
			LOGGER.info("procesando...");
			Thread.sleep(1000);
			if (count++ > 10) {
				process.destroy();
			}
		}
		LOGGER.info("salida proceso {}", process.exitValue());
		process = null;
		Thread.sleep(10000);
		LOGGER.info("fin");
	}
	
	@Test
	public void doCreateImagenIntermedia() throws TesseractException, IOException {
		
		String file = "url_34_0-ECONT-MPV1-231108-08141374_cif.jpeg";
		
		LOGGER.info("working dir:{}",System.getProperty("user.dir"));
		Tesseract instance = new Tesseract();  // JNA Interface Mapping
		
        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
        instance.setLanguage("spa"); 
        instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
        instance.setVariable("user_defined_dpi", "160");
        instance.setVariable("tessedit_create_hocr", "0");
//        instance.setConfigs(Arrays.asList("quiet"));
//        instance.setConfigs(Arrays.asList("debug"));
        instance.setVariable("tessedit_write_images", "1");
        
        instance.createDocuments(CIFS_PATH + file,"output" , Arrays.asList(RenderedFormat.TEXT));
        Desktop.getDesktop().open(new File(".processed.tif"));
	}
	
	
	@Test
	public void doAllCreateImagenIntermedia() throws TesseractException, IOException {
		
		//NO
//		String [] images = CIF_JPG_NO_MODE1;
		//SI
		String [] images = CIF_JPG_SI_MODE1;

		for (String image: images) {
			LOGGER.info("working dir:{}",System.getProperty("user.dir"));
			Tesseract instance = new Tesseract();  // JNA Interface Mapping
			
	        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
	        instance.setLanguage("spa"); 
	        instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
//	        instance.setVariable("user_defined_dpi", "370");
	        instance.setVariable("tessedit_create_hocr", "0");
//	        instance.setConfigs(Arrays.asList("quiet"));
//	        instance.setConfigs(Arrays.asList("debug"));
	        instance.setVariable("tessedit_write_images", "1");
	        
	        instance.createDocuments(CIFS_PATH + image,"output" , Arrays.asList(RenderedFormat.TEXT));
	        
	        File f = new File(".processed.tif");
	        f.renameTo(new File(CIFS_PATH+"cifs SI reconocidos mode1\\procesado\\"+image+".tif"));
	        
	        f = new File("output.txt");
	        f.renameTo(new File(CIFS_PATH+"cifs SI reconocidos mode1\\procesado\\"+image+".txt"));
		}
		
		
		
	}
	
	@Test
	public void doPageModeTest() throws TesseractException, IOException {
		
//		String[] modes = {"1","2","3","6"};
		String[] modes = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13"};
		String file = "url_19_0-ECONT-MPV1-231107-08126804_cif.jpeg";
		
		for (String mode: modes) {
			Tesseract instance = new Tesseract();  // JNA Interface Mapping
	        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
	        instance.setLanguage("spa"); 
	        instance.setVariable("tessedit_pageseg_mode", mode); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
//	        instance.setVariable("user_defined_dpi", "370");
	        instance.setVariable("tessedit_create_hocr", "0");
	        
	        byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH+file);
//	        byte[] jpgContent = EcontratoUtils.readImageFile("C:\\tmp\\Screenshot_2.jpg");
	        
	        String ocr = instance.doOCR(ImageIO.read(new ByteArrayInputStream(jpgContent)));
	        printCifs(file, ocr);
	        LOGGER.info("pageseg_mode {}=>\n[INICIO]{}[FIN]",mode, ocr);
		}
		

	}
	
	@Test
	public void doAllImagenes() throws TesseractException, IOException {
		
		String [] images = CIF_JPG;
		
		Tesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
        instance.setLanguage("spa"); 
        instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
        instance.setVariable("tessedit_create_hocr", "0");
        
//        instance.setVariable("load_system_dawg", "0");
//        instance.setVariable("load_freq_dawg", "0");
//        instance.setVariable("tessedit_char_whitelist", "ABCDEFGHJNPQRSUVWKLMXI0123456789");
        
        for (String image:images) {
        	byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH + image);
            String ocr = instance.doOCR(ImageIO.read(new ByteArrayInputStream(jpgContent)));
            
            printCifs(image, ocr);
            
            LOGGER.info("{} texto=>\n[INICIO]{}[FIN]", image, ocr);
        }
        
	}
	
	@Test
	public void doAllPdfs() throws TesseractException, IOException {
		
		String [] pdfFiles = CIF_PDF;
		
		Tesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
        instance.setLanguage("spa"); 
        instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
        instance.setVariable("tessedit_create_hocr", "0");
        
//        instance.setVariable("load_system_dawg", "0");
//        instance.setVariable("load_freq_dawg", "0");
//        instance.setVariable("tessedit_char_whitelist", "ABCDEFGHJNPQRSUVWKLMXI0123456789");
        
        for (String pdfFile:pdfFiles) {
        	byte[] pdfContent = EcontratoUtils.readImageFile(CIFS_PATH + pdfFile);
        	String pdfText = PDFUtils.extractText(pdfContent);
        	if (!printCifs(pdfFile, pdfText)) {
        		
        		List<BufferedImage> images = PDFUtils.extraerImagenes(pdfContent);
        		for (BufferedImage image:images) {
        			LOGGER.info("{} haciendo ocr...", pdfFile);
        			String ocr = instance.doOCR(image);
        			printCifs(pdfFile, ocr);
//                    LOGGER.info("{} texto=>\n[INICIO]{}[FIN]", pdfFile, ocr);
        		}
        	}
        }
        
	}
	
	@Test
	public void doImagePdfsXPageMode() throws TesseractException, IOException {

		String [] pdfFiles = CIF_PDF_IMAGE;

		String[] modes = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13"};

		LOGGER.info("IMAGE;0;1;2;3;4;5;6;7;8;9;10;11;12;13");
		for (String pdfFile:pdfFiles) {
			byte[] pdfContent = EcontratoUtils.readImageFile(CIFS_PATH + pdfFile);
			List<BufferedImage> images = PDFUtils.extraerImagenes(pdfContent);
			StringBuffer sb = new StringBuffer();
			
			for (String mode:modes) {
				
				ArrayList<String> cifs = new ArrayList<>();
				for (BufferedImage image:images) {
					Tesseract instance = new Tesseract();  // JNA Interface Mapping
					instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
					instance.setLanguage("spa"); 
					instance.setVariable("tessedit_pageseg_mode", mode); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
					instance.setVariable("tessedit_create_hocr", "0");
					instance.setConfigs(Arrays.asList("quiet"));

					String ocr = instance.doOCR(image);
					cifs.addAll( cifAnalayzer.getCifs(ocr) );

				}
				if (!cifs.isEmpty()) {
					sb.append("OK;");
				} else {
					sb.append(";");
				}
				
			}
			LOGGER.info("{};{}", pdfFile, sb.toString());
	        
		}

	}
	
	@Test
	public void doAutoDpiImagenesNoOCR() throws TesseractException, IOException {
		
		// imagenes que no procesa el mode1 sin variable dpi
		String [] images = CIF_JPG_NO_MODE1;
		
		Tesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
        instance.setLanguage("spa"); 
        instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
        instance.setVariable("tessedit_create_hocr", "0");

        for (String image:images) {
        	byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH + image);
            String ocr = instance.doOCR(ImageIO.read(new ByteArrayInputStream(jpgContent)));
            
            printCifs(image, ocr);
            
            LOGGER.info("{} ...", image);
        }
        
	}
	
	@Test
	public void doImagenesSIOCR() throws TesseractException, IOException {
		
		// imagenes que se procesa el mode1 sin variable dpi
		String [] images = CIF_JPG_SI_MODE1;
		
		Tesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
        instance.setLanguage("spa"); 
        instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
        instance.setVariable("tessedit_create_hocr", "0");

        for (String image:images) {
        	byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH + image);
            String ocr = instance.doOCR(ImageIO.read(new ByteArrayInputStream(jpgContent)));
            
            printCifs(image, ocr);
            
            LOGGER.info("{} ...", image);
        }
        
	}
	
	@Test
	public void doAllImagenesXPageMode() throws TesseractException, IOException {

		String [] images = CIF_JPG;

		String[] modes = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13"};

		LOGGER.info("IMAGE;0;1;2;3;4;5;6;7;8;9;10;11;12;13");
		for (String image:images) {

//			ArrayList<String> result = new ArrayList<>();
			StringBuffer sb = new StringBuffer();
			
			for (String mode:modes) {
				Tesseract instance = new Tesseract();  // JNA Interface Mapping
				instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
				instance.setLanguage("spa"); 
				instance.setVariable("tessedit_pageseg_mode", mode); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
				instance.setVariable("tessedit_create_hocr", "0");
				instance.setConfigs(Arrays.asList("quiet"));

				byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH + image);
				String ocr = instance.doOCR(ImageIO.read(new ByteArrayInputStream(jpgContent)));

				List<String> cifs = cifAnalayzer.getCifs(ocr);
				if (!cifs.isEmpty()) {
					sb.append("OK;");
				} else {
					sb.append(";");
				}

			}
	        LOGGER.info("{};{}", image, sb.toString());
		}

	}

	@Test
	public void doImagenesXdpi() throws TesseractException, IOException {
		
		// imagenes que no procesa el mode1 sin variable dpi
		String [] images = CIF_JPG_NO_MODE1;
		final int DPI_INIT=50;
		final int DPI_FIN=400;
		final int DPI_STEP=10;
		
		StringBuffer sb = new StringBuffer("IMAGEN;");
		for (int dpi=DPI_INIT; dpi<=DPI_FIN; dpi=dpi+DPI_STEP) {
			sb.append(dpi).append(';');
		}
		LOGGER.info(sb.toString());
		
        for (String image:images) {
        	
        	sb = new StringBuffer(image).append(';');
        	
        	for (int dpi=DPI_INIT; dpi<=DPI_FIN; dpi=dpi+DPI_STEP) {
        		
        		Tesseract instance = new Tesseract();  // JNA Interface Mapping
                instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
                instance.setLanguage("spa"); 
                instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
                instance.setVariable("tessedit_create_hocr", "0");
                instance.setConfigs(Arrays.asList("quiet"));
                
                instance.setVariable("user_defined_dpi", String.valueOf(dpi));
        		
        		byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH + image);
                String ocr = instance.doOCR(ImageIO.read(new ByteArrayInputStream(jpgContent)));
				List<String> cifs = cifAnalayzer.getCifs(ocr);
				if (!cifs.isEmpty()) {
					sb.append("OK;");
				} else {
					sb.append(";");
				}
                
    		}
        	
            LOGGER.info(sb.toString());
        }
		
	}
	
	

	@Test
	public void doAllImagenesPDF_Directory() throws TesseractException, IOException {

		LOGGER.info("Cargando librerias openCV...");
		OpenCV.loadShared();
		LOGGER.info("librerias cargadas");
		
		List<String> filtrar = new ArrayList<>();//Arrays.asList("url_01_0-ECONT-MPV1-231011-07700092_cif.jpeg","url_02_0-ECONT-MPV1-231017-07786223_cif.pdf","url_03_0-ECONT-MPV1-231003-07553265_cif.pdf","url_04_0-ECONT-MPV1-231002-07532184_cif.pdf","url_05_0-ECONT-MPV1-231002-07531879_cif.pdf","url_06_0-ECONT-MPV1-231005-07598517_cif.pdf","url_07_0-ECONT-MPV1-231010-07678986_cif.jpeg","url_08_0-ECONT-MPV1-231013-07729590_cif.jpeg","url_09_0-ECONT-PANGEA-231016-07768841_tc.pdf","url_100_0-ECONT-PANGEA-231028-07984479_tc.pdf","url_102_0-ECONT-MPV1-231006-07620756_cif.pdf","url_103_0-ECONT-MPV1-231002-07532734_cif.pdf","url_104_0-ECONT-MPV1-231031-08020507_cif.jpeg","url_105_0-ECONT-MPV1-231031-08020581_doi1.jpeg","url_106_0-ECONT-MPV1-231002-07545815_cif.pdf","url_107_0-ECONT-MPV1-231020-07844710_cif.jpeg","url_108_0-ECONT-MPV1-231002-07551013_cif.jpeg","url_109_0-ECONT-MPV1-231026-07940202_cif.pdf","url_10_0-ECONT-MPV1-231019-07827499_cif.jpeg","url_110_0-ECONT-MPV1-231002-07545470_cif.pdf","url_111_0-ECONT-MPV1-231031-08020359_cif.jpeg","url_112_0-ECONT-MPV1-231001-07529454_cif.jpeg","url_113_0-ECONT-MPV1-231001-07529596_cif.jpeg","url_114_0-ECONT-MPV1-231003-07569026_cif.jpeg","url_115_0-ECONT-MPV1-231003-07572584_cif.pdf","url_116_0-ECONT-MPV1-231004-07588390_cif.jpeg","url_117_0-ECONT-MPV1-231004-07583007_cif.jpeg","url_118_0-ECONT-MPV1-231031-08016542_cif.jpeg","url_119_0-ECONT-MPV1-231002-07537434_cif.jpeg","url_11_0-ECONT-MPV1-231019-07829453_cif.jpeg","url_120_0-ECONT-MPV1-231003-07573355_cif.jpeg","url_121_0-ECONT-MPV1-231031-08019007_cif.jpeg","url_122_0-ECONT-MPV1-231006-07631225_cif.jpeg","url_123_0-ECONT-MPV1-231003-07564199_cif.pdf","url_124_0-ECONT-MPV1-231004-07585077_cif.jpeg","url_125_0-ECONT-MPV1-231004-07583677_cif.jpeg","url_126_0-ECONT-MPV1-231006-07631998_cif.jpeg","url_127_0-ECONT-MPV1-231006-07629595_cif.pdf","url_128_0-ECONT-MPV1-231001-07529957_cif.jpeg","url_129_0-ECONT-MPV1-231005-07603093_cif.pdf","url_12_0-ECONT-MPV1-231019-07826363_cif.jpeg","url_130_0-ECONT-MPV1-231006-07630144_cif.pdf","url_131_0-ECONT-MPV1-231030-07999278_doi1.pdf","url_132_0-ECONT-MPV1-231002-07543744_cif.pdf","url_133_0-ECONT-MPV1-231005-07604180_cif.pdf","url_134_0-S8-OPER-20231003-30821807_tc.pdf","url_135_0-ECONT-MPV1-231004-07586371_cif.pdf","url_136_0-ECONT-MPV1-231002-07553058_cif.jpeg","url_138_0-ECONT-MPV1-231004-07586707_cif.pdf","url_139_0-ECONT-MPV1-231005-07605870_cif.jpeg","url_13_0-ECONT-MPV1-231019-07826786_cif.pdf","url_140_0-ECONT-MPV1-231002-07546955_cif.pdf","url_141_0-ECONT-MPV1-231004-07576120_cif.pdf","url_142_0-ECONT-MPV1-231031-08021955_cif.pdf","url_143_0-ECONT-MPV1-231030-07999785_cif.pdf","url_144_0-ECONT-MPV1-231003-07564899_cif.pdf","url_145_0-ECONT-MPV1-231003-07570930_cif.jpeg","url_146_0-ECONT-MPV1-231004-07579543_cif.jpeg","url_147_0-ECONT-MPV1-231005-07616011_cif.pdf","url_148_0-ECONT-MPV1-231031-08019025_cif.jpeg","url_149_0-ECONT-MPV1-231005-07617580_cif.pdf","url_14_0-ECONT-MPV1-231019-07826279_cif.pdf","url_150_0-ECONT-MPV1-231005-07618048_cif.jpeg","url_151_0-ECONT-MPV1-231001-07525442_cif.jpeg","url_152_0-ECONT-MPV1-231006-07637444_cif.pdf","url_153_0-ECONT-MPV1-231005-07600228_cif.jpeg","url_155_0-ECONT-MPV1-231007-07647152_cif.pdf","url_156_0-ECONT-MPV1-231005-07602686_cif.jpeg","url_157_0-ECONT-MPV1-231005-07608646_cif.jpeg","url_158_0-ECONT-MPV1-231002-07541448_cif.pdf","url_159_0-ECONT-MPV1-231002-07533540_cif.pdf","url_15_0-ECONT-MPV1-231002-07532585_cif.jpeg","url_160_0-ECONT-MPV1-231016-07765583_cif.jpeg","url_161_0-ECONT-MPV1-231016-07765648_cif.jpeg","url_162_0-ECONT-MPV1-231002-07546342_cif.jpeg","url_163_0-ECONT-MPV1-231029-07991427_cif.pdf","url_164_0-ECONT-MPV1-231002-07540717_cif.pdf","url_165_0-ECONT-MPV1-231001-07528579_cif.pdf","url_166_0-ECONT-MPV1-231001-07529432_cif.pdf","url_167_0-ECONT-MPV1-231002-07543603_cif.jpeg","url_16_0-ECONT-MPV1-231018-07805737_cif.pdf","url_170_0-ECONT-MPV1-231016-07765778_cif.jpeg","url_171_0-ECONT-MPV1-231001-07528427_cif.pdf","url_172_0-ECONT-MPV1-231003-07556745_cif.pdf","url_173_0-ECONT-MPV1-231002-07544454_cif.pdf","url_174_0-ECONT-MPV1-231004-07582206_cif.jpeg","url_175_0-ECONT-MPV1-231003-07567827_cif.jpeg","url_176_0-ECONT-MPV1-231004-07576253_cif.pdf","url_177_0-ECONT-MPV1-231031-08023109_cif.jpeg","url_178_0-ECONT-MPV1-231003-07572868_cif.pdf","url_179_0-ECONT-MPV1-231002-07552694_cif.pdf","url_17_0-ECONT-MPV1-231001-07525033_cif.jpeg","url_180_0-ECONT-MPV1-231005-07605085_cif.pdf","url_181_0-ECONT-MPV1-231004-07581521_cif.pdf","url_182_0-ECONT-MPV1-231005-07617826_cif.pdf","url_183_0-ECONT-MPV1-231001-07528156_cif.jpeg","url_185_0-ECONT-MPV1-231011-07709599_cif.pdf","url_186_0-ECONT-MPV1-231008-07657764_cif.jpeg","url_187_0-ECONT-MPV1-231011-07705205_cif.jpeg","url_189_0-ECONT-MPV1-231013-07732656_cif.pdf","url_18_0-ECONT-MPV1-231031-08016400_cif.pdf","url_191_0-ECONT-PANGEA-231028-07979812_tc.jpeg","url_192_0-ECONT-MPV1-231011-07719655_cif.jpeg","url_193_0-ECONT-MPV1-231011-07701929_cif.jpeg","url_194_0-ECONT-MPV1-231009-07672182_cif.jpeg","url_195_0-ECONT-MPV1-231009-07667033_cif.pdf","url_196_0-ECONT-MPV1-231012-07724931_cif.pdf","url_197_0-ECONT-MPV1-231011-07700911_cif.jpeg","url_198_0-ECONT-MPV1-231011-07712718_cif.pdf","url_199_0-ECONT-MPV1-231010-07698892_cif.jpeg","url_19_0-ECONT-MPV1-231017-07786577_cif.pdf","url_200_0-ECONT-MPV1-231011-07702899_cif.pdf","url_201_0-ECONT-MPV1-231011-07711920_cif.jpeg","url_202_0-ECONT-MPV1-231011-07718886_cif.jpeg","url_203_0-ECONT-MPV1-231009-07672210_cif.jpeg","url_204_0-ECONT-MPV1-231011-07701132_cif.pdf","url_205_0-ECONT-MPV1-231011-07711668_cif.pdf","url_206_0-ECONT-MPV1-231013-07731114_cif.jpeg","url_207_0-ECONT-MPV1-231010-07684599_cif.pdf","url_208_0-ECONT-MPV1-231010-07686880_cif.pdf","url_209_0-ECONT-MPV1-231010-07683081_cif.jpeg","url_211_0-ECONT-MPV1-231013-07734029_cif.pdf","url_212_0-ECONT-MPV1-231010-07679454_doi1.pdf","url_213_0-ECONT-MPV1-231026-07940286_cif.jpeg","url_214_0-ECONT-MPV1-231031-08017235_cif.jpeg","url_215_0-ECONT-MPV1-231018-07823455_cif.jpeg","url_217_0-ECONT-MPV1-231017-07790257_cif.jpeg","url_218_0-ECONT-PANGEA-231016-07776902_tc.pdf","url_220_0-ECONT-MPV1-231017-07789053_cif.jpeg","url_221_0-ECONT-MPV1-231018-07822641_cif.jpeg","url_222_0-ECONT-MPV1-231016-07774625_cif.pdf","url_223_0-ECONT-MPV1-231019-07829653_cif.jpeg","url_224_0-ECONT-MPV1-231017-07799886_cif.jpeg","url_225_0-ECONT-MPV1-231016-07775849_cif.pdf","url_226_0-ECONT-MPV1-231019-07835692_cif.jpeg","url_229_0-ECONT-MPV1-231017-07790905_cif.jpeg","url_22_0-ECONT-MPV1-231026-07941843_cif.jpeg","url_230_0-ECONT-MPV1-231017-07788717_doi1.jpeg","url_231_0-ECONT-PANGEA-231018-07817207_tc.pdf","url_233_0-ECONT-MPV1-231018-07807270_cif.jpeg","url_234_0-ECONT-MPV1-231018-07808311_cif.jpeg","url_235_0-ECONT-MPV1-231017-07789575_cif.jpeg","url_236_0-ECONT-MPV1-231017-07794481_cif.jpeg","url_237_0-ECONT-MPV1-231018-07816178_cif.jpeg","url_238_0-ECONT-MPV1-231019-07836234_cif.jpeg","url_239_0-ECONT-MPV1-231016-07784217_cif.jpeg","url_23_0-ECONT-MPV1-231001-07527497_cif.jpeg","url_240_0-ECONT-MPV1-231019-07843199_cif.pdf","url_241_0-ECONT-MPV1-231017-07788731_cif.jpeg","url_244_0-ECONT-MPV1-231018-07811554_doi1.pdf","url_245_0-ECONT-MPV1-231018-07809928_cif.pdf","url_246_0-ECONT-MPV1-231019-07843653_cif.pdf","url_248_0-ECONT-PANGEA-231018-07817325_tc.jpeg","url_249_0-ECONT-MPV1-231019-07842205_cif.jpeg","url_24_0-ECONT-MPV1-231001-07528053_cif.jpeg","url_250_0-ECONT-MPV1-231020-07844997_cif.jpeg","url_251_0-ECONT-MPV1-231019-07838622_cif.jpeg","url_252_0-ECONT-MPV1-231019-07843655_cif.pdf","url_253_0-ECONT-MPV1-231019-07843661_cif.pdf","url_254_0-ECONT-MPV1-231019-07843666_cif.pdf","url_255_0-ECONT-MPV1-231016-07783160_cif.jpeg","url_256_0-ECONT-MPV1-231019-07843668_cif.pdf","url_257_0-ECONT-MPV1-231019-07843670_cif.pdf","url_258_0-ECONT-MPV1-231019-07843672_cif.pdf","url_259_0-ECONT-MPV1-231019-07843678_cif.pdf","url_25_0-ECONT-MPV1-231003-07554059_cif.pdf","url_260_0-ECONT-PANGEA-231018-07820660_tc.pdf","url_261_0-ECONT-MPV1-231018-07811792_cif.pdf","url_265_0-ECONT-MPV1-231020-07849406_cif.jpeg","url_266_0-ECONT-MPV1-231020-07849977_cif.pdf","url_267_0-ECONT-PANGEA-231020-07850001_tc.tiff","url_268_0-ECONT-MPV1-231020-07863009_cif.jpeg","url_269_0-ECONT-MPV1-231020-07854250_cif.jpeg","url_26_0-ECONT-MPV1-231003-07554083_cif.pdf","url_271_0-ECONT-MPV1-231020-07851080_cif.jpeg","url_272_0-ECONT-MPV1-231020-07851078_cif.jpeg","url_273_0-ECONT-MPV1-231020-07851464_cif.jpeg","url_274_0-ECONT-MPV1-231021-07871314_cif.jpeg","url_277_0-ECONT-MPV1-231030-08008689_cif.jpeg","url_278_0-ECONT-MPV1-231023-07893029_cif.pdf","url_279_0-ECONT-MPV1-231027-07964844_cif.jpeg","url_27_0-ECONT-MPV1-231009-07658700_cif.pdf","url_280_0-ECONT-MPV1-231026-07949446_cif.jpeg","url_283_0-ECONT-MPV1-231026-07948900_cif.jpeg","url_284_0-ECONT-MPV1-231026-07950287_cif.jpeg","url_285_0-ECONT-MPV1-231031-08027569_cif.jpeg","url_286_0-ECONT-MPV1-231024-07919176_cif.jpeg","url_287_0-ECONT-MPV1-231031-08026307_cif.pdf","url_288_0-ECONT-MPV1-231031-08026390_cif.pdf","url_289_0-ECONT-MPV1-231031-08026437_cif.pdf","url_28_0-ECONT-MPV1-231013-07728213_cif.pdf","url_292_0-ECONT-MPV1-231023-07880803_cif.jpeg","url_294_0-ECONT-MPV1-231023-07895521_cif.jpeg","url_295_0-ECONT-MPV1-231023-07886562_cif.pdf","url_296_0-ECONT-PANGEA-231028-07986774_tc.jpeg","url_297_0-ECONT-MPV1-231026-07945770_cif.jpeg","url_298_0-ECONT-MPV1-231024-07912023_cif.jpeg","url_299_0-ECONT-MPV1-231026-07949506_cif.pdf","url_29_0-ECONT-MPV1-231027-07959862_cif.pdf","url_300_0-ECONT-MPV1-231026-07950006_cif.pdf","url_301_0-ECONT-MPV1-231025-07937046_cif.jpeg","url_302_0-ECONT-MPV1-231031-08021444_cif.pdf","url_303_0-ECONT-MPV1-231031-08021428_cif.jpeg","url_305_0-ECONT-MPV1-231030-08015124_cif.jpeg","url_306_0-ECONT-PANGEA-231026-07951574_tc.jpeg","url_307_0-ECONT-MPV1-231026-07944980_cif.jpeg","url_308_0-ECONT-MPV1-231025-07936820_cif.pdf","url_309_0-ECONT-MPV1-231031-08019258_cif.jpeg","url_30_0-ECONT-MPV1-231011-07701192_cif.pdf","url_310_0-ECONT-MPV1-231030-07998704_cif.pdf","url_311_0-ECONT-MPV1-231031-08026322_cif.pdf","url_312_0-ECONT-MPV1-231030-07999861_cif.jpeg","url_313_0-ECONT-MPV1-231030-08005627_cif.pdf","url_314_0-ECONT-MPV1-231030-07997349_cif.jpeg","url_315_0-ECONT-PANGEA-231026-07951406_tc.pdf","url_316_0-ECONT-MPV1-231031-08026276_cif.pdf","url_317_0-ECONT-MPV1-231025-07938923_cif.pdf","url_318_0-ECONT-MPV1-231028-07982066_cif.pdf","url_319_0-ECONT-MPV1-231029-07994316_cif.jpeg","url_31_0-ECONT-MPV1-231012-07722755_cif.pdf","url_320_0-ECONT-MPV1-231030-08003535_cif.pdf","url_321_0-ECONT-MPV1-231030-08003853_cif.pdf","url_322_0-ECONT-MPV1-231026-07942511_cif.jpeg","url_323_0-ECONT-PANGEA-231027-07975054_tc.pdf","url_324_0-ECONT-MPV1-231030-08002467_cif.pdf","url_325_0-ECONT-MPV1-231025-07920206_cif.jpeg","url_326_0-ECONT-MPV1-231025-07920259_cif.pdf","url_327_0-ECONT-MPV1-231026-07949764_cif.pdf","url_328_0-ECONT-MPV1-231025-07926952_cif.jpeg","url_329_0-ECONT-PANGEA-231023-07883965_tc.pdf","url_32_0-ECONT-MPV1-231012-07724920_cif.pdf","url_330_0-ECONT-MPV1-231027-07969639_cif.jpeg","url_331_0-ECONT-PANGEA-231023-07894694_tc.pdf","url_332_0-ECONT-MPV1-231025-07928888_cif.jpeg","url_333_0-ECONT-MPV1-231031-08020822_cif.jpeg","url_334_0-ECONT-MPV1-231024-07915806_cif.pdf","url_335_0-ECONT-MPV1-231026-07942181_cif.jpeg","url_337_0-ECONT-MPV1-231026-07950111_cif.pdf","url_338_0-S8-OPER-20231025-32300257_tc.jpeg","url_339_0-ECONT-MPV1-231024-07907716_cif.pdf","url_33_0-ECONT-MPV1-231013-07739630_cif.pdf","url_340_0-ECONT-MPV1-231030-08000877_cif.pdf","url_341_0-ECONT-PANGEA-231023-07888063_tc.jpeg","url_342_0-ECONT-MPV1-231031-08021544_cif.jpeg","url_344_0-ECONT-MPV1-231024-07909483_cif.jpeg","url_345_0-ECONT-MPV1-231025-07925898_cif.pdf","url_346_0-ECONT-MPV1-231025-07925158_cif.pdf","url_347_0-S8-OPER-20231026-32328607_tc.jpeg","url_348_0-ECONT-PANGEA-231027-07965649_tc.jpeg","url_34_0-ECONT-MPV1-231009-07658746_cif.pdf","url_352_0-ECONT-MPV1-231031-08017214_cif.jpeg","url_353_0-ECONT-MPV1-231024-07912100_cif.jpeg","url_354_0-ECONT-MPV1-231031-08025188_cif.jpeg","url_355_0-ECONT-PANGEA-231030-08013146_tc.pdf","url_356_0-ECONT-MPV1-231024-07905630_cif.jpeg","url_357_0-ECONT-MPV1-231031-08019309_cif.jpeg","url_358_0-ECONT-MPV1-231024-07902322_cif.jpeg","url_359_0-ECONT-MPV1-231026-07944034_cif.pdf","url_35_0-ECONT-MPV1-231010-07680764_cif.pdf","url_360_0-ECONT-PANGEA-231030-08013021_tc.pdf","url_361_0-ECONT-MPV1-231024-07918934_cif.jpeg","url_362_0-ECONT-MPV1-231026-07942858_cif.jpeg");
		String dir = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs_octubre\\";
		File dirF = new File(dir);
		
		Tesseract instance = new Tesseract();  // JNA Interface Mapping
		instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
		instance.setLanguage("spa"); 
		instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
		instance.setVariable("tessedit_create_hocr", "0");
		instance.setConfigs(Arrays.asList("quiet"));

		File[] files = dirF.listFiles();
//		File[] files = {new File(dir+"url_179_0-ECONT-MPV1-231002-07552694_cif.pdf")};
		for (File file: files) {
//			LOGGER.debug("procesando: {}", file.getAbsolutePath());
			if (file.isDirectory() || file.getAbsolutePath().endsWith(".txt")) {
				continue;
			}
			// filtramos los ya procesados
			if (filtrar.stream().anyMatch(f -> file.getAbsolutePath().endsWith(f))) {
				continue;
			}
			
			LOGGER.debug("tratando {}",file.getName());
			long t1 = System.currentTimeMillis();
//			String txt ="";
			List<String> cifs = null;
			if (file.getName().endsWith(".pdf")) {

				byte[] pdfContent = EcontratoUtils.readImageFile(file.getAbsolutePath());
				String txt = PDFUtils.extractText(pdfContent);
	        	cifs = cifAnalayzer.getCifs(txt);
	        	if (!cifs.isEmpty()) {
	        		LOGGER.info("{} | PDF_TXT | CIFS:{} | ms:{} | {}", file.getName(), cifs.size() , System.currentTimeMillis()-t1,cifs);
	        	} else {
	        		List<BufferedImage> images = PDFUtils.extraerImagenes(pdfContent);
	        		if (images.isEmpty()) {
	        			LOGGER.warn("{} - PDF SIN CIF EN TEXTO NI IMAGENES");
	        			continue;
	        		}
	        		for (BufferedImage image:images) {
//	        			txt += instance.doOCR(image);
	        			cifs = getCifs(instance, image);
	        			if (!cifs.isEmpty()) {
	        				break;
	        			}
	        		}
//	        		cifs = cifAnalayzer.getCifs(txt);
	        		LOGGER.info("{} | PDF_IMAGEN | CIFS:{} | ms:{} | {}", file.getName(), cifs.size(), System.currentTimeMillis()-t1, cifs );
	        	}
				
			} else {
				byte[] jpgContent = EcontratoUtils.readImageFile(file.getAbsolutePath());
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(jpgContent));
				cifs = getCifs(instance, image);
	            LOGGER.info("{} | IMAGEN | CIFS:{} | ms:{} | {}", file.getName(), cifs.size(), System.currentTimeMillis()-t1, cifs);
			}
			
//			EcontratoUtils.writeImageFile(txt.getBytes(), new File(file.getAbsolutePath()+".txt"));

		}

	}


	private List<String> getCifs(Tesseract instance,
			BufferedImage bufferedImage) throws TesseractException, IOException {
		List<String> cifs = null;
		try {
			OpenCvExtractor openCvExtractor = new OpenCvExtractor(bufferedImage);
			for (Rectangle rectangle: openCvExtractor) {
				LOGGER.debug("ocr con {}", rectangle);
				String txt = instance.doOCR(bufferedImage,rectangle);
				cifs = cifAnalayzer.getCifs(txt);
				if (!cifs.isEmpty()) {
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error analizando rectangulos",e);
		}
		
		try {
			if (cifs == null || cifs.isEmpty()) {
				LOGGER.debug("ocr imagen completa");
				String txt = instance.doOCR(bufferedImage);
			    cifs = cifAnalayzer.getCifs(txt);
			}
		} catch (Exception e) {
			LOGGER.error("Error analizando imagen completa",e);
		}
		
		
		return cifs;
	}
	
	
	@Test
	public void doOcrRectangleImagen() throws TesseractException, IOException {
		String image="url_70_0-ECONT-MPV1-231109-08179965_cif.jpeg";
		int x=733;
		int y=180;
		int w=299; 
		int h=50;
		showRectangle(image,x,y,w,h);
		String[] modes = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13"};
		
		for (String mode:modes) {
			Tesseract instance = new Tesseract();  // JNA Interface Mapping
	        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
	        instance.setLanguage("spa"); 
	        instance.setVariable("tessedit_pageseg_mode", mode); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
//	        instance.setVariable("user_defined_dpi", "370");
	        instance.setVariable("tessedit_create_hocr", "0");
//	        instance.setConfigs(Arrays.asList("quiet"));
	        byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH+image);
	        Rectangle rectangle = new Rectangle(x,y,w,h);
	        String ocr = instance.doOCR(ImageIO.read(new ByteArrayInputStream(jpgContent)), rectangle);
	        printCifs(image, ocr);
	        LOGGER.info("mode{} texto=>[INICIO]{}[FIN]", mode, ocr);
		}
		

	}
	
	@Test
	public void cutImage() throws IOException {
		String image="url_19_0-ECONT-MPV1-231107-08126804_cif.jpeg";
		byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH+image);
		BufferedImage original = ImageIO.read(new ByteArrayInputStream(jpgContent));
		BufferedImage cut = original.getSubimage(281, 333, 89, 23);
		
		File outputfile = new File("c:\\tmp\\image_cut.jpg");
		ImageIO.write(cut, "jpg", outputfile);
	}
	
	/**
	 * 
     *   * Block of text/image/separator line.
     *  public static final int RIL_BLOCK = 0;
	 *
     *   * Paragraph within a block.
     *  public static final int RIL_PARA = 1;
     *  
     *   * Line within a paragraph.
     *  public static final int RIL_TEXTLINE = 2;
     *  
     *   * Word within a textline.
     *  public static final int RIL_WORD = 3;
     *  
     *   * Symbol/character within a word.
     *  public static final int RIL_SYMBOL = 4;
	 */
	@Test
	public void doSegmentedRegions() throws TesseractException, IOException {
		
		String file = "url_33_0-ECONT-MPV1-231108-08141344_cif.jpeg";
		
		Tesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
        instance.setLanguage("spa"); 
        instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
//        instance.setVariable("user_defined_dpi", "370");
        instance.setVariable("tessedit_create_hocr", "0");
        
        byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH+file);
        
        
        BufferedImage in = ImageIO.read(new ByteArrayInputStream(jpgContent));
        List<Rectangle> regions = instance.getSegmentedRegions(in, 3);
        for (Rectangle region:regions) {
        	LOGGER.info("Region: " + region);
        	showRectangle(file, region.x, region.y, region.width, region.height,"c:\\tmp\\"+file+".region_"+region.x+"-"+region.y+"-"+region.width+"-"+region.height+".jpeg", false);
        }
        
        
//        String ocr = instance.doOCR();
//        printCifs(file, ocr);
//        LOGGER.info("pageseg_mode {}=>\n[INICIO]{}[FIN]","1", ocr);
	}
	
	private void showRectangle(String image,int x, int y, int w, int h) throws IOException {
		showRectangle(image, x, y, w, h, "c:\\tmp\\image_cut.jpg", true);
	}
	
	private void showRectangle(String image,int x, int y, int w, int h, String pathFile, boolean show) throws IOException {
		byte[] jpgContent = EcontratoUtils.readImageFile(CIFS_PATH+image);
		BufferedImage original = ImageIO.read(new ByteArrayInputStream(jpgContent));
		BufferedImage cut = original.getSubimage(x, y, w, h);
		
		File outputfile = new File(pathFile);
		ImageIO.write(cut, "jpg", outputfile);
		if (show)
			Desktop.getDesktop().open(outputfile);
	}
	
	
	private boolean printCifs(String image, String ocr) {
		List<String> cifs = cifAnalayzer.getCifs(ocr);
		if (cifs.isEmpty()) {
			LOGGER.info("{} NO SE ENCUENTRA CIF", image);
			return false;
		} else {
			cifs.forEach(cif -> LOGGER.info("{} cif=>{}", image, cif));
			return true;
		}
	}
	
	private void loadLibreria() {
		System.load("C:\\utilidades\\ocr\\liblept.so.5");
	}
	
	class OcrThread implements Runnable{
		
		 private Thread worker;
		
		final byte[] imageContent;
		
		private String ocr;
		
		public OcrThread(byte[] imageContent) {
			this.imageContent = imageContent;
	        worker = new Thread(this);
	        worker.start();
		}
		
		public void run() {
			try {
				LOGGER.info("INICIO THREAD");
				Tesseract instance = new Tesseract();  // JNA Interface Mapping
		        instance.setDatapath(TESSDATA_PATH); // path to tessdata directory
		        instance.setLanguage("spa"); 
		        instance.setVariable("tessedit_pageseg_mode", "1"); // https://tesseract-ocr.github.io/tessdoc/ImproveQuality#page-segmentation-method
//		        instance.setVariable("user_defined_dpi", "370");
		        instance.setVariable("tessedit_create_hocr", "0");
				
		        
		        ocr = instance.doOCR(ImageIO.read(new ByteArrayInputStream(imageContent)));
		        
		        LOGGER.info("FIN THREAD");
			} catch (Exception e) {
				LOGGER.error("Error en thread ocr", e );
			}
		};
		

	    public void interrupt() {
	        worker.interrupt();
	    }
		
		public String getOcr() {
			return ocr;
		}
	}
	
}
