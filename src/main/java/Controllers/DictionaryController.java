package Controllers;

import DictionaryApplication.*;
import com.darkprograms.speech.translator.GoogleTranslate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;

import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.ResourceBundle;


public class DictionaryController implements Initializable {
    @FXML
    private TextField wordInput, textFieldParagraph;
    @FXML
    private TextArea printTranslation, textAreaParagraph;
    @FXML
    private Button translate, translateParagraph;

    @FXML
    private ListView<String> listView;

    private Dictionary dictionary = new Dictionary();

    private static final String IN_PATH = "C:\\Users\\User\\IdeaProjects\\BTLOOP\\DictionaryApplication\\data\\dictionaries.txt";

    public DictionaryController() {
        NewDictionaryManagement.loadFromFile(dictionary,IN_PATH);
    }
    /*public void enterWord(ActionEvent e) {
        try {
            String wordTarget = wordInput.getText();
            String wordExplain = NewDictionaryManagement.lookupWord(dictionary,wordTarget);
            printTranslation.setText(wordExplain);
        } catch (InputMismatchException error) {
            printTranslation.setText("Invalid input. Please enter a string.");
        } catch (Exception error) {
            printTranslation.setText("error");
        }
    }*/

    public void enterWord(KeyEvent e){
            String keyword = wordInput.getText().toLowerCase(); // Chuyển từ khóa về chữ thường
            ObservableList<String> matchingWords = FXCollections.observableArrayList();

            // Lọc danh sách từ trong TreeMap của bạn
            for (Map.Entry<String, Word> entry : dictionary.entrySet()){
                String englishWord = entry.getKey().toLowerCase();
                if (englishWord.startsWith(keyword)) {
                    matchingWords.add(entry.getKey());
                }
            }

            // Cập nhật ListView với danh sách từ tìm thấy
            listView.setItems(matchingWords);

            listView.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            // Lấy nghĩa tiếng Việt từ TreeMap dựa trên từ tiếng Anh được chọn
                            String vietnameseMeaning = dictionary.get(newValue).getWordExplain();

                            // Hiển thị nghĩa tiếng Việt trong WebView
                            printTranslation.setText(vietnameseMeaning);
                        }
                    }
            );
    }

    public void enterWord2(ActionEvent e) {
        try {
            String wordTarget = wordInput.getText();
            String wordExplain = NewDictionaryManagement.lookupWord(dictionary, wordTarget);
            printTranslation.setText(wordExplain);
        } catch (InputMismatchException error) {
            printTranslation.setText("Invalid input. Please enter a string.");
        } catch (Exception error) {
            printTranslation.setText("error");
        }
    }

    public void translatePara(ActionEvent e2){
        String textToTranslate = textFieldParagraph.getText();
        try {
            String translatedText = GoogleTranslate.translate("en", "vi", textToTranslate);
            textAreaParagraph.setText(translatedText);
        } catch (IOException e) {
            // Xử lý lỗi nếu cần
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}