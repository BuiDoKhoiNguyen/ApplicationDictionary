package Controllers;

import Base.TranslateAPI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    public void setToEN() throws IOException {
        refreshButtonTo();
        toEN.setSelected(true);
        languageTo = "en";
        if (!inputField.getText().isEmpty()) {
            translate();
        }
    }

    @FXML
    public void setToVN() throws IOException {
        refreshButtonTo();
        toVN.setSelected(true);
        languageTo = "vi";
        if (!inputField.getText().isEmpty()) {
            translate();
        }
    }

    @FXML
    public void setToFR() throws IOException {
        refreshButtonTo();
        toFR.setSelected(true);
        languageTo = "fr";
        if (!inputField.getText().isEmpty()) {
            translate();
        }
    }

    @FXML
    public void setToSimplifiedCN() throws IOException {
        refreshButtonTo();
        toSimplifiedCN.setSelected(true);
        languageTo = "zh";
        if (!inputField.getText().isEmpty()) {
            translate();
        }
    }

    @FXML
    public void translate() throws IOException {
        String inputText = inputField.getText();
        String translatedText = TranslateAPI.googleTranslate(languageFrom, languageTo, inputText);
        translationField.getEngine().loadContent(translatedText);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFromEN();
        try {
            setToVN();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
