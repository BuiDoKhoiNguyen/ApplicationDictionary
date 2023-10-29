package Controllers;

import Base.Dictionary;
import Base.NewDictionaryManagement;
import Base.Word;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    @FXML
    private WebView webView,tableResult;
    @FXML
    private Button ansA, ansB, ansC, ansD, playButton;
    @FXML
    private Label question,resultTable;
    @FXML
    private ProgressBar progressBar;

    @FXML
    private ToggleButton togButton;
    @FXML
    private AnchorPane myAnchor;

    Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/audioOn.png"));
    Image myImage2 = new Image(getClass().getResourceAsStream("/sources_music_picture/audioOff.png"));

    Image myImage3 = new Image(getClass().getResourceAsStream("/sources_music_picture/gameImage.png"));

    String mediaFile = "file:///C:/Users/User/IdeaProjects/BTLOOP/DictionaryApplication/src/main/resources/sources_music_picture/sleepy-cat-118974.mp3";

    Media media = new Media(mediaFile);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    private Task<Void> task;

    private List<Word> listWord;
    int index = 0;
    int correctAnswer = 0;
    int totalQuestion= 10;
    int seconds = 10;
    boolean noChoice = false;

    String trueAns = "";

    private Dictionary dictionary = new Dictionary();
    private static final String IN_PATH = "C:\\Users\\User\\IdeaProjects\\BTLOOP\\DictionaryApplication\\data\\dictionaries.txt";


    public GameController() {
        NewDictionaryManagement.loadFromFile(dictionary,IN_PATH);
    }



    public void quiz(ActionEvent event){
        listWord = new ArrayList<>(dictionary.values());
        changeOnOff();
        showQues();
        handle(new ActionEvent());
    }


    public void down(){
        if (task != null && task.isRunning()) {
            task.cancel();
        }

        //progressBar.setProgress(1.0);

        Task<Void> newTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int counter = 100; counter >= 0; counter--) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(counter, 100);
                    Thread.sleep(50); // 50
                    System.out.println(counter);
                    if (counter == 0) {
                        // noChoice = true;
                        displayAnswer();
                    }
                }
                return null;
            }
        };

        task = newTask;

        progressBar.progressProperty().bind(newTask.progressProperty());
        // if(progressBar.getProgress()<=0) displayAnswer();

        // task.setOnSucceeded(e -> progressBar.setProgress(1.0));
        // task.setOnFailed(e -> progressBar.setProgress(1.0));

        new Thread(newTask).start();
    }



    public void showQues(){
        if(index>=totalQuestion){
            result();
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
            String tmp = randomWords.get(randomIndex).getWordTarget();
            ques = ques.replaceAll("/.*?/", "");
            ques = ques.replaceAll("'.*?/", "");
            ques = ques.replaceAll(tmp,"...");
            webView.getEngine().loadContent(ques);
            trueAns = randomWords.get(randomIndex).getWordTarget();
            down();
        }
    }


    public void handle (ActionEvent event){
        /*if(noChoice==true){
            displayAnswer();
            //System.out.println("ok");
        }

         */

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
        //timer.stop();
        progressBar.progressProperty().unbind();
        //noChoice = false;
        ansA.setDisable(true);
        ansB.setDisable(true);
        ansC.setDisable(true);
        ansD.setDisable(true);

        ansA.setOpacity(1.0);
        ansB.setOpacity(1.0);
        ansC.setOpacity(1.0);
        ansD.setOpacity(1.0);

        if(ansA.getText()!=trueAns){
            ansA.setVisible(false);
        }
        if(ansB.getText()!=trueAns){
            ansB.setVisible(false);
        }
        if(ansC.getText()!=trueAns){
            ansC.setVisible(false);
        }
        if(ansD.getText()!=trueAns){
            ansD.setVisible(false);
        }

        Timeline pause = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ansA.setVisible(true);
                ansB.setVisible(true);
                ansC.setVisible(true);
                ansD.setVisible(true);


                trueAns="";
                //seconds = 10;
                //time.setText(String.valueOf(seconds));
                //down();
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
    public void result(){
        ansA.setText("");
        ansB.setText("");
        ansC.setText("");
        ansD.setText("");
        webView.getEngine().loadContent("");
        resultTable.setVisible(true);
        resultTable.setTextFill(Color.WHITE);
        progressBar.setProgress(0.0);
        if(correctAnswer>=5){
            String tmp = "So cool, well done bruh";
            tmp+="\n";
            tmp+="             ";
            tmp+="("+correctAnswer+"/"+totalQuestion+")";
            resultTable.setText(tmp);
        }
        else{
            String tmp = "Opps, try again bruh";
            tmp+="\n";
            tmp+="             ";
            tmp+="("+correctAnswer+"/"+totalQuestion+")";
            resultTable.setText(tmp);
        }
    }

    public void changeOnOff(){
        /*boolean isMuted = mediaPlayer.isMute();
        mediaPlayer.setMute(!isMuted);
        togButton.setSelected(mediaPlayer.isMute());

         */
        boolean isMuted = togButton.isSelected();

        // Đặt trạng thái âm thanh tương ứng
        mediaPlayer.setMute(isMuted);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView imageView = new ImageView(myImage3);
        myAnchor.getChildren().add(imageView);
        imageView.toBack();
            togButton.getStyleClass().add("buttonAudio");
            quiz(new ActionEvent());
            resultTable.setVisible(false);
            ansA.getStyleClass().add("button1");
            ansB.getStyleClass().add("button2");
            ansC.getStyleClass().add("button3");
            ansD.getStyleClass().add("button4");
            mediaPlayer.play();

    }
}
