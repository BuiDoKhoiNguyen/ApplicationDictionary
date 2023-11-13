package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class  TaskControllers {
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
    protected Button cancelButton;

    @FXML
    protected ProfileController profileController;
    @FXML
    protected SearchController searchController;
    @FXML
    protected TranslateController translateController;
    @FXML
    protected FavouriteController favouriteController;
    @FXML
    protected connectionGandM gameController;

    public void loadController(ProfileController profileController,
                               SearchController searchController,
                               TranslateController translateController,
                               FavouriteController favouriteController,
                               connectionGandM gameController) {
        this.profileController = profileController;
        this.searchController = searchController;
        this.translateController = translateController;
        this.favouriteController = favouriteController;
        this.gameController = gameController;
    }
}
