package Controllers;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.Notifications;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static Controllers.PreloaderController.connectDB;

public class LoginController implements Initializable {
    /**
     * Effects processing
     */
    @FXML
    private AnchorPane layer1, layer2, layer3;
    @FXML
    private Button signUp, signIn;
    @FXML
    private Label l1, b1, b2, b3, b4, b5;
    @FXML
    private ImageView i1, i2, i3;
    @FXML
    private Button buttonSignUp, buttonSignIn;
    @FXML
    private TextField usernameTextField2, passwordField2, confirmPassword, fname, lname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonSignUp.setVisible(true);
        buttonSignIn.setVisible(false);
        signIn.setVisible(true);
        signUp.setVisible(false);

        b1.setVisible(true);
        b2.setVisible(true);
        b3.setVisible(false);
        b4.setVisible(true);
        b5.setVisible(false);
        i1.setVisible(true);
        i2.setVisible(true);
        i3.setVisible(true);
        usernameTextField1.setVisible(true);
        passwordField1.setVisible(true);

        l1.setVisible(false);
        usernameTextField2.setVisible(false);
        passwordField2.setVisible(false);
        confirmPassword.setVisible(false);
        fname.setVisible(false);
        lname.setVisible(false);
    }

    @FXML
    private void buttonSignUpAction(MouseEvent event) {
        TranslateTransition slide1 = new TranslateTransition();
        TranslateTransition slide2 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(0.7));
        slide2.setDuration(Duration.seconds(0.7));
        slide1.setNode(layer2);
        slide2.setNode(layer1);

        slide1.setToX(430);
        slide2.setToX(-370);
        slide1.play();
        slide2.play();

        layer3.setVisible(false);

        buttonSignUp.setVisible(false);
        buttonSignIn.setVisible(true);
        signIn.setVisible(false);
        signUp.setVisible(true);

        b1.setVisible(false);
        b2.setVisible(false);
        b3.setVisible(true);
        b4.setVisible(false);
        b5.setVisible(true);
        i1.setVisible(false);
        i2.setVisible(false);
        i3.setVisible(false);
        usernameTextField1.setVisible(false);
        passwordField1.setVisible(false);

        l1.setVisible(true);
        usernameTextField2.setVisible(true);
        passwordField2.setVisible(true);
        confirmPassword.setVisible(true);
        fname.setVisible(true);
        lname.setVisible(true);
    }

    @FXML
    private void buttonSignInAction(MouseEvent event) {
        TranslateTransition slide1 = new TranslateTransition();
        TranslateTransition slide2 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(0.7));
        slide2.setDuration(Duration.seconds(0.7));
        slide1.setNode(layer2);
        slide2.setNode(layer1);

        slide1.setToX(0);
        slide2.setToX(0);
        slide1.play();
        slide2.play();

        layer3.setVisible(true);
        buttonSignUp.setVisible(true);
        buttonSignIn.setVisible(false);
        signIn.setVisible(true);
        signUp.setVisible(false);

        b1.setVisible(true);
        b2.setVisible(true);
        b3.setVisible(false);
        b4.setVisible(true);
        b5.setVisible(false);
        i1.setVisible(true);
        i2.setVisible(true);
        i3.setVisible(true);
        usernameTextField1.setVisible(true);
        passwordField1.setVisible(true);

        l1.setVisible(false);
        usernameTextField2.setVisible(false);
        passwordField2.setVisible(false);
        confirmPassword.setVisible(false);
        fname.setVisible(false);
        lname.setVisible(false);
    }

    /** ------------------------------------------------ */
    /**
     * Data processing
     */

    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameTextField1;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private Button loginButton;
    @FXML
    private StackPane stackPane = new StackPane();

    SceneController sceneController = new SceneController();
    public static String username;

    public void loginButtonOnAction(ActionEvent e) throws IOException {
        username = usernameTextField1.getText();
        System.out.println(username);
        if (usernameTextField1.getText().isBlank() == false && passwordField1.getText().isBlank() == false) {
            if (validateLogin()) {
                successNotification("Welcome to application!");
                sceneController.switchToMenu(e);
            } else {
                errorNotification("Invalid login. Please try again!");
            }
        } else {
            errorNotification("Please enter your username and password");
        }
    }

    public void createAccountButtonOnAction(ActionEvent e) throws IOException {
        if (usernameTextField2.getText().isBlank() == false && passwordField2.getText().isBlank() == false
                && fname.getText().isBlank() == false && lname.getText().isBlank() == false && confirmPassword.getText().isBlank() == false) {
            if (validateSignUp(usernameTextField2.getText(), passwordField2.getText(), confirmPassword.getText(), fname.getText(), lname.getText())) {
                successNotification("Account successfully created! Please login to try app");
            } else {
                errorNotification("Unable to register account. Please try again later!");
            }
        } else {
            errorNotification("Please enter your information!");
        }
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean validateLogin() {
//        Connection connectDB = DatabaseConnection.getConnection();

        String verifyLogin = "SELECT COUNT(1) FROM UserAccounts WHERE username='" + usernameTextField1.getText() + "' AND password='" + passwordField1.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateSignUp(String username, String password, String confirmPassword, String fName, String lName) {
//        Connection connectDB = DatabaseConnection.getConnection();

        String verifySignUp = "SELECT COUNT(1) FROM UserAccounts WHERE username='" + usernameTextField1.getText() + "' AND password='" + passwordField1.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifySignUp);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1 && password.equals(confirmPassword)) {
                    return false;
                } else {
                    if (password.equals(confirmPassword)) {
                        String createAccount = "INSERT INTO UserAccounts (FirstName, LastName, Username, Password) VALUES ('" + fName + "', '" + lName + "', '" + username + "', '" + password + "')";
                        statement.executeUpdate(createAccount);
                        return true;
                    }
                }
            }
            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static UserInfo getUserInfo() {
        String query = "SELECT idUserAccounts, CONCAT(FirstName,' ', LastName) as FullName,profileImage FROM UserAccounts WHERE idUserAccounts = '" + username + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            if (queryResult.next()) {
                int userId = queryResult .getInt("IdUserAccounts");
                String name = queryResult.getString("FullName");
                byte[] profileImg = queryResult.getBytes("profileImage");
                return new UserInfo(userId, name, username, profileImg);
            } else {
                System.out.println("Không tìm thấy người dùng !" );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public void errorNotification(String text) {
        Image image = new Image("/img/icons8-x-48.png");
        Notifications.create()
                .graphic(new ImageView(image))
                .title("Error")
                .text(text)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT)
                .show();
    }

    public void successNotification(String text) {
        Image image = new Image("/img/icons8-tick-48.png");
        Notifications.create()
                .graphic(new ImageView(image))
                .title("Success")
                .text(text)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT)
                .show();
    }

    /** ------------------------------------------------ */
    /**
     * Dragged screen
     */
    private double x = 0;
    private double y = 0;

    @FXML
    public void paneDragged(MouseEvent e) {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.setY(e.getScreenY() - y);
        stage.setX(e.getScreenX() - x);
    }

    @FXML
    public void panePressed(MouseEvent e) {
        x = e.getSceneX();
        y = e.getSceneY();
    }
}
