package Controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class controllTypeGame implements Initializable {
    @FXML
    StackPane pane1; // Đây là VBox hoặc StackPane chứa chuỗi văn bản

    @FXML
    TextField textField;
    int x = 0;

    int t = 532;
    int tmp = -1;
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 532;
    private static final String[] WORDS = {"hello", "world", "javafx", "ztype"};
    private static final int WORD_SPEED = 7000;

    private Random random;
    private Timeline gameLoop;

    List<String> english = new ArrayList<>();
    List<Boolean> check = new ArrayList<>();
    Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/blackSky.png"));
    Image myImage2 = new Image(getClass().getResourceAsStream("/sources_music_picture/boom.png"));
    @FXML
    ImageView tank;

    @FXML
    AnchorPane myAnchor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        random = new Random();
        ImageView imageView1 = new ImageView(myImage);
        imageView1.setFitWidth(WINDOW_WIDTH);
        imageView1.setFitHeight(WINDOW_HEIGHT);

        ImageView imageView2 = new ImageView(myImage);
        imageView2.setFitWidth(WINDOW_WIDTH);
        imageView2.setFitHeight(WINDOW_HEIGHT);
        imageView2.setTranslateX(0);
        imageView2.setTranslateY(-WINDOW_HEIGHT);

        myAnchor.getChildren().addAll(imageView2, imageView1);
        imageView1.toBack();
        imageView2.toBack();

        KeyFrame startFrame = new KeyFrame(Duration.ZERO, new KeyValue(imageView1.translateYProperty(), 0), new KeyValue(imageView2.translateYProperty(), -WINDOW_HEIGHT));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(15), new KeyValue(imageView1.translateYProperty(), WINDOW_HEIGHT), new KeyValue(imageView2.translateYProperty(), 0));

        Timeline timeline = new Timeline(startFrame, endFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setRate(1.0); // Điều chỉnh tốc độ của hiệu ứng

        timeline.play();




        gameLoop = new Timeline(new KeyFrame(Duration.millis(WORD_SPEED), event -> {
            String word = WORDS[random.nextInt(WORDS.length)];
            english.add(word);
            check.add(true);
            Text text = new Text(word);
            text.setStyle("-fx-font-family: Arial; -fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: white;");
            text.setTranslateY(-140);
            int randomNumber = random.nextInt(501) - 250;
            text.setTranslateX(randomNumber);
            System.out.println(english.get(x));
            System.out.println(x);
            pane1.getChildren().add(text);
            textField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    checkAndRemoveString(textField.getText());
                }
            });

            moveWordDown(text);


        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        gameLoop.play();
    }

    public void moveWordDown(Text word) {
        //x++;
        double wordHeight = word.getBoundsInLocal().getHeight();
        double startY = -140;
        double endY = 392;
        Duration duration = Duration.seconds(25 * (endY - startY) / WINDOW_HEIGHT); // Tính thời gian dựa trên chiều cao của cửa sổ

        TranslateTransition transition = new TranslateTransition(duration, word);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setFromY(startY);
        transition.setToY(endY);

        transition.setOnFinished(event -> {
            if(x>=1 && check.get(x-1)==false && tmp!=x){
                tmp=x;
            }
            else{
                x++;
            }
            word.setVisible(false);
        });
        transition.play();
    }

    public void checkAndRemoveString(String input) {
        if (input.equals(english.get(x))) {
            Text textRemove =(Text)pane1.getChildren().get(x);
            check.set(x,false);
            x++;
            //animateExplosion(textRemove);
            double t = (double) textRemove.getTranslateX();
            double z = (double) textRemove.getTranslateY();
            fire(t,z,textRemove);
            //aimAndFire(x,y,textRemove);
            textField.clear();
        }
    }
    public void fire(double targetX, double targetY,Text text) {
        // Tạo hình ảnh tên lửa
        Image missileImage = new Image("/sources_music_picture/bullet.png");
        ImageView bullet = new ImageView(missileImage);

        double startX = text.getTranslateX()+365;
        double startY = 532;
        // Đặt tọa độ ban đầu của viên đạn
        bullet.setTranslateX(startX);
        bullet.setTranslateY(startY);

        // Thêm viên đạn vào giao diện
        myAnchor.getChildren().add(bullet);

        // Tạo animation di chuyển tên lửa
        Duration duration = Duration.seconds(0.01);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            // Di chuyển viên đạn lên
            bullet.setTranslateY(bullet.getTranslateY() - 1);

            // Kiểm tra va chạm viên đạn với chuỗi text
            if (bullet.getBoundsInParent().intersects(text.getBoundsInParent())) {
                // Kích hoạt hiệu ứng nổ tại vị trí chuỗi text
                // Xóa chuỗi text và tên lửa
                Duration duration2 = Duration.millis(10);
                KeyValue opacityValue = new KeyValue(text.opacityProperty(), 0);
                KeyFrame keyFrame2 = new KeyFrame(duration2,opacityValue);
                Timeline timeline = new Timeline(keyFrame2);

                // Xóa từ sau khi hoàn thành hiệu ứng
                timeline.setOnFinished(e -> {

                });

                timeline.play();
                myAnchor.getChildren().removeAll(bullet);
                explode(text); // Hàm hiệu ứng nổ
            }
        });

        Timeline missileTimeline = new Timeline(keyFrame);
        missileTimeline.setCycleCount(Timeline.INDEFINITE); // Lặp vô hạn
        missileTimeline.play();
    }
    public void animateExplosion(Text text) {
        //text.getStyleClass().add("text-explosion");
        Duration duration = Duration.millis(1000);

        KeyValue scaleXValue = new KeyValue(text.scaleXProperty(), 2);
        KeyValue scaleYValue = new KeyValue(text.scaleYProperty(), 2);
        KeyValue opacityValue = new KeyValue(text.opacityProperty(), 0);

        KeyFrame keyFrame = new KeyFrame(duration, scaleXValue, scaleYValue, opacityValue);
        Timeline timeline = new Timeline(keyFrame);

        // Xóa từ sau khi hoàn thành hiệu ứng
        timeline.setOnFinished(event -> {
            //english.remove(text.getText());
            //pane1.getChildren().remove(text);
        });

        timeline.play();
    }
    public void explode(Text text){
        String tmp = "/sources_music_picture/boom.png";
        int frameCount = 8;
        int frameWidth = (int) myImage2.getWidth() / 4;
        int frameHeight = (int) myImage2.getHeight() / 2;

        ImageView animationExplode = new ImageView();
        animationExplode.setFitWidth(frameWidth);
        animationExplode.setFitHeight(frameHeight);
        animationExplode.setLayoutX(text.getTranslateX() + 345);
        animationExplode.setLayoutY(text.getTranslateY()+115);
        myAnchor.getChildren().add(animationExplode);

        // Tạo một Image từ file hình ảnh
        Image fullImage = new Image(getClass().getResource(tmp).toExternalForm());

        Timeline timeline = new Timeline();
        for (int i = 0; i < frameCount; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis( i*100), // Thời gian giữa mỗi frame (có thể điều chỉnh)
                    event -> {
                        int x = frameIndex * frameWidth;
                        int y = 0; // Vị trí y tại hàng đầu tiên của sprite sheet
                        if (frameIndex >= 4) {
                            // Nếu frameIndex >= 4, sử dụng hàng thứ hai của sprite sheet
                            y = frameHeight;
                            x = (frameIndex - 4) * frameWidth;
                        }

                        // Cắt phần cụ thể từ hình ảnh gốc
                        PixelReader pixelReader = fullImage.getPixelReader();
                        WritableImage frameImage = new WritableImage(pixelReader, x, y, frameWidth, frameHeight);
                        animationExplode.setImage(frameImage);
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(1); // Chạy một lần (hoặc chỉnh lại cho số lần cần)
        timeline.setOnFinished(event -> {
            myAnchor.getChildren().remove(animationExplode);
        });
        timeline.play();
    }
    public void aimAndFire(double targetX, double targetY, Text text) {
        // Tính góc xoay cần thiết để quay xe tăng về phía mục tiêu
        double tankX = tank.getTranslateX() + tank.getBoundsInLocal().getWidth() / 2;
        double tankY = tank.getTranslateY() + tank.getBoundsInLocal().getHeight() / 2;
        double deltaX = targetX - tankX;
        double deltaY = targetY - tankY;
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

        // Quay xe tăng về góc này
        tank.setRotate(angle);

        // Tạo viên đạn và di chuyển nó từ nòng súng
        ImageView bullet = new ImageView(new Image("/sources_music_picture/bullet.png"));
        bullet.setTranslateX(tankX);
        bullet.setTranslateY(tankY);
        myAnchor.getChildren().add(bullet);

        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double bulletSpeed = 1.0; // Tốc độ của viên đạn
        double duration = distance / bulletSpeed;

        // Di chuyển viên đạn đến mục tiêu và kiểm tra va chạm
        TranslateTransition bulletTransition = new TranslateTransition(Duration.seconds(duration), bullet);
        bulletTransition.setToX(targetX);
        bulletTransition.setToY(targetY);

        bulletTransition.setOnFinished(event -> {
            myAnchor.getChildren().remove(bullet);
            checkCollision(text, targetX, targetY); // Kiểm tra va chạm và thực hiện hiệu ứng nổ
        });

        bulletTransition.play();
    }
    public void checkCollision(Text text, double targetX, double targetY) {
        Bounds textBounds = text.getBoundsInParent();
        double textX = textBounds.getMinX();
        double textY = textBounds.getMinY();
        double textWidth = textBounds.getWidth();
        double textHeight = textBounds.getHeight();

        // Kiểm tra xem viên đạn (text) có va chạm với mục tiêu (targetX, targetY) không.
        if (textX <= targetX && targetX <= textX + textWidth && textY <= targetY && targetY <= textY + textHeight) {
            // Nếu có va chạm, thực hiện hiệu ứng nổ.
            animateExplosion(text);
        }
    }
}
