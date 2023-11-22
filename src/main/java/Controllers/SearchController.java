package Controllers;

import java.net.URL;
import java.util.*;

import API.TextToSpeechAPI;
import Base.Word;

import DatabaseConnect.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import static Controllers.PreloaderController.dailyWord;
import static Controllers.PreloaderController.dictionary;
import static java.lang.System.exit;

public class SearchController extends DictionaryController implements Initializable {
    protected boolean isEditing = false;

    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> dailyWordList;
    @FXML
    private WebView definitionView;
    @FXML
    private HTMLEditor editField;
    @FXML
    private Pane addField;
    @FXML
    private ToggleButton addButton;
    @FXML
    private ToggleButton favourButton;
    @FXML
    private ToggleButton editButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> favouriteList = new ArrayList<>();
        DatabaseConnection.getFavouriteList(LoginController.user.getUserId(),favouriteList);
        for (String ele : favouriteList) {
            if (dictionary.containsKey(ele)) {
                dictionary.get(ele).setFavoured(true);
            }
        }
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
        search(dictionary);
    }

    @FXML
    public void selectWord() {
        select(dictionary, wordList);
    }

    @FXML
    public void selectWord2() {
        select(dictionary, dailyWordList);
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
        } else {
            favourButton.setSelected(true);
            selectedWord.setFavoured(true);
            favouriteController.addFromSearch(wordTarget);
        }
    }

    @FXML
    public void editWordTarget(ListView.EditEvent<String> event) {
        String oldWordTarget = searchField.getText();
        String newWordTarget = event.getNewValue();
        String wordExplain = dictionary.get(oldWordTarget).getWordExplain();
        editWordTarget(oldWordTarget, newWordTarget, wordExplain);
        dictionary.remove(oldWordTarget);
        dictionary.put(newWordTarget, new Word(newWordTarget, wordExplain));
        DatabaseConnection.addEditWord(LoginController.user.getUserId(),oldWordTarget, wordExplain, newWordTarget, wordExplain);
        DatabaseConnection.updateDeleteWord(LoginController.user.getUserId(), oldWordTarget);
        if (favouriteController.removeFromSearch(oldWordTarget)) {
            dictionary.get(newWordTarget).setFavoured(true);
            favouriteController.addFromSearch(newWordTarget);
        }
        reset();
    }

    @Override
    @FXML
    public void editWordExplain() {
        String wordTarget = searchField.getText();
        if (wordTarget.isEmpty()) {
            editButton.setSelected(false);
            return;
        }
        String oldWordExplain = dictionary.get(wordTarget).getWordExplain();
//        System.out.println(oldWordExplain);
        if (isEditing) {
            isEditing = false;
            editField.setVisible(false);
            dictionary.editWord(wordTarget, editField.getHtmlText());
            favouriteController.editFromSearch(wordTarget);
//            System.out.println("edit: " + editField.getHtmlText());
            DatabaseConnection.addEditWord(LoginController.user.getUserId(), wordTarget, oldWordExplain, wordTarget, editField.getHtmlText());
            definitionView.getEngine().loadContent(editField.getHtmlText(), "text/html");
        } else {
            isEditing = true;
            editField.setVisible(true);
            editField.setHtmlText(oldWordExplain);
            favouriteController.reset();
        }
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

    public void reset() {
        reset(dictionary);
    }

    public Word getWord(String wordTarget) {
        return dictionary.get(wordTarget);
    }

    public void editWordTarget(String oldWordTarget, String newWordTarget, String wordExplain) {
        dictionary.remove(oldWordTarget);
        dictionary.put(newWordTarget, new Word(newWordTarget, wordExplain));
    }

    public void editWordExplainFromFavourite(String wordTarget, String wordExplain) {
        dictionary.editWord(wordTarget, wordExplain);
    }
}
