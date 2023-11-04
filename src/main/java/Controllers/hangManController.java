package Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class hangManController implements Initializable {

    @FXML
    private StackPane pane; // Đây là VBox hoặc StackPane chứa chuỗi văn bản

    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 532;
    private static final String[] WORDS = {"Hello", "World", "JavaFX", "ZTYPE"};
    private static final int WORD_SPEED = 300; // Tốc độ rơi của từ (1 từ mỗi giây)

    private Random random;
    private Timeline gameLoop;

    List<String> english;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        random = new Random();

        gameLoop = new Timeline(new KeyFrame(Duration.millis(WORD_SPEED), event -> {
            // Tạo một từ ngẫu nhiên
            String word = WORDS[random.nextInt(WORDS.length)];
            Text text = new Text(word);
            text.setTranslateY(-266);
            int randomNumber = random.nextInt(501) - 250;
            text.setTranslateX(randomNumber); // Random vị trí trên trục X

            // Bắt đầu di chuyển từ trên xuống
            moveWordDown(text);

            pane.getChildren().add(text);
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        gameLoop.play();
    }

    private void moveWordDown(Text word) {
        double wordHeight = word.getBoundsInLocal().getHeight();
        double startY = -266;
        double endY = WINDOW_HEIGHT;
        Duration duration = Duration.seconds(10*(endY - startY) / WINDOW_HEIGHT); // Tính thời gian dựa trên chiều cao của cửa sổ

        TranslateTransition transition = new TranslateTransition(duration, word);
        transition.setFromY(startY);
        transition.setToY(endY);

        transition.setOnFinished(event -> {
            // Đã đạt đáy, loại bỏ từ
            pane.getChildren().remove(word);
        });

        transition.play();
    }
}
