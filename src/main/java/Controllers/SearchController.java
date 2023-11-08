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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;

import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SearchController extends TaskControllers implements Initializable {
    private Dictionary dictionary = new Dictionary(Dictionary.EV_IN_PATH);


    protected  VoiceController voiceController = new VoiceController();

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
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> favouriteList = new ArrayList<>();
        NewDictionaryManagement.loadOnlyWordTarget(favouriteList, Dictionary.FAVOURITE_IN_PATH);
        for (String ele : favouriteList) {
            if (dictionary.containsKey(ele)) {
                dictionary.get(ele).setFavoured(true);
            }
        }
        this.wordList.setEditable(true);
        this.wordList.setCellFactory(TextFieldListCell.forListView());
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
    public void editWordTarget(ListView.EditEvent<String> event) {
        String oldWordTarget = searchField.getText();
        String newWordTarget = event.getNewValue();
        String wordExplain = dictionary.get(oldWordTarget).getWordExplain();
        dictionary.remove(oldWordTarget);
        dictionary.put(newWordTarget, new Word(newWordTarget, wordExplain));
        if (favouriteController.removeFromSearch(oldWordTarget)) {
            dictionary.get(newWordTarget).setFavoured(true);
            favouriteController.addFromSearch(newWordTarget);
        }

        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(dictionary.keySet());
        wordList.setItems(matchingWords);
        resetSelection();
    }

    @FXML
    public void editWordExplain() {
        String wordTarget = searchField.getText();
        if (wordTarget.isEmpty()) {
            editButton.setSelected(false);
            return;
        }
        if (isEditing) {
            isEditing = false;
            editField.setVisible(false);
            dictionary.editWord(wordTarget, editField.getHtmlText());
            favouriteController.editFromSearch(wordTarget);
            definitionView.getEngine().loadContent(editField.getHtmlText(), "text/html");
            return;
        }
        isEditing = true;
        editField.setVisible(true);
        String wordExplain = dictionary.get(wordTarget).getWordExplain();
        editField.setHtmlText(wordExplain);
    }

    @FXML
    public void deleteWord() {
        String wordTarget = searchField.getText();
        favouriteController.removeFromSearch(wordTarget);
        dictionary.remove(wordTarget);
        wordList.getItems().remove(wordTarget);
        resetSelection();
    }

    public void resetSelection() {
        wordList.getSelectionModel().clearSelection();
        searchField.setText("");
        definitionView.getEngine().loadContent("");
        favourButton.setSelected(false);
    }

    protected Word getWord(String wordTarget) {
        return this.dictionary.get(wordTarget);
    }

    private void speak(String language) {
        VoiceController.language = language;
        VoiceController.speakWord(searchField.getText());
    }

    @FXML
    private void speakUSButtonOnAction(ActionEvent e) {
        speak("en-us");
    }

    @FXML
    private void speakUKButtonOnAction(ActionEvent e) {
        speak("en-gb");
    }

    public void cancelButtonOnAction(ActionEvent e) {
        ProfileController.recordAppUsage();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
//        exit(0);
    }
}
