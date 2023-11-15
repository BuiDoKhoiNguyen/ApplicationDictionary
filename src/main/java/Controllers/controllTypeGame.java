package Controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.util.*;

public class controllTypeGame implements Initializable {
    @FXML
    StackPane pane1;

    @FXML
    TextField textField;
    int x = 0;

    int t = 532;
    int tmp = 0;

    int score = 0;
    String mediaFile = "/sources_music_picture/cyber-war.mp3";
    URL resourceUrl = getClass().getResource(mediaFile);
    String urlString = resourceUrl.toExternalForm();
    private Media media = new Media(urlString);
    private MediaPlayer mediaPlayer = new MediaPlayer(media);

    int WINDOW_WIDTH = 750;
    int WINDOW_HEIGHT = 532;

    String[] DATA ={
            "able",
            "about",
            "above",
            "abuse",
            "accept",
            "accident",
            "accuse",
            "across",
            "actor",
            "admit",
            "adult",
            "advise",
            "affect",
            "afraid",
            "after",
            "again",
            "agree",
            "agency",
            "airport",
            "album",
            "alive",
            "ally",
            "alone",
            "along",
            "along",
            "also",
            "always",
            "amend",
            "amount",
            "ancient",
            "animal",
            "anger",
            "another",
            "answer",
            "appear",
            "appoint",
            "approve",
            "area",
            "argue",
            "army",
            "arrest",
            "arrive",
            "attack",
            "autumn",
            "atom",
            "award",
            "baby",
            "bacteria",
            "ball",
            "base",
            "battle",
            "become",
            "beauty",
            "because",
            "begin",
            "behind",
            "believe",
            "below",
            "best",
            "betray",
            "better",
            "bill",
            "bird",
            "bite",
            "black",
            "blame",
            "bleed",
            "blind",
            "block",
            "blood",
            "blow",
            "boat",
            "body",
            "boil",
            "bomb",
            "bone",
            "border",
            "borrow",
            "bottle",
            "breathe",
            "bridge",
            "brief",
            "budget",
            "bullet",
            "burst",
            "bury",
            "calm",
            "call",
            "camera",
            "camp",
            "cancel",
            "cancer",
            "capital",
            "capture",
            "career",
            "careful",
            "catch",
            "cell",
            "center",
            "chance",
            "charge",
            "chase",
            "cheat",
            "chief",
            "choose",
            "circle",
            "citizen",
            "clash",
            "climate",
            "climb",
            "cloth",
            "coast",
            "combine",
            "command",
            "common",
            "compare",
            "complex",
            "confirm",
            "conflict",
            "contact",
            "control",
            "crisis",
            "crash",
            "credit",
            "crowd",
            "custom",
            "damage",
            "danger",
            "deaf",
            "dabate",
            "decide",
            "debt",
            "defeat",
            "defend",
            "define",
            "demand",
            "deny",
            "desert",
            "design",
            "detail",
            "device",
            "direct",
            "dirt",
            "disaster",
            "dismiss",
            "dive",
            "duty",
            "earth",
            "east",
            "edge",
            "effect",
            "effort",
            "elect",
            "emotion",
            "employ",
            "empty",
            "equal",
            "escape",
            "estimate",
            "excuse",
            "exist",
            "expand",
            "extreme",
            "fact",
            "fail",
            "false",
            "famous",
            "father",
            "fear",
            "feed",
            "female",
            "fence",
            "field",
            "final",
            "flag",
            "flood",
            "float",
            "follow",
            "force",
            "forgive",
            "former",
            "fuel",
            "forward",
            "fruit",
            "game",
            "gift",
            "general",
            "glass",
            "govern",
            "gravity",
            "grind",
            "ground",
            "guide",
            "guilty",
            "half",
            "hang",
            "harvest",
            "heal",
            "hero",
            "history",
            "hole",
            "honor",
            "horse",
            "hostile",
            "hotel",
            "humor",
            "hurt",
            "idea",
            "image",
            "ignore",
            "import",
            "improve",
            "include",
            "industry",
            "infect",
            "inform",
            "inject",
            "injure",
            "increase",
            "insane",
            "insect",
            "insult",
            "interest",
            "invest",
            "invite",
            "island",
            "issue",
            "jewel",
            "join",
            "joke",
            "jump",
            "just",
            "justice",
            "kidnap",
            "kind",
            "knife",
            "know",
            "labor",
            "lack",
            "lake",
            "laser",
            "laugh",
            "launch",
            "lead",
            "leak",
            "legal",
            "letter",
            "life",
            "level",
            "limit",
            "liquid",
            "listen",
            "loan",
            "local",
            "loyal",
            "luck",
            "machine",
            "magnet",
            "major",
            "male",
            "mark",
            "mass",
            "marry",
            "mate",
            "mayor",
            "meat",
            "meal",
            "member",
            "mental",
            "metal",
            "method",
            "middle",
            "minor",
            "mineral",
            "miss",
            "model",
            "money",
            "month",
            "moral",
            "motion",
            "move",
            "murder",
            "music",
            "mystery",
            "narrow",
            "nation",
            "native",
            "navy",
            "near",
            "nerve",
            "night",
            "normal",
            "note",
            "nuclear",
            "number",
            "obey",
            "object",
            "ocean",
            "occupy",
            "offer",
            "often",
            "once",
            "operate",
            "oppose",
            "order",
            "other",
            "over",
            "pain",
            "paper",
            "parent",
            "party",
            "pass",
            "past",
            "path",
            "peace",
            "perfect",
            "permit",
            "picture",
            "pilot",
            "place",
            "planet",
            "plastic",
            "plenty",
            "poison",
            "poor",
            "position",
            "power",
            "pray",
            "predict",
            "press",
            "prison",
            "price",
            "private",
            "process",
            "profit",
            "prevent",
            "project",
            "propose",
            "protect",
            "publish",
            "punish",
            "push",
            "pure",
            "quality",
            "quick",
            "quiet",
            "question",
            "radar",
            "rain",
            "rare",
            "reach",
            "reason",
            "recent",
            "record",
            "recover",
            "refuse",
            "reject",
            "remain",
            "remove",
            "repeat",
            "resist",
            "rest",
            "result",
            "rich",
            "risk",
            "robot",
            "rock",
            "rocket",
            "rope",
            "round",
            "rubber",
            "ruin",
            "safe",
            "sailor",
            "sand",
            "same",
            "school",
            "seat",
            "search",
            "seed",
            "self",
            "sense",
            "serious",
            "several",
            "shape",
            "shake",
            "sharp",
            "short",
            "shoe",
            "ship",
            "sick",
            "side",
            "sign",
            "silver",
            "similar",
            "sink",
            "skill",
            "small",
            "smooth",
            "smash",
            "soil",
            "social",
            "solid",
            "sound",
            "south",
            "speech",
            "spend",
            "tank",
            "target",
            "teach",
            "tell",
            "tense",
            "tear",
            "thank",
            "theater",
            "thick",
            "thing",
            "third",
            "threaten",
            "tissue",
            "together",
            "tool",
            "total",
            "touch",
            "toward",
            "traffic",
            "traval",
            "treasure",
            "treat",
            "trick",
            "trouble",
            "turn",
            "under",
            "unite",
            "urge",
            "usual",
            "valley",
            "value",
            "vehicle",
            "victory",
            "victim",
            "virus",
            "village",
            "voice",
            "visit"
    };
    String[] WORDS = {"hello", "world", "javafx", "ztype"};
    int WORD_SPEED = 5000;

