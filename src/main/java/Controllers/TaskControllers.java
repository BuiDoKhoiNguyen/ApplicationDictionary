package Controllers;

import Game.connectionGandM;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class  TaskControllers {
    @FXML
    protected ProfileController profileController;
    @FXML
    protected SearchController searchController;
    @FXML
    protected TranslateController2 translateController;
    @FXML
    protected FavouriteController favouriteController;
    @FXML
    protected connectionGandM gameController;

    public void loadController(ProfileController profileController,
                               SearchController searchController,
                               TranslateController2 translateController,
                               FavouriteController favouriteController,
                               connectionGandM gameController) {
        this.profileController = profileController;
        this.searchController = searchController;
        this.translateController = translateController;
        this.favouriteController = favouriteController;
        this.gameController = gameController;
    }
}
