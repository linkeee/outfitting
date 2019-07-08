package App.controller;

import App.dataModel.ParameterData;
import App.database.ParameterDb;
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

public class KBParameter {

    @FXML
    private TableColumn<?, ?> outfittingNameTC;

    @FXML
    private Button modifyBtn;

    @FXML
    private TableView<ParameterData> paramTV;

    @FXML
    private TableColumn<?, ?> paramDescTC;

    @FXML
    private TableColumn<?, ?> paramScopeTC;

    @FXML
    private Button addBtn;

    @FXML
    private TableColumn<?, ?> paramNameTC;

    @FXML
    private TableColumn<?, ?> paramTypeTC;

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();

    @FXML
    void addAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddParameter.fxml");
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("添加");
        stage.showAndWait();
        paramTV.setItems(FXCollections.observableArrayList(ParameterDb.getParameterList()));
    }

    @FXML
    void modifyAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddParameter.fxml");
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("修改");

        AddParameter controller = loader.getController();
        ParameterData parameterData = paramTV.getSelectionModel().getSelectedItem();
        controller.show(parameterData, parameterData.getParam_id());
        stage.showAndWait();
        paramTV.setItems(FXCollections.observableArrayList(ParameterDb.getParameterList()));
    }

    @FXML
    void initialize() {
        outfittingNameTC.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        paramNameTC.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        paramDescTC.setCellValueFactory(new PropertyValueFactory<>("param_description"));
        paramScopeTC.setCellValueFactory(new PropertyValueFactory<>("param_scope"));
        paramTypeTC.setCellValueFactory(new PropertyValueFactory<>("param_type"));
        paramTV.setItems(FXCollections.observableArrayList(ParameterDb.getParameterList()));
    }
}
