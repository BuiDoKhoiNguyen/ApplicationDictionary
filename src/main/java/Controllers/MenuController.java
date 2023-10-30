package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends Controllers implements Initializable {
    @FXML
    private AnchorPane mainAP;
    @FXML
    private AnchorPane searchAP;
    @FXML
    private AnchorPane translateAP;

    @FXML
    private SearchController searchController;
    @FXML
    private TranslateController translateController;
    @FXML
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
        searchController.load(this.searchButton, this.translateButton, this.favouriteButton, this.editButton, this.gameButton, this.logoutButton);

        loader = new FXMLLoader(getClass().getResource("/FXML/translate.fxml"));
        try {
            translateAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        translateController = loader.getController();
        translateController.load(this.searchButton, this.translateButton, this.favouriteButton, this.editButton, this.gameButton, this.logoutButton);

        mainAP.getChildren().setAll(searchAP);
    }
}
