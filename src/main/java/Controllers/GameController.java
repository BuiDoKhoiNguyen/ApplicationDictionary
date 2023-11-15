package Controllers;

import Base.Dictionary;
import Base.NewDictionaryManagement;
import Base.Word;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    @FXML
    WebView webView;
    @FXML
    Button ansA, ansB, ansC, ansD, playButton, yesButton, noButton, review, Ques1, Ques2, Ques3, Ques4, Ques5,
            Ques6, Ques7, Ques8, Ques9, Ques10, backResult;
    @FXML
    private Label question, resultTable;
    @FXML
    ProgressBar progressBar;

    @FXML
    ToggleButton togButton;
    @FXML
    AnchorPane myAnchor;

    Image myImage = new Image(getClass().getResourceAsStream("/sources_music_picture/audioOn.png"));
    Image myImage2 = new Image(getClass().getResourceAsStream("/sources_music_picture/audioOff.png"));

    Image myImage3 = new Image(getClass().getResourceAsStream("/sources_music_picture/gameImage.png"));

    String mediaFile = "/sources_music_picture/sleepy-cat-118974.mp3";

    URL resourceUrl = getClass().getResource(mediaFile);
    Media media = new Media(resourceUrl.toExternalForm());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    private Task<Void> task;

    private List<Word> listWord;
    int index = 0;
    int correctAnswer = 0;
    int totalQuestion = 10;
    int seconds = 10;
    boolean Choice = false;

    String trueAns = "";

    Dictionary dictionary = new Dictionary();
    private static final String IN_PATH = "data/dictionaries.txt";

    List<String> examE = new ArrayList<>();
    List<String> ansE = new ArrayList<>();

    List<Boolean> color = new ArrayList<>();

    List<Integer> viTri = new ArrayList<>();

    List<Button> all = new ArrayList<>();

    int quesI = -1;


    public static void loadFromFile(Dictionary dictionary, String IN_PATH) {
        try {
            FileReader fileReader = new FileReader(IN_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String englishWord = bufferedReader.readLine();
            englishWord = englishWord.replace("|", "");
            String line = null;


            while ((line = bufferedReader.readLine()) != null) {
                Word word = new Word();
                word.setWordTarget(englishWord.trim());
                String meaning = line + "\n";
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.startsWith("|")) meaning += line + "\n";
                    else {
                        englishWord = line.replace("|", "");
                        break;
                    }
                    word.setWordExplain(meaning.trim());
                    dictionary.put(englishWord, word);
                }
            }
            bufferedReader.close();
            System.out.println("Loaded from file successfully");
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public GameController() {
        loadFromFile(dictionary, IN_PATH);
    }


    public void quiz(ActionEvent event) {
        listWord = new ArrayList<>(dictionary.values());
        changeOnOff();
        showQues();
        handle(new ActionEvent());
    }

    //task bar progress
    public void down() {
        if (task != null && task.isRunning()) {
            task.cancel();
        }

        Task<Void> newTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int counter = 200; counter >= 0; counter--) {
                    if (isCancelled() || Choice == true) {
                        break;
                    }
                    updateProgress(counter, 200);
                    Thread.sleep(50);
                    //System.out.println(counter);
                    if (counter == 0) {
                        displayAnswer();
                    }
                }
                return null;
            }
        };

        task = newTask;

        progressBar.progressProperty().bind(newTask.progressProperty());

        new Thread(newTask).start();
    }


    //set up question and show res when enough question
    public void showQues() {
        if (index >= totalQuestion) {
            result();
        } else {
            question.setText("Question " + (index + 1));
            Collections.shuffle(listWord);
            List<Word> randomWords = listWord.subList(0, 4);
            int t = 0;
            ansA.setText(randomWords.get(t).getWordTarget());
            t++;
            ansB.setText(randomWords.get(t).getWordTarget());
            t++;
            ansC.setText(randomWords.get(t).getWordTarget());
            t++;
            ansD.setText(randomWords.get(t).getWordTarget());
            Random random = new Random();
            int randomIndex = random.nextInt(randomWords.size());
            String ques = randomWords.get(randomIndex).getWordExplain();
            examE.add(ques);
            String tmp = randomWords.get(randomIndex).getWordTarget();
            ques = ques.replaceAll("/.*?/", "");
            ques = ques.replaceAll("'.*?/", "");
            ques = ques.replaceAll(tmp, "...");
            webView.getEngine().loadContent(ques);
            trueAns = randomWords.get(randomIndex).getWordTarget();
            ansE.add(trueAns);
            down();
        }
    }

    //handle event when you push 1 out of 4 button
    public void handle(ActionEvent event) {

        ansA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ansA.setDisable(true);
                ansB.setDisable(true);
                ansC.setDisable(true);
                ansD.setDisable(true);
                if (event.getSource() == ansA) {
                    if (ansA.getText() == trueAns) {
                        correctAnswer++;
                        color.add(true);
                    } else color.add(false);
                }
                displayAnswer();
            }
        });
        ansB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ansA.setDisable(true);
                ansB.setDisable(true);
                ansC.setDisable(true);
                ansD.setDisable(true);
                if (event.getSource() == ansB) {
                    if (ansB.getText() == trueAns) {
                        correctAnswer++;
                        color.add(true);
                    } else color.add(false);
                }
                displayAnswer();
            }
        });
        ansC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ansA.setDisable(true);
                ansB.setDisable(true);
                ansC.setDisable(true);
                ansD.setDisable(true);
                if (event.getSource() == ansC) {
                    if (ansC.getText() == trueAns) {
                        correctAnswer++;
                        color.add(true);
                    } else color.add(false);
                }
                displayAnswer();
            }
        });
        ansD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ansA.setDisable(true);
                ansB.setDisable(true);
                ansC.setDisable(true);
                ansD.setDisable(true);
                if (event.getSource() == ansD) {
                    if (ansD.getText() == trueAns) {
                        correctAnswer++;
                        color.add(true);
                    } else color.add(false);
                }
                displayAnswer();
            }
        });
    }

    public void displayAnswer() {
        //timer.stop();
        progressBar.progressProperty().unbind();
        Choice = true;
        ansA.setDisable(true);
        ansB.setDisable(true);
        ansC.setDisable(true);
        ansD.setDisable(true);

        ansA.setOpacity(1.0);
        ansB.setOpacity(1.0);
        ansC.setOpacity(1.0);
        ansD.setOpacity(1.0);

        if (ansA.getText() != trueAns) {
            ansA.setVisible(false);
        } else viTri.add(1);
        if (ansB.getText() != trueAns) {
            ansB.setVisible(false);
        } else viTri.add(2);
        if (ansC.getText() != trueAns) {
            ansC.setVisible(false);
        } else viTri.add(3);
        if (ansD.getText() != trueAns) {
            ansD.setVisible(false);
        } else viTri.add(4);

        Timeline pause = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ansA.setVisible(true);
                ansB.setVisible(true);
                ansC.setVisible(true);
                ansD.setVisible(true);

                Choice = false;
                trueAns = "";
                ansA.setDisable(false);
                ansB.setDisable(false);
                ansC.setDisable(false);
                ansD.setDisable(false);
                index++;
                showQues();
            }
        }));
        pause.setCycleCount(1);
        pause.play();
    }

    public void result() {
        ansA.setText("");
        ansB.setText("");
        ansC.setText("");
        ansD.setText("");
        ansA.setDisable(true);
        ansB.setDisable(true);
        ansC.setDisable(true);
        ansD.setDisable(true);
        webView.getEngine().loadContent("");
        noButton.setVisible(true);
        yesButton.setVisible(true);
        resultTable.setVisible(true);
        review.setVisible(true);
        resultTable.setTextFill(Color.WHITE);
        progressBar.setProgress(0.0);
        //if you right more than or equal 5
        if (correctAnswer >= 5) {
            String tmp = "     So cool, well done bruh";
            tmp += "\n";
            tmp += "                   ";
            tmp += "(" + correctAnswer + "/" + totalQuestion + ")";
            tmp += "\n";
            tmp += "    ";
            tmp += "continue one more time";
            tmp += "    ";
            resultTable.setText(tmp);

            //back to menu game choose
            noButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1));
                    fadeOut.setNode(((javafx.scene.Node) event.getSource()).getScene().getRoot());
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);

                    fadeOut.setOnFinished(e -> {
                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/openSimpleGame.fxml"));
                            mediaPlayer.stop();
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
                    ansE.clear();
                    examE.clear();
                    viTri.clear();
                    color.clear();
                    all.clear();
                }
            });

            //play again
            yesButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ansE.clear();
                    examE.clear();
                    viTri.clear();
                    color.clear();
                    correctAnswer = 0;
                    progressBar.setProgress(1.0);
                    trueAns = "";
                    index = 0;
                    noButton.setVisible(false);
                    yesButton.setVisible(false);
                    resultTable.setVisible(false);
                    review.setVisible(false);
                    ansA.setDisable(false);
                    ansB.setDisable(false);
                    ansC.setDisable(false);
                    ansD.setDisable(false);
                    quiz(new ActionEvent());


                }
            });

            //check ans
            review.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (int i = 0; i < color.size(); i++) {
                        if (color.get(i) == true) {
                            all.get(i).setStyle("-fx-background-color:green;");
                        } else all.get(i).setStyle("-fx-background-color:red;");
                    }
                    Ques1.getStyleClass().add("text_border");
                    quesI = 1;
                    webView.getEngine().loadContent(examE.get(0));
                    if (viTri.get(0) == 1) {
                        ansA.setText(ansE.get(0));
                    }
                    if (viTri.get(0) == 2) {
                        ansB.setText(ansE.get(0));
                    }
                    if (viTri.get(0) == 3) {
                        ansC.setText(ansE.get(0));
                    }
                    if (viTri.get(0) == 4) {
                        ansD.setText(ansE.get(0));
                    }

                    backResult.setVisible(true);
                    Ques1.setVisible(true);
                    Ques2.setVisible(true);
                    Ques3.setVisible(true);
                    Ques4.setVisible(true);
                    Ques5.setVisible(true);
                    Ques6.setVisible(true);
                    Ques7.setVisible(true);
                    Ques8.setVisible(true);
                    Ques9.setVisible(true);
                    Ques10.setVisible(true);

                    review.setVisible(false);
                    noButton.setVisible(false);
                    yesButton.setVisible(false);
                    resultTable.setVisible(false);
                    progressBar.setVisible(false);
                    Ques1.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 1) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 1;
                                webView.getEngine().loadContent(examE.get(0));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(0) == 1) {
                                    ansA.setText(ansE.get(0));
                                }
                                if (viTri.get(0) == 2) {
                                    ansB.setText(ansE.get(0));
                                }
                                if (viTri.get(0) == 3) {
                                    ansC.setText(ansE.get(0));
                                }
                                if (viTri.get(0) == 4) {
                                    ansD.setText(ansE.get(0));
                                }
                            }

                        }
                    });
                    Ques2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 2) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 2;
                                webView.getEngine().loadContent(examE.get(1));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(1) == 1) {
                                    ansA.setText(ansE.get(1));
                                }
                                if (viTri.get(1) == 2) {
                                    ansB.setText(ansE.get(1));
                                }
                                if (viTri.get(1) == 3) {
                                    ansC.setText(ansE.get(1));
                                }
                                if (viTri.get(1) == 4) {
                                    ansD.setText(ansE.get(1));
                                }
                            }

                        }
                    });

                    Ques3.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 3) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 3;
                                webView.getEngine().loadContent(examE.get(2));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(2) == 1) {
                                    ansA.setText(ansE.get(2));
                                }
                                if (viTri.get(2) == 2) {
                                    ansB.setText(ansE.get(2));
                                }
                                if (viTri.get(2) == 3) {
                                    ansC.setText(ansE.get(2));
                                }
                                if (viTri.get(2) == 4) {
                                    ansD.setText(ansE.get(2));
                                }
                            }

                        }
                    });
                    Ques4.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 4) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 4;
                                webView.getEngine().loadContent(examE.get(3));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(3) == 1) {
                                    ansA.setText(ansE.get(3));
                                }
                                if (viTri.get(3) == 2) {
                                    ansB.setText(ansE.get(3));
                                }
                                if (viTri.get(3) == 3) {
                                    ansC.setText(ansE.get(3));
                                }
                                if (viTri.get(3) == 4) {
                                    ansD.setText(ansE.get(3));
                                }
                            }

                        }
                    });
                    Ques5.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 5) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 5;
                                webView.getEngine().loadContent(examE.get(4));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(4) == 1) {
                                    ansA.setText(ansE.get(4));
                                }
                                if (viTri.get(4) == 2) {
                                    ansB.setText(ansE.get(4));
                                }
                                if (viTri.get(4) == 3) {
                                    ansC.setText(ansE.get(4));
                                }
                                if (viTri.get(4) == 4) {
                                    ansD.setText(ansE.get(4));
                                }
                            }

                        }
                    });
                    Ques6.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 6) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 6;
                                webView.getEngine().loadContent(examE.get(5));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(5) == 1) {
                                    ansA.setText(ansE.get(5));
                                }
                                if (viTri.get(5) == 2) {
                                    ansB.setText(ansE.get(5));
                                }
                                if (viTri.get(5) == 3) {
                                    ansC.setText(ansE.get(5));
                                }
                                if (viTri.get(5) == 4) {
                                    ansD.setText(ansE.get(5));
                                }
                            }

                        }
                    });
                    Ques7.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 7) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 7;
                                webView.getEngine().loadContent(examE.get(6));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(6) == 1) {
                                    ansA.setText(ansE.get(6));
                                }
                                if (viTri.get(6) == 2) {
                                    ansB.setText(ansE.get(6));
                                }
                                if (viTri.get(6) == 3) {
                                    ansC.setText(ansE.get(6));
                                }
                                if (viTri.get(6) == 4) {
                                    ansD.setText(ansE.get(6));
                                }
                            }

                        }
                    });
                    Ques8.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 8) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 8;
                                webView.getEngine().loadContent(examE.get(7));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(7) == 1) {
                                    ansA.setText(ansE.get(7));
                                }
                                if (viTri.get(7) == 2) {
                                    ansB.setText(ansE.get(7));
                                }
                                if (viTri.get(7) == 3) {
                                    ansC.setText(ansE.get(7));
                                }
                                if (viTri.get(7) == 4) {
                                    ansD.setText(ansE.get(7));
                                }
                            }

                        }
                    });
                    Ques9.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 9) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 9;
                                webView.getEngine().loadContent(examE.get(8));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(8) == 1) {
                                    ansA.setText(ansE.get(8));
                                }
                                if (viTri.get(8) == 2) {
                                    ansB.setText(ansE.get(8));
                                }
                                if (viTri.get(8) == 3) {
                                    ansC.setText(ansE.get(8));
                                }
                                if (viTri.get(8) == 4) {
                                    ansD.setText(ansE.get(8));
                                }
                            }

                        }
                    });
                    Ques10.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 10) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 10;
                                webView.getEngine().loadContent(examE.get(9));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(9) == 1) {
                                    ansA.setText(ansE.get(9));
                                }
                                if (viTri.get(9) == 2) {
                                    ansB.setText(ansE.get(9));
                                }
                                if (viTri.get(9) == 3) {
                                    ansC.setText(ansE.get(9));
                                }
                                if (viTri.get(9) == 4) {
                                    ansD.setText(ansE.get(9));
                                }
                            }

                        }
                    });
                    // return to result table
                    backResult.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            ansA.setText("");
                            ansB.setText("");
                            ansC.setText("");
                            ansD.setText("");
                            webView.getEngine().loadContent("");
                            Ques1.setVisible(false);
                            Ques2.setVisible(false);
                            Ques3.setVisible(false);
                            Ques4.setVisible(false);
                            Ques5.setVisible(false);
                            Ques6.setVisible(false);
                            Ques7.setVisible(false);
                            Ques8.setVisible(false);
                            Ques9.setVisible(false);
                            Ques10.setVisible(false);
                            backResult.setVisible(false);

                            noButton.setVisible(true);
                            yesButton.setVisible(true);
                            resultTable.setVisible(true);
                            progressBar.setVisible(true);
                            review.setVisible(true);
                        }
                    });
                }
            });
        }
        // if your right ans less than 5
        else {
            String tmp = "       Opps, try again bruh";
            tmp += "\n";
            tmp += "                  ";
            tmp += "(" + correctAnswer + "/" + totalQuestion + ")";
            tmp += "\n";
            tmp += "    ";
            tmp += "continue one more time";
            tmp += "    ";
            resultTable.setText(tmp);
            noButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1));
                    fadeOut.setNode(((javafx.scene.Node) event.getSource()).getScene().getRoot());
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);

                    fadeOut.setOnFinished(e -> {
                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/openSimpleGame.fxml"));
                            mediaPlayer.stop();
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
                    ansE.clear();
                    examE.clear();
                    viTri.clear();
                    color.clear();
                    all.clear();
                }
            });
            yesButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ansE.clear();
                    examE.clear();
                    viTri.clear();
                    color.clear();
                    correctAnswer = 0;
                    progressBar.setProgress(1.0);
                    trueAns = "";
                    index = 0;
                    noButton.setVisible(false);
                    yesButton.setVisible(false);
                    resultTable.setVisible(false);
                    review.setVisible(false);
                    ansA.setDisable(false);
                    ansB.setDisable(false);
                    ansC.setDisable(false);
                    ansD.setDisable(false);
                    quiz(new ActionEvent());


                }
            });
            review.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (int i = 0; i < color.size(); i++) {
                        if (color.get(i) == true) {
                            all.get(i).setStyle("-fx-background-color:green;");
                        } else all.get(i).setStyle("-fx-background-color:red;");
                    }
                    Ques1.getStyleClass().add("text_border");
                    quesI = 1;
                    webView.getEngine().loadContent(examE.get(0));
                    if (viTri.get(0) == 1) {
                        ansA.setText(ansE.get(0));
                    }
                    if (viTri.get(0) == 2) {
                        ansB.setText(ansE.get(0));
                    }
                    if (viTri.get(0) == 3) {
                        ansC.setText(ansE.get(0));
                    }
                    if (viTri.get(0) == 4) {
                        ansD.setText(ansE.get(0));
                    }

                    backResult.setVisible(true);
                    Ques1.setVisible(true);
                    Ques2.setVisible(true);
                    Ques3.setVisible(true);
                    Ques4.setVisible(true);
                    Ques5.setVisible(true);
                    Ques6.setVisible(true);
                    Ques7.setVisible(true);
                    Ques8.setVisible(true);
                    Ques9.setVisible(true);
                    Ques10.setVisible(true);

                    review.setVisible(false);
                    noButton.setVisible(false);
                    yesButton.setVisible(false);
                    resultTable.setVisible(false);
                    progressBar.setVisible(false);
                    Ques1.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 1) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 1;
                                webView.getEngine().loadContent(examE.get(0));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(0) == 1) {
                                    ansA.setText(ansE.get(0));
                                }
                                if (viTri.get(0) == 2) {
                                    ansB.setText(ansE.get(0));
                                }
                                if (viTri.get(0) == 3) {
                                    ansC.setText(ansE.get(0));
                                }
                                if (viTri.get(0) == 4) {
                                    ansD.setText(ansE.get(0));
                                }
                            }

                        }
                    });
                    Ques2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 2) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 2;
                                webView.getEngine().loadContent(examE.get(1));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(1) == 1) {
                                    ansA.setText(ansE.get(1));
                                }
                                if (viTri.get(1) == 2) {
                                    ansB.setText(ansE.get(1));
                                }
                                if (viTri.get(1) == 3) {
                                    ansC.setText(ansE.get(1));
                                }
                                if (viTri.get(1) == 4) {
                                    ansD.setText(ansE.get(1));
                                }
                            }

                        }
                    });

                    Ques3.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 3) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 3;
                                webView.getEngine().loadContent(examE.get(2));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(2) == 1) {
                                    ansA.setText(ansE.get(2));
                                }
                                if (viTri.get(2) == 2) {
                                    ansB.setText(ansE.get(2));
                                }
                                if (viTri.get(2) == 3) {
                                    ansC.setText(ansE.get(2));
                                }
                                if (viTri.get(2) == 4) {
                                    ansD.setText(ansE.get(2));
                                }
                            }

                        }
                    });
                    Ques4.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 4) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 4;
                                webView.getEngine().loadContent(examE.get(3));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(3) == 1) {
                                    ansA.setText(ansE.get(3));
                                }
                                if (viTri.get(3) == 2) {
                                    ansB.setText(ansE.get(3));
                                }
                                if (viTri.get(3) == 3) {
                                    ansC.setText(ansE.get(3));
                                }
                                if (viTri.get(3) == 4) {
                                    ansD.setText(ansE.get(3));
                                }
                            }

                        }
                    });
                    Ques5.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 5) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 5;
                                webView.getEngine().loadContent(examE.get(4));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(4) == 1) {
                                    ansA.setText(ansE.get(4));
                                }
                                if (viTri.get(4) == 2) {
                                    ansB.setText(ansE.get(4));
                                }
                                if (viTri.get(4) == 3) {
                                    ansC.setText(ansE.get(4));
                                }
                                if (viTri.get(4) == 4) {
                                    ansD.setText(ansE.get(4));
                                }
                            }

                        }
                    });
                    Ques6.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 6) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 6;
                                webView.getEngine().loadContent(examE.get(5));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(5) == 1) {
                                    ansA.setText(ansE.get(5));
                                }
                                if (viTri.get(5) == 2) {
                                    ansB.setText(ansE.get(5));
                                }
                                if (viTri.get(5) == 3) {
                                    ansC.setText(ansE.get(5));
                                }
                                if (viTri.get(5) == 4) {
                                    ansD.setText(ansE.get(5));
                                }
                            }

                        }
                    });
                    Ques7.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 7) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 7;
                                webView.getEngine().loadContent(examE.get(6));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(6) == 1) {
                                    ansA.setText(ansE.get(6));
                                }
                                if (viTri.get(6) == 2) {
                                    ansB.setText(ansE.get(6));
                                }
                                if (viTri.get(6) == 3) {
                                    ansC.setText(ansE.get(6));
                                }
                                if (viTri.get(6) == 4) {
                                    ansD.setText(ansE.get(6));
                                }
                            }

                        }
                    });
                    Ques8.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 8) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 8;
                                webView.getEngine().loadContent(examE.get(7));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(7) == 1) {
                                    ansA.setText(ansE.get(7));
                                }
                                if (viTri.get(7) == 2) {
                                    ansB.setText(ansE.get(7));
                                }
                                if (viTri.get(7) == 3) {
                                    ansC.setText(ansE.get(7));
                                }
                                if (viTri.get(7) == 4) {
                                    ansD.setText(ansE.get(7));
                                }
                            }

                        }
                    });
                    Ques9.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 9) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 9;
                                webView.getEngine().loadContent(examE.get(8));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(8) == 1) {
                                    ansA.setText(ansE.get(8));
                                }
                                if (viTri.get(8) == 2) {
                                    ansB.setText(ansE.get(8));
                                }
                                if (viTri.get(8) == 3) {
                                    ansC.setText(ansE.get(8));
                                }
                                if (viTri.get(8) == 4) {
                                    ansD.setText(ansE.get(8));
                                }
                            }

                        }
                    });
                    Ques10.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (quesI != 10) {
                                ansA.setText("");
                                ansB.setText("");
                                ansC.setText("");
                                ansD.setText("");
                                all.get(quesI - 1).getStyleClass().remove("text_border");
                                quesI = 10;
                                webView.getEngine().loadContent(examE.get(9));
                                all.get(quesI - 1).getStyleClass().add("text_border");
                                if (viTri.get(9) == 1) {
                                    ansA.setText(ansE.get(9));
                                }
                                if (viTri.get(9) == 2) {
                                    ansB.setText(ansE.get(9));
                                }
                                if (viTri.get(9) == 3) {
                                    ansC.setText(ansE.get(9));
                                }
                                if (viTri.get(9) == 4) {
                                    ansD.setText(ansE.get(9));
                                }
                            }

                        }
                    });
                    backResult.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            ansA.setText("");
                            ansB.setText("");
                            ansC.setText("");
                            ansD.setText("");
                            webView.getEngine().loadContent("");
                            Ques1.setVisible(false);
                            Ques2.setVisible(false);
                            Ques3.setVisible(false);
                            Ques4.setVisible(false);
                            Ques5.setVisible(false);
                            Ques6.setVisible(false);
                            Ques7.setVisible(false);
                            Ques8.setVisible(false);
                            Ques9.setVisible(false);
                            Ques10.setVisible(false);
                            backResult.setVisible(false);

                            noButton.setVisible(true);
                            yesButton.setVisible(true);
                            resultTable.setVisible(true);
                            progressBar.setVisible(true);
                            review.setVisible(true);
                        }
                    });
                }
            });
        }
    }

    //change on/off music
    public void changeOnOff() {
        boolean isMuted = togButton.isSelected();

        mediaPlayer.setMute(isMuted);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView imageView = new ImageView(myImage3);
        myAnchor.getChildren().add(imageView);
        imageView.toBack();
        //webView.getStyleClass().add("webView");
        progressBar.getStyleClass().add("progress-bar");
        togButton.getStyleClass().add("buttonAudio");
        quiz(new ActionEvent());
        resultTable.setVisible(false);
        ansA.getStyleClass().add("button1");
        ansB.getStyleClass().add("button2");
        ansC.getStyleClass().add("button3");
        ansD.getStyleClass().add("button4");
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
        noButton.getStyleClass().add("noButton");
        yesButton.getStyleClass().add("yesButton");
        review.getStyleClass().add("reviewButton");
        review.setVisible(false);
        noButton.setVisible(false);
        yesButton.setVisible(false);
        backResult.setVisible(false);
        Ques1.setVisible(false);
        Ques2.setVisible(false);
        Ques3.setVisible(false);
        Ques4.setVisible(false);
        Ques5.setVisible(false);
        Ques6.setVisible(false);
        Ques7.setVisible(false);
        Ques8.setVisible(false);
        Ques9.setVisible(false);
        Ques10.setVisible(false);
        all.add(Ques1);
        all.add(Ques2);
        all.add(Ques3);
        all.add(Ques4);
        all.add(Ques5);
        all.add(Ques6);
        all.add(Ques7);
        all.add(Ques8);
        all.add(Ques9);
        all.add(Ques10);

    }
}