    //String explodeFile = "file:///C:/Users/User/IdeaProjects/BTLOOP/DictionaryApplication/src/main/resources/sources_music_picture/explode.mp3";
    //AudioClip explodeBoom = new AudioClip(explodeFile);

    String explodeFile = "/sources_music_picture/medium-explosion.mp3";
    URL resourceUrl2 = getClass().getResource(explodeFile);
    String urlString2 = resourceUrl2.toExternalForm();
    private Media me = new Media(urlString2);
    private MediaPlayer explodeBoom = new MediaPlayer(me);

    String bulletJam = "/sources_music_picture/bulletS.mp3";
    URL resourceUrl3 = getClass().getResource(bulletJam);
    Media me2 = new Media(resourceUrl3.toExternalForm());
    MediaPlayer bulletJ = new MediaPlayer(me2);


    Random random,random2;
    Timeline gameLoop;

    List<String> english = new ArrayList<>();
    List<Boolean> check = new ArrayList<>();

    List<String> chk = new ArrayList<>();

    Set<String> how = new LinkedHashSet<>();
    Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/blackSky.png"));
    Image myImage2 = new Image(getClass().getResourceAsStream("/sources_music_picture/boom.png"));


    @FXML
    Label scoreLabel,result,scLabel,bcLabel,scoreL,bScoreL,pLabel,lbsc;

