/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelmanipulation_refactor;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author garci
 */
public class imageRotate {

    public static Mat rotationImage(Mat imagen, double teta, float escala, int desplazamientoX, int desplazamientoY) {
        Mat rotaImagen = new Mat(imagen.rows(), imagen.cols(), imagen.type());
        /* 
         */
        double[] newSpec;

        double x, y, angulito = Math.toRadians(teta);
        System.out.println(angulito);
        for (int i = 0; i < imagen.rows(); i++) {
            for (int j = 0; j < imagen.cols(); j++) {
                newSpec = imagen.get(i, j);
                x = i;
                y = j;
                if (teta != 0 && teta != 360) {
                    x = (i * Math.cos(angulito)) - (j * Math.sin(angulito));
                    y = (i * Math.sin(angulito)) + (j * Math.cos(angulito));
                }
                if (escala == 0) {
                    escala = 1;
                }

                x = x * escala;
                y = y * escala;

                x = x + desplazamientoY;
                y = y + desplazamientoX;

                if (x < 0) {
                    x = x * -1;
                }
                if (y < 0) {
                    y = y * -1;
                }

                rotaImagen.put((int) x, (int) y, newSpec);

            }
        }
        for (int i = 0; i < rotaImagen.rows(); i++) {
            for (int j = 0; j < rotaImagen.cols(); j++) {
                newSpec = rotaImagen.get(i, j);
                imagen.put(i, j, newSpec);
            }

        }
        return imagen;
    }

    public static void generarBarras() {
        double[] vector = {50, 150, 70};
        try {
//           DefaultCategoryDataset ds = new DefaultCategoryDataset();
//           
//           ds.addValue(10, "cc", "whatever");
//           ds.addValue(20, "yeah", "whatever");
            HistogramDataset hs = new HistogramDataset();
            hs.setType(HistogramType.RELATIVE_FREQUENCY);
            hs.addSeries("", vector, vector.length);
            JFreeChart chart = ChartFactory.createHistogram("", null, null, hs, PlotOrientation.VERTICAL, true, true, false);
            ChartFrame frame = new ChartFrame("", chart);
            frame.pack();
            frame.setVisible(true);
            //JFreeChart jf = ChartFactory.createBarChart3D("alum", "nn", "eda", hs, PlotOrientation.VERTICAL, false, false, false);
//           ChartFrame f = new ChartFrame("edades", jf);
//           f.setSize(1000, 600);
//           f.setLocationRelativeTo(null);
//           f.setVisible(true);
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    public static Mat doSobel(Mat frame) {
        // init
        Mat grayImage = new Mat();
        Mat detectedEdges = new Mat();
        int ddepth = CvType.CV_16S;
        Mat grad_x = new Mat();
        Mat grad_y = new Mat();
        Mat abs_grad_x = new Mat();
        Mat abs_grad_y = new Mat();

        

        // convert to grayscale
        Imgproc.cvtColor(frame, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Gradient X
        // Imgproc.Sobel(grayImage, grad_x, ddepth, 1, 0, 3, scale,
        // this.threshold.getValue(), Core.BORDER_DEFAULT );
        Imgproc.Sobel(grayImage, grad_x, ddepth, 1, 0);
        Core.convertScaleAbs(grad_x, abs_grad_x);

        // Gradient Y
        // Imgproc.Sobel(grayImage, grad_y, ddepth, 0, 1, 3, scale,
        // this.threshold.getValue(), Core.BORDER_DEFAULT );
        Imgproc.Sobel(grayImage, grad_y, ddepth, 0, 1);
        Core.convertScaleAbs(grad_y, abs_grad_y);

        // Total Gradient (approximate)
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, detectedEdges);
        // Core.addWeighted(grad_x, 0.5, grad_y, 0.5, 0, detectedEdges);

        return detectedEdges;

    }
}
