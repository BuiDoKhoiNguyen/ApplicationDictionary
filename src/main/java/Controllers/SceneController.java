package Controllers;


import java.io.IOException;
import java.util.Stack;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SceneController {
    private Stage stage;
    private Scene scene;

    public void switchToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));

        Node sourceNode = (Node) event.getSource();
        Scene sourceScene = sourceNode.getScene();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), sourceScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            stage = (Stage) sourceNode.getScene().getWindow();
            scene = new Scene(root);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            stage.setScene(scene);
        });

        fadeOut.play();
    }

    public void switchToLogin(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(root);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public static Stack<Scene> sceneStack = new Stack<>();

    public static void switchBack(ActionEvent event) throws Exception {
        Scene previousScene = sceneStack.pop();
        if (previousScene != null) {
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(previousScene);
        }
    }

    public static void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(SceneController.class.getResource(fxmlPath));
        sceneStack.push(new Scene(root));

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(sceneStack.peek());
    }

}
