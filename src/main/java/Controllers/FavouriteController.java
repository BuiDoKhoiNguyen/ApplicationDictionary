package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Base.Dictionary;
import Base.Word;
import Base.NewDictionaryManagement;

import DatabaseConnect.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;

public class FavouriteController extends SearchController implements Initializable {
    private Dictionary favouriteDict = new Dictionary();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> favouriteList = new ArrayList<>();
//        NewDictionaryManagement.loadOnlyWordTarget(favouriteList, Dictionary.FAVOURITE_IN_PATH);
        DatabaseConnection.getFavouriteList(LoginController.user.getUserId(),favouriteList);
        for (String ele : favouriteList) {
            favouriteDict.put(ele, getWord(ele));
            favouriteDict.get(ele).setFavoured(true);
        }
        this.wordList.setEditable(true);
        this.wordList.setCellFactory(TextFieldListCell.forListView());
        this.wordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.wordList.getItems().addAll(favouriteDict.keySet());
    }

    @Override
    @FXML
    public void searchWord() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(NewDictionaryManagement.partialSearch(favouriteDict, keyword).keySet());
        wordList.setItems(matchingWords);
    }

    @Override
    @FXML
    public void selectWord() {
        String selectedWord = this.wordList.getSelectionModel().selectedItemProperty().getValue();
        if (selectedWord != null) {
            Word word = favouriteDict.get(selectedWord.trim());
            String wordExplain = word.getWordExplain();
            definitionView.getEngine().loadContent(wordExplain, "text/html");
            searchField.setText(word.getWordTarget());
            favourButton.setSelected(word.isFavoured());
        }
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
            return;
        }
        isEditing = true;
        editField.setVisible(true);
        editField.setHtmlText(favouriteDict.get(wordTarget).getWordExplain());
        searchController.reset();
    }

    @Override
    protected void reset() {
        ObservableList<String> matchingWords = FXCollections.observableArrayList();
        matchingWords.addAll(favouriteDict.keySet());
        wordList.setItems(matchingWords);
        wordList.getSelectionModel().clearSelection();
        searchField.setText("");
        definitionView.getEngine().loadContent("");
        favourButton.setSelected(false);
    }

    public boolean removeFromSearch(String wordTarget) {
        if (favouriteDict.containsKey(wordTarget)) {
            favouriteDict.remove(wordTarget);
            DatabaseConnection.removeFavouriteWord(LoginController.user.getUserId(),wordTarget);
            reset();
            return true;
        }
        return false;
    }

    public boolean addFromSearch(String wordTarget) {
        if (!favouriteDict.containsKey(wordTarget)) {
            favouriteDict.put(wordTarget, searchController.getWord(wordTarget));
            DatabaseConnection.addFavouriteWord(LoginController.user.getUserId(), wordTarget);
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
