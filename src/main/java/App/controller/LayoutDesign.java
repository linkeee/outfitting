package App.controller;

import App.dataModel.LayoutData;
import App.database.LayoutDb;
import App.utile.Constant;
import App.utile.HyperlinkTableCell;
import App.utile.JieBaUtils;
import App.utile.ProgressFrom;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LayoutDesign {

    @FXML
    private ComboBox<String> shipTypeCB;

    @FXML
    private TextField draughtTF;

    @FXML
    private TextField coefTF;

    @FXML
    private Label titleLabel;

    @FXML
    private Button searchBtn;

    @FXML
    private TableColumn<LayoutData, String> filePathTC;

    @FXML
    private TextField loadTF;

    @FXML
    private TextField searchTF;

    @FXML
    private TableColumn<?, ?> itemNameTC;

    @FXML
    private Button resetBtn;

    @FXML
    private TableColumn<?, ?> shipTypeTC;

    @FXML
    private TextField widthTF;

    @FXML
    private TextField lengthTF;

    @FXML
    private TitledPane titledPane;

    @FXML
    private TextArea contentTA;

    @FXML
    private TextField shipNumTF;

    @FXML
    private TextField depthTF;

    @FXML
    private TableView<LayoutData> table;

    @FXML
    private TableColumn<?, ?> shipNumTC;

    @FXML
    private ComboBox<String> itemNameCB;

    private JieBaUtils jieBaUtils = JieBaUtils.getInstance();

    @FXML
    void searchAction(ActionEvent event) {

        String inputStr = searchTF.getText().trim();
        Map<String, Double> map = jieBaUtils.getSortedRelativityMap(inputStr, LayoutDb.getIndexAndTfIdfMapStr());
        List<String> list = new LinkedList<>(map.keySet());
        List<LayoutData> orderedDataList = LayoutDb.getOrderedDataList(list);
        table.setItems(FXCollections.observableArrayList(orderedDataList));
    }

    @FXML
    void resetAction(ActionEvent event) {
        refresh();
        searchTF.setText(null);
        titledPane.setExpanded(false);
    }

    private void refresh() {
        table.setItems(FXCollections.observableArrayList(LayoutDb.getAllLayoutData()));
        itemNameCB.setItems(FXCollections.observableArrayList(Constant.getOutfittingName()));
        shipTypeCB.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        showDetails(null);
    }

    private String file = null;

    @FXML
    void initialize() {

        shipTypeCB.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        itemNameCB.setItems(FXCollections.observableArrayList(Constant.getOutfittingName()));
        itemNameTC.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        shipTypeTC.setCellValueFactory(new PropertyValueFactory<>("shipType"));
        shipNumTC.setCellValueFactory(new PropertyValueFactory<>("shipNum"));
        filePathTC.setCellValueFactory(new PropertyValueFactory<>("filePath"));
        filePathTC.setCellFactory(param -> new HyperlinkTableCell<>());
        table.setItems(FXCollections.observableArrayList(LayoutDb.getAllLayoutData()));
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showDetails(newValue);
                file = newValue.getFilePath();
                titledPane.setExpanded(true);
            }
        });

        MenuItem item1 = new MenuItem("打开文件");
        item1.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem item2 = new MenuItem("打开文件夹");
        item2.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(new File(file).getParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(item1, item2);
        table.setOnContextMenuRequested(event -> menu.show(table, event.getScreenX(), event.getScreenY()));
        table.setOnMouseClicked(event -> menu.hide());
    }

    private void showDetails(LayoutData layoutData) {
        if (layoutData != null) {
            itemNameCB.setValue(layoutData.getOutfitting_name());
            shipTypeCB.setValue(layoutData.getShipType());
            shipNumTF.setText(layoutData.getShipNum());
            coefTF.setText(layoutData.getShipTypeCoefficient());
            loadTF.setText(layoutData.getShipLoad());
            lengthTF.setText(layoutData.getShipLength());
            widthTF.setText(layoutData.getShipWidth());
            depthTF.setText(layoutData.getShipDepth());
            draughtTF.setText(layoutData.getShipDraught());
            contentTA.setText(layoutData.getLayoutContent());
        } else {
            itemNameCB.setValue("");
            shipTypeCB.setValue("");
            shipNumTF.setText("");
            coefTF.setText("");
            loadTF.setText("");
            lengthTF.setText("");
            widthTF.setText("");
            depthTF.setText("");
            draughtTF.setText("");
            contentTA.setText("");
        }
    }
}
