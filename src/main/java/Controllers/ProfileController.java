package Controllers;

import DatabaseConnect.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static Controllers.PreloaderController.connectDB;

public class ProfileController implements Initializable {
    @FXML
    private BarChart<?, ?> barChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private Label dateTime;
    @FXML
    private Button addPic;
    @FXML
    private ImageView imgView;

    @FXML
    private Label time,hello,account,level;
    private volatile boolean stop = false;

    public static int currtime = 0;

    private static final String FILE_NAME = "data/usedtime.txt";

    private static Map<String, Integer> chartData;
    private int totalTime, averageTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hello.setText("Welcome back " + LoginController.user.getName());
        account.setText("Account: " + LoginController.user.getUsername());
        level.setText("Your level vocabulary:" /** To do */);
        if (LoginController.user.getProfileImage() != null) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(LoginController.user.getProfileImage());
            Image profileImage = new Image(byteArrayInputStream);
            imgView.setImage(profileImage);
        }

        chartData = DatabaseConnection.getTimeUsage(LoginController.user.getUserId());

        Timer timer = new Timer();
        if(LoginController.isLogin) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    currtime++;
                }
            }, 1000, 1000);
        }


        timeDisplay();
        XYChart.Series set1 = new XYChart.Series<>();
        set1.setName("Minutes/Day");

        set1.getData().add(new XYChart.Data("Monday", (double)chartData.get("Monday") / 60.0));
        set1.getData().add(new XYChart.Data("Tuesday", (double)chartData.get("Tuesday") / 60.0));
        set1.getData().add(new XYChart.Data("Wednesday", (double)chartData.get("Wednesday") / 60.0));
        set1.getData().add(new XYChart.Data("Thursday", (double)chartData.get("Thursday") / 60.0));
        set1.getData().add(new XYChart.Data("Friday", (double)chartData.get("Friday") / 60.0));
        set1.getData().add(new XYChart.Data("Saturday", (double)chartData.get("Saturday") / 60.0));
        set1.getData().add(new XYChart.Data("Sunday", (double)chartData.get("Sunday") / 60.0));
        barChart.getData().addAll(set1);
        barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
    }

    public static void recordAppUsage() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        String currentDay = dateFormat.format(new Date());
        int usageTime = chartData.get(currentDay);
        DatabaseConnection.updateTimeUsage(LoginController.user.getUserId(), currentDay, usageTime + currtime);
    }

    public static void resetWeeklyUsage() {

    }

    public void timeDisplay() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat adf = new SimpleDateFormat("hh:mm:ss");
            while (!stop) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timeNow = adf.format(new Date());
                Platform.runLater(() -> {
                    time.setText(timeNow);
                });
            }
        });
        thread.start();
    }

    public static String getDayOfWeek() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek.toString();
    }


    @FXML
    public void buttonAddPicOnAction(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.setInitialDirectory(new File("D:/"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );

        Stage stage = (Stage) addPic.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgView.setImage(image);
            DatabaseConnection.updateProfilePicture(LoginController.user.getUserId(),selectedFile.getAbsolutePath().replace("\\","/"));
            System.out.println("Successfully added photo from " + selectedFile.getAbsolutePath().replace("\\","/"));
        } else {
            System.out.println("No file has been selected");
        }
    }
}
