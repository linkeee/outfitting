package App.controller;

import App.utile.FxmlUtile;
import App.utile.ProgressFrom;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SelectTypeDesign {

    @FXML
    private BorderPane selectTypeBorderPane;

    @FXML
    void createProjectAction(ActionEvent event) throws IOException {
        showCreateProj();
    }

    void showCreateProj() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/CreateProject.fxml");
        selectTypeBorderPane.setCenter(loader.load());
        selectTypeBorderPane.setPadding(new Insets(5, 5, 5, 5));

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
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/InputParameter.fxml");
        selectTypeBorderPane.setCenter(loader.load());
        selectTypeBorderPane.setPadding(new Insets(5, 5, 5, 5));

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

    }

    @FXML
    void modifyResultAction(ActionEvent event) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/ModifyResult.fxml");
        selectTypeBorderPane.setCenter(loader.load());
        selectTypeBorderPane.setPadding(new Insets(5, 5, 5, 5));

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
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/SelectTheType.fxml");
        selectTypeBorderPane.setCenter(loader.load());
        selectTypeBorderPane.setPadding(new Insets(5, 5, 5, 5));

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

}
