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
        FxmlUtile.setStyle(exportResultBtn, exportItemListBtn);
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/ExportResult.fxml");
        exportBorderPane.setCenter(loader.load());
    }

    @FXML
    void exportItemListAction(ActionEvent event) {
        FxmlUtile.setStyle(exportItemListBtn, exportResultBtn);
    }

    @FXML
    void initialize() {

    }
}
