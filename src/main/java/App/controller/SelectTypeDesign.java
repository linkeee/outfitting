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
import java.util.ArrayList;
import java.util.Collections;
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
        FxmlUtile.setStyle(createProjBtn, inputParamBtn, calculateBtn, correctBtn, selectTypeBtn);

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/CreateProject.fxml");
        selectTypeBorderPane.setCenter(loader.load());
    }

    @FXML
    void inputParameterAction(ActionEvent event) throws IOException {
        FxmlUtile.setStyle(inputParamBtn, createProjBtn, calculateBtn, correctBtn, selectTypeBtn);

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/InputParameter.fxml");
        selectTypeBorderPane.setCenter(loader.load());
    }

    @FXML
    void calculateAction(ActionEvent event) throws IOException {
        FxmlUtile.setStyle(calculateBtn, createProjBtn, inputParamBtn, correctBtn, selectTypeBtn);

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/Calculate.fxml");
        selectTypeBorderPane.setCenter(loader.load());
    }

    @FXML
    void modifyResultAction(ActionEvent event) throws IOException {
        FxmlUtile.setStyle(correctBtn, createProjBtn, inputParamBtn, calculateBtn, selectTypeBtn);

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/ModifyResult.fxml");
        selectTypeBorderPane.setCenter(loader.load());
    }

    @FXML
    void selectTypeAction(ActionEvent event) throws IOException {
        FxmlUtile.setStyle(selectTypeBtn, createProjBtn, inputParamBtn, calculateBtn, correctBtn);

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/SelectTheType.fxml");
        selectTypeBorderPane.setCenter(loader.load());
    }

}
