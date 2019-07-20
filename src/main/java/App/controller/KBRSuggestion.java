package App.controller;

import App.dataModel.SuggestionData;
import App.database.SuggestionDb;
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

public class KBRSuggestion {

    @FXML
    private ComboBox<String> shipTypeCB;

    @FXML
    private TableColumn<?, ?> outfittingRegionTC;

    @FXML
    private Button searchBtn;

    @FXML
    private TableColumn<SuggestionData, String> filePathTC;

    @FXML
    private TextArea problemTA;

    @FXML
    private ComboBox<String> chuandongcompanyCB;

    @FXML
    private TextArea sugContentTA;

    @FXML
    private Button resetBtn;

    @FXML
    private TableColumn<?, ?> shipTypeTC;

    @FXML
    private TextField inputTF;

    @FXML
    private TextArea methodTA;

    @FXML
    private TableColumn<?, ?> chuandongcompanyTC;

    @FXML
    private TableView<SuggestionData> table;

    private String file;
    private JieBaUtils jieBaUtils = JieBaUtils.getInstance();

    @FXML
    void searchAction(ActionEvent event) {
        String inputStr = inputTF.getText().trim();
        Map<String, Double> map = jieBaUtils.getSortedRelativityMap(inputStr, SuggestionDb.getIndexAndTfIdfMapStr());
        List<String> list = new LinkedList<>(map.keySet());
        List<SuggestionData> orderedDataList = SuggestionDb.getOrderedDataList(list);
        table.setItems(FXCollections.observableArrayList(orderedDataList));
    }

    @FXML
    void resetAction(ActionEvent event) {
        table.setItems(FXCollections.observableArrayList(SuggestionDb.getSugDataList()));
        shipTypeCB.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        chuandongcompanyCB.setItems(FXCollections.observableArrayList(Constant.getShipOwnerCompany()));
        showSuggestionDetail(null);
        inputTF.setText(null);
    }

    @FXML
    void initialize() {
        shipTypeCB.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        chuandongcompanyCB.setItems(FXCollections.observableArrayList(Constant.getShipOwnerCompany()));
        shipTypeTC.setCellValueFactory(new PropertyValueFactory<>("sugShipType"));
        chuandongcompanyTC.setCellValueFactory(new PropertyValueFactory<>("sugShipCompany"));
        outfittingRegionTC.setCellValueFactory(new PropertyValueFactory<>("sugOutfittingRegion"));
        filePathTC.setCellValueFactory(new PropertyValueFactory<>("sugFilePath"));
        filePathTC.setCellFactory(param -> new HyperlinkTableCell<>());
        table.setItems(FXCollections.observableArrayList(SuggestionDb.getSugDataList()));

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSuggestionDetail(newValue);
                file = newValue.getSugFilePath();
            }
        });

        MenuItem sugItem1 = new MenuItem("打开文件");
        sugItem1.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem sugItem2 = new MenuItem("打开文件夹");
        sugItem2.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(new File(file).getParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu sugMenu = new ContextMenu();
        sugMenu.getItems().addAll(sugItem1, sugItem2);
        table.setOnContextMenuRequested(event -> sugMenu.show(table, event.getScreenX(), event.getScreenY()));
        table.setOnMouseClicked(event -> sugMenu.hide());
    }

    private void showSuggestionDetail(SuggestionData suggestionData) {
        if (suggestionData != null) {
            problemTA.setText(suggestionData.getSugProblemDescribe());
            methodTA.setText(suggestionData.getSugSolutionDescribe());
            sugContentTA.setText(suggestionData.getSugContent());
            chuandongcompanyCB.setValue(suggestionData.getSugShipCompany());
            shipTypeCB.setValue(suggestionData.getSugShipType());
        } else {
            problemTA.setText(null);
            methodTA.setText(null);
            sugContentTA.setText(null);
            chuandongcompanyCB.setValue(null);
            shipTypeCB.setValue(null);
        }
    }
}
