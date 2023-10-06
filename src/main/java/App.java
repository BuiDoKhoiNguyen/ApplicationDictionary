import DictionaryApplication.DictionaryCommandLine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("demo.fxml"));
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
//            String css = this.getClass().getResource("style.css").toExternalForm();
//            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

//    public static void main(String[] args) throws FileNotFoundException {
//        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
//        dictionaryCommandLine.dictionaryAdvanced();
//    }
}
