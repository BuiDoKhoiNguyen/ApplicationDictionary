package Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class controllTypeGame implements Initializable {
    @FXML
    StackPane pane1; // Đây là VBox hoặc StackPane chứa chuỗi văn bản

    @FXML
    TextField textField;
    int x = 0;

    int tmp = -1;
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 532;
    private static final String[] WORDS = {"hello", "world", "javafx", "ztype"};
    private static final int WORD_SPEED = 5000;

    private Random random;
    private Timeline gameLoop;

    List<String> english = new ArrayList<>();
    List<Boolean> check = new ArrayList<>();
    Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/universe.jpg"));

    @FXML
    AnchorPane myAnchor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        random = new Random();
        ImageView imageView = new ImageView(myImage);
        myAnchor.getChildren().add(imageView);
        imageView.toBack();
        gameLoop = new Timeline(new KeyFrame(Duration.millis(WORD_SPEED), event -> {
            String word = WORDS[random.nextInt(WORDS.length)];
            english.add(word);
            check.add(true);
            Text text = new Text(word);
            text.setStyle("-fx-font-family: Arial; -fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: white;");
            text.setTranslateY(-140);
            int randomNumber = random.nextInt(501) - 250;
            text.setTranslateX(randomNumber);
            System.out.println(english.get(x));
            System.out.println(x);
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

    private void moveWordDown(Text word) {
        //x++;
        double wordHeight = word.getBoundsInLocal().getHeight();
        double startY = -140;
        double endY = 392;
        Duration duration = Duration.seconds(30 * (endY - startY) / WINDOW_HEIGHT); // Tính thời gian dựa trên chiều cao của cửa sổ

        TranslateTransition transition = new TranslateTransition(duration, word);
        transition.setFromY(startY);
        transition.setToY(endY);

        transition.setOnFinished(event -> {
            if(x>=1 && check.get(x-1)==false && tmp!=x){
                tmp=x;
            }
            else{
                x++;
            }
            word.setVisible(false);
        });
        transition.play();
    }

    private void checkAndRemoveString(String input) {
        if (input.equals(english.get(x))) {
            Text textRemove =(Text)pane1.getChildren().get(x);
            check.set(x,false);
            x++;
            animateExplosion(textRemove);
            textField.clear();
        }
    }
    public void animateExplosion(Text text) {
        //text.getStyleClass().add("text-explosion");
        Duration duration = Duration.millis(1000);

        KeyValue scaleXValue = new KeyValue(text.scaleXProperty(), 2);
        KeyValue scaleYValue = new KeyValue(text.scaleYProperty(), 2);
        KeyValue opacityValue = new KeyValue(text.opacityProperty(), 0);

        KeyFrame keyFrame = new KeyFrame(duration, scaleXValue, scaleYValue, opacityValue);
        Timeline timeline = new Timeline(keyFrame);

        // Xóa từ sau khi hoàn thành hiệu ứng
        timeline.setOnFinished(event -> {
            //english.remove(text.getText());
            //pane1.getChildren().remove(text);
        });

        timeline.play();
    }
}
