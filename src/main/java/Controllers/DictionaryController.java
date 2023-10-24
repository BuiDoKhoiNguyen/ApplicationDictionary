package Controllers;


import DictionaryApplication.*;
import DictionaryApplication.Dictionary;
import com.darkprograms.speech.translator.GoogleTranslate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import DictionaryApplication.Dictionary;
import DictionaryApplication.NewDictionaryManagement;
import DictionaryApplication.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.web.WebView;



public class DictionaryController implements Initializable {
    @FXML
    private TextField wordInput, textFieldParagraph;
    @FXML
    private TextArea printTranslation, textAreaParagraph;
    @FXML
    private Button translate, translateParagraph;


    @FXML
    private WebView webView;

    @FXML
    private ListView<String> listView;

    private Dictionary dictionary = new Dictionary();

    private static final String IN_PATH = "C:\\Users\\User\\IdeaProjects\\BTLOOP\\DictionaryApplication\\data\\V_E.txt";

    public DictionaryController() {
        NewDictionaryManagement.loadDataFromHTMLFile(dictionary,IN_PATH);
    }



    public void enterWord(KeyEvent e){
            String keyword = wordInput.getText().toLowerCase();
            ObservableList<String> matchingWords = FXCollections.observableArrayList();

            for (Map.Entry<String, Word> entry : dictionary.entrySet()){
                String englishWord = entry.getKey().toLowerCase();
                if (englishWord.startsWith(keyword)) {
                    matchingWords.add(entry.getKey());
                }
            }
            listView.setItems(matchingWords);

            listView.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            String wordExplain = dictionary.get(newValue).getWordExplain();
                            webView.getEngine().loadContent(wordExplain, "text/html");
                        }
                    }
            );
    }

    public void enterWord2(ActionEvent e) {
        try {
            String wordTarget = wordInput.getText();
            String wordExplain = dictionary.get(wordTarget).getWordExplain();

            webView.getEngine().loadContent(wordExplain, "text/html");
        } catch (InputMismatchException error) {
            webView.getEngine().loadContent("Invalid input. Please enter a string.");
        } catch (Exception error) {
            webView.getEngine().loadContent("Error.");
        }
    }

    public void translatePara(ActionEvent e2){
        String textToTranslate = textFieldParagraph.getText();
        try {
            String translatedText = GoogleTranslate.translate("en", "vi", textToTranslate);
            textAreaParagraph.setText(translatedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}