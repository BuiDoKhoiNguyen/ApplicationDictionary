package Controllers;

import DatabaseConnect.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    SceneController sceneController = new SceneController();
    public void loginButtonOnAction(ActionEvent e) throws IOException {
        if(usernameTextField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {
//            loginMessageLabel.setText("You try to login!");
            if(validateLogin()) {
                sceneController.switchToScene2(e);
            }
            else {
                loginMessageLabel.setText("Invalid login. Please try again!");
            }
        } else {
            loginMessageLabel.setText("Please enter your username and password");
        }
    }
    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean validateLogin() {
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectDB = connectionNow.getConnection();

        String verifyLogin = "SELECT COUNT(1) FROM UserAccounts WHERE username='" + usernameTextField.getText() + "' AND password='" + passwordField.getText() +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return true;
//                    loginMessageLabel.setText("Welcome!");
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
