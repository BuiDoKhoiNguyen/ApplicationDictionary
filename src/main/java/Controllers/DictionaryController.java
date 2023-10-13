package Controllers;

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

    private static final String IN_PATH = "data/E_V.txt";

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
            printTranslation.setText("Invalid input. Please enter a string.");
        } catch (Exception error) {
            printTranslation.setText("error");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}