package Controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class controllTypeGame implements Initializable {
    @FXML
    StackPane pane1; // Đây là VBox hoặc StackPane chứa chuỗi văn bản

    @FXML
    TextField textField;
    int x = 0;

    int t = 532;
    int tmp = 0;

    int score = 0;
    String mediaFile = "file:///C:/Users/User/IdeaProjects/BTLOOP/DictionaryApplication/src/main/resources/sources_music_picture/cyber-war.mp3";

    Media media = new Media(mediaFile);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    int WINDOW_WIDTH = 750;
    int WINDOW_HEIGHT = 532;

    String[] DATA ={
            "able",
            "about",
            "above",
            "abuse",
            "accept",
            "accident",
            "accuse",
            "across",
            "actor",
            "admit",
            "adult",
            "advise",
            "affect",
            "afraid",
            "after",
            "again",
            "agree",
            "agency",
            "airport",
            "album",
            "alive",
            "ally",
            "alone",
            "along",
            "along",
            "also",
            "always",
            "amend",
            "amount",
            "ancient",
            "animal",
            "anger",
            "another",
            "answer",
            "appear",
            "appoint",
            "approve",
            "area",
            "argue",
            "army",
            "arrest",
            "arrive",
            "attack",
            "autumn",
            "atom",
            "award",
            "baby",
            "bacteria",
            "ball",
            "base",
            "battle",
            "become",
            "beauty",
            "because",
            "begin",
            "behind",
            "believe",
            "below",
            "best",
            "betray",
            "better",
            "bill",
            "bird",
            "bite",
            "black",
            "blame",
            "bleed",
            "blind",
            "block",
            "blood",
            "blow",
            "boat",
            "body",
            "boil",
            "bomb",
            "bone",
            "border",
            "borrow",
            "bottle",
            "breathe",
            "bridge",
            "brief",
            "budget",
            "bullet",
            "burst",
            "bury",
            "calm",
            "call",
            "camera",
            "camp",
            "cancel",
            "cancer",
            "capital",
            "capture",
            "career",
            "careful",
            "catch",
            "cell",
            "center",
            "chance",
            "charge",
            "chase",
            "cheat",
            "chief",
            "choose",
            "circle",
            "citizen",
            "clash",
            "climate",
            "climb",
            "cloth",
            "coast",
            "combine",
            "command",
            "common",
    };
    String[] WORDS = {"hello", "world", "javafx", "ztype"};
    int WORD_SPEED = 7000;

    //String explodeFile = "file:///C:/Users/User/IdeaProjects/BTLOOP/DictionaryApplication/src/main/resources/sources_music_picture/explode.mp3";
    //AudioClip explodeBoom = new AudioClip(explodeFile);

    String explodeFile = "file:///C:/Users/User/IdeaProjects/BTLOOP/DictionaryApplication/src/main/resources/sources_music_picture/medium-explosion.mp3";

    Media me = new Media(explodeFile);
    MediaPlayer explodeBoom = new MediaPlayer(me);


    Random random,random2;
    Timeline gameLoop;

    List<String> english = new ArrayList<>();
    List<Boolean> check = new ArrayList<>();

    List<String> chk = new ArrayList<>();

    //List<Text> update = new ArrayList<>();

    Set<String> how = new LinkedHashSet<>();
    Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/blackSky.png"));
    Image myImage2 = new Image(getClass().getResourceAsStream("/sources_music_picture/boom.png"));

    Image myImage3 = new Image(getClass().getResourceAsStream("/sources_music_picture/label.png"));

    @FXML
    Label scoreLabel,result,scLabel,bcLabel,scoreL,bScoreL,pLabel,lbsc;

    @FXML
    Button NBut,YBut;

    @FXML
    AnchorPane myAnchor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start();
    }
    public void start(){
        mediaPlayer.seek(mediaPlayer.getStartTime());
        scLabel.setVisible(false);
        bcLabel.setVisible(false);
        scoreL.setVisible(false);
        bScoreL.setVisible(false);
        pLabel.setVisible(false);
        NBut.setVisible(false);
        YBut.setVisible(false);

        lbsc.setVisible(true);
        scoreLabel.setVisible(true);
        textField.setVisible(true);

        scoreLabel.setText(""+score);
        random = new Random();
        random2 = new Random();
        List<String> Word = new ArrayList<>();
        for(int i=0;i<DATA.length;i++){
            Word.add(DATA[i]);
        }
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
        ImageView imageView1 = new ImageView(myImage);
        imageView1.setFitWidth(WINDOW_WIDTH);
        imageView1.setFitHeight(WINDOW_HEIGHT);

        ImageView imageView2 = new ImageView(myImage);
        imageView2.setFitWidth(WINDOW_WIDTH);
        imageView2.setFitHeight(WINDOW_HEIGHT);
        imageView2.setTranslateX(0);
        imageView2.setTranslateY(-WINDOW_HEIGHT);

        myAnchor.getChildren().addAll(imageView2, imageView1);
        imageView1.toBack();
        imageView2.toBack();

        KeyFrame startFrame = new KeyFrame(Duration.ZERO, new KeyValue(imageView1.translateYProperty(), 0), new KeyValue(imageView2.translateYProperty(), -WINDOW_HEIGHT));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(15), new KeyValue(imageView1.translateYProperty(), WINDOW_HEIGHT), new KeyValue(imageView2.translateYProperty(), 0));

        Timeline timeline = new Timeline(startFrame, endFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setRate(1.0); // Điều chỉnh tốc độ của hiệu ứng

        timeline.play();

        gameLoop = new Timeline(new KeyFrame(Duration.millis(WORD_SPEED), event -> {
            //String word = WORDS[random.nextInt(WORDS.length)];
            Collections.shuffle(Word);
            english.add(Word.get(x));
            int index = random2.nextInt(Word.get(x).length());
            String tmp2 = Word.get(x);
            String tmp3 = Character.toString(tmp2.charAt(index));
            tmp2 = tmp2.replace(tmp2.charAt(index),'_');
            chk.add(tmp3);
            check.add(true);
            Text text = new Text(tmp2);
            how.add(tmp2);
            System.out.println(how.size());
            text.setStyle("-fx-font-family: Arial; -fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: white;");
            text.setTranslateY(-140);
            int randomNumber = random.nextInt(501) - 250;
            text.setTranslateX(randomNumber);
            //System.out.println(chk.get(x));
            //System.out.println(english.get(x));
            //System.out.println(x);
            pane1.getChildren().add(text);
            textField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    checkAndRemoveString(textField.getText());
                }
            });

            moveWordDown(text);


        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        gameLoop.play();
    }

    public void moveWordDown(Text word) {
        //x++;
        double wordHeight = word.getBoundsInLocal().getHeight();
        double startY = -140;
        double endY = 313;
        Duration duration = Duration.seconds(10 * (endY - startY) / WINDOW_HEIGHT); // Tính thời gian dựa trên chiều cao của cửa sổ

        TranslateTransition transition = new TranslateTransition(duration, word);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setFromY(startY);
        transition.setToY(endY);

        transition.setOnFinished(event -> {
            /*if(x>=1 && check.get(x-1)==false && tmp!=x){
                tmp=x;
            }
            else{
                System.out.println("something");
                x++;
            }*/
            if(how.contains(word.getText())){
                System.out.println("something");
                gameLoop.stop();
                result();
            }
            //System.out.println("abcd");
            System.out.println(word.getText());
            word.setVisible(false);
        });
        transition.play();
    }

    public void checkAndRemoveString(String input) {
        if(input.equals(chk.get(x))){
            Text textRemove =(Text)pane1.getChildren().get(x);
            how.remove(textRemove.getText());
            check.set(x,false);
            score++;
            scoreLabel.setText(""+score);
            textRemove.setText(english.get(x));

            x++;
            double t = (double) textRemove.getTranslateX();
            double z = (double) textRemove.getTranslateY();
            fire(t,z,textRemove);
            textField.clear();
        }
    }
    public void fire(double targetX, double targetY,Text text) {
        // Tạo hình ảnh tên lửa
        //explodeBoom.seek(explodeBoom.getStartTime());
        Image missileImage = new Image("/sources_music_picture/bullet.png");
        ImageView bullet = new ImageView(missileImage);

        double startX = text.getTranslateX()+365;
        double startY = 532;
        // Đặt tọa độ ban đầu của viên đạn
        bullet.setTranslateX(startX);
        bullet.setTranslateY(startY);

        // Thêm viên đạn vào giao diện
        myAnchor.getChildren().add(bullet);

        // Tạo animation di chuyển tên lửa
        Duration duration = Duration.seconds(0.01);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            bullet.setTranslateY(bullet.getTranslateY() - 2);

            if (bullet.getBoundsInParent().intersects(text.getBoundsInParent())) {
                Duration duration2 = Duration.millis(10);
                KeyValue opacityValue = new KeyValue(text.opacityProperty(), 0);
                KeyFrame keyFrame2 = new KeyFrame(duration2,opacityValue);
                Timeline timeline = new Timeline(keyFrame2);

                timeline.setOnFinished(e -> {

                });

                timeline.play();
                myAnchor.getChildren().removeAll(bullet);
                //MediaPlayer explodeBoom = new MediaPlayer(me);
                //AudioClip explodeBoom = new AudioClip(explodeFile);
                explodeBoom.seek(explodeBoom.getStartTime());
                explodeBoom.play();
                explode(text);
            }
        });

        Timeline missileTimeline = new Timeline(keyFrame);
        missileTimeline.setCycleCount(Timeline.INDEFINITE);
        missileTimeline.play();
    }
    public void explode(Text text){
        String tmp = "/sources_music_picture/boom.png";
        int frameCount = 8;
        int frameWidth = (int) myImage2.getWidth() / 4;
        int frameHeight = (int) myImage2.getHeight() / 2;

        ImageView animationExplode = new ImageView();
        animationExplode.setFitWidth(frameWidth);
        animationExplode.setFitHeight(frameHeight);
        animationExplode.setLayoutX(text.getTranslateX() + 345);
        animationExplode.setLayoutY(text.getTranslateY()+115);
        myAnchor.getChildren().add(animationExplode);

        Image fullImage = new Image(getClass().getResource(tmp).toExternalForm());

        Timeline timeline = new Timeline();
        for (int i = 0; i < frameCount; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis( i*50),
                    event -> {
                        int x = frameIndex * frameWidth;
                        int y = 0;
                        if (frameIndex >= 4) {

                            y = frameHeight;
                            x = (frameIndex - 4) * frameWidth;
                        }

                        PixelReader pixelReader = fullImage.getPixelReader();
                        WritableImage frameImage = new WritableImage(pixelReader, x, y, frameWidth, frameHeight);
                        animationExplode.setImage(frameImage);
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> {
            myAnchor.getChildren().remove(animationExplode);
        });
        timeline.play();
    }
    public void result(){
        pane1.getChildren().clear();
        english.clear();
        chk.clear();
        how.clear();
        check.clear();
        lbsc.setVisible(false);
        scoreLabel.setVisible(false);
        textField.setVisible(false);

        scoreL.setText(""+score);
        scLabel.setVisible(true);
        bcLabel.setVisible(true);
        scoreL.setVisible(true);
        bScoreL.setVisible(true);
        pLabel.setVisible(true);
        NBut.setVisible(true);
        YBut.setVisible(true);
        textField.setVisible(false);
        explode2();
        x=0;
        score=0;
        YBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lbsc.setVisible(true);
                scoreLabel.setVisible(true);
                textField.setVisible(true);

                scLabel.setVisible(false);
                bcLabel.setVisible(false);
                scoreL.setVisible(false);
                bScoreL.setVisible(false);
                pLabel.setVisible(false);
                NBut.setVisible(false);
                YBut.setVisible(false);
                textField.setVisible(false);
                start();
            }
        });

    }
    public void explode2(){
        String tmp = "/sources_music_picture/boom.png";
        int frameCount = 8;
        int frameWidth = (int) myImage2.getWidth() / 4;
        int frameHeight = (int) myImage2.getHeight() / 2;

        ImageView animationExplode = new ImageView();
        animationExplode.setFitWidth(frameWidth);
        animationExplode.setFitHeight(frameHeight);
        animationExplode.setLayoutX(350);
        animationExplode.setLayoutY(470);
        myAnchor.getChildren().add(animationExplode);

        Image fullImage = new Image(getClass().getResource(tmp).toExternalForm());

        Timeline timeline = new Timeline();
        for (int i = 0; i < frameCount; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis( i*50),
                    event -> {
                        int x = frameIndex * frameWidth;
                        int y = 0;
                        if (frameIndex >= 4) {

                            y = frameHeight;
                            x = (frameIndex - 4) * frameWidth;
                        }

                        PixelReader pixelReader = fullImage.getPixelReader();
                        WritableImage frameImage = new WritableImage(pixelReader, x, y, frameWidth, frameHeight);
                        animationExplode.setImage(frameImage);
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> {
            myAnchor.getChildren().remove(animationExplode);
        });
        timeline.play();
        explodeBoom.seek(explodeBoom.getStartTime());
        explodeBoom.play();
    }
}
