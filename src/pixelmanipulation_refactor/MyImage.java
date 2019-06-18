/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelmanipulation_refactor;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 *
 * @author LINK5
 */
public class MyImage {
    
    private static Mat myImage;
            
    private MyImage(){}
    
    public static Mat create(){
    
          myImage =  new Mat(2, 2, CvType.CV_8UC3);
          int totalBytes = (int)(myImage.total() * myImage.elemSize());
          byte buffer[] = new byte[totalBytes];
          
          for(int i=0;i<totalBytes;i++){
              buffer[i]=(byte) (Math.random() * 255);
          }
          myImage.put(0, 0, buffer);
         return myImage;
    
    }
}
