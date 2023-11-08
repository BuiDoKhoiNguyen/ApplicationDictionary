
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/menuTypeG.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("FXML/typeGame.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("FXML/openSimpleGame.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("FXML/Login.fxml"));

            Scene scene = new Scene(root);

            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
