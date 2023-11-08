package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controllers {
    @FXML
    protected Button searchButton;
    @FXML
    protected Button translateButton;
    @FXML
    protected Button saveButton;
    @FXML
    protected Button favouriteButton;
    @FXML
    protected Button gameButton;
    @FXML
    protected Button logoutButton;

    @FXML
    protected SearchController searchController;
    @FXML
    protected TranslateController translateController;
    @FXML
    protected FavouriteController favouriteController;
}
