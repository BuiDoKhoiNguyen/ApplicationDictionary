package API;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.*;

import java.io.File;

public class ImageToTextAPI {
    public static String ImageToText(String imagePath) throws TesseractException {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("lib\\Tess4J\\tessdata");
        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            String text = tesseract.doOCR(imageFile);
            return text;
        } else {
            System.err.println("Image file does not exist: " + imagePath);
            return "";
        }
    }
}
