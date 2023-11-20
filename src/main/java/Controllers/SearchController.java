package Controllers;


import java.net.URL;
import java.util.*;

import API.TextToSpeechAPI;
import Base.Dictionary;
import Base.Word;
import Base.NewDictionaryManagement;

import DatabaseConnect.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import static Controllers.PreloaderController.dailyWord;
import static Controllers.PreloaderController.dictionary;
import static java.lang.System.exit;

public class SearchController extends TaskControllers implements Initializable {
    protected boolean isEditing = false;

    @FXML
    protected TextField searchField;
    @FXML
    protected ListView<String> wordList, dailyWordList;
    @FXML
    protected WebView definitionView;
    @FXML
    protected HTMLEditor editField;
    @FXML
    private Pane addField;
    @FXML
    protected ToggleButton addButton;
    @FXML
    protected ToggleButton favourButton;
    @FXML
    protected ToggleButton editButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeMap<String, String> listAddWord = DatabaseConnection.getAddWord(LoginController.user.getUserId());
        for (Map.Entry<String, String> entry : listAddWord.entrySet()) {
            dictionary.put(entry.getKey(), new Word(entry.getKey(), entry.getValue()));
        }
        TreeMap<String, String> listEditWord = DatabaseConnection.getListEditWord(LoginController.user.getUserId());
        for (Map.Entry<String, String> entry : listEditWord.entrySet()) {
            dictionary.put(entry.getKey(), new Word(entry.getKey(), entry.getValue()));
        }
        List<String> listDeleteWord = DatabaseConnection.getDeleteWord(LoginController.user.getUserId());
        for(String x:listDeleteWord) {
            dictionary.remove(x);
        }
        this.wordList.setEditable(true);
        this.wordList.setCellFactory(TextFieldListCell.forListView());
        this.wordList.getItems().addAll(dictionary.keySet());
        this.dailyWordList.setEditable(true);
        this.dailyWordList.setCellFactory(TextFieldListCell.forListView());
        this.dailyWordList.getItems().addAll(dailyWord.keySet());

        addField.visibleProperty().bind(addButton.selectedProperty());
        addButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            addField.toFront();
        });
    }

    @FXML
    public void searchWord() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(NewDictionaryManagement.partialSearch(dictionary, keyword).keySet());
        wordList.setItems(matchingWords);
    }

    @FXML
    public void searchWord(KeyEvent e) {
        String keyword = searchField.getText().toLowerCase();
//        System.out.println(keyword);
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
    public void selectWord2() {
        String selectedWord = this.dailyWordList.getSelectionModel().selectedItemProperty().getValue();
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
//        System.out.println(wordExplain);
        DatabaseConnection.addEditWord(LoginController.user.getUserId(),oldWordTarget, wordExplain, newWordTarget, wordExplain);
        DatabaseConnection.updateDeleteWord(LoginController.user.getUserId(), oldWordTarget);
        if (favouriteController.removeFromSearch(oldWordTarget)) {
            dictionary.get(newWordTarget).setFavoured(true);
            favouriteController.addFromSearch(newWordTarget);
        }
        reset();
    }

    @FXML
    public void editWordExplain() {
        String wordTarget = searchField.getText();
        String oldWordExplain = dictionary.get(wordTarget).getWordExplain();
        System.out.println(oldWordExplain);
        if (wordTarget.isEmpty()) {
            editButton.setSelected(false);
            return;
        }
        if (isEditing) {
            isEditing = false;
            editField.setVisible(false);
            dictionary.editWord(wordTarget, editField.getHtmlText());
            favouriteController.editFromSearch(wordTarget);
            System.out.println("edit: " + editField.getHtmlText());
            DatabaseConnection.addEditWord(LoginController.user.getUserId(), wordTarget, oldWordExplain, wordTarget, editField.getHtmlText());
            definitionView.getEngine().loadContent(editField.getHtmlText(), "text/html");
            return;
        }
        isEditing = true;
        editField.setVisible(true);
        editField.setHtmlText(dictionary.get(wordTarget).getWordExplain());
        favouriteController.reset();
    }

    @FXML
    public void deleteWord() {
        String wordTarget = searchField.getText();
        favouriteController.removeFromSearch(wordTarget);
        dictionary.remove(wordTarget);
        DatabaseConnection.updateDeleteWord(LoginController.user.getUserId(), wordTarget);
        wordList.getItems().remove(wordTarget);
        reset();
    }

    @FXML
    public void addWord() {
        TextField addTextField = (TextField) addField.getChildren().get(1);
        HTMLEditor addDefField = (HTMLEditor) addField.getChildren().get(3);
        String wordTarget = addTextField.getText();
        String wordExplain = addDefField.getHtmlText();
        if (!wordTarget.isEmpty() && !wordExplain.isEmpty()) {
            dictionary.put(wordTarget, new Word(wordTarget, wordExplain));
            DatabaseConnection.updateAddWord(LoginController.user.getUserId(), wordTarget, wordExplain);
        }
        addTextField.setText("");
        addDefField.setHtmlText("");
        addButton.setSelected(false);
        reset();
    }

    @FXML
    public void cancelAdding() {
        addButton.setSelected(false);
        reset();
    }

    protected void reset() {
        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(dictionary.keySet());
        wordList.setItems(matchingWords);
        wordList.getSelectionModel().clearSelection();
        searchField.setText("");
        definitionView.getEngine().loadContent("");
        favourButton.setSelected(false);
    }

    protected Word getWord(String wordTarget) {
        return dictionary.get(wordTarget);
    }

    protected void editWordExplainFromFavourite(String wordTarget, String wordExplain) {
        dictionary.editWord(wordTarget, wordExplain);
    }

    private void speak(String language) {
        if (!searchField.getText().isEmpty()) {
            TextToSpeechAPI.language = language;
            TextToSpeechAPI.speakWord(searchField.getText());
        }
    }
    @FXML
    private void speakUSButtonOnAction() {
        speak("en-us");
    }

    @FXML
    private void speakUKButtonOnAction() {
        speak("en-gb");
    }

    public void cancelButtonOnAction() {
        ProfileController.recordAppUsage();
        LoginController.isLogin = false;
        ProfileController.currtime = 0;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        exit(0);
    }
}
