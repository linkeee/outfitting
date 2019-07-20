package App.controller;

import App.dataModel.ExperienceData;
import App.database.ExperienceDb;
import App.utile.Constant;
import App.utile.HyperlinkTableCell;
import App.utile.JieBaUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KBRExperience {

    @FXML
    private Button resetBtn;

    @FXML
    private ComboBox<String> shipTypeCB;

    @FXML
    private TableColumn<?, ?> shipTypeTC;

    @FXML
    private TableColumn<?, ?> outfittingRegionTC;

    @FXML
    private TextField inputTF;

    @FXML
    private Button searchBtn;

    @FXML
    private TableColumn<ExperienceData, String> filePathTC;

    @FXML
    private TextArea contentTA;

    @FXML
    private TableColumn<?, ?> nameTC;

    @FXML
    private TableView<ExperienceData> table;

    private String file;
    private JieBaUtils jieBaUtils = JieBaUtils.getInstance();

    @FXML
    void searchAction(ActionEvent event) {
        String inputStr = inputTF.getText().trim();
        Map<String, Double> map = jieBaUtils.getSortedRelativityMap(inputStr, ExperienceDb.getIndexAndTfIdfMapStr());
        List<String> list = new LinkedList<>(map.keySet());
        List<ExperienceData> orderedDataList = ExperienceDb.getOrderedDataList(list);
        table.setItems(FXCollections.observableArrayList(orderedDataList));
    }

    @FXML
    void resetAction(ActionEvent event) {
        table.setItems(FXCollections.observableArrayList(ExperienceDb.getExpDataList()));
        shipTypeCB.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        showExperienceDetail(null);
        inputTF.setText(null);
    }

    @FXML
    void initialize() {
        shipTypeCB.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        shipTypeTC.setCellValueFactory(new PropertyValueFactory<>("expShipType"));
        outfittingRegionTC.setCellValueFactory(new PropertyValueFactory<>("expOutfittingRegion"));
        nameTC.setCellValueFactory(new PropertyValueFactory<>("expName"));
        filePathTC.setCellValueFactory(new PropertyValueFactory<>("expFilePath"));
        filePathTC.setCellFactory(param -> new HyperlinkTableCell<>());

        table.setItems(FXCollections.observableArrayList(ExperienceDb.getExpDataList()));

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showExperienceDetail(newValue);
                file = newValue.getExpFilePath();
            }
        });

        MenuItem expItem1 = new MenuItem("打开文件");
        expItem1.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem expItem2 = new MenuItem("打开文件夹");
        expItem2.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(new File(file).getParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu expMenu = new ContextMenu();
        expMenu.getItems().addAll(expItem1, expItem2);
        table.setOnContextMenuRequested(event -> expMenu.show(table, event.getScreenX(), event.getScreenY()));
        table.setOnMouseClicked(event -> expMenu.hide());
    }

    private void showExperienceDetail(ExperienceData experienceData) {
        if (experienceData != null) {
            shipTypeCB.setValue(experienceData.getExpShipType());
            contentTA.setText(experienceData.getExpContent());
        } else {
            shipTypeCB.setValue(null);
            contentTA.setText(null);
        }
    }
}
