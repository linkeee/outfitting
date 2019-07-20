package App.controller;

import App.dataModel.CriterionData;
import App.database.CriterionDb;
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

public class KBRCriterion {

    @FXML
    private Button resetBtn;

    @FXML
    private ComboBox<String> shipTypeCB;

    @FXML
    private ComboBox<String> chuanjisheCB;

    @FXML
    private TableColumn<?, ?> shipTypeTC;

    @FXML
    private TableColumn<?, ?> chuanjisheTC;

    @FXML
    private TableColumn<?, ?> outfittingRegionTC;

    @FXML
    private TableColumn<?, ?> criterionNameTC;

    @FXML
    private TextField inputTF;

    @FXML
    private Button searchBtn;

    @FXML
    private TextArea contentTA;

    @FXML
    private TableColumn<CriterionData, String> criterionUrlTC;

    @FXML
    private TableView<CriterionData> table;

    private String criFile;
    private JieBaUtils jieBaUtils = JieBaUtils.getInstance();

    @FXML
    void searchAction(ActionEvent event) {
        String inputStr = inputTF.getText().trim();
        Map<String, Double> map = jieBaUtils.getSortedRelativityMap(inputStr, CriterionDb.getIndexAndTfIdfMapStr());
        List<String> list = new LinkedList<>(map.keySet());
        List<CriterionData> orderedDataList = CriterionDb.getOrderedDataList(list);
        table.setItems(FXCollections.observableArrayList(orderedDataList));
    }

    @FXML
    void resetAction(ActionEvent event) {
        table.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));
        shipTypeCB.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        chuanjisheCB.setItems(FXCollections.observableArrayList(Constant.getChuanjisheList()));
        showCriterionDetail(null);
        inputTF.setText(null);
    }

    @FXML
    void initialize() {
        shipTypeCB.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        chuanjisheCB.setItems(FXCollections.observableArrayList(Constant.getChuanjisheList()));
        shipTypeTC.setCellValueFactory(new PropertyValueFactory<>("criShipType"));
        chuanjisheTC.setCellValueFactory(new PropertyValueFactory<>("criShipCompany"));
        outfittingRegionTC.setCellValueFactory(new PropertyValueFactory<>("criOutfittingRegion"));
        criterionNameTC.setCellValueFactory(new PropertyValueFactory<>("criName"));
        criterionUrlTC.setCellValueFactory(new PropertyValueFactory<>("criFilePath"));
        criterionUrlTC.setCellFactory(param -> new HyperlinkTableCell<>());
        table.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showCriterionDetail(newValue);
                criFile = newValue.getCriFilePath();
            }
        });

        MenuItem criItem1 = new MenuItem("打开文件");
        criItem1.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(criFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem criItem2 = new MenuItem("打开文件夹");
        criItem2.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(new File(criFile).getParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu criMenu = new ContextMenu();
        criMenu.getItems().addAll(criItem1, criItem2);
        table.setOnContextMenuRequested(event -> criMenu.show(table, event.getScreenX(), event.getScreenY()));
        table.setOnMouseClicked(event -> criMenu.hide());
    }

    private void showCriterionDetail(CriterionData criterionData) {
        if (criterionData != null) {
            shipTypeCB.setValue(criterionData.getCriShipType());
            chuanjisheCB.setValue(criterionData.getCriShipCompany());
            contentTA.setText(criterionData.getCriContent());
        } else {
            shipTypeCB.setValue(null);
            chuanjisheCB.setValue(null);
            contentTA.setText(null);
        }
    }
}
