package com.nebur.opencvTest;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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

public class OpenCvScanerRectangleTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenCvScanerRectangleTest.class);
	
	public OpenCvScanerRectangleTest() {
		LOGGER.info("Cargando librerias openCV...");
		OpenCV.loadShared();
		LOGGER.info("librerias cargadas");
	}
	
	@Test
//	https://stackoverflow.com/questions/38748508/java-opencv-rectangle-detection-with-hough-transform
	public void rectangleDetectionOne() throws IOException {
		String imagePath = "C:\\Users\\67760769\\OneDrive - GFI\\Images\\Pellicule\\scanner2024\\ECONT-PANGEA-240509-11048260-00-doi1.jpg";
		String targetPath = "C:\\tmp\\doi_opencv.jpg";

		// borramos doi_opencv anterior
		File f = new File(targetPath);
		f.delete();
//		rectangleDetection(imagePath, targetPath, 100, 300);
		List<Rect> rectangles = rectangleDetection(imagePath, 25, 50, true);
		writeRectangles(imagePath, rectangles, targetPath);
		Desktop.getDesktop().open(new File(targetPath));
		LOGGER.info("FIN");
	}
	
	@Test
	public void rectangleDetectionDir() {
		String imagePath = "C:\\Users\\67760769\\OneDrive - GFI\\Images\\Pellicule\\scanner2024";
		String targetPath = "C:\\tmp\\";

		// borramos doi_opencv anterior
		File fDir = new File(imagePath); 
		for ( File file: fDir.listFiles() ) {
			if (!file.isDirectory() && file.getName().endsWith(".jpg")) {
				List<Rect> rectangles1 = rectangleDetection(file.getAbsolutePath(), 25, 50, true, new Size(5,5), 0, 0, Core.BORDER_DEFAULT);
				List<Rect> rectangles2 = rectangleDetection(file.getAbsolutePath(), 25, 50, true, new Size(3,9), 4, 0, Core.BORDER_DEFAULT);
				
				writeRectangles(file.getAbsolutePath(), rectangles1,rectangles2, targetPath+file.getName());
			}
		}
		LOGGER.info("FIN");
	}
	
	@Test
	public void rectangleDetectionOne_Threshold() {
		String imagePath = "C:\\Users\\67760769\\OneDrive - GFI\\Images\\Pellicule\\scanner2024\\ECONT-PANGEA-240509-11048260-00-doi1.jpg";
		String targetPath = "C:\\tmp\\";
		
        for (int i=0 ; i < 500; i=i+25) {
        	for (int j=0 ; j < 500; j=j+25) {
        		List<Rect> r = rectangleDetection(imagePath,i,j,true);
        		if (!r.isEmpty()) {
        			writeRectangles(imagePath, r, targetPath + "borderDetection_"+i+"_"+j+".jpg");
        		}
        	}
        }
        
	}
	
	/**
	 * sigma 3, 4 ,5 o 6
	 * size(3,7) , size(3,9), size(3,11)
	 * 
	 * @throws IOException
	 */
	@Test
