package Controllers;

import Base.Dictionary;
import Base.Word;
import Base.NewDictionaryManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class SearchController implements Initializable {
    private Dictionary dictionary = new Dictionary();
    private ObservableList<String> favouriteWords = FXCollections.observableArrayList();

    private final String EV_IN_PATH = "data/E_V.txt";
    private final String FAVOURITE_IN_PATH = "data/favourite.txt";

    private  VoiceController voiceController = new VoiceController();

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
    private ToggleButton favouriteButton;
    @FXML
    private ToggleButton editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button speakUS,speakUK;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewDictionaryManagement.loadDataFromHTMLFile(dictionary, EV_IN_PATH);
        this.wordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.wordList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        Word selectedWord = dictionary.get(newValue.trim());
                        String wordExplain = selectedWord.getWordExplain();
                        definitionView.getEngine().loadContent(wordExplain, "text/html");
                        searchField.setText(selectedWord.getWordTarget());
                        favouriteButton.setSelected(selectedWord.isFavoured());
                    }
                }
        );
        this.wordList.getItems().addAll(dictionary.keySet());
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
    public void searchWord(KeyEvent e) {
        String keyword = searchField.getText().toLowerCase();
        System.out.println(keyword);
        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(NewDictionaryManagement.partialSearch(dictionary, keyword).keySet());
        wordList.setItems(matchingWords);
    }

    @FXML
    public void favouriteWord() {
        String targetWord = searchField.getText();
        if (targetWord.isEmpty()) {
            favouriteButton.setSelected(false);
            return;
        }
        Word selectedWord = dictionary.get(targetWord);
        if (selectedWord.isFavoured()) {
            favouriteButton.setSelected(false);
            selectedWord.setFavoured(false);
            favouriteWords.removeAll(targetWord);
            return;
        }
        favouriteButton.setSelected(true);
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

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
//        exit(0);
    }
}
