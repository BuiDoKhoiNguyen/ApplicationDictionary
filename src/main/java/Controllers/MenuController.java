package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private AnchorPane mainAP;
    @FXML
    private AnchorPane searchAP;
    @FXML
    private AnchorPane translateAP;
    @FXML
    private Button searchButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button editButton;
    @FXML
    private Button favouriteButton;
    @FXML
    private Button logoutButton;
    @FXML
    private SearchController searchController;
    @FXML
    private TranslateController translateController;
    public MenuController() {
        searchController = new SearchController();
        translateController = new TranslateController();
    }
    SceneController sceneController = new SceneController();
    @FXML
    public void searchFunction() {
        mainAP.getChildren().setAll(searchAP);
    }

    @FXML
    public void translateFunction() {
        mainAP.getChildren().setAll(translateAP);
    }

    public void logoutButtonOnAction(ActionEvent event) throws IOException {
        Stage stageToClose = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageToClose.close();
        sceneController.switchToLogin(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/search.fxml"));
        try {
            searchAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("/FXML/translate.fxml"));
        try {
            translateAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        translateController = loader.getController();

        mainAP.getChildren().setAll(searchAP);
    }
}
