    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelmanipulation_refactor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

/**
 *
 * @author LINK5
 */
public class CustomImage {

    public CustomImage() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public File getImage() {

        JFileChooser chooser = new JFileChooser();
        chooser.showDialog(null, null);

        File selectedFile = chooser.getSelectedFile();
        //Mat newImage = Imgcodecs.imread(selectedFile.getPath());

        return selectedFile;
    }
    
    /*public String getImageLink() {

        JFileChooser chooser = new JFileChooser();
        chooser.showDialog(null, null);

        File selectedFile = chooser.getSelectedFile();
        
        
        
        return selectedFile.toString();
    }*/

    public byte[] toBytes(Mat image) {

        int bufferSize = (int) (image.total() * image.elemSize());
        byte[] buffer = new byte[bufferSize];
        return buffer;
    }

    public Image toImage(Mat image, byte[] bytes) {

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (image.channels() > 1 && image.channels() <= 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        if (image.channels() > 3) {
            type = BufferedImage.TYPE_4BYTE_ABGR;
        }

        image.get(0, 0, bytes); // get all the pixels
        BufferedImage toImage = new BufferedImage(image.width(), image.height(), type);
        final byte[] targetPixels = ((DataBufferByte) toImage.getRaster().
                getDataBuffer()).getData();
        System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);
        return toImage;
    }

    public void guardarImg(Mat image, int n) {
        String nombre = new String("imagenNew");
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showDialog(null, null);

        if (fileChooser.getSelectedFile().getName() == (nombre + n + ".jpg")) {
            imwrite(fileChooser.getSelectedFile().toString() + "\\" + nombre + n + ".jpg", image);
        } else {
            n++;
            imwrite(fileChooser.getSelectedFile().toString() + "\\" + nombre + n + ".jpg", image);
        }
    }

}
