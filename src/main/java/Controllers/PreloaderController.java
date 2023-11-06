package Controllers;

import DatabaseConnect.DatabaseConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class PreloaderController implements Initializable {

    @FXML
    public Label lblLoading;
    @FXML
    public static Label lblLoadingg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblLoadingg=lblLoading;
    }

    public void checkFunctions(){
        final String[] message = {""};
        Thread t1 = new Thread(() -> {
            message[0] = "Checking menu controller";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
            try {
                Thread.sleep(2000);
                MenuController menuController = new MenuController();
                System.out.println("Connect to menu,search and translate successfully!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            message[0] = "Checking database connection";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
            try {
                Thread.sleep(2000);
                DatabaseConnection connectionNow = new DatabaseConnection();
                Connection connectDB = connectionNow.getConnection();
                System.out.println("Connect to database successfully!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            message[0] = "Checking data connection";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
            try {
                Thread.sleep(2000);
                DictionaryController dictionaryController = new DictionaryController();
                System.out.println("Connect to dictionary data successfully!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        Thread t4 = new Thread(() -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        Stage stage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        scene.setFill(Color.TRANSPARENT);
                        stage.initStyle(StageStyle.TRANSPARENT);

                        stage.show();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        });

        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
            t4.start();
            t4.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
