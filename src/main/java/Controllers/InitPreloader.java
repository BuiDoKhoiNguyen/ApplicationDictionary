package Controllers;

import Base.NewDictionaryManagement;
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
import java.util.ResourceBundle;

public class InitPreloader implements Initializable {

    @FXML
    public Label lblLoading;
    @FXML
    public static Label lblLoadingg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblLoadingg = lblLoading;
    }

    public void checkFunctions() {

        final String[] message = {""};
        Thread t1 = new Thread(() -> {
            message[0] = "Loading data";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
            try {
                Thread.sleep(2000);
                MenuController menuController = new MenuController();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            message[0] = "Connecting to database";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            message[0] = "Opening login stage";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
        });

        Thread t3 = new Thread(() -> {
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

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
