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

public class Export {

    @FXML
    private BorderPane exportBorderPane;

    @FXML
    private Button exportItemListBtn;

    @FXML
    private Button exportResultBtn;

    @FXML
    private VBox buttonVBox;

    @FXML
    private Label firstNameLabel;

    @FXML
    void exportResultAction(ActionEvent event) throws IOException {
        setStyle("rgb(0, 180, 255)", "rgb(0, 150, 255)");

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/ExportResult.fxml");
        exportBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                ExportResult controller = loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void exportItemListAction(ActionEvent event) {
        setStyle("rgb(0, 150, 255)", "rgb(0, 180, 255)");

    }

    @FXML
    void initialize() {

    }

    private void setStyle(String btn1Style, String btn2Style) {
        exportResultBtn.setStyle("-fx-background-color: " + btn1Style);
        exportItemListBtn.setStyle("-fx-background-color: " + btn2Style);
    }
}
