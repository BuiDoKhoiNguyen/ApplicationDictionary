package Controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UsedTimeController implements Initializable {
    @FXML
    private BarChart<?, ?>  barChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private Label dateTime;

    private static final String FILE_NAME = "app_usage.txt";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Calendar calendar = Calendar.getInstance();
    private static int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            dateTime.setText(LocalDateTime.now().format(formatter));
//        }), new KeyFrame(Duration.seconds(1)));
//        clock.setCycleCount(Animation.INDEFINITE);
//        clock.play();
        timeNow();
        XYChart.Series set1 = new XYChart.Series<>();
        set1.setName("Hours/Day");
        int i1 = 0;

        set1.getData().add(new XYChart.Data("Monday", i1++));
        set1.getData().add(new XYChart.Data("Tuesday", 6));
        set1.getData().add(new XYChart.Data("Wednesday", 1));
        set1.getData().add(new XYChart.Data("Thursday", 2));
        set1.getData().add(new XYChart.Data("Friday", 3));
        set1.getData().add(new XYChart.Data("Saturday", 9));
        set1.getData().add(new XYChart.Data("Sunday", 1));
        barChart.getData().addAll(set1);
        barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
    }

    public static void logTimeUsage() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                recordAppUsage();
            }
        }, 0, 60 * 1000); // Ghi thời gian mỗi phút

        // Ghi thời gian mỗi khi ứng dụng bắt đầu
        recordAppUsage();
    }

    public static void recordAppUsage() {
        Date currentTime = new Date();
        calendar.setTime(currentTime);
        int newWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        if (newWeek != currentWeek) {
            resetWeeklyUsage();
            currentWeek = newWeek;
        }

        String formattedTime = dateFormat.format(currentTime);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(formattedTime);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetWeeklyUsage() {
        // Thực hiện reset thời gian sử dụng
        // Ví dụ: Xóa nội dung tệp hoặc thực hiện các hoạt động khác để reset dữ liệu
        System.out.println("Resetting weekly usage data...");
    }

    @FXML
    private Label time;
    private volatile boolean stop = false;

    public void timeNow() {
        Thread thread = new Thread( () -> {
            SimpleDateFormat adf = new SimpleDateFormat("hh:mm:ss");
            while(!stop) {
                try {
                    Thread.sleep(1000);
                } catch(Exception e) {
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
}
