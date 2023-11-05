package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Base.Dictionary;
import Base.Word;
import Base.NewDictionaryManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.SelectionMode;

public class SearchController extends TaskControllers implements Initializable {
    private Dictionary dictionary = new Dictionary(Dictionary.EV_IN_PATH);

    private  VoiceController voiceController = new VoiceController();

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
    protected ToggleButton editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button speakUS,speakUK;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> favouriteList = new ArrayList<>();
        NewDictionaryManagement.loadOnlyWordTarget(favouriteList, Dictionary.FAVOURITE_IN_PATH);
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
        String wordTarget = searchField.getText();
        if (wordTarget.isEmpty()) {
            favourButton.setSelected(false);
            return;
        }
        Word selectedWord = dictionary.get(wordTarget);
        if (selectedWord.isFavoured()) {
            favourButton.setSelected(false);
            selectedWord.setFavoured(false);
            favouriteController.removeFromSearch(wordTarget);
            return;
        }
        favourButton.setSelected(true);
        selectedWord.setFavoured(true);
        favouriteController.addFromSearch(wordTarget);
    }

    @FXML
    public void editWord() {
        String targetWord = searchField.getText();
        if (targetWord.isEmpty()) {
            editButton.setSelected(false);
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
        String wordTarget = searchField.getText();
        favouriteController.removeFromSearch(wordTarget);
        dictionary.remove(wordTarget);
        wordList.getItems().remove(wordTarget);
        selectWord();
    }

    protected Word getWord(String wordTarget) {
        return this.dictionary.get(wordTarget);
    }

    public void speak(String language) {
        VoiceController.language = language;
        VoiceController.speakWord(searchField.getText());
    }
    @FXML
    public void speakUSButtonOnAction(ActionEvent e) {
        speak("en-us");
    }

    @FXML
    public void speakUKButtonOnAction(ActionEvent e) {
        speak("en-gb");
    }
}
