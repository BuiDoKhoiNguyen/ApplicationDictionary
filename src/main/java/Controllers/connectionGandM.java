package Controllers;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class connectionGandM implements Initializable {

    Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/aHalf.png"));

    Image myImage2 = new Image(getClass().getResourceAsStream("/sources_music_picture/universeHalf.jpg"));

    @FXML
    public AnchorPane gameAP;

    @FXML
    Button chooseGame,TypeGame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageView = new ImageView(myImage);
        ImageView imageView2 = new ImageView(myImage2);
        imageView.setLayoutY(267);
        imageView2.setLayoutY(0);
        gameAP.getChildren().add(imageView);
        gameAP.getChildren().add(imageView2);
        imageView.toBack();
        imageView2.toBack();
    }
    public void switchType(ActionEvent event) throws Exception{
        Rectangle whitePane = new Rectangle(gameAP.getWidth(), gameAP.getHeight());
        whitePane.setFill(Color.WHITE);

        // Tạo màn hình menu với nền đen (không hiển thị ban đầu)
        AnchorPane menuPane = new AnchorPane();
        menuPane.setStyle("-fx-background-color: black;");
        menuPane.setPrefSize(gameAP.getWidth(), gameAP.getHeight());
        menuPane.setOpacity(0.0);

        // Tạo màn hình
        AnchorPane root = new AnchorPane(whitePane, menuPane);
        root.setPrefSize(gameAP.getWidth(), gameAP.getHeight());
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);

        // Thêm root vào anchorPane
        gameAP.getChildren().add(root);

        // Thực hiện hiệu ứng chuyển từ trắng sang đen
        FillTransition fillTransition = new FillTransition(Duration.seconds(2), whitePane, Color.WHITE, Color.BLACK);
        fillTransition.setOnFinished(event2 -> {
            // Sau khi kết thúc hiệu ứng chuyển màu, hiện menu
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), menuPane);
            fadeTransition.setToValue(1.0);
            fadeTransition.setOnFinished(fadeEvent -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/menuTypeG.fxml"));
                    Parent scene2Parent = loader.load();
                    Scene scene2 = new Scene(scene2Parent);

                    Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fadeTransition.play();
        });
        fillTransition.play();
    }

    public void switchChoose(ActionEvent event) throws Exception {
        // Tạo màn hình menu với nền đen (không hiển thị ban đầu)
        AnchorPane menuPane = new AnchorPane();
        menuPane.setStyle("-fx-background-color: black;");
        menuPane.setPrefSize(gameAP.getWidth(), 0); // Độ cao ban đầu là 0
        menuPane.setOpacity(0.0);

        // Tạo màn hình ban đầu với nền trắng
        Rectangle whitePane = new Rectangle(gameAP.getWidth(), gameAP.getHeight());
        whitePane.setFill(Color.WHITE);

        // Tạo màn hình
        AnchorPane root = new AnchorPane(menuPane, whitePane);
        root.setPrefSize(gameAP.getWidth(), gameAP.getHeight());
        AnchorPane.setTopAnchor(menuPane, 0.0);
        AnchorPane.setTopAnchor(whitePane, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);

        // Thêm root vào anchorPane
        gameAP.getChildren().add(root);

        // Thực hiện hiệu ứng chuyển từ trắng sang đen
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), menuPane);
        translateTransition.setFromY(gameAP.getHeight());
        translateTransition.setToY(0);
        translateTransition.setOnFinished(event2 -> {
            // Hiện menu
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), menuPane);
            fadeTransition.setToValue(1.0);
            fadeTransition.setOnFinished(fadeEvent -> {
                // Chuyển sang màn hình chính (openSimpleGame.fxml)
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/openSimpleGame.fxml"));
                    Parent scene2Parent = loader.load();
                    Scene scene2 = new Scene(scene2Parent);

                    Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene2);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("something");
                    // Xử lý hoặc hiển thị thông báo lỗi tùy thuộc vào yêu cầu của bạn
                }
            });
            fadeTransition.play();
        });
        translateTransition.play();
    }

    public AnchorPane getGameAP(){
        return gameAP;
    }
    /*public void switchType(ActionEvent event) throws Exception {
        SceneController.switchScene(event,"/fxml/menuTypeG.fxml");
    }*/

    /*public void switchChoose(ActionEvent event) throws Exception{
        SceneController.switchScene(event,"/fxml/openSimpleGame");
    }*/
}
