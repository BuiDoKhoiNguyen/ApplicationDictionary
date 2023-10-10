package Controllers;

import DictionaryApplication.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import java.util.InputMismatchException;


public class DictionaryController {
    @FXML
    private TextField wordInput;
    @FXML
    private TextArea printTranslation;
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

}