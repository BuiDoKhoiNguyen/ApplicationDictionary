package Controllers;

import DatabaseConnect.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

import static Controllers.PreloaderController.connectDB;

public class ProfileController extends TaskControllers implements Initializable {
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
    private ProgressIndicator skill;
    @FXML
    private Label time,hello,account,level, average, total;
    private volatile boolean stop = false;

    public static int currtime = 0;

    private static Map<String, Integer> chartData;
    private int totalTime, averageTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection.startWeeklyReset(LoginController.user.getUserId());
        hello.setText(LoginController.user.getName());
        account.setText(LoginController.user.getUsername());
        int temp = DatabaseConnection.countProblemSolved(LoginController.user.getUserId());
        String tmp;
        if(temp < 200) {
            tmp = "fresher";
        } else if (200<temp && temp<1000 ) {
            tmp = "junior";
        } else if (1000 < temp && temp < 5000) {
            tmp = "senior";
        } else tmp = "master";
        level.setText(tmp);

        skill.setProgress((double) DatabaseConnection.countProblemSolved(LoginController.user.getUserId())/1000);

        if (LoginController.user.getProfileImage() != null) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(LoginController.user.getProfileImage());
            Image profileImage = new Image(byteArrayInputStream);
            imgView.setImage(profileImage);
        }

        chartData = DatabaseConnection.getTimeUsage(LoginController.user.getUserId());
        chartData.forEach((key, value) -> {
            totalTime += value;
        });
        averageTime = totalTime/7;
        average.setText(String.valueOf(averageTime/60) + " minutes");
        total.setText(String.valueOf(totalTime/60) + " minutes");

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
            try {
                FileInputStream inputStream = new FileInputStream(selectedFile);
                byte[] imageData = new byte[(int) selectedFile.length()];
                inputStream.read(imageData);

                Blob imageBlob = new SerialBlob(imageData);

                DatabaseConnection.updateProfilePicture(LoginController.user.getUserId(), imageBlob);

                Image image = new Image(selectedFile.toURI().toString());
                imgView.setImage(image);

                System.out.println("Successfully added photo from " + selectedFile.getAbsolutePath().replace("\\","/"));
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Failed to add photo");
            }
        } else {
            System.out.println("No file has been selected");
        }
    }
}
