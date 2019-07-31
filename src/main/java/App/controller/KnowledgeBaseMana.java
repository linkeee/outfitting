package App.controller;

import App.formulalib.LibraryManager;
import App.utile.FxmlUtile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class KnowledgeBaseMana {

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();
    @FXML
    private Button formulaBtn;
    @FXML
    private BorderPane kbManaBorderPane;
    @FXML
    private Label KBLabel;
    @FXML
    private Button experienceBtn;
    @FXML
    private VBox KBManaVbox;
    @FXML
    private Button suggestionBtn;
    @FXML
    private Button criterionBtn;

    @FXML
    void goExperienceAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBExperience.fxml");
        kbManaBorderPane.setCenter(loader.load());
    }

    @FXML
    void goSuggestionAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBSuggestion.fxml");
        kbManaBorderPane.setCenter(loader.load());
    }

    @FXML
    void goCriterionAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBCriterion.fxml");
        kbManaBorderPane.setCenter(loader.load());
    }

    @FXML
    void layoutAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBLayout.fxml");
        kbManaBorderPane.setCenter(loader.load());
    }

    @FXML
    void goFormulaAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/consoler.fxml");
        kbManaBorderPane.setCenter(loader.load());
    }

    @FXML
    void initialize() {
        new LibraryManager().formulaManager();
    }

}
