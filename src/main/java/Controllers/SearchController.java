package Controllers;

import Base.Dictionary;
import Base.Word;
import Base.NewDictionaryManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController extends TaskControllers implements Initializable {
    private final String EV_IN_PATH = "data/E_V.txt";
    protected final String FAVOURITE_IN_PATH = "data/favourite.txt";

    private Dictionary dictionary = new Dictionary(EV_IN_PATH);
    protected List<String> favouriteList = new ArrayList<>();

    private boolean isEditing = false;

    @FXML
    protected TextField searchField;
    @FXML
    protected ListView<String> wordList;
    @FXML
    protected WebView definitionView;
    @FXML
    protected HTMLEditor editField;
    @FXML
    protected ToggleButton favourButton;
    @FXML
    protected ToggleButton editingButton;
    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        NewDictionaryManagement.loadDataFromHTMLFile(dictionary, EV_IN_PATH);

        NewDictionaryManagement.loadOnlyWordTarget(favouriteList, FAVOURITE_IN_PATH);
        for (String ele : favouriteList) {
            if (dictionary.containsKey(ele)) {
                dictionary.get(ele).setFavoured(true);
            }
        }

        this.wordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.wordList.getItems().addAll(dictionary.keySet());
    }

    @FXML
    public void searchWord() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(NewDictionaryManagement.partialSearch(dictionary, keyword).keySet());
        wordList.setItems(matchingWords);
    }

    @FXML
    public void selectWord() {
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
//            favouriteWords.removeAll(targetWord); TODO
            return;
        }
        favourButton.setSelected(true);
        selectedWord.setFavoured(true);
//        favouriteWords.add(targetWord);   TODO
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
    public void deleteWord() {
        String targetWord = searchField.getText();
//        favouriteWords.removeAll(targetWord); TODO
        dictionary.remove(targetWord);
        wordList.getItems().remove(targetWord);
        selectWord();
    }

    public Dictionary getDictionary() {
        return this.dictionary;
    }
}