//	https://stackoverflow.com/questions/38748508/java-opencv-rectangle-detection-with-hough-transform
	public void rectangleDetectionOneGaussianBlur() throws IOException {
		String imagePath = "C:\\Users\\67760769\\OneDrive - GFI\\Images\\Pellicule\\scanner2024\\ECONT-PANGEA-240509-11048260-00-doi1.jpg";
		String targetPath = "C:\\tmp\\";

		
		for (int sigmaX=-10;sigmaX<11;sigmaX++) {
			for (int i=1;i<12;i=i+2) {
				for (int j=1;j<12;j=j+2) {
					try {
						List<Rect> rectangles = rectangleDetection(imagePath, 25, 50, true, new Size(i,j), sigmaX, 0, Core.BORDER_DEFAULT);
						if (!rectangles.isEmpty()) {
							String aux = sigmaX<0?"menos_"+String.valueOf(-sigmaX):String.valueOf(sigmaX);
							writeRectangles(imagePath, rectangles, targetPath+"doiOpencv_gaussian_"+aux+"_"+i+"_"+j+".jpg");
						}
					} catch (Exception e) {
						LOGGER.error("Error gaussianBlur",e);
					}
				}
			}
		}
		
		
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
		return rectangleDetection(imagePath, threshold1, threshold2, log, new Size(5, 5), 0, 0, Core.BORDER_DEFAULT);
	}
	
	private List<Rect> rectangleDetection(String imagePath, int threshold1, int threshold2, boolean log, Size size, int sigmaX, int sigmaY, int borderType) {
		if (log)
			LOGGER.info("threshold1={} - threshold2={} - imagen:{}", threshold1, threshold2, imagePath);
		ArrayList<Rect> r = new ArrayList<>();
//		Mat imageMat = Imgcodecs.imread(imagePath);
		
//		Imgcodecs.imread("rectangle.jpg", Imgcodecs.CV_LOAD_IMAGE_ANYCOLOR);
        Mat source = Imgcodecs.imread(imagePath);
        double imageArea = source.rows()* source.cols();
        
        // Convertir a escala de grises
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.cvtColor(source, destination, Imgproc.COLOR_RGB2GRAY);
//        Imgcodecs.imwrite("c:\\tmp\\postColor.jpg", destination);
        
        // Aplicar un desenfoque para reducir el ruido
        Imgproc.GaussianBlur(destination, destination, size, sigmaX , sigmaY, borderType);
//        Imgcodecs.imwrite("c:\\tmp\\postGaussianBlur.jpg", destination);

        // Aplicar la detección de bordes de Canny
        Imgproc.Canny(destination, destination, threshold1, threshold2);
//        Imgcodecs.imwrite("c:\\tmp\\postCanny.jpg", destination);
        
//        Imgproc.equalizeHist(destination, destination);
//        Imgproc.GaussianBlur(destination, destination, new Size(5, 5), 0, 0, Core.BORDER_DEFAULT);

        
        //Imgproc.adaptiveThreshold(destination, destination, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 40);
//        Imgproc.threshold(destination, destination, 0, 255, Imgproc.THRESH_BINARY);

//        Imgcodecs.imwrite("c:\\tmp\\postCanny.jpg", destination);
        
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(destination, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        
        MatOfPoint2f matOfPoint2f = new MatOfPoint2f();

        int count=1;
        for (MatOfPoint contour: contours) {
//        	for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
//        	count++;
//            MatOfPoint contour = contours.get(idx);
            Rect rect = Imgproc.boundingRect(contour);
            

            matOfPoint2f.fromList(contour.toList());
            MatOfPoint2f approxCurve = new MatOfPoint2f();
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
//            if (total >= 4 && total <= 6) {
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
//                	double contourArea = Imgproc.contourArea(contour);
                	double contourArea = rect.width * rect.height ;
//                	r.add(rect);
//                	LOGGER.info("RECTANGULO => ratio:{} - lados:{} - rectangulo:{} - area: {}", ratio, total, rect, contourArea);
                	if ( 
                			ratio > 0.30 &&
//                			&& ratio < 0.30 && 
//                			contourArea > imageArea*0.03
                			rect.width > 40
                			&& rect.height > 40
//                			true
                			) {
                		if (log)
                			LOGGER.info("{} RECTANGULO => ratio:{} - lados:{} - rectangulo:{} - area:{} - imageArea:{} - umbralArea:{}", count++,ratio, total, rect, contourArea, imageArea, imageArea*0.001);
                		r.add(rect);
//                		Imgproc.rectangle(source, rect.tl(), rect.br(), new Scalar(0, 0, 255), 3);
                	}
                    
//                }
//                if (isPolygon) {
////                    drawText(source, rect.tl(), "Polygon");
////                    LOGGER.info("POLIGONO => Contorno:{} - lados {} - rectangulo: {} - area: {}", contour, total, rect, contourArea);
//                }
//            }
        }
        
//        LOGGER.info("contornos={} - hierarchy={}",contours.size(), count);
        
//		Imgcodecs.imwrite(targetPath, source);
		return r.stream().max((x,y)-> x.width*x.height - y.width*y.height ).map(x->Arrays.asList(x)).orElse(new ArrayList<>());
//        return r;
	}
	
	private void writeRectangles(String imagePath, List<Rect> rectangles, String destino) {
		Mat image = Imgcodecs.imread(imagePath);
		int i=1;
		for (Rect rect: rectangles) {
			Imgproc.rectangle(image, rect.tl(), rect.br(), new Scalar(0, 0, 255), 3);
			drawText(image, rect.tl(), String.valueOf(i++)); // pintar el orden
		}
		Imgcodecs.imwrite(destino, image);
	}
	
	private void writeRectangles(String imagePath, List<Rect> rectangles1, List<Rect> rectangles2, String destino) {
		Mat image = Imgcodecs.imread(imagePath);
		for (Rect rect: rectangles1) {
			Imgproc.rectangle(image, rect.tl(), rect.br(), new Scalar(0, 0, 255), 3);
		}
		for (Rect rect: rectangles2) {
			rect.tl().x = rect.tl().x +3;
			rect.tl().y = rect.tl().y +3;
			rect.br().x = rect.br().x +3;
			rect.br().y = rect.br().y +3;
			Imgproc.rectangle(image, rect.tl(), rect.br(), new Scalar(0,255, 0 ), 3);
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
	
}
