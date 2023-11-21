package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

import static Controllers.PreloaderController.dictionary;
import static java.lang.System.exit;

public class SearchController extends DictionaryController implements Initializable {
    protected boolean isEditing = false;

    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> wordList;
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

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> favouriteList = new ArrayList<>();
        DatabaseConnection.getFavouriteList(LoginController.user.getUserId(),favouriteList);
        for (String ele : favouriteList) {
            if (dictionary.containsKey(ele)) {
                dictionary.get(ele).setFavoured(true);
            }
        }
        this.wordList.setEditable(true);
        this.wordList.setCellFactory(TextFieldListCell.forListView());
        this.wordList.getItems().addAll(dictionary.keySet());
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
        select(dictionary);
    }

    @Override
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
        if (isEditing) {
            isEditing = false;
            editField.setVisible(false);
            dictionary.editWord(wordTarget, editField.getHtmlText());
            favouriteController.editFromSearch(wordTarget);
            definitionView.getEngine().loadContent(editField.getHtmlText(), "text/html");
        } else {
            isEditing = true;
            editField.setVisible(true);
            editField.setHtmlText(dictionary.get(wordTarget).getWordExplain());
            favouriteController.reset();
        }
    }

    @FXML
    public void deleteWord() {
        String wordTarget = searchField.getText();
        favouriteController.removeFromSearch(wordTarget);
        dictionary.remove(wordTarget);
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

    public void cancelButtonOnAction() {
        ProfileController.recordAppUsage();
        LoginController.isLogin = false;
        ProfileController.currtime = 0;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        exit(0);
    }
}
