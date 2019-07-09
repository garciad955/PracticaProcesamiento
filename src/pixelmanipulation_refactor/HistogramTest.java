/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelmanipulation_refactor;

/**
 *
 * @author garci
 */

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.opencv.core.Mat;

public class HistogramTest {
    private static Color COLOR_SERIE_1 = Color.BLUE;
    private static Color COLOR_SERIE_2 = Color.GREEN;
    private static Color COLOR_SERIE_3 = Color.RED;
    private static Color COLOR_RECUADROS_GRAFICA = new Color(31, 87, 4);
    private static Color COLOR_FONDO_GRAFICA = Color.white;
    
    public static JFreeChart histograma(byte bytes[], Mat image, boolean[] colores) {
        double[] newSpec;
        int tamaño = image.rows()*image.cols();
        double[] red = new double[tamaño];
        double[] green = new double[tamaño];
        double[] blue = new double[tamaño];
        int posicion = 0;
        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                newSpec = image.get(i, j);
                
                red[posicion] = newSpec[2];
                blue[posicion] = newSpec[0];
                green[posicion] = newSpec[1];
                posicion = posicion + 1;
            }
        }
        
        HistogramDataset dataset = new HistogramDataset();
        if(colores[0]){
            dataset.addSeries("Red", red, tamaño,0,255); 
            
        }
        if(colores[1]){
            dataset.addSeries("Blue", blue,tamaño,0,255);
        }
        if(colores[2]){
            dataset.addSeries("Green", green, tamaño,0,255); 
        }
        
        JFreeChart chart = ChartFactory.createHistogram("Histograma imagen",null,null,dataset,PlotOrientation.VERTICAL,true,false,false);
        
        return chart;   
    }
}
