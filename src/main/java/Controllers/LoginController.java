package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;


public class LoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    public void loginButtonOnAction(ActionEvent e) {
        if(usernameTextField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {
//            loginMessageLabel.setText("You try to login!");
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter your username and password");
        }
    }
    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        Connection connectDB = new DatabaseConnection().getConnection();

        String verifyLogin = "SELECT COUNT(1) FROM UserAccounts WHERE username='" + usernameTextField.getText() + "' AND password='" + passwordField.getText() +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMessageLabel.setText("Welcome!");
                } else {
                    loginMessageLabel.setText("Invalid login. Please try again!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
