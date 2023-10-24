package Controllers;

import DictionaryApplication.Dictionary;
import DictionaryApplication.Word;
import DictionaryApplication.NewDictionaryManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> wordList;
    @FXML
    private WebView definitionView;
    @FXML
    private HTMLEditor editField;
    @FXML
    private Button favouriteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private Dictionary dictionary = new Dictionary();
    private static final String IN_PATH = "data/E_V.txt";

    private boolean isEditing = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewDictionaryManagement.loadDataFromHTMLFile(dictionary,IN_PATH);
        this.wordList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Word selectedWord = dictionary.get(newValue.trim());
                    String wordExplain = selectedWord.getWordExplain();
                    definitionView.getEngine().loadContent(wordExplain, "text/html");
                    searchField.setText(selectedWord.getWordTarget());
                }
        );
        this.wordList.getItems().addAll(dictionary.keySet());
    }

    @FXML
    public void editWord() {
        String targetWord = searchField.getText();
        if (isEditing) {
            dictionary.editWord(targetWord, editField.getHtmlText());
            isEditing = false;
            editField.setVisible(false);
            definitionView.getEngine().loadContent(editField.getHtmlText(), "text/html");
            return;
        }
        isEditing = true;
        editField.setVisible(true);
        String wordExplain = dictionary.get(targetWord).getWordExplain();
        editField.setHtmlText(wordExplain);
    }

    @FXML
    public void searchWord(KeyEvent e) {
        String keyword = searchField.getText().toLowerCase();
        System.out.println(keyword);
        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(NewDictionaryManagement.partialSearch(dictionary, keyword).keySet());
        wordList.setItems(matchingWords);
    }
}
