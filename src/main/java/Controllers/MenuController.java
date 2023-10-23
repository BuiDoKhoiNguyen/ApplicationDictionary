package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private AnchorPane mainAP;
    @FXML
    private AnchorPane searchAP;

    @FXML
    private Button searchButton;
    @FXML
    private Button ggTranslateButton;
    @FXML
    private Button editButton;
    @FXML
    private Button favouriteButton;
    @FXML
    protected Button menuButton;

    @FXML
    private SearchController searchController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../search.fxml"));
        try {
            searchAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchController = loader.getController();
        mainAP.getChildren().setAll(searchAP);
    }
}