    @FXML
    Button NBut,YBut;

    @FXML
    AnchorPane myAnchor;

    int bestScore = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start();
    }
    public void start(){
        readBestScore();
        if(menuTypeGame.isAudi()==false){
            mediaPlayer.setMute(true);
            explodeBoom.setMute(true);
            bulletJ.setMute(true);
        }
        if(menuTypeGame.isAudi()==true){
            mediaPlayer.setMute(false);
            explodeBoom.setMute(false);
            bulletJ.setMute(false);
        }
        mediaPlayer.seek(mediaPlayer.getStartTime());
        scLabel.setVisible(false);
        bcLabel.setVisible(false);
        scoreL.setVisible(false);
        bScoreL.setVisible(false);
        pLabel.setVisible(false);
        NBut.setVisible(false);
        YBut.setVisible(false);
        result.setVisible(false);

        lbsc.setVisible(true);
        scoreLabel.setVisible(true);
        textField.setVisible(true);

        scoreLabel.setText(""+score);
        random = new Random();
        random2 = new Random();
        List<String> Word = new ArrayList<>();
        for(int i=0;i<DATA.length;i++){
            Word.add(DATA[i]);
        }
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
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
        timeline.setRate(1.0);

        timeline.play();

        gameLoop = new Timeline(new KeyFrame(Duration.millis(WORD_SPEED), event -> {
            Collections.shuffle(Word);
            english.add(Word.get(x));
            int index = random2.nextInt(Word.get(x).length());
            String tmp2 = Word.get(x);
            String tmp3 = Character.toString(tmp2.charAt(index));
            tmp2 = tmp2.replace(tmp2.charAt(index),'_');
            chk.add(tmp3);
            check.add(true);
            Text text = new Text(tmp2);
            how.add(tmp2);
            System.out.println(how.size());
            text.setStyle("-fx-font-family: Arial; -fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: white;");
            text.setTranslateY(-140);
            int randomNumber = random.nextInt(651) - 325;
            text.setTranslateX(randomNumber);
            pane1.getChildren().add(text);
            textField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    String tmp = textField.getText();
                    checkAndRemoveString(tmp.toLowerCase());
                }
            });

            moveWordDown(text);


        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        gameLoop.play();
    }

    public void readBestScore() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/bestScore.txt"));
            String line = reader.readLine();
            reader.close();
            bestScore = Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeBestScore(int bestScore) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/bestScore.txt"));
            writer.write(Integer.toString(bestScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void moveWordDown(Text word) {
        double wordHeight = word.getBoundsInLocal().getHeight();
        double startY = -140;
        double endY = 313;
        Duration duration = Duration.seconds(10 * (endY - startY) / WINDOW_HEIGHT);

        TranslateTransition transition = new TranslateTransition(duration, word);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setFromY(startY);
        transition.setToY(endY);

        transition.setOnFinished(event -> {
            if(how.contains(word.getText()) || score == DATA.length){
                gameLoop.stop();
                result();
            }
            System.out.println(word.getText());
            word.setVisible(false);
        });
        transition.play();
    }

    public void checkAndRemoveString(String input) {
        if(chk.size()==0 || x >= chk.size()){
                bulletJ.seek(bulletJ.getStartTime());
                bulletJ.play();
        }
        else if(input.equals(chk.get(x))){
            Text textRemove =(Text)pane1.getChildren().get(x);
            how.remove(textRemove.getText());
            check.set(x,false);
            score++;
            scoreLabel.setText(""+score);
            textRemove.setText(english.get(x));

            x++;
            double t = (double) textRemove.getTranslateX();
            double z = (double) textRemove.getTranslateY();
            fire(t,z,textRemove);
            textField.clear();
        }
        else {
            bulletJ.seek(bulletJ.getStartTime());
            bulletJ.play();
        }
    }
    //fire bullet
    public void fire(double targetX, double targetY,Text text) {
        Image missileImage = new Image("/sources_music_picture/bullet.png");
        ImageView bullet = new ImageView(missileImage);

        double startX = text.getTranslateX()+365;
        double startY = 532;
        bullet.setTranslateX(startX);
        bullet.setTranslateY(startY);

        myAnchor.getChildren().add(bullet);

        Duration duration = Duration.seconds(0.01);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            bullet.setTranslateY(bullet.getTranslateY() - 2);

            if (bullet.getBoundsInParent().intersects(text.getBoundsInParent())) {
                Duration duration2 = Duration.millis(10);
                KeyValue opacityValue = new KeyValue(text.opacityProperty(), 0);
                KeyFrame keyFrame2 = new KeyFrame(duration2,opacityValue);
                Timeline timeline = new Timeline(keyFrame2);

                timeline.setOnFinished(e -> {

                });

                timeline.play();
                myAnchor.getChildren().removeAll(bullet);
                explodeBoom.seek(explodeBoom.getStartTime());
                explodeBoom.play();
                explode(text);
            }
        });

        Timeline missileTimeline = new Timeline(keyFrame);
        missileTimeline.setCycleCount(Timeline.INDEFINITE);
        missileTimeline.play();
    }
    //explode in game
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

        Image fullImage = new Image(getClass().getResource(tmp).toExternalForm());

        Timeline timeline = new Timeline();
        for (int i = 0; i < frameCount; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis( i*50),
                    event -> {
                        int x = frameIndex * frameWidth;
                        int y = 0;
                        if (frameIndex >= 4) {

                            y = frameHeight;
                            x = (frameIndex - 4) * frameWidth;
                        }

                        PixelReader pixelReader = fullImage.getPixelReader();
                        WritableImage frameImage = new WritableImage(pixelReader, x, y, frameWidth, frameHeight);
                        animationExplode.setImage(frameImage);
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> {
            myAnchor.getChildren().remove(animationExplode);
        });
        timeline.play();
    }
    public void result(){
        pane1.getChildren().clear();
        english.clear();
        chk.clear();
        how.clear();
        check.clear();
        lbsc.setVisible(false);
        scoreLabel.setVisible(false);
        textField.setVisible(false);
        textField.setVisible(false);

        scoreL.setText(""+score);
        if(score>bestScore) bestScore = score;
        bScoreL.setText(""+bestScore);
        scLabel.setVisible(true);
        bcLabel.setVisible(true);
        scoreL.setVisible(true);
        bScoreL.setVisible(true);
        pLabel.setVisible(true);
        NBut.setVisible(true);
        YBut.setVisible(true);
        result.setVisible(true);
        writeBestScore(bestScore);

        explode2();
        x=0;
        score=0;
        bestScore=0;
        YBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lbsc.setVisible(true);
                scoreLabel.setVisible(true);
                textField.setVisible(true);

                scLabel.setVisible(false);
                bcLabel.setVisible(false);
                scoreL.setVisible(false);
                bScoreL.setVisible(false);
                pLabel.setVisible(false);
                NBut.setVisible(false);
                YBut.setVisible(false);
                textField.setVisible(false);
                result.setVisible(false);
                start();
            }
        });
        NBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(1));
                fadeOut.setNode(((javafx.scene.Node) event.getSource()).getScene().getRoot());
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);

                fadeOut.setOnFinished(e -> {
                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/menuTypeG.fxml"));
                        Parent scene2Parent = loader.load();
                        Scene scene2 = new Scene(scene2Parent);

                        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                        window.setScene(scene2);

                        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1));
                        fadeIn.setNode(scene2.getRoot());
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);
                        fadeIn.play();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                fadeOut.play();
                mediaPlayer.stop();
            }
        });

    }
    //explode in menu type game
    public void explode2(){
        String tmp = "/sources_music_picture/boom.png";
        int frameCount = 8;
        int frameWidth = (int) myImage2.getWidth() / 4;
        int frameHeight = (int) myImage2.getHeight() / 2;

        ImageView animationExplode = new ImageView();
        animationExplode.setFitWidth(frameWidth);
        animationExplode.setFitHeight(frameHeight);
        animationExplode.setLayoutX(350);
        animationExplode.setLayoutY(470);
        myAnchor.getChildren().add(animationExplode);

        Image fullImage = new Image(getClass().getResource(tmp).toExternalForm());

        Timeline timeline = new Timeline();
        for (int i = 0; i < frameCount; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis( i*50),
                    event -> {
                        int x = frameIndex * frameWidth;
                        int y = 0;
                        if (frameIndex >= 4) {

                            y = frameHeight;
                            x = (frameIndex - 4) * frameWidth;
                        }

                        PixelReader pixelReader = fullImage.getPixelReader();
                        WritableImage frameImage = new WritableImage(pixelReader, x, y, frameWidth, frameHeight);
                        animationExplode.setImage(frameImage);
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> {
            myAnchor.getChildren().remove(animationExplode);
        });
        timeline.play();
        explodeBoom.seek(explodeBoom.getStartTime());
        explodeBoom.play();
    }
}
