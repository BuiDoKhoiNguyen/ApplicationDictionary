package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TaskControllers extends Controllers {
    @FXML
    protected Label searchLabel;
    @FXML
    protected Label translateLabel;
    @FXML
    protected Label favouriteLabel;
    @FXML
    protected Label editLabel;
    @FXML
    protected Label gameLabel;
    @FXML
    protected Label logoutLabel;

    private void bindLabel(Label label, Button button) {
        label.visibleProperty().bind(button.hoverProperty());
        label.backgroundProperty().bind(button.backgroundProperty());
        button.hoverProperty().addListener(
                (observable, oldValue, newValue) -> {
                    label.toFront();
                }
        );
    }

    public void loadButton(Button searchButton, Button translateButton,
                     Button favouriteButton, Button saveButton,
                     Button gameButton, Button logoutButton) {
        this.searchButton = searchButton;
        this.translateButton = translateButton;
        this.favouriteButton = favouriteButton;
        this.saveButton = saveButton;
        this.gameButton = gameButton;
        this.logoutButton = logoutButton;

        bindLabel(searchLabel, searchButton);
        bindLabel(translateLabel, translateButton);
        bindLabel(favouriteLabel, favouriteButton);
        bindLabel(editLabel, saveButton);
        bindLabel(gameLabel, gameButton);
    }

    public void loadController(SearchController searchController,
                               TranslateController translateController,
                               FavouriteController favouriteController) {
        this.searchController = searchController;
        this.translateController = translateController;
        this.favouriteController = favouriteController;
    }
}
