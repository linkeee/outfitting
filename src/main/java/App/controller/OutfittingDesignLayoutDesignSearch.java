package App.controller;

import App.dataModel.LayoutData;
import App.database.LayoutDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class OutfittingDesignLayoutDesignSearch {

    @FXML
    private TextField layoutDesignQueryTextField;

    @FXML
    private TextField layoutTypeTextField;

    @FXML
    private TextField depthTextFiled;

    @FXML
    private TableView<LayoutData> layoutDesignTable;

    @FXML
    private TextField draughtTextField;

    @FXML
    private TableColumn<LayoutData, String> l52;

    @FXML
    private TableColumn<LayoutData, String> l51;

    @FXML
    private TableColumn<LayoutData, String> l54;

    @FXML
    private TableColumn<LayoutData, String> l53;

    @FXML
    private TableColumn<LayoutData, String> l56;

    @FXML
    private TableColumn<LayoutData, String> l55;

    @FXML
    private TextField weightTextField;

    @FXML
    private ImageView design2ImageView;

    @FXML
    private TextField layoutShipNumTextField;

    @FXML
    private TextArea layoutDesignTextArea;

    @FXML
    private TextField widthTextFiled;

    @FXML
    private TextField coefficientsTextField;

    @FXML
    private TextField lengthTextFiled;

    @FXML
    private ChoiceBox<String> chuanboTypeChoiceBox;

    @FXML
    private TextField layoutNameTextField;

    @FXML
    private ImageView design1ImageView;

    @FXML
    void initialize() {
        chuanboTypeChoiceBox.setItems(shipTypeList);

        //添加区域布局设计
        l51.setCellValueFactory(cellData -> cellData.getValue().layoutDesignIDProperty());
        l52.setCellValueFactory(cellData -> cellData.getValue().layoutDesignNameProperty());
        l53.setCellValueFactory(cellData -> cellData.getValue().layoutDesignTypeProperty());
        l54.setCellValueFactory(cellData -> cellData.getValue().layoutShipTypeProperty());
        l55.setCellValueFactory(cellData -> cellData.getValue().shipWeightProperty());
        l56.setCellValueFactory(cellData -> cellData.getValue().layoutDesignContentProperty());
        refreshLayoutDesignTable();

        showLayoutDesignDetails(null);
        layoutDesignTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showLayoutDesignDetails(newValue)
        );
    }

    @FXML
    private void handleLayoutDesignQuery() throws SQLException {
        String keyword = chuanboTypeChoiceBox.getValue()+layoutNameTextField.getText()+layoutTypeTextField.getText()+layoutShipNumTextField.getText()+lengthTextFiled.getText()+widthTextFiled.getText()+depthTextFiled.getText()+draughtTextField.getText()+coefficientsTextField.getText()+weightTextField.getText();
        layoutDesignTable.setItems(LayoutDb.query(keyword));
    }

    @FXML
    private void handleResetLayoutDesign() {
        layoutDesignTable.setItems(LayoutDb.getLayoutDataList());
        showLayoutDesignDetails(null);
    }

    private ObservableList<String> shipTypeList = FXCollections.observableArrayList("油船", "散货船", "集装箱船", "FPSO");

    //在表格中显示数据库的内容
    void refreshLayoutDesignTable() {
        layoutDesignTable.setItems(LayoutDb.getLayoutDataList());
    }

    //实例详细显示
    private void showLayoutDesignDetails(LayoutData layoutData){
        if(layoutData !=null){
            layoutNameTextField.setText(layoutData.getLayoutDesignName());
            layoutTypeTextField.setText(layoutData.getLayoutDesignType());
            layoutShipNumTextField.setText(layoutData.getLayoutShipNum());
            lengthTextFiled.setText(layoutData.getShipLength());
            widthTextFiled.setText(layoutData.getShipWidth());
            depthTextFiled.setText(layoutData.getShipDepth());
            draughtTextField.setText(layoutData.getShipDraught());
            coefficientsTextField.setText(layoutData.getShipCoefficients());
            weightTextField.setText(layoutData.getShipWeight());
            layoutDesignTextArea.setText(layoutData.getLayoutDesignContent());
            chuanboTypeChoiceBox.setValue(layoutData.getLayoutShipType());
        }else{
            layoutNameTextField.setText("");
            layoutTypeTextField.setText("");
            layoutShipNumTextField.setText("");
            lengthTextFiled.setText("");
            widthTextFiled.setText("");
            depthTextFiled.setText("");
            draughtTextField.setText("");
            coefficientsTextField.setText("");
            weightTextField.setText("");
            layoutDesignTextArea.setText("");
            chuanboTypeChoiceBox.setValue("");
        }
    }
}
