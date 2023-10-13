package Controllers;

import DictionaryApplication.Dictionary;
import DictionaryApplication.NewDictionaryManagement;
import DictionaryApplication.Word;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    @FXML
    private TextArea titleQuestion;
    @FXML
    private Button ansA, ansB, ansC, ansD;
    @FXML
    private Label time, question;

    private List<Word> listWord;
    int index = 0;
    int correctAnswer = 0;
    int totalQuestion= 10;
    int seconds = 10;

    String trueAns = "";

    private Dictionary dictionary = new Dictionary();
    private static final String IN_PATH = "C:\\Users\\User\\IdeaProjects\\BTLOOP\\DictionaryApplication\\data\\dictionaries.txt";


    public GameController() {
        NewDictionaryManagement.loadFromFile(dictionary,IN_PATH);
    }

    Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            seconds--;
            time.setText(String.valueOf(seconds));

            if (seconds <= 0) {
                displayAnswer();
            }
        }
    }));

    public void quiz(ActionEvent event){
        listWord = new ArrayList<>(dictionary.values());
        showQues();
        handle(new ActionEvent());

    }


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

        ansA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ansA.setDisable(true);
                ansB.setDisable(true);
                ansC.setDisable(true);
                ansD.setDisable(true);
                if(event.getSource()==ansA){
                    if(ansA.getText()==trueAns){
                        correctAnswer++;
                    }
                }
                displayAnswer();
            }
        });
        ansB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ansA.setDisable(true);
                ansB.setDisable(true);
                ansC.setDisable(true);
                ansD.setDisable(true);
                if(event.getSource()==ansB){
                    if(ansB.getText()==trueAns){
                        correctAnswer++;
                    }
                }
                displayAnswer();
            }
        });
        ansC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ansA.setDisable(true);
                ansB.setDisable(true);
                ansC.setDisable(true);
                ansD.setDisable(true);
                if(event.getSource()==ansC){
                    if(ansC.getText()==trueAns){
                        correctAnswer++;
                    }
                }
                displayAnswer();
            }
        });
        ansD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ansA.setDisable(true);
                ansB.setDisable(true);
                ansC.setDisable(true);
                ansD.setDisable(true);
                if(event.getSource()==ansD){
                    if(ansD.getText()==trueAns){
                        correctAnswer++;
                    }
                }
                displayAnswer();
            }
        });
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
