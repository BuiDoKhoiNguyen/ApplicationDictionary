package Controllers;

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

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

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
    private Label time;
    private volatile boolean stop = false;

    private static int currtime = 0;

    private static final String FILE_NAME = "data/usedtime.txt";

    private Map<String, String> chartData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chartData = new HashMap<>();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currtime++;
//                System.out.println(currtime);
            }
        }, 1000, 1000);

        timeDisplay();
        XYChart.Series set1 = new XYChart.Series<>();
        getTimeUsage();
        set1.setName("Minute/Day");

        set1.getData().add(new XYChart.Data("Monday", Double.parseDouble(chartData.get("Monday")) / 60.0));
        set1.getData().add(new XYChart.Data("Tuesday", Double.parseDouble(chartData.get("Tuesday")) / 60.0));
        set1.getData().add(new XYChart.Data("Wednesday", Double.parseDouble(chartData.get("Wednesday")) / 60.0));
        set1.getData().add(new XYChart.Data("Thursday", Double.parseDouble(chartData.get("Thursday")) / 60.0));
        set1.getData().add(new XYChart.Data("Friday", Double.parseDouble(chartData.get("Friday")) / 60.0));
        set1.getData().add(new XYChart.Data("Saturday", Double.parseDouble(chartData.get("Saturday")) / 60.0));
        set1.getData().add(new XYChart.Data("Sunday", Double.parseDouble(chartData.get("Sunday")) / 60.0));
        barChart.getData().addAll(set1);
        barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
    }

    public void getTimeUsage() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String dayOfWeek = parts[0];
                    String time = parts[1];

                    chartData.put(dayOfWeek, time);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void recordAppUsage() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            StringBuilder fileContent = new StringBuilder();

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            String currentDay = dateFormat.format(new Date());

            boolean dayFound = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String day = parts[0].trim();
                    int usage = Integer.parseInt(parts[1].trim());

                    if (day.equals(currentDay)) {
                        usage += getCurrtime();
                        dayFound = true;
                    }

                    fileContent.append(day).append(": ").append(usage).append("\n");
                }
            }
            reader.close();

            if (!dayFound) {
                fileContent.append(currentDay).append(": ").append(getCurrtime()).append("\n");
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static int getCurrtime() {
        return currtime;
    }

    public static String getDayOfWeek() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek.toString();
    }

    @FXML
    void buttonAddPicOnAction(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.setInitialDirectory(new File("C:/Users/ASUS/KhoiNguyen"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );

        Stage stage = (Stage) addPic.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgView.setImage(image);
        } else {
            System.out.println("No file has been selected");
        }
    }
}
