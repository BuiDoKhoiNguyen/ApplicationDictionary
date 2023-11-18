package Game;

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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import static Controllers.PreloaderController.sceneController;

public class connectionGandM implements Initializable {

    Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/aHalf.png"));

    Image myImage2 = new Image(getClass().getResourceAsStream("/sources_music_picture/universeHalf.jpg"));

    @FXML
    AnchorPane gameAP;

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

    public void switchType(ActionEvent event) throws Exception {
//        sceneController.switchScene(event,"/fxml/menuTypeG.fxml");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuTypeG.fxml"));
        Parent sceneParent = loader.load();
        Scene scene = new Scene(sceneParent);

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }

    public void switchChoose(ActionEvent event) throws Exception{
//        sceneController.switchScene(event,"/fxml/openSimpleGame");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/openSimpleGame.fxml"));
        Parent sceneParent = loader.load();
        Scene scene = new Scene(sceneParent);

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
}
