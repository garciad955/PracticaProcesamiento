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
import org.opencv.core.Mat;

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
                    escala  = 1;
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
                
                

                rotaImagen.put((int) x,(int) y, newSpec);

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
}
