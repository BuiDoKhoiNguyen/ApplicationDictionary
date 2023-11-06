//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.StageStyle;
//
//public class App extends Application {
//
//    @Override
//    public void start(Stage stage) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("FXML/Login.fxml"));
//            Scene scene = new Scene(root);
//
//            scene.setFill(Color.TRANSPARENT);
//            stage.initStyle(StageStyle.TRANSPARENT);
//
//            stage.setScene(scene);
//            stage.show();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}

//import Controllers.PreloaderController;
//import Controllers.LauncherPreloader;
//import javafx.application.Application;
//import javafx.stage.Stage;
//
//
//public class App extends Application {
//    public static Stage primaryStage = null;
//    @Override
//    public void init() {
//        PreloaderController init = new PreloaderController();
//        init.checkFunctions();
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        App.primaryStage = primaryStage;
//
//    }
//
//    public static void main(String[] args) {
//        System.setProperty("javafx.preloader",  LauncherPreloader.class.getCanonicalName());
//        launch(args);
//    }
//
//}


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/menu.fxml"));
        primaryStage.setScene(new Scene(root,800, 600));
        primaryStage.show();
    }
}