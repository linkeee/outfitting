package App.controller;

import App.utile.FxmlUtile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class KBRecommend {

    @FXML
    private BorderPane recommendBorderPane;

    @FXML
    private VBox recVbox;

    @FXML
    private Button experienceBtn;

    @FXML
    private Label recLabel;

    @FXML
    private Button suggestionBtn;

    @FXML
    private Button criterionBtn;

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();

    @FXML
    void experienceAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBRExperience.fxml");
        recommendBorderPane.setCenter(loader.load());
    }

    @FXML
    void suggestionAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBRSuggestion.fxml");
        recommendBorderPane.setCenter(loader.load());
    }

    @FXML
    void criterionAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBRCriterion.fxml");
        recommendBorderPane.setCenter(loader.load());
    }

    @FXML
    void initialize() {
    }
}
