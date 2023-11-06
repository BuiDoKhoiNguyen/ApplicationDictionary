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
    private BarChart<?, ?>  barChart;
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

    private static final String FILE_NAME = "data/usedtime.txt";
    private static Calendar calendar = Calendar.getInstance();
    private static int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

    private Map<String, String> chartData = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currtime++;
                System.out.println(currtime);
            }
        }, 1000, 1000);

        timeDisplay();
        XYChart.Series set1 = new XYChart.Series<>();
        getTimeUsage();
        set1.setName("Hours/Day");

        set1.getData().add(new XYChart.Data("Monday", 4));
        set1.getData().add(new XYChart.Data("Tuesday", 6));
        set1.getData().add(new XYChart.Data("Wednesday", 1));
        set1.getData().add(new XYChart.Data("Thursday", 2));
        set1.getData().add(new XYChart.Data("Friday", 3));
        set1.getData().add(new XYChart.Data("Saturday", 9));
        set1.getData().add(new XYChart.Data("Sunday", 1));
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

    public void recordAppUsage() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
            writer.write(getDayOfWeek() + ": " + getCurrtime());
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetWeeklyUsage() {

    }

    @FXML
    private Label time;
    private volatile boolean stop = false;

    private static int currtime = 0;

    public void timeDisplay() {
        Thread thread = new Thread( () -> {
            SimpleDateFormat adf = new SimpleDateFormat("hh:mm:ss");
            while(!stop) {
                try {
                    Thread.sleep(1000);
                } catch(Exception e) {
                    System.out.println  (e);
                }
                final String timeNow = adf.format(new Date());
                Platform.runLater(() -> {
                    time.setText(timeNow);
                });
            }
        });
        thread.start();
    }

    public int getCurrtime() {
        return currtime;
    }

    public String getDayOfWeek() {
        DayOfWeek dayOfWeek =  LocalDate.now().getDayOfWeek();
        return dayOfWeek.toString();
    }

    @FXML
    void buttonAddPicOnAction(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:\\Users\\ASUS\\KhoiNguyen\\Máy tính"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image","*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files","*.jpg","*.png"));
        Stage stage = (Stage) addPic.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile != null){
            Image image = new Image(selectedFile.getPath());
            imgView.setImage(image);
        }else{
            System.out.println("No file has been selected");
        }
    }
}
