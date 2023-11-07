package Controllers;

import Base.Dictionary;
import Base.NewDictionaryManagement;
import Base.Word;
import javafx.animation.FadeTransition;
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
    private WebView webView;
    @FXML
    private Button ansA, ansB, ansC, ansD, playButton, yesButton, noButton,review;
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
    boolean Choice = false;

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

        Task<Void> newTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int counter = 100; counter >= 0; counter--) {
                    if (isCancelled() || Choice==true) {
                        break;
                    }
                    updateProgress(counter, 100);
                    Thread.sleep(50); // 50 milliseconds
                    System.out.println(counter);
                    if (counter == 0) {
                        displayAnswer();
                    }
                }
                return null;
            }
        };

        task = newTask;

        progressBar.progressProperty().bind(newTask.progressProperty());

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
        Choice = true;
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

                Choice = false;
                trueAns="";
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
        ansA.setDisable(true);
        ansB.setDisable(true);
        ansC.setDisable(true);
        ansD.setDisable(true);
        webView.getEngine().loadContent("");
        noButton.setVisible(true);
        yesButton.setVisible(true);
        resultTable.setVisible(true);
        resultTable.setTextFill(Color.WHITE);
        progressBar.setProgress(0.0);
        if(correctAnswer>=5){
            String tmp = "     So cool, well done bruh";
            tmp+="\n";
            tmp+="                   ";
            tmp+="("+correctAnswer+"/"+totalQuestion+")";
            tmp+="\n";
            tmp+="    ";
            tmp+="continue one more time";
            tmp+="    ";
            resultTable.setText(tmp);
            noButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1));
                    fadeOut.setNode(((javafx.scene.Node) event.getSource()).getScene().getRoot()); // Scene hiện tại
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);

                    // Xử lý khi hoàn thành chuyển đổi
                    fadeOut.setOnFinished(e -> {
                        try {
                            // Tạo scene mới từ FXML
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/openSimpleGame.fxml"));
                            mediaPlayer.stop();
                            Parent scene2Parent = loader.load();
                            Scene scene2 = new Scene(scene2Parent);

                            // Lấy stage hiện tại và đặt scene mới
                            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                            window.setScene(scene2);

                            // Tạo FadeTransition mới cho scene mới (nếu muốn)
                            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1));
                            fadeIn.setNode(scene2.getRoot()); // scene2 là Scene mới
                            fadeIn.setFromValue(0.0);
                            fadeIn.setToValue(1.0);
                            fadeIn.play();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    // Bắt đầu chuyển đổi
                    fadeOut.play();
                }
            });
            yesButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    correctAnswer = 0;
                    progressBar.setProgress(1.0);
                    trueAns="";
                    index = 0;
                    noButton.setVisible(false);
                    yesButton.setVisible(false);
                    resultTable.setVisible(false);
                    ansA.setDisable(false);
                    ansB.setDisable(false);
                    ansC.setDisable(false);
                    ansD.setDisable(false);
                    quiz(new ActionEvent());

                }
            });
            //review.setOnAction(new EventHandler<ActionEvent>() {

            //});
        }
        else{
            String tmp = "       Opps, try again bruh";
            tmp+="\n";
            tmp+="                    ";
            tmp+="("+correctAnswer+"/"+totalQuestion+")";
            tmp+="\n";
            tmp+="    ";
            tmp+="continue one more time";
            tmp+="    ";
            resultTable.setText(tmp);
            noButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1));
                    fadeOut.setNode(((javafx.scene.Node) event.getSource()).getScene().getRoot());
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);

                    fadeOut.setOnFinished(e -> {
                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/openSimpleGame.fxml"));
                            mediaPlayer.stop();
                            Parent scene2Parent = loader.load();
                            Scene scene2 = new Scene(scene2Parent);

                            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                            window.setScene(scene2);

                            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1));
                            fadeIn.setNode(scene2.getRoot()); // scene2 là Scene mới
                            fadeIn.setFromValue(0.0);
                            fadeIn.setToValue(1.0);
                            fadeIn.play();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    fadeOut.play();
                }
            });
            yesButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    correctAnswer = 0;
                    progressBar.setProgress(1.0);
                    trueAns="";
                    index = 0;
                    noButton.setVisible(false);
                    yesButton.setVisible(false);
                    resultTable.setVisible(false);
                    ansA.setDisable(false);
                    ansB.setDisable(false);
                    ansC.setDisable(false);
                    ansD.setDisable(false);
                    quiz(new ActionEvent());
                }
            });
        }
    }

    public void changeOnOff(){
        boolean isMuted = togButton.isSelected();

        mediaPlayer.setMute(isMuted);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView imageView = new ImageView(myImage3);
        myAnchor.getChildren().add(imageView);
        imageView.toBack();
        //webView.getStyleClass().add("webView");
        progressBar.getStyleClass().add("progress-bar");
            togButton.getStyleClass().add("buttonAudio");
            quiz(new ActionEvent());
            resultTable.setVisible(false);
            ansA.getStyleClass().add("button1");
            ansB.getStyleClass().add("button2");
            ansC.getStyleClass().add("button3");
            ansD.getStyleClass().add("button4");
            mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
            noButton.getStyleClass().add("noButton");
            yesButton.getStyleClass().add("yesButton");
            review.getStyleClass().add("reviewButton");
            review.setVisible(false);
            noButton.setVisible(false);
            yesButton.setVisible(false);

    }
}
