package App.controller;

import App.dataModel.ManufacturerData;
import App.database.ManufacturerDb;
import App.utile.FxmlUtile;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DBManufacturer {

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();
    @FXML
    private TableColumn<?, ?> manuTC;
    @FXML
    private TableView<ManufacturerData> manuTV;
    @FXML
    private TableColumn<?, ?> itemTypeTC;
    @FXML
    private TableColumn<?, ?> remarkTC;
    @FXML
    private TableColumn<?, ?> itemTC;
    @FXML
    private TableColumn<?, ?> paramScopeTC;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button modifyBtn;

    @FXML
    void modifyAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddManufacturer.fxml");
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("修改");

        AddManufacturer controller = loader.getController();
        ManufacturerData manufacturerData = manuTV.getSelectionModel().getSelectedItem();
        controller.showModify(manufacturerData, manufacturerData.getId());
        stage.showAndWait();
        manuTV.setItems(FXCollections.observableArrayList(ManufacturerDb.getManuData()));
    }

    @FXML
    void addAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddManufacturer.fxml");
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("添加");
        stage.showAndWait();
        manuTV.setItems(FXCollections.observableArrayList(ManufacturerDb.getManuData()));
    }

    @FXML
    void deleteAction(ActionEvent event) {
        ManufacturerDb.delete(manuTV.getSelectionModel().getSelectedItem());
        manuTV.setItems(FXCollections.observableArrayList(ManufacturerDb.getManuData()));
    }

    @FXML
    void initialize() {
        itemTC.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        itemTypeTC.setCellValueFactory(new PropertyValueFactory<>("outfitting_type"));
        manuTC.setCellValueFactory(new PropertyValueFactory<>("manufacturer_name"));
        paramScopeTC.setCellValueFactory(new PropertyValueFactory<>("param_scope"));
        remarkTC.setCellValueFactory(new PropertyValueFactory<>("remark"));
        manuTV.setItems(FXCollections.observableArrayList(ManufacturerDb.getManuData()));
    }
}
