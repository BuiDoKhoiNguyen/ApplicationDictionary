package Controllers;

import DictionaryApplication.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.input.InputMethodEvent;

import java.util.InputMismatchException;
import java.util.Map;

import static DictionaryApplication.NewDictionaryManagement.partialSearch;

public class DictionaryController {
    @FXML
    private TextField wordInput;
    @FXML
    private TextArea printTranslation;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button translate;

    private Dictionary dictionary = new Dictionary();
    private static final String IN_PATH = "data/dictionaries.txt";

    public DictionaryController() {
        NewDictionaryManagement.loadFromFile(dictionary,IN_PATH);
    }
    public void enterWord(ActionEvent e) {
        try {
            String wordTarget = wordInput.getText();
            String wordExplain = NewDictionaryManagement.lookupWord(dictionary,wordTarget);
            printTranslation.setText(wordExplain);
        } catch (InputMismatchException error) {
            printTranslation.setText("Invalid input. Please enter a string.");
        } catch (Exception error) {
            printTranslation.setText("error");
        }
    }

    public void searchWord(ActionEvent e) {
        try {
            listView.refresh();
            String partialWord = wordInput.getText();
            Map<String, Word> data = NewDictionaryManagement.partialSearch(dictionary, partialWord);
            listView.getItems().addAll(data.keySet());
        } catch(Exception ec) {
            System.out.println("a");
        }
    }

}