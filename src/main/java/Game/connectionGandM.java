package Game;

import Controllers.TaskControllers;
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

import static Controllers.PreloaderController.sceneController;

public class connectionGandM extends TaskControllers implements Initializable {

    private Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/aHalf.png"));

    private Image myImage2 = new Image(getClass().getResourceAsStream("/sources_music_picture/universeHalf.jpg"));

    @FXML
    private AnchorPane gameAP;

    @FXML
    private Button chooseGame,TypeGame;

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
        Rectangle whitePane = new Rectangle(800, 570);
        whitePane.setFill(Color.WHITE);
        AnchorPane root = new AnchorPane(whitePane);
        root.setPrefSize(800, 570);
        AnchorPane.setTopAnchor(whitePane, 0.0);
        AnchorPane.setLeftAnchor(root, -50.0);

        gameAP.getChildren().add(root);

        Image image = new Image("/sources_music_picture/black.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(570);
        imageView.setTranslateY(gameAP.getHeight()+38);
        root.getChildren().add(imageView);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), imageView);
        translateTransition.setFromY(gameAP.getHeight()+38);
        translateTransition.setToY(0);
        translateTransition.setOnFinished(event2 -> {
            try {
                root.getChildren().remove(imageView);
                gameAP.getChildren().remove(root);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuTypeG.fxml"));
                Parent scene2Parent = loader.load();
                Scene scene2 = new Scene(scene2Parent);

                Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                window.setScene(scene2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        translateTransition.play();
    }

    public void switchChoose(ActionEvent event) throws Exception {
        Rectangle whitePane = new Rectangle(800, 570);
        whitePane.setFill(Color.WHITE);

        AnchorPane root = new AnchorPane(whitePane);
        root.setPrefSize(800, 570);
        AnchorPane.setTopAnchor(whitePane, 0.0);
        AnchorPane.setLeftAnchor(root, -50.0);

        gameAP.getChildren().add(root);

        Image image = new Image("/sources_music_picture/black.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(570);
        imageView.setTranslateY(gameAP.getHeight()+38);
        root.getChildren().add(imageView);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), imageView);
        translateTransition.setFromY(gameAP.getHeight()+38);
        translateTransition.setToY(0);
        translateTransition.setOnFinished(event2 -> {
            try {
                root.getChildren().remove(imageView);
                gameAP.getChildren().remove(root);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/openSimpleGame.fxml"));
                Parent scene2Parent = loader.load();
                Scene scene2 = new Scene(scene2Parent);

                Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                window.setScene(scene2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        translateTransition.play();
    }

    public AnchorPane getGameAP(){
        return gameAP;
    }
}
