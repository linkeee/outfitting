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

public class DatabaseMana {

    @FXML
    private BorderPane DBManaBorderPane;

    @FXML
    private Button manufacturerBtn;

    @FXML
    private VBox DBManaVbox;

    @FXML
    private Label DBManaLabel;

    @FXML
    void goManufacturerAction(ActionEvent event) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/DBManufacturer.fxml");
        DBManaBorderPane.setCenter(loader.load());
        FxmlUtile.setStyle(manufacturerBtn);
    }

    @FXML
    void initialize() {
    }
}
