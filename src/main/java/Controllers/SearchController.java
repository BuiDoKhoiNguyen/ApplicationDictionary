package Controllers;

import Base.Dictionary;
import Base.Word;
import Base.NewDictionaryManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController extends TaskControllers implements Initializable {
    private Dictionary dictionary = new Dictionary();
    private ObservableList<String> favouriteWords = FXCollections.observableArrayList();

    private final String EV_IN_PATH = "data/E_V.txt";
    private final String FAVOURITE_IN_PATH = "data/favourite.txt";

    private boolean isEditing = false;

    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> wordList;
    @FXML
    private WebView definitionView;
    @FXML
    private HTMLEditor editField;
    @FXML
    private ToggleButton favourButton;
    @FXML
    private ToggleButton editingButton;
    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewDictionaryManagement.loadDataFromHTMLFile(dictionary, EV_IN_PATH);
        this.wordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.wordList.getItems().addAll(dictionary.keySet());
    }

    @FXML
    public void editWord() {
        String targetWord = searchField.getText();
        if (targetWord.isEmpty()) {
            editingButton.setSelected(false);
            return;
        }
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

    @FXML
    public void selectWord(MouseEvent e) {
        String selectedWord = this.wordList.getSelectionModel().selectedItemProperty().getValue();
        if (selectedWord != null) {
            Word word = dictionary.get(selectedWord.trim());
            String wordExplain = word.getWordExplain();
            definitionView.getEngine().loadContent(wordExplain, "text/html");
            searchField.setText(word.getWordTarget());
            favourButton.setSelected(word.isFavoured());
        }
    }

    @FXML
    public void favouriteWord() {
        String targetWord = searchField.getText();
        if (targetWord.isEmpty()) {
            favourButton.setSelected(false);
            return;
        }
        Word selectedWord = dictionary.get(targetWord);
        if (selectedWord.isFavoured()) {
            favourButton.setSelected(false);
            selectedWord.setFavoured(false);
            favouriteWords.removeAll(targetWord);
            return;
        }
        favourButton.setSelected(true);
        selectedWord.setFavoured(true);
        favouriteWords.add(targetWord);
    }

    @FXML
    public void deleteWord() {
        String targetWord = searchField.getText();
        favouriteWords.removeAll(targetWord);
        dictionary.remove(targetWord);
        wordList.getItems().remove(targetWord);
    }
}
