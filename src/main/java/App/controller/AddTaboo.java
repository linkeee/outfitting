package App.controller;
import java.io.IOException;

import App.database.TabooDatabase;
import App.dataModel.TabooData;
import App.utile.FxmlUtile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddTaboo {

    @FXML
    private TextField jinjiIDTextField;

    @FXML
    private TextArea jinjiTextArea;

    @FXML
    private TextField jinjiNameTextField;

    @FXML
    private TextField outfittingTextField;

    @FXML
    private ChoiceBox<String> jinjiLevelChoiceBox;

    @FXML
    private ChoiceBox<String> chuanboTypeChoiceBox;

    private ObservableList<String> shipTypeList = FXCollections.observableArrayList("油船", "散货船", "集装箱船", "FPSO");
    private ObservableList<String> jinjiLevelList = FXCollections.observableArrayList("公司级", "部门级", "船级社级");

    private TabooData tabooData = new TabooData();
    private String editTabooId;

    @FXML
    void initialize () {
        chuanboTypeChoiceBox.setItems(shipTypeList);
        jinjiLevelChoiceBox.setItems(jinjiLevelList);
    }

    //将选择的禁忌信息添加到修改界面中
    private void setJinji(TabooData tabooData) {
        this.editTabooId = tabooData.getJinjiID();

        if (editTabooId != null) {
            jinjiIDTextField.setText(tabooData.getJinjiID());
            outfittingTextField.setText(tabooData.getOutfittingType());
            jinjiNameTextField.setText(tabooData.getJinjiName());
            jinjiTextArea.setText(tabooData.getJinjiContent());
            jinjiLevelChoiceBox.setValue(tabooData.getJjinjiLevel());
            chuanboTypeChoiceBox.setValue(tabooData.getShipType());
        }
    }

    @FXML
    public void handleOk(ActionEvent event) throws IOException {

        tabooData.setJinjiID(jinjiIDTextField.getText());
        tabooData.setJinjiName(jinjiNameTextField.getText());
        tabooData.setShipType(chuanboTypeChoiceBox.getValue());
        tabooData.setOutfittingType(outfittingTextField.getText());
        tabooData.setJinjiContent(jinjiTextArea.getText());
        tabooData.setJjinjiLevel(jinjiLevelChoiceBox.getValue());

        if (editTabooId != null) {
            TabooDatabase.update(tabooData, editTabooId);
            closeAddTaboo(event);
        } else {
            TabooDatabase.insert(tabooData);
            closeAddTaboo(event);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event){
        closeAddTaboo(event);
    }

    //显示禁忌添加页面
    void showAddTaboo(TabooData tabooData) throws IOException{
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddTaboo.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit TabooData");
        stage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL表示一次只能打开一个窗口进行操作
        stage.setScene(scene);

        AddTaboo addTaboo =loader.getController();
        addTaboo.setJinji(tabooData);

        stage.showAndWait();
    }

    //关闭界面
    private void closeAddTaboo(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
