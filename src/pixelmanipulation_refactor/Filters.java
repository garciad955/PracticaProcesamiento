/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelmanipulation_refactor;

import java.util.Arrays;
import org.jfree.ui.RefineryUtilities;
import org.opencv.core.Mat;

/**
 *
 * @author LINK5
 */
public class Filters {

    public static Mat addBrightness(byte bytes[], Mat image, int level) {

        double[] newSpec;

        if (level <= 0) {
            level = level * (-1);
        }

        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {

                newSpec = image.get(i, j);
                newSpec[0] = newSpec[0] + level;
                newSpec[1] = newSpec[1] + level;
                newSpec[2] = newSpec[2] + level;
                image.put(i, j, newSpec);

            }

        }

        return image;
    }

    public static Mat lessBrightness(byte bytes[], Mat image, int level) {

        double[] newSpec;

        if (level <= 0) {
            level = level * (-1);
        }

        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                newSpec = image.get(i, j);
                newSpec[0] = newSpec[0] - level;
                newSpec[1] = newSpec[1] - level;
                newSpec[2] = newSpec[2] - level;
                image.put(i, j, newSpec);

            }
        }

        return image;
    }

    public static Mat correccionGamma(Mat image, float level) {

        double[] newSpec;

        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                newSpec = image.get(i, j);

                newSpec[0] = newSpec[0] + Math.pow(newSpec[0], level);
                newSpec[1] = newSpec[1] + Math.pow(newSpec[1], level);
                newSpec[2] = newSpec[2] + Math.pow(newSpec[2], level);
                image.put(i, j, newSpec);

            }
        }

        return image;
    }

    public static Mat addcorreccionContraste(Mat image, float level) {

        double[] newSpec;
        int valor = 5;
        /* if (level <= 0) {
            valor = valor * (-1);
        }*/

        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                newSpec = image.get(i, j);

                for (int k = 0; k < 3; k++) {
                    if (newSpec[k] >= 127) {
                        newSpec[k] = newSpec[k] + valor;
                    } else {
                        newSpec[k] = newSpec[k] - valor;
                    }
                }

                image.put(i, j, newSpec);

            }
        }

        return image;
    }

    public static Mat lesscorreccionContraste(Mat image, float level) {

        double[] newSpec;
        int valor = 5;
        /*if (level <= 0) {
            valor = valor * (-1);
        }
         */
        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                newSpec = image.get(i, j);

                for (int k = 0; k < 3; k++) {
                    if (newSpec[k] < 127) {
                        newSpec[k] = newSpec[k] + valor;
                    } else {
                        newSpec[k] = newSpec[k] - valor;
                    }
                }

                image.put(i, j, newSpec);

            }
        }

        return image;
    }

    public static Mat negativo(Mat image) {

        double[] newSpec;
        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                newSpec = image.get(i, j);
                newSpec[0] = 255 - 1 - (newSpec[0]);
                newSpec[1] = 255 - 1 - (newSpec[1]);
                newSpec[2] = 255 - 1 - (newSpec[2]);

                image.put(i, j, newSpec);

            }
        }

        return image;
    }

    public static Mat binarizacion(Mat image) {

        double[] newSpec;
        double gray;
        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                newSpec = image.get(i, j);
                gray = (newSpec[2] * 0.3 + newSpec[1] * 0.59 + newSpec[0] * 0.11);
                // Blanco o negro
                int value = 0;
                if (gray > 120) {
                    value = 255;
                }
                // Asginar nuevo color
                newSpec[0] = value;
                newSpec[1] = value;
                newSpec[2] = value;

                image.put(i, j, newSpec);

            }
        }

        return image;
    }

    public static double[] avering(Mat imagen, int fila, int columna) {

        double[] colores = {0.0, 0.0, 0.0}, newSpec;

        for (int i = fila; i < fila + 3; i++) {
            for (int j = columna; j < columna + 3; j++) {
                newSpec = imagen.get(i, j);
                colores[0] = colores[0] + newSpec[0] / 9.0;
                colores[1] = colores[1] + newSpec[1] / 9.0;
                colores[2] = colores[2] + newSpec[2] / 9.0;

            }
        }

        return colores;
    }

    public static double[] gaussian(Mat imagen, int fila, int columna) {
        double[] colores = {0.0, 0.0, 0.0}, newSpec;
        int[][] kernnel = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        int row = 0, col = 0;
        for (int i = fila; i < fila + 3; i++) {
            for (int j = columna; j < columna + 3; j++) {
                newSpec = imagen.get(i, j);
                colores[0] = colores[0] + newSpec[0] * (kernnel[row][col] / 16.0);
                colores[1] = colores[1] + newSpec[1] * (kernnel[row][col] / 16.0);
                colores[2] = colores[2] + newSpec[2] * (kernnel[row][col] / 16.0);
                col++;
            }
            row++;
            col = 0;
        }
        return colores;
    }

    public static double[] media(Mat imagen, int fila, int columna) {
        double[] colores = {0.0, 0.0, 0.0}, mediaAzul = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, mediaVerde = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                mediaRojo = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, newSpec;
        int cont = 0;
        for (int i = fila; i < fila + 3; i++) {
            for (int j = columna; j < columna + 3; j++) {
                newSpec = imagen.get(i, j);
                mediaAzul[cont] = newSpec[0];
                mediaVerde[cont] = newSpec[1];
                mediaRojo[cont] = newSpec[2];

                cont++;

            }
        }
        Arrays.sort(mediaAzul);
        Arrays.sort(mediaVerde);
        Arrays.sort(mediaRojo);
        
        colores[0] = mediaAzul[4];
        colores[1] = mediaVerde[4];
        colores[2] = mediaRojo[4];
       
        return colores;
    }
    
}
