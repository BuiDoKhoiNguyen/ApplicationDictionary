package Controllers;

import API.ImageToTextAPI;
import API.SpeechToTextAPI;
import API.TextToSpeechAPI;
import API.TranslateAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sourceforge.tess4j.TesseractException;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static API.SpeechToTextAPI.RECORD_PATH;

public class TranslateController extends TaskControllers implements Initializable {

    String languageFrom = "";
    String languageTo = "vi";
    String nameFrom;
    String speakFrom;
    String nameTo;
    String speakTo;

    @FXML
    private TextArea area1;
    @FXML
    private TextField area2;
    @FXML
    private TextField text1;
    @FXML
    private TextField text2;
    @FXML
    private Button fromAutoDetect, fromEng, fromVie, fromKor, fromFr;
    @FXML
    private Button toVie, toEng, toKor, toFr, toChina;
    @FXML
    private Button scan,mic;
    private File selectedFile;
    private boolean isRecording = false;
    private TargetDataLine line;

    public void resetStyleLangFrom() {
        fromAutoDetect.getStyleClass().removeAll("active");
        fromEng.getStyleClass().removeAll("active");
        fromVie.getStyleClass().removeAll("active");
        fromKor.getStyleClass().removeAll("active");
        fromFr.getStyleClass().removeAll("active");
    }

    public void fromDetect() {
        resetStyleLangFrom();
        fromAutoDetect.getStyleClass().add("active");
        languageFrom = "";
        text1.setText("Phát hiện n.ngữ");
        nameFrom = "Linda";
        speakFrom = "en-gb";
    }

    @FXML
    void fromEng() {
        resetStyleLangFrom();
        fromEng.getStyleClass().add("active");
        languageFrom = "en";
        text1.setText("Tiếng Anh");
        nameFrom = "Linda";
        speakFrom = "en-gb";
    }

    @FXML
    void fromVie() {
        resetStyleLangFrom();
        fromVie.getStyleClass().add("active");
        text1.setText("Tiếng Việt");
        languageFrom = "vi";
        nameFrom = "Chi";
        speakFrom = "vi-vn";
    }

    @FXML
    void fromKor() {
        resetStyleLangFrom();
        fromKor.getStyleClass().add("active");
        text1.setText("Tiếng Hàn");
        languageFrom = "ko";
        nameFrom = "Nari";
        speakFrom = "ko-kr";
    }

    @FXML
    void fromFr() {
        resetStyleLangFrom();
        fromFr.getStyleClass().add("active");
        text1.setText("Tiếng Pháp");
        languageFrom = "fr";
        nameFrom = "Bette";
        speakFrom = "fr-fr";
    }


    public void resetStyleLangTo() {
        toVie.getStyleClass().removeAll("active");
        toEng.getStyleClass().removeAll("active");
        toKor.getStyleClass().removeAll("active");
        toFr.getStyleClass().removeAll("active");
        toChina.getStyleClass().removeAll("active");
    }

    @FXML
    void toVie() throws IOException {
        resetStyleLangTo();
        toVie.getStyleClass().add("active");
        text2.setText("Tiếng Việt");
        languageTo = "vi";
        nameTo = "Chi";
        speakTo = "vi-vn";
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void toEng() throws IOException {
        resetStyleLangTo();
        toEng.getStyleClass().add("active");
        text2.setText("Tiếng Anh");
        languageTo = "en";
        nameTo = "Linda";
        speakTo = "en-gb";
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void toKor() throws IOException {
        resetStyleLangTo();
        toKor.getStyleClass().add("active");
        text2.setText("Tiếng Hàn");
        languageTo = "ko";
        nameTo = "Nari";
        speakTo = "ko-kr";
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void toFr() throws IOException {
        resetStyleLangTo();
        toFr.getStyleClass().add("active");
        text2.setText("Tiếng Pháp");
        languageTo = "fr";
        nameTo = "Bette";
        speakTo = "fr-fr";
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void toChina() throws IOException {
        resetStyleLangTo();
        toChina.getStyleClass().add("active");
        languageTo = "zh";
        text2.setText("Tiếng Trung");
        nameTo = "Luli";
        speakTo = "zh-cn";
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void translate() throws IOException {
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void speak1() throws Exception {
        TextToSpeechAPI.Name = nameFrom;
        TextToSpeechAPI.language = speakFrom;
        if (!Objects.equals(area1.getText(), "")) {
            TextToSpeechAPI.speakWord(area1.getText());
        }
    }

    @FXML
    void speak2() throws Exception {
        TextToSpeechAPI.Name = nameTo;
        TextToSpeechAPI.language = speakTo;
        if (!Objects.equals(area2.getText(), "")) {
            TextToSpeechAPI.speakWord(area2.getText());
        }
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
                area1.setText(extractedText);

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

    @FXML
    public void toggleRecording(ActionEvent e) throws IOException {
        if (!isRecording) {
            startRecording();
            mic.getStyleClass().add("recording");
        } else {
            stopRecording();
            mic.getStyleClass().remove("recording");
        }
        isRecording = !isRecording;
    }

    public void startRecording() {
        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Microphone is not supported");
                return;
            }

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("Recording...");
            new Thread(() -> {
                AudioInputStream audioStream = new AudioInputStream(line);
                File outputFile = new File(RECORD_PATH);
                try {
                    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() throws IOException {
        if (line != null) {
            line.stop();
            line.close();
            System.out.println("Recording stopped");
        }
        area1.setText(SpeechToTextAPI.speechToTextAPI());
        translate();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fromAutoDetect.getStyleClass().add("active");
        toVie.getStyleClass().add("active");

        text1.setText("Phát hiện n.ngữ");
        area1.setText("");
        nameFrom = "Linda";
        speakFrom = "en-gb";
        languageFrom = "";

        text2.setText("Tiếng Việt");
        nameTo = "Chi";
        speakTo = "vi-vn";
        languageTo = "vi";
    }

}
