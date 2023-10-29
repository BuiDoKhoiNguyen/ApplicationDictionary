package Controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SwitchGame implements Initializable {
    @FXML
    private Button playButton;

    String mediaFile = "file:///C:/Users/User/IdeaProjects/BTLOOP/DictionaryApplication/src/main/resources/sources_music_picture/good-night-160166.mp3";

    Media media = new Media(mediaFile);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    public void playGame(ActionEvent event) throws Exception {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1));
        fadeOut.setNode(((javafx.scene.Node) event.getSource()).getScene().getRoot()); // Scene hiện tại
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Xử lý khi hoàn thành chuyển đổi
        fadeOut.setOnFinished(e -> {
            try {
                // Tạo scene mới từ FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/simpleGame.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaPlayer.play();
        playButton.getStyleClass().add("buttonPlay");

    }
}
