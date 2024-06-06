package com.nebur.opencvTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nu.pattern.OpenCV;

public class OpenCvTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenCvTest.class);
	
	public OpenCvTest() {
		LOGGER.info("Cargando librerias openCV...");
		OpenCV.loadShared();
		LOGGER.info("librerias cargadas");
	}
	
	@Test
	// https://www.baeldung.com/java-opencv
	public void faceDetection() {
		String imagePath = "C:\\tmp\\TestImg.jpg";
		String targetPath = "C:\\tmp\\TestImg_opencv.jpg";
		LOGGER.info("Cargando imagen {}", imagePath);
		Mat imageMat = Imgcodecs.imread(imagePath);
		
		LOGGER.info("imagen cargada {}. Rows={}. Cols={}", imagePath, imageMat.rows(), imageMat.cols());
		
		//  object to store the faces we find
		MatOfRect facesDetected = new MatOfRect();
		
		// initialize the CascadeClassifier to do the recognition
		CascadeClassifier cascadeClassifier = new CascadeClassifier(); 
		// https://github.com/opencv/opencv/tree/master/data/haarcascades
		cascadeClassifier.load("./src/main/resources/haarcascades/haarcascade_frontalface_alt.xml"); // pretrained model for faces
		
		int minFaceSize = Math.round(imageMat.rows() * 0.1f);
		cascadeClassifier.detectMultiScale(imageMat,
				facesDetected,
				1.1,
				3,
				Objdetect.CASCADE_SCALE_IMAGE,
				new Size(minFaceSize, minFaceSize),
				new Size() 
				);
		
		Rect[] facesArray = facesDetected.toArray(); 
		for(Rect face : facesArray) { 
		    Imgproc.rectangle(imageMat, face.tl(), face.br(), new Scalar(0, 0, 255), 3); 
		} 
		
		Imgcodecs.imwrite(targetPath, imageMat);
		
		LOGGER.info("FIN");
	}
	
	@Test
