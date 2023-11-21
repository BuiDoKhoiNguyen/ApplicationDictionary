package Controllers;

import Game.connectionGandM;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static java.lang.System.exit;

public class TaskControllers {
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
    @FXML
    protected Button cancelButton;

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

    public void cancelButtonOnAction() {
        ProfileController.recordAppUsage();
        LoginController.isLogin = false;
        ProfileController.currtime = 0;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        exit(0);
    }
}
