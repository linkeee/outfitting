package App.controller;

import App.utile.Docker;
import App.utile.FxmlUtile;
import App.utile.ProgressFrom;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;

public class SelectTypeDesign {

    @FXML
    private BorderPane selectTypeBorderPane;

    @FXML
    private Button createProjBtn, inputParamBtn, calculateBtn, correctBtn, selectTypeBtn;

    @FXML
    void initialize() {
        Docker.put("selectTypeBorderPane", selectTypeBorderPane);

        Docker.put("createProjBtn", createProjBtn);
        Docker.put("inputParamBtn", inputParamBtn);
        Docker.put("calculateBtn", calculateBtn);
        Docker.put("correctBtn", correctBtn);
        Docker.put("selectTypeBtn", selectTypeBtn);
    }

    @FXML
    void createProjectAction(ActionEvent event) throws IOException {
        setStyle("#646464", "#505050", "#505050", "#505050", "#505050");

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/CreateProject.fxml");
        selectTypeBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                CreateProject controller = loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void inputParameterAction(ActionEvent event) throws IOException {
        setStyle("#505050", "#646464", "#505050", "#505050", "#505050");

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/InputParameter.fxml");
        selectTypeBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                InputParameter controller = loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void calculateAction(ActionEvent event) throws IOException {
        setStyle("#505050", "#505050", "#646464", "#505050", "#505050");

    }

    @FXML
    void modifyResultAction(ActionEvent event) throws IOException {
        setStyle("#505050", "#505050", "#505050", "#646464", "#505050");

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/ModifyResult.fxml");
        selectTypeBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                ModifyResult controller = loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void selectTypeAction(ActionEvent event) throws IOException {
        setStyle("#505050", "#505050", "#505050", "#505050", "#646464");

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/SelectTheType.fxml");
        selectTypeBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                SelectTheType controller = loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    private void setStyle(String btn1, String btn2, String btn3, String btn4, String btn5) {
        createProjBtn.setStyle("-fx-background-color: " + btn1);
        inputParamBtn.setStyle("-fx-background-color: " + btn2);
        calculateBtn.setStyle("-fx-background-color: " + btn3);
        correctBtn.setStyle("-fx-background-color: " + btn4);
        selectTypeBtn.setStyle("-fx-background-color: " + btn5);
    }

}
