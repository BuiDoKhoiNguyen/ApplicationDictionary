package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

import javafx.scene.input.*;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javafx.embed.swing.SwingFXUtils;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class SnippingController implements Initializable {
    public static String ImageToText() throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:\\JavaProject\\DictionaryApplication\\lib\\Tess4J\\tessdata");
        String text = tesseract.doOCR(new File("D:\\test3.png"));
        return text;
    }

    @FXML
    ImageView display,imageView;

    @FXML
    public void takeScreenShot(ActionEvent event) {
        try {
            Robot robot = new Robot();
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage image = robot.createScreenCapture(rectangle);
            Image myImage = SwingFXUtils.toFXImage(image,null);
            display.setImage(myImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles() ||   event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
    }

    @FXML
    public void handleDrop(DragEvent event)  {
        if(event.getDragboard().hasFiles() || event.getDragboard().hasImage()) {
            try {
                imageView.setImage(new Image(new FileInputStream(event.getDragboard().getFiles().get(0))));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