//	https://stackoverflow.com/questions/38748508/java-opencv-rectangle-detection-with-hough-transform
	public void rectangleDetection() {
		String imagePath = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs\\url_12_0-ECONT-MPV1-231107-08122207_cif.jpeg";
		String targetPath = "C:\\tmp\\cif_opencv.jpg";
//		rectangleDetection(imagePath, targetPath, 100, 300);
		List<Rect> rectangles = rectangleDetection(imagePath, 50, 100, true);
		writeRectangles(imagePath, rectangles, targetPath);
		
		LOGGER.info("FIN");
	}
	
	@Test
	public void allCifsDetection() {
		String dir = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs\\";
		String output = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs\\deteccion_rectangulos\\";
		
		File fDir = new File(dir); 
		for ( File file: fDir.listFiles() ) {
			if (file.getName().contains("url_32")
					|| file.getName().contains("url_37")
					) {
				continue;
			}
			
			if (!file.isDirectory() && file.getName().endsWith(".jpeg")) {
				ArrayList<Rect> rectangles = new ArrayList<>(); 
				rectangles.addAll(rectangleDetection(file.getAbsolutePath(), 25,25, true));
				rectangles.addAll(rectangleDetection(file.getAbsolutePath(), 50,100, true));
				rectangles.addAll(rectangleDetection(file.getAbsolutePath(), 100,300, true));
				
				writeRectangles(file.getAbsolutePath(), rectangles, output+file.getName());
			}
		}
		
	}
	
	@Test
	public void doBorderDetection() {
		String imagePath = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs\\url_70_0-ECONT-MPV1-231109-08179965_cif.jpeg";
		Mat source = Imgcodecs.imread(imagePath);
		
		Mat imageGrey = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.cvtColor(source, imageGrey, Imgproc.COLOR_RGB2GRAY);
        
        for (int i=0 ; i < 500; i=i+25) {
        	for (int j=0 ; j < 500; j=j+25) {
        		Mat imageBorder = new Mat(source.rows(), source.cols(), source.type());
        		Imgproc.Canny(imageGrey, imageBorder, i, j);
        		Imgcodecs.imwrite("c:\\tmp\\canny_"+i+"_"+j+".jpg", imageBorder);
        	}
        }
        
	}

	@Test
	public void doBorderDetection4Threshold() {
		String imagePath = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs\\url_12_0-ECONT-MPV1-231107-08122207_cif.jpeg";
		
        for (int i=0 ; i < 500; i=i+25) {
        	for (int j=0 ; j < 500; j=j+25) {
        		List<Rect> r = rectangleDetection(imagePath,i,j,true);
        		if (!r.isEmpty()) {
        			writeRectangles(imagePath, r, "c:\\tmp\\borderDetection_"+i+"_"+j+".jpeg");
        		}
        	}
        }
        
	}
	
	@Test
	public void allCifsBorder4Threshold() {
		String dir = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs\\";
		
		final int MIN_THRESHOLD_1=0;
		final int MAX_THRESHOLD_1=376;
		
		final int MIN_THRESHOLD_2=0;
		final int MAX_THRESHOLD_2=376;
		
		final int STEP_THRESHOLD=25;
		
		StringBuilder sb = new StringBuilder("FICHERO;");
		for (int i=MIN_THRESHOLD_1 ; i < MAX_THRESHOLD_1; i=i+STEP_THRESHOLD) {
        	for (int j=MIN_THRESHOLD_2 ; j < MAX_THRESHOLD_2; j=j+STEP_THRESHOLD) {
        		sb.append(i).append('x').append(j).append(';');
        	}
		}
		LOGGER.info(sb.toString());
		
		File fDir = new File(dir); 
		for ( File file: fDir.listFiles() ) {
			if (!file.isDirectory() && file.getName().endsWith(".jpeg")) {
				
				sb = new StringBuilder(file.getName());
				sb.append(';');
		        for (int i=MIN_THRESHOLD_1 ; i < MAX_THRESHOLD_1; i=i+STEP_THRESHOLD) {
		        	for (int j=MIN_THRESHOLD_2 ; j < MAX_THRESHOLD_2; j=j+STEP_THRESHOLD) {
		        		List<Rect> rectangulos = rectangleDetection(file.getAbsolutePath(),i,j,false);
		        		sb.append(rectangulos.size()).append(';');
		        	}
		        }
		        LOGGER.info(sb.toString());
				
			}
		}
		
	}
	
	private List<Rect> rectangleDetection(String imagePath, int threshold1, int threshold2, boolean log) {
		if (log)
			LOGGER.info("threshold1={} - threshold2={} - imagen:{}", threshold1, threshold2, imagePath);
		ArrayList<Rect> r = new ArrayList<>();
//		Mat imageMat = Imgcodecs.imread(imagePath);
		
//		Imgcodecs.imread("rectangle.jpg", Imgcodecs.CV_LOAD_IMAGE_ANYCOLOR);
        Mat source = Imgcodecs.imread(imagePath);
        double imageArea = source.rows()* source.cols();
        
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.cvtColor(source, destination, Imgproc.COLOR_RGB2GRAY);
        
        // Aplicar un desenfoque para reducir el ruido
        Imgproc.GaussianBlur(destination, destination, new Size(5, 5), 0);
        
//        int threshold = 100;
//        Imgproc.Canny(destination, destination, 50, 100);
//        Imgproc.Canny(destination, destination, threshold, threshold*3);
        Imgproc.Canny(destination, destination, threshold1, threshold2);
        
//        Imgproc.equalizeHist(destination, destination);
//        Imgproc.GaussianBlur(destination, destination, new Size(5, 5), 0, 0, Core.BORDER_DEFAULT);

        
        //Imgproc.adaptiveThreshold(destination, destination, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 40);
//        Imgproc.threshold(destination, destination, 0, 255, Imgproc.THRESH_BINARY);

//        Imgcodecs.imwrite("c:\\tmp\\postCanny.jpg", destination);
        
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(destination, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        
        MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
        MatOfPoint2f approxCurve = new MatOfPoint2f();

//        int count=0;
        for (MatOfPoint contour: contours) {
//        	for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
//        	count++;
//            MatOfPoint contour = contours.get(idx);
            Rect rect = Imgproc.boundingRect(contour);
            

            matOfPoint2f.fromList(contour.toList());
            Imgproc.approxPolyDP(matOfPoint2f, approxCurve, Imgproc.arcLength(matOfPoint2f, true) * 0.02, true);
            long total = approxCurve.total();
            
            // mostrar TODOS RECTANGULOS
//          Imgproc.rectangle(source, rect.tl(), rect.br(), new Scalar(0, 0, 255), 3);
//          double contourAreaX = Imgproc.contourArea(contour);
//        	if ( 
//        			contourAreaX > imageArea*0.001
//        			) {
//        		LOGGER.info("RECTANGULO => ratio:{} - lados:{} - rectangulo:{} - area: {}", total, rect, contourAreaX);
//        		r.add(rect);
//        	}
//            LOGGER.info("Contorno:{} - lados {} - rectangulo: {} - area: {}", contour, total, rect, contourArea);
            
//            if (Imgproc.contourArea(contour) > imageArea*0.001) {
//            	LOGGER.info("Contorno:{} - lados {} - rectangulo: {} - area: {}", contour, total, rect, Imgproc.contourArea(contour));
//            }
            
            if (total == 3) { // is triangle
                // do things for triangle
            }
            if (total >= 4 && total <= 6) {
//                List<Double> cos = new ArrayList<>();
//                Point[] points = approxCurve.toArray();
//                for (int j = 2; j < total + 1; j++) {
//                    cos.add(angle(points[(int) (j % total)], points[j - 2], points[j - 1]));
//                }
//                Collections.sort(cos);
//                Double minCos = cos.get(0);
//                Double maxCos = cos.get(cos.size() - 1);
//                boolean isRect = total == 4 && minCos >= -0.1 && maxCos <= 0.3;
//                boolean isPolygon = (total == 5 && minCos >= -0.34 && maxCos <= -0.27) || (total == 6 && minCos >= -0.55 && maxCos <= -0.45);
//                if (isRect || isPolygon) {
                	double ratio ;
                	if (rect.width < rect.height) {
                		ratio = (double) rect.width / rect.height; 
                	} else {
                		ratio = (double) rect.height / rect.width;
                	}
                	double contourArea = Imgproc.contourArea(contour);
//                	r.add(rect);
//                	LOGGER.info("RECTANGULO => ratio:{} - lados:{} - rectangulo:{} - area: {}", ratio, total, rect, contourArea);
                	if ( 
                			ratio > 0.15 
                			&& ratio < 0.30 && 
                			contourArea > imageArea*0.001
                			) {
                		if (log)
                			LOGGER.info("RECTANGULO => ratio:{} - lados:{} - rectangulo:{} - area:{} - imageArea:{} - umbralArea:{}", ratio, total, rect, contourArea, imageArea, imageArea*0.001);
                		r.add(rect);
//                		Imgproc.rectangle(source, rect.tl(), rect.br(), new Scalar(0, 0, 255), 3);
                	}
                    
//                }
//                if (isPolygon) {
////                    drawText(source, rect.tl(), "Polygon");
////                    LOGGER.info("POLIGONO => Contorno:{} - lados {} - rectangulo: {} - area: {}", contour, total, rect, contourArea);
//                }
            }
        }
        
//        LOGGER.info("contornos={} - hierarchy={}",contours.size(), count);
        
//		Imgcodecs.imwrite(targetPath, source);
		return r;
	}
	
	private void writeRectangles(String imagePath, List<Rect> rectangles, String destino) {
		Mat image = Imgcodecs.imread(imagePath);
		for (Rect rect: rectangles) {
			Imgproc.rectangle(image, rect.tl(), rect.br(), new Scalar(0, 0, 255), 3);
		}
		Imgcodecs.imwrite(destino, image);
	}
	
	private double angle(Point pt1, Point pt2, Point pt0) {
	    double dx1 = pt1.x - pt0.x;
	    double dy1 = pt1.y - pt0.y;
	    double dx2 = pt2.x - pt0.x;
	    double dy2 = pt2.y - pt0.y;
	    return (dx1*dx2 + dy1*dy2)/Math.sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
	}

	private void drawText(Mat image, Point ofs, String text) {
	    Imgproc.putText(image, text, ofs, Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 255));
	}

	
	@Test
	public void rectangleDetection2() {
		
		String imagePath = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs\\url_1_0-ECONT-MPV1-231106-08101191_cif.jpeg";
		String targetPath = "C:\\tmp\\cif_opencv.jpg";
		Mat imageMat = doRectangleDetection2(imagePath);
        
		Imgcodecs.imwrite(targetPath, imageMat);
		
		LOGGER.info("FIN");
	}

	private Mat doRectangleDetection2(String imagePath) {
		LOGGER.info("Cargando imagen {}", imagePath);
//		Mat imageMat = Imgcodecs.imread(imagePath);
		
//		Imgcodecs.imread("rectangle.jpg", Imgcodecs.CV_LOAD_IMAGE_ANYCOLOR);
        Mat imageMat = Imgcodecs.imread(imagePath);
        
     // Convertir la imagen a escala de grises
        Mat grayImage = new Mat();
        Imgproc.cvtColor(imageMat, grayImage, Imgproc.COLOR_BGR2GRAY);
//        Imgcodecs.imwrite("C:\\tmp\\imagen_gris.jpg", grayImage);
        
        
     // Aplicar un desenfoque para reducir el ruido
        Imgproc.GaussianBlur(grayImage, grayImage, new Size(5, 5), 0);
        
//        Imgcodecs.imwrite("C:\\tmp\\imagen_desenfoque.jpg", grayImage);
        
        // Detectar bordes usando el operador de Sobel
        Mat edges = new Mat();
        Imgproc.Sobel(grayImage, edges, CvType.CV_8U, 1, 0, 3, 1, 0, Core.BORDER_DEFAULT);
        
//        Imgcodecs.imwrite("C:\\tmp\\imagen_edges.jpg", edges);

        // Aplicar un umbral para convertir la imagen de bordes en una imagen binaria
        Mat threshold = new Mat();
        Imgproc.threshold(edges, threshold, 50, 255, Imgproc.THRESH_BINARY);
        
//        Imgcodecs.imwrite("C:\\tmp\\imagen_threshold.jpg", threshold);

        // Encontrar contornos en la imagen binaria
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(threshold, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Filtrar contornos para obtener solo rectángulos
        List<MatOfPoint> rectangles = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfPoint2f curve = new MatOfPoint2f(contour.toArray());
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            Imgproc.approxPolyDP(curve, approxCurve, Imgproc.arcLength(curve, true) * 0.02, true);
            
            if (approxCurve.total() == 4 && Imgproc.contourArea(contour) > 300) {
//            	LOGGER.info("approxCurve.total()={}    Y     Imgproc.contourArea(contour)={}", approxCurve.total(), Imgproc.contourArea(contour));
                rectangles.add(contour);
            }
        }

        // Dibujar rectángulos encontrados en la imagen original
//        for (MatOfPoint rectangle : rectangles) {
//            Imgproc.drawContours(imageMat, List.of(rectangle), 0, new Scalar(0, 255, 0), 2);
//        }
        Imgproc.drawContours(imageMat, rectangles, 0, new Scalar(0, 0, 255), 3);
		return imageMat;
	}
	
	@Test
	public void rectangleDetection2Concurrent() throws InterruptedException {
		
		String dir = "C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-PRJ-5812-Implementación CJ para captura de CIF y DOI apoderado_Tesseract\\cifs_signa";
		List<String> filter = Arrays.asList();
		
		ExecutorService x = Executors.newFixedThreadPool(2);
		int executorTimeout = 5; // minutes
		
		File dirF = new File(dir);
		File[] dirs = dirF.listFiles();
//		File[] files = {new File(dir+"url_179_0-ECONT-MPV1-231002-07552694_cif.pdf")};
		for (File d: dirs) {
			
			for (File file: d.listFiles()) {
				if (file.isDirectory() || file.getAbsolutePath().endsWith(".txt") || file.getAbsolutePath().endsWith(".pdf") || filter.contains(file.getName())  ) {
					continue;
				}
				
				LOGGER.debug("tratando {}",file.getName());
				
				x.submit(() -> {
					doRectangleDetection2(file.getAbsolutePath());
				});
				
			}
		}
		
		LOGGER.debug("");
		LOGGER.debug("");
		LOGGER.debug("");
		x.shutdown();
		x.awaitTermination(executorTimeout, TimeUnit.MINUTES);
		LOGGER.debug("FIN CONCURRENCIA");
		
	}
}
