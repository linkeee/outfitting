package App.controller;

import App.dataModel.LayoutData;
import App.database.LayoutDb;
import App.function.FileReader;
import App.utile.Constant;
import App.utile.FxmlUtile;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddLayout {

    private static final AddLayout instance = new AddLayout();
    FxmlUtile fxmlUtile = FxmlUtile.getInstance();
    @FXML
    private TextField tfShipLoad;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField tfDraught;
    @FXML
    private TextArea taContent;
    @FXML
    private TextField tfShipNum;
    @FXML
    private ComboBox<String> cbOutfittingName;
    @FXML
    private Button okBtn;
    @FXML
    private TextField tfShipWidth;
    @FXML
    private Hyperlink hlFilePath;
    @FXML
    private ComboBox<String> cbShipType;
    @FXML
    private TextField tfShipCoef;
    @FXML
    private TextField tfShipLength;
    @FXML
    private TextField tfShipDepth;

    public static AddLayout getInstance() {
        return instance;
    }

    @FXML
    void initialize() {
        cbOutfittingName.setItems(FXCollections.observableArrayList(Constant.getOutfittingName()));
        cbShipType.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
    }

    //将选择的布局设计信息添加到修改界面中
    private void setLayout(LayoutData layoutData) {

        this.id = layoutData.getId();
        cbOutfittingName.setValue(layoutData.getOutfitting_name());
        cbShipType.setValue(layoutData.getShipType());
        tfShipNum.setText(layoutData.getShipNum());
        tfShipCoef.setText(layoutData.getShipTypeCoefficient());
        tfShipLoad.setText(layoutData.getShipLoad());
        tfShipLength.setText(layoutData.getShipLength());
        tfShipWidth.setText(layoutData.getShipWidth());
        tfShipDepth.setText(layoutData.getShipDepth());
        tfDraught.setText(layoutData.getShipDraught());
        taContent.setText(layoutData.getLayoutContent());
        hlFilePath.setText(layoutData.getFilePath());

    }

    @FXML
    public void okAction(ActionEvent event) throws IOException {
        LayoutData layoutData = new LayoutData();

        layoutData.setOutfitting_name(cbOutfittingName.getValue());
        layoutData.setShipType(cbShipType.getValue());
        layoutData.setShipNum(tfShipNum.getText());
        layoutData.setShipTypeCoefficient(tfShipCoef.getText());
        layoutData.setShipLoad(tfShipLoad.getText());
        layoutData.setShipLength(tfShipLength.getText());
        layoutData.setShipWidth(tfShipWidth.getText());
        layoutData.setShipDepth(tfShipDepth.getText());
        layoutData.setShipDraught(tfDraught.getText());
        layoutData.setFilePath(hlFilePath.getText());
        layoutData.setLayoutContent(taContent.getText());

        if (id != null) {
            LayoutDb.update(layoutData, Integer.valueOf(id));
        } else {
            LayoutDb.insert(layoutData);
        }
        closeAddLayoutDesign(event);
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        closeAddLayoutDesign(event);
    }

    @FXML
    void dragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            String[] temp = dragboard.getFiles().toString().split("\\[");
            hlFilePath.setText(temp[temp.length - 1].split("]")[0]);
            taContent.setText(FileReader.readFileContent(temp[temp.length - 1].split("]")[0]));
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void dragOver(DragEvent event) {
        if (event.getGestureSource() != taContent && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    //显示布局设计添加页面
    void showAddLayout() throws IOException {
        fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddLayout.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setTitle("添加布局案例");
        stage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL表示一次只能打开一个窗口进行操作
        stage.setScene(scene);

        stage.showAndWait();
    }

    private String id;

    //显示布局设计添加页面
    void showEditLayout(LayoutData layoutData, Integer editId) throws IOException {
        fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddLayout.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setTitle("修改布局案例");
        stage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL表示一次只能打开一个窗口进行操作
        stage.setScene(scene);
        AddLayout addLayout = loader.getController();
        addLayout.setLayout(layoutData);

        stage.showAndWait();
    }

    //关闭界面
    private void closeAddLayoutDesign(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
