package Controllers;

import API.TextToSpeechAPI;
import Base.Dictionary;
import Base.NewDictionaryManagement;
import Base.Word;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public abstract class DictionaryController extends TaskControllers {
    public static final String EV_IN_PATH = "data/E_V.txt";

    protected boolean isEditing = false;

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

    public void search(Dictionary dict) {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(NewDictionaryManagement.partialSearch(dict, keyword).keySet());
        wordList.setItems(matchingWords);
    }

    public void select(Dictionary dict, ListView<String> listView) {
        String selectedWord = listView.getSelectionModel().selectedItemProperty().getValue();
        if (selectedWord != null) {
            Word word = dict.get(selectedWord.trim());
            String wordExplain = word.getWordExplain();
            definitionView.getEngine().loadContent(wordExplain, "text/html");
            searchField.setText(word.getWordTarget());
            favourButton.setSelected(word.isFavoured());
        }
    }

    public void reset(Dictionary dict) {
        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(dict.keySet());
        wordList.setItems(matchingWords);
        wordList.getSelectionModel().clearSelection();
        searchField.setText("");
        definitionView.getEngine().loadContent("");
        favourButton.setSelected(false);
    }

    private void speak(String language) {
        if (!searchField.getText().isEmpty()) {
            TextToSpeechAPI.language = language;
            TextToSpeechAPI.speakWord(searchField.getText());
        }
    }

    @FXML
    public void speakUS() {
        speak("en-us");
    }

    @FXML
    public void speakUK() {
        speak("en-gb");
    }

    @FXML
    public abstract void favouriteWord();

    @FXML
    public abstract void editWordExplain();
}
