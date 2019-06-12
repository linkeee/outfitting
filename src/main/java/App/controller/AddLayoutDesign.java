package App.controller;

import App.appModel.LayoutData;
import App.database.LayoutDatabase;
import App.utile.FxmlUtile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

public class AddLayoutDesign {

    @FXML
    private TextField depthTextFiled;

    @FXML
    private TextField layoutTypeTextField;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private TextField draughtTextField;

    @FXML
    private TextField weightTextField;

    @FXML
    private TextField layoutShipNumTextField;

    @FXML
    private TextArea layoutDesignTextArea;

    @FXML
    private TextField widthTextFiled;

    @FXML
    private TextField layoutIDTextField;

    @FXML
    private TextField lengthTextFiled;

    @FXML
    private ComboBox<String> chuanboTypeChoiceBox;

    @FXML
    private TextField coefficientsTextField;

    @FXML
    private Label lbl;

    @FXML
    private TextField layoutNameTextField;

    private LayoutData layoutData = new LayoutData();
    private String editLayoutId;

    private ObservableList<String> shipTypeList = FXCollections.observableArrayList(null, "油船", "散货船", "集装箱船", "FPSO");

    @FXML
    void initialize() {
        chuanboTypeChoiceBox.setItems(shipTypeList);
    }

    //将选择的布局设计信息添加到修改界面中
    private void setLayoutDesign(LayoutData layoutData) {
        this.editLayoutId = layoutData.getLayoutDesignID();

        if (editLayoutId != null) {
            layoutIDTextField.setText(layoutData.getLayoutDesignID());
            layoutNameTextField.setText(layoutData.getLayoutDesignName());
            layoutTypeTextField.setText(layoutData.getLayoutDesignType());
            chuanboTypeChoiceBox.setValue(layoutData.getLayoutShipType());
            layoutShipNumTextField.setText(layoutData.getLayoutShipNum());
            lengthTextFiled.setText(layoutData.getShipLength());
            widthTextFiled.setText(layoutData.getShipWidth());
            depthTextFiled.setText(layoutData.getShipDepth());
            draughtTextField.setText(layoutData.getShipDraught());
            coefficientsTextField.setText(layoutData.getShipCoefficients());
            weightTextField.setText(layoutData.getShipWeight());
            layoutDesignTextArea.setText(layoutData.getLayoutDesignContent());
        }
    }

    @FXML
    public void handleOk(ActionEvent event) throws IOException {

        layoutData.setLayoutDesignID(layoutIDTextField.getText());
        layoutData.setLayoutDesignName(layoutNameTextField.getText());
        layoutData.setLayoutDesignType(layoutTypeTextField.getText());
        layoutData.setLayoutShipType(chuanboTypeChoiceBox.getValue());
        layoutData.setLayoutShipNum(layoutShipNumTextField.getText());
        layoutData.setShipLength(lengthTextFiled.getText());
        layoutData.setShipWidth(widthTextFiled.getText());
        layoutData.setShipDepth(depthTextFiled.getText());
        layoutData.setShipDraught(draughtTextField.getText());
        layoutData.setShipCoefficients(coefficientsTextField.getText());
        layoutData.setShipWeight(weightTextField.getText());
        layoutData.setLayoutDesignContent(layoutDesignTextArea.getText());

        if (editLayoutId != null) {
            LayoutDatabase.update(layoutData, editLayoutId);
            closeAddLayoutDesign(event);
        } else {
            LayoutDatabase.insert(layoutData);
            closeAddLayoutDesign(event);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event){
        closeAddLayoutDesign(event);
    }

    //显示布局设计添加页面
    void showAddLayoutDesign(LayoutData layoutData)throws IOException{
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddLayoutDesign.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit LayoutData");
        stage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL表示一次只能打开一个窗口进行操作
        stage.setScene(scene);

        AddLayoutDesign addLayoutDesign =loader.getController();
        addLayoutDesign.setLayoutDesign(layoutData);

        stage.showAndWait();
    }

    //关闭界面
    private void closeAddLayoutDesign(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
