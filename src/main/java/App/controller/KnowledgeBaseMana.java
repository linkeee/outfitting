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
import java.sql.SQLException;

public class KnowledgeBaseMana {

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();
    @FXML
    private BorderPane kbManaBorderPane;
    @FXML
    private Label KBLabel;
    @FXML
    private Button experienceBtn, suggestionBtn, criterionBtn, layoutBtn, formulaBtn;
    @FXML
    private VBox KBManaVbox;

    @FXML
    void goExperienceAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBExperience.fxml");
        kbManaBorderPane.setCenter(loader.load());
        FxmlUtile.setStyle(experienceBtn, suggestionBtn, criterionBtn, layoutBtn, formulaBtn);
    }

    @FXML
    void goSuggestionAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBSuggestion.fxml");
        kbManaBorderPane.setCenter(loader.load());
        FxmlUtile.setStyle(suggestionBtn, experienceBtn, criterionBtn, layoutBtn, formulaBtn);
    }

    @FXML
    void goCriterionAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBCriterion.fxml");
        kbManaBorderPane.setCenter(loader.load());
        FxmlUtile.setStyle(criterionBtn, experienceBtn, suggestionBtn, layoutBtn, formulaBtn);
    }

    @FXML
    void layoutAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBLayout.fxml");
        kbManaBorderPane.setCenter(loader.load());
        FxmlUtile.setStyle(layoutBtn, experienceBtn, suggestionBtn, criterionBtn, formulaBtn);
    }

    @FXML
    void goFormulaAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/consoler.fxml");
        kbManaBorderPane.setCenter(loader.load());
        FxmlUtile.setStyle(formulaBtn, experienceBtn, suggestionBtn, criterionBtn, layoutBtn);
    }

    @FXML
    void initialize() throws SQLException {
        new LibraryManager().formulaManager();
    }

}
