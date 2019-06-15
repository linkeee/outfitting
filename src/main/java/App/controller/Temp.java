package App.controller;

import App.dataModel.TempModel;
import App.utile.FxmlUtile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Temp {

    @FXML
    private TableColumn<TempModel, String> tempTC;

    @FXML
    private TableView<TempModel> tempTV;

    private final ObservableList<TempModel> data = FXCollections.observableArrayList(
            new TempModel("case 1: 2 points for the oil tank, 1 point for the middle of the oil hold, 1 point for the head"),
            new TempModel("case 2: 1 points for the oil tank, 2 point for the middle of the oil hold, 2 point for the head"),
            new TempModel("case 3: 2 points for the oil tank, 1 point for the middle of the oil hold, 2 point for the head")
    );

    @FXML
    void initialize() {
        tempTC.setCellValueFactory(cellData -> cellData.getValue().myTempProperty());
        tempTV.setItems(data);
    }

    @FXML
    void goHome(ActionEvent event) throws IOException {
        closeTemp(event);
        Home home = new Home();
        home.showHomeWindows();
    }

    //显示舾装智能设计界面
    void showTemp() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/Temp.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Layout Design");
        stage.getIcons().add(new Image("App/appView/images/java图标.png"));
        stage.show();
    }

    private void closeTemp(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
