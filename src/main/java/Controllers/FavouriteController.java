package Controllers;

import Base.Word;
import Base.NewDictionaryManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.util.ResourceBundle;

public class FavouriteController extends SearchController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NewDictionaryManagement.loadOnlyWordTarget(favouriteList, FAVOURITE_IN_PATH);
        this.wordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.wordList.getItems().addAll(favouriteList);
    }

    @Override
    @FXML
    public void selectWord() {
        String selectedWord = this.wordList.getSelectionModel().selectedItemProperty().getValue();
        if (selectedWord != null) {
            Word word = getDictionary().get(selectedWord.trim());
            String wordExplain = word.getWordExplain();
            definitionView.getEngine().loadContent(wordExplain, "text/html");
            searchField.setText(word.getWordTarget());
            favourButton.setSelected(true);
        }
    }
}
