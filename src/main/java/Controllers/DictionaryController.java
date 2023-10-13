package Controllers;

import DictionaryApplication.*;
import DictionaryApplication.Dictionary;
import com.darkprograms.speech.translator.GoogleTranslate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class DictionaryController implements Initializable {
    @FXML
    private TextField wordInput, textFieldParagraph;
    @FXML
    private TextArea printTranslation, textAreaParagraph, titleQuestion;
    @FXML
    private Button translate, translateParagraph, ansA, ansB, ansC, ansD;

    @FXML
    private Label time, question;

    int index = 0;
    int correctAnswer = 0;
    int totalQuestion= 10;
    int seconds = 10;

    String trueAns = "";

    @FXML
    private ListView<String> listView;

    private List<Word> listWord;
    private Dictionary dictionary = new Dictionary();

    private static final String IN_PATH = "C:\\Users\\User\\IdeaProjects\\BTLOOP\\DictionaryApplication\\data\\dictionaries.txt";

    public DictionaryController() {
        NewDictionaryManagement.loadFromFile(dictionary,IN_PATH);
    }

    public void enterWord(KeyEvent e){
            String keyword = wordInput.getText().toLowerCase(); // Chuyển từ khóa về chữ thường
            ObservableList<String> matchingWords = FXCollections.observableArrayList();

            // Lọc danh sách từ trong TreeMap của bạn
            for (Map.Entry<String, Word> entry : dictionary.entrySet()){
                String englishWord = entry.getKey().toLowerCase();
                if (englishWord.startsWith(keyword)) {
                    matchingWords.add(entry.getKey());
                }
            }

            // Cập nhật ListView với danh sách từ tìm thấy
            listView.setItems(matchingWords);

            listView.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            // Lấy nghĩa tiếng Việt từ TreeMap dựa trên từ tiếng Anh được chọn
                            String vietnameseMeaning = dictionary.get(newValue).getWordExplain();

                            // Hiển thị nghĩa tiếng Việt trong WebView
                            printTranslation.setText(vietnameseMeaning);
                        }
                    }
            );
    }

    public void enterWord2(ActionEvent e) {
        try {
            String wordTarget = wordInput.getText();
            String wordExplain = NewDictionaryManagement.lookupWord(dictionary, wordTarget);
            printTranslation.setText(wordExplain);
        } catch (InputMismatchException error) {
            printTranslation.setText("Invalid input. Please enter a string.");
        } catch (Exception error) {
            printTranslation.setText("error");
        }
    }

    public void translatePara(ActionEvent e2){
        String textToTranslate = textFieldParagraph.getText();
        try {
            String translatedText = GoogleTranslate.translate("en", "vi", textToTranslate);
            textAreaParagraph.setText(translatedText);
        } catch (IOException e) {
            // Xử lý lỗi nếu cần
            e.printStackTrace();
        }
    }

    public void quiz(ActionEvent event){
        listWord = new ArrayList<>(dictionary.values());
        showQues();
        handle(event);

    }

    Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            // Thực hiện công việc sau mỗi giây
            seconds--;
            time.setText(String.valueOf(seconds));

            if (seconds <= 0) {
                displayAnswer();
            }
        }
    }));


    public void showQues(){
        if(index>=totalQuestion){
            //results();
        }
        else{
            question.setText("Question "+(index+1));
            Collections.shuffle(listWord);
            List<Word> randomWords = listWord.subList(0,4);
            int t = 0;
            ansA.setText(randomWords.get(t).getWordTarget());
            t++;
            ansB.setText(randomWords.get(t).getWordTarget());
            t++;
            ansC.setText(randomWords.get(t).getWordTarget());
            t++;
            ansD.setText(randomWords.get(t).getWordTarget());
            Random random = new Random();
            int randomIndex = random.nextInt(randomWords.size());
            String ques = randomWords.get(randomIndex).getWordExplain();
            titleQuestion.setText(ques);
            trueAns = randomWords.get(randomIndex).getWordTarget();
            timer.play();
        }
    }

    public void handle (ActionEvent event){
        ansA.setDisable(true);
        ansB.setDisable(true);
        ansC.setDisable(true);
        ansD.setDisable(true);

        if(event.getSource()==ansA){
            if(ansA.getText()==trueAns){
                correctAnswer++;
            }
        }
        if(event.getSource()==ansB){
            if(ansB.getText()==trueAns){
                correctAnswer++;
            }
        }
        if(event.getSource()==ansC){
            if(ansC.getText()==trueAns){
                correctAnswer++;
            }
        }
        if(event.getSource()==ansD){
            if(ansD.getText()==trueAns){
                correctAnswer++;
            }
        }
        displayAnswer();
    }

    public void displayAnswer(){
        timer.stop();
        ansA.setDisable(true);
        ansB.setDisable(true);
        ansC.setDisable(true);
        ansD.setDisable(true);

        if(ansA.getText()==trueAns){
            ansA.setTextFill(Color.GREEN);
        }
        else ansA.setTextFill(Color.RED);

        if(ansB.getText()==trueAns){
            ansB.setTextFill(Color.GREEN);
        }
        else ansB.setTextFill(Color.RED);

        if(ansC.getText()==trueAns){
            ansC.setTextFill(Color.GREEN);
        }
        else ansC.setTextFill(Color.RED);

        if(ansD.getText()==trueAns){
            ansD.setTextFill(Color.GREEN);
        }
        else ansD.setTextFill(Color.RED);

        Timeline pause = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ansA.setTextFill(Color.BLACK);
                ansB.setTextFill(Color.BLACK);
                ansC.setTextFill(Color.BLACK);
                ansD.setTextFill(Color.BLACK);
                trueAns="";
                seconds = 10;
                time.setText(String.valueOf(seconds));
                ansA.setDisable(false);
                ansB.setDisable(false);
                ansC.setDisable(false);
                ansD.setDisable(false);
                index++;
                showQues();
            }
        }));
        pause.setCycleCount(1);
        pause.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quiz(new ActionEvent());
    }
}