package es.ocr.pruebas.pruebasocr;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenCvExtractor implements Iterable<Rectangle>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenCvExtractor.class);
	
	private final Mat source;
	private final double imageArea;
	
	public OpenCvExtractor(BufferedImage bufImag) throws IOException {
		this.source = bufferedImage2Mat(bufImag); 
        this.imageArea = source.rows()* source.cols();
	}
	
	@Override
	public Iterator<Rectangle> iterator() {
		return new MiIterator();
	}
	
	private List<Rectangle> toRectangle(List<Rect> rects) {
		return rects.stream().map(x->toRectangle(x)).collect(Collectors.toList());
	}
	
	private Rectangle toRectangle(Rect rect) {
		return new Rectangle(rect.x, rect.y,  rect.width, rect.height);
	}
	
	private Set<Rectangle> rectangleDetection(int threshold1, int threshold2, boolean log) {
//		if (log)
//			LOGGER.info("threshold1={} - threshold2={} - imagen:{}", threshold1, threshold2, imagePath);
		HashSet<Rectangle> r = new HashSet<>();
//		Mat imageMat = Imgcodecs.imread(imagePath);
		
//		Imgcodecs.imread("rectangle.jpg", Imgcodecs.CV_LOAD_IMAGE_ANYCOLOR);
//        Mat source = Imgcodecs.imread(imagePath);
		
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
                		r.add(toRectangle(rect));
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

	private Mat bufferedImage2Mat(BufferedImage image) throws IOException {
		Mat mat = null;
		
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    ImageIO.write(image, "jpg", byteArrayOutputStream);
	    byteArrayOutputStream.flush();
//	    return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
	    mat = Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);
		
		if (mat.channels() == 1) {
			if (image.getRaster().getDataBuffer() instanceof DataBufferByte) {
				mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
				byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
				mat.put(0, 0, pixels);
			} else if (image.getRaster().getDataBuffer() instanceof DataBufferInt) {
				DataBufferInt data = (DataBufferInt) image.getRaster().getDataBuffer();
				 
				mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
				mat.put(0, 0, data.getData());
	//			source = bufferedImage2Mat(bufImag);
//				LOGGER.info("image type: {} - mat type:{}", image.getType(), mat.type());
			}		
			LOGGER.debug("new channels: {}" , mat.channels());
		}
		
		return mat;
		
		
		
	}

	
	class MiIterator implements Iterator<Rectangle>{
		
		private static final int THRESHOLD_25_25 = 1;
//		private static final int THRESHOLD_50_100 = 2;
//		private static final int THRESHOLD_100_300 = 3;
		
		private int currentThreshold = 0;
		
		private HashSet<Rectangle> totalRectangles = new HashSet<>();
		private Set<Rectangle> currentRectangles;
		
		private Iterator<Rectangle> currentIterator;

		public MiIterator() {
			newRectangleThreshold();
		}
		
		private void newRectangleThreshold() {
			currentThreshold++;
			switch (currentThreshold) {
			case THRESHOLD_25_25:
//				LOGGER.debug("THRESHOLD_25_25");
				currentRectangles = rectangleDetection(25,25, false);
				LOGGER.debug("THRESHOLD_25_25  - {} rectangulos: {}", currentRectangles.size(), currentRectangles);
				currentIterator = currentRectangles.iterator();
				break;
//			case THRESHOLD_50_100:
//				LOGGER.debug("THRESHOLD_50_100");
//				currentRectangles = rectangleDetection(50,100, false);
//				int totalRect = currentRectangles.size();
//				currentRectangles.removeAll(totalRectangles);
//				LOGGER.debug("THRESHOLD_50_100 - {} rectangulos - {} resta : {}", totalRect, currentRectangles.size(), currentRectangles);
//				currentIterator = currentRectangles.iterator();
//				break;
//			case THRESHOLD_100_300:
//				LOGGER.debug("THRESHOLD_100_300");
//				currentRectangles = rectangleDetection(100,300, false);
//				int totalRect2 = currentRectangles.size();
//				currentRectangles.removeAll(totalRectangles);
//				LOGGER.debug("THRESHOLD_100_300 - {} rectangulos - {} resta : {}", totalRect2, currentRectangles.size(), currentRectangles);
//				currentIterator = currentRectangles.iterator();
//				break;
			default:
				currentRectangles = null;
				currentIterator = null;
				break;
			}
			if (currentRectangles != null) {
				totalRectangles.addAll(currentRectangles);
			}
			
		}
		
		@Override
		public boolean hasNext() {
			if (currentIterator == null) {
				return false;
			}
			if (currentIterator.hasNext()) {
				return true;
			}
			newRectangleThreshold();
			return hasNext();
		}

		@Override
		public Rectangle next() {
			if (hasNext()) {
				return currentIterator.next();
			}
			throw new NoSuchElementException("No more openCv rectangles");
		}
		
	}

}
