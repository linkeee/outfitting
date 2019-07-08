package App.controller;

import App.utile.FxmlUtile;
import App.utile.ProgressFrom;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class KnowledgeBaseMana {

    @FXML
    private Button paramBtn;

    @FXML
    private Button formulaBtn;

    @FXML
    private BorderPane kbManaBorderPane;

    @FXML
    private Label KBLabel;

    @FXML
    private Button experienceBtn;

    @FXML
    private Button qualityCaseBtn;

    @FXML
    private VBox KBManaVbox;

    @FXML
    private Button suggestionBtn;

    @FXML
    private Button criterionBtn;

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();

    @FXML
    void goExperienceAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBExperience.fxml");
        kbManaBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void goSuggestionAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBSuggestion.fxml");
        kbManaBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void goCriterionAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBCriterion.fxml");
        kbManaBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void goFormulaAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/consoler.fxml");
        kbManaBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void goParamAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBParameter.fxml");
        kbManaBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void goQualityCaseAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }
}
