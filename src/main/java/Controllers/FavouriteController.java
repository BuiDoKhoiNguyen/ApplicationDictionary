package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Base.Dictionary;
import Base.Word;

import DatabaseConnect.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;

public class FavouriteController extends DictionaryController implements Initializable {
    private Dictionary favouriteDict = new Dictionary();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.wordList.setEditable(true);
        this.wordList.setCellFactory(TextFieldListCell.forListView());
        this.wordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void initFavouriteDict() {
        List<String> favouriteList = new ArrayList<>();
        DatabaseConnection.getFavouriteList(LoginController.user.getUserId(),favouriteList);
        for (String ele : favouriteList) {
            favouriteDict.put(ele, searchController.getWord(ele));
            favouriteDict.get(ele).setFavoured(true);
        }
        this.wordList.getItems().addAll(favouriteDict.keySet());
    }

    @FXML
    public void searchWord() {
        search(favouriteDict);
    }

    @FXML
    public void selectWord() {
        select(favouriteDict);
    }

    @Override
    @FXML
    public void favouriteWord() {
        String wordTarget = searchField.getText();
        if (wordTarget.isEmpty()) {
            favourButton.setSelected(false);
            return;
        }
        Word selectedWord = favouriteDict.get(wordTarget);
        if (selectedWord.isFavoured()) {
            removeFromSearch(wordTarget);
            searchController.getWord(wordTarget).setFavoured(false);
            searchController.reset();
            return;
        }
        System.out.println("Error: The word " + selectedWord + " is not in Favourite!");
    }

    @FXML
    public void editWordTarget(ListView.EditEvent<String> event) {
        String oldWordTarget = searchField.getText();
        String newWordTarget = event.getNewValue();
        String wordExplain = favouriteDict.get(oldWordTarget).getWordExplain();
        favouriteDict.remove(oldWordTarget);
        favouriteDict.put(newWordTarget, new Word(newWordTarget, wordExplain));
        searchController.editWordTarget(oldWordTarget, newWordTarget, wordExplain);
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
            favouriteDict.editWord(wordTarget, editField.getHtmlText());
            searchController.editWordExplainFromFavourite(wordTarget, editField.getHtmlText());
            definitionView.getEngine().loadContent(editField.getHtmlText(), "text/html");
        } else {
            isEditing = true;
            editField.setVisible(true);
            editField.setHtmlText(favouriteDict.get(wordTarget).getWordExplain());
            searchController.reset();
        }
    }

    public void reset() {
        reset(favouriteDict);
    }

    public boolean removeFromSearch(String wordTarget) {
        if (favouriteDict.containsKey(wordTarget)) {
            favouriteDict.remove(wordTarget);
            reset();
            return true;
        }
        return false;
    }

    public boolean addFromSearch(String wordTarget) {
        if (!favouriteDict.containsKey(wordTarget)) {
            favouriteDict.put(wordTarget, searchController.getWord(wordTarget));
            reset();
            return true;
        }
        return false;
    }

    public void editFromSearch(String wordTarget) {
        if (favouriteDict.containsKey(wordTarget)) {
            favouriteDict.editWord(wordTarget, searchController.getWord(wordTarget).getWordExplain());
            reset();
        }
    }
}
