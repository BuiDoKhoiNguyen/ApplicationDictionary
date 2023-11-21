package Controllers;

import API.ImageToTextAPI;
import API.TranslateAPI;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;


public class TranslateController extends TaskControllers implements Initializable {
    @FXML
    private TextField inputField;
    @FXML
    private WebView translationField;

    @FXML
    private ToggleButton fromEN;
    @FXML
    private ToggleButton fromVN;
    @FXML
    private ToggleButton fromFR;
    @FXML
    private ToggleButton fromAutoDetect;

    @FXML
    private ToggleButton toVN;
    @FXML
    private ToggleButton toEN;
    @FXML
    private ToggleButton toFR;
    @FXML
    private ToggleButton toSimplifiedCN;
    @FXML
    private Button cancelButton;
    @FXML
    private Button scan;
    private File selectedFile;
    private String languageFrom;
    private String languageTo;

    private void refreshButtonFrom() {
        fromEN.setSelected(false);
        fromVN.setSelected(false);
        fromFR.setSelected(false);
        fromAutoDetect.setSelected(false);
    }

    private void refreshButtonTo() {
        toEN.setSelected(false);
        toVN.setSelected(false);
        toFR.setSelected(false);
        toSimplifiedCN.setSelected(false);
    }

    @FXML
    public void setFromEN() {
        refreshButtonFrom();
        fromEN.setSelected(true);
        languageFrom = "en";
    }

    @FXML
    public void setFromVN() {
        refreshButtonFrom();
        fromVN.setSelected(true);
        languageFrom = "vi";
    }

    @FXML
    public void setFromFR() {
        refreshButtonFrom();
        fromFR.setSelected(true);
        languageFrom = "fr";
    }

    @FXML
    public void setFromAutoDetect() {
        refreshButtonFrom();
        fromAutoDetect.setSelected(true);
        languageFrom = "";
    }

    public void setToEN() throws IOException, TesseractException {
        refreshButtonTo();
        toEN.setSelected(true);
        languageTo = "en";
        if (!inputField.getText().isEmpty()) {
            translate();
        }
    }

    @FXML
    public void setToVN() throws IOException, TesseractException {
        refreshButtonTo();
        toVN.setSelected(true);
        languageTo = "vi";
        if (!inputField.getText().isEmpty()) {
            translate();
        }
    }

    @FXML
    public void setToFR() throws IOException, TesseractException {
        refreshButtonTo();
        toFR.setSelected(true);
        languageTo = "fr";
        if (!inputField.getText().isEmpty()) {
            translate();
        }
    }

    @FXML
    public void setToSimplifiedCN() throws IOException, TesseractException {
        refreshButtonTo();
        toSimplifiedCN.setSelected(true);
        languageTo = "zh";
        if (!inputField.getText().isEmpty()) {
            translate();
        }
    }

    @FXML
    public void translate() throws IOException, TesseractException {
        String inputText = inputField.getText();

        if (inputText.isEmpty() && selectedFile != null) {
            try {
                inputText = ImageToTextAPI.ImageToText(selectedFile.getAbsolutePath());
            } catch (TesseractException e) {
                e.printStackTrace();
                return;
            }
        }
        String translatedText = TranslateAPI.googleTranslate(languageFrom, languageTo, inputText);
        translationField.getEngine().loadContent(translatedText);
    }

    @FXML
    public void cancelButtonOnAction(ActionEvent e) {
        ProfileController.recordAppUsage();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        exit(0);
    }

    @FXML
    public void scanButtonOnAction(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.setInitialDirectory(new File("D:/"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );

        Stage stage = (Stage) scan.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                String extractedText = ImageToTextAPI.ImageToText(selectedFile.getAbsolutePath());
                System.out.println(extractedText);
                inputField.setText(extractedText);

                translate();
            } catch (TesseractException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            System.out.println("No file has been selected");
        }
    }

    public static String ImageToText(String imagePath) throws TesseractException {
        Tesseract tesseract = new Tesseract();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFromEN();
        try {
            setToVN();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }
}
