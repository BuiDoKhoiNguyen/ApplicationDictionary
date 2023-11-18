package Controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import Controllers.MenuController;
public class menuTypeGame implements Initializable {

    @FXML
    Button play;
    @FXML
    Button music;

    @FXML
    AnchorPane myArchor;

    Timeline gameLoop;

    Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/blackSky.png"));

    Image myImage2 = new Image(getClass().getResourceAsStream("/sources_music_picture/boom.png"));

    String[] WORDS = {"hello", "world", "javafx", "ztype"};

    @FXML
    StackPane pane1;

    int WINDOW_WIDTH = 750;
    int WINDOW_HEIGHT = 532;

    int WORD_SPEED = 2000;

    Random random;

    public static boolean audi = true;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start();
    }
    public void playG(ActionEvent event) throws Exception {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1));
        fadeOut.setNode(((javafx.scene.Node) event.getSource()).getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/typeGame.fxml"));
                Parent scene2Parent = loader.load();
                Scene scene2 = new Scene(scene2Parent);

                Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                window.setScene(scene2);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1));
                fadeIn.setNode(scene2.getRoot());
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        fadeOut.play();
    }
    public void changeMusic(){
        if(music.getText().equals("MUSIC: ON")){
            System.out.println("sth");
            music.setText("MUSIC: OFF");
            audi = false;
        }
        else{
            music.setText("MUSIC: ON");
            audi = true;
        }
    }

    public void switchMenu(ActionEvent event) throws Exception{
      //  SceneController.switchBack(event);
        MenuController.switchG = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/menu.fxml"));
        Parent scene2Parent = loader.load();
        Scene scene2 = new Scene(scene2Parent);

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
    }

    public static boolean isAudi() {
        return audi;
    }
    public void start(){
        if(audi==true){
            music.setText("MUSIC: ON");
        }
        else{
            music.setText("MUSIC: OFF");
        }
        random = new Random();
        ImageView imageView1 = new ImageView(myImage);
        imageView1.setFitWidth(WINDOW_WIDTH);
        imageView1.setFitHeight(WINDOW_HEIGHT);

        ImageView imageView2 = new ImageView(myImage);
        imageView2.setFitWidth(WINDOW_WIDTH);
        imageView2.setFitHeight(WINDOW_HEIGHT);
        imageView2.setTranslateX(0);
        imageView2.setTranslateY(-WINDOW_HEIGHT);

        myArchor.getChildren().addAll(imageView2, imageView1);
        imageView1.toBack();
        imageView2.toBack();

        KeyFrame startFrame = new KeyFrame(Duration.ZERO, new KeyValue(imageView1.translateYProperty(), 0), new KeyValue(imageView2.translateYProperty(), -WINDOW_HEIGHT));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(15), new KeyValue(imageView1.translateYProperty(), WINDOW_HEIGHT), new KeyValue(imageView2.translateYProperty(), 0));

        Timeline timeline = new Timeline(startFrame, endFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setRate(1.0);

        timeline.play();
        gameLoop = new Timeline(new KeyFrame(Duration.millis(WORD_SPEED), event -> {
            String word = WORDS[random.nextInt(WORDS.length)];
            Text text = new Text(word);
            text.setStyle("-fx-font-family: Arial; -fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: white;");
            text.setTranslateY(-140);
            int randomNumber = random.nextInt(651) - 325;
            text.setTranslateX(randomNumber);
            pane1.getChildren().add(text);
            moveWordDown(text);



        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        gameLoop.play();
    }
    public void moveWordDown(Text word) {
        //x++;
        double wordHeight = word.getBoundsInLocal().getHeight();
        double startY = -140;
        double endY = 100;
        Duration duration = Duration.seconds(10 * (endY - startY) / WINDOW_HEIGHT);

        TranslateTransition transition = new TranslateTransition(duration, word);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setFromY(startY);
        transition.setToY(endY);

        double endY2=50;
        Duration duration2 = Duration.seconds(10 * (endY2 - startY) / WINDOW_HEIGHT);
        TranslateTransition transition2 = new TranslateTransition(duration2);
        transition2.setInterpolator(Interpolator.LINEAR);
        transition2.setFromY(startY);
        transition2.setToY(endY2);
        transition2.setOnFinished(event -> {
            fire(word.getTranslateX(),word.getTranslateY(),word);
        });

        transition.setOnFinished(event -> {
            pane1.getChildren().remove(word);
            word.setVisible(false);
        });
        transition.play();
        transition2.play();
    }
    public void fire(double targetX, double targetY,Text text) {
        Image missileImage = new Image("/sources_music_picture/bullet.png");
        ImageView bullet = new ImageView(missileImage);

        double startX = text.getTranslateX()+365;
        double startY = 532;
        bullet.setTranslateX(startX);
        bullet.setTranslateY(startY);

        myArchor.getChildren().add(bullet);

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
                myArchor.getChildren().removeAll(bullet);
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
        myArchor.getChildren().add(animationExplode);

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
            myArchor.getChildren().remove(animationExplode);
        });
        timeline.play();
    }
}
