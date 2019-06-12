package App.controller;

import App.appModel.CriterionData;

import App.database.CriterionDatabase;
import App.function.FileReader;
import App.utile.FxmlUtile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCriterion {

    @FXML
    private TextField critNameTextField;

    @FXML
    private ComboBox<String> critClassificationSocietyChoiceBox;

    @FXML
    private TextField critIDTextField;

    @FXML
    private ComboBox<String> chuanboTypeChoiceBox;

    @FXML
    private TextField outfittingTextField;

    @FXML
    private TextArea critTextArea;

    @FXML
    private Hyperlink pathNameHyperlink;

    @FXML
    private HBox criterionContentHBox;

    private CriterionData criterion = new CriterionData();
    private String editCriterionId;

    private ObservableList<String> shipTypeList = FXCollections.observableArrayList(null, "油轮", "散货船", "集装箱船", "平台", "豪华游轮");
    private ObservableList<String> classificationSocietyList = FXCollections.observableArrayList(null, "CCS", "BV", "ABS", "DNV-GL", "LR");

    @FXML
    void initialize() {
        critClassificationSocietyChoiceBox.setItems(classificationSocietyList);
        chuanboTypeChoiceBox.setItems(shipTypeList);
    }

    //将选择的人员信息添加到修改界面中
    private void setGuiFan(CriterionData criterion) {
        this.editCriterionId = criterion.getCriId();

        if (editCriterionId != null) {
            critIDTextField.setText(criterion.getCriId());
            critNameTextField.setText(criterion.getCriName());
            chuanboTypeChoiceBox.setValue(criterion.getCriShipType());
            outfittingTextField.setText(criterion.getCriOutfittingRegion());
            critClassificationSocietyChoiceBox.setValue(criterion.getCriShipCompany());
            pathNameHyperlink.setText(criterion.getCriFilePath());
            critTextArea.setText(criterion.getCriContent());
        }
    }

    @FXML
    public void handleOk(ActionEvent event) throws IOException {

        criterion.setCriId(critIDTextField.getText());
        criterion.setCriName(critNameTextField.getText());
        criterion.setCriShipType(chuanboTypeChoiceBox.getValue());
        criterion.setCriOutfittingRegion(outfittingTextField.getText());
        criterion.setCriShipCompany(critClassificationSocietyChoiceBox.getValue());
        criterion.setCriContent(critTextArea.getText());
        String[] temp = pathNameHyperlink.toString().split("'");
        criterion.setCriFilePath(temp[1]);

        if (editCriterionId != null) {
            CriterionDatabase.update(criterion, editCriterionId);
            closeAddGuiFan(event);
        } else {
            CriterionDatabase.insert(criterion);
            closeAddGuiFan(event);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeAddGuiFan(event);
    }

    @FXML
    void handleDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            System.out.println(dragboard.getFiles());
            String[] temp = dragboard.getFiles().toString().split("\\[");
            pathNameHyperlink.setText(temp[temp.length - 1].split("]")[0]);
            critTextArea.setText(FileReader.readFileContent(temp[temp.length - 1].split("]")[0]));
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != critTextArea && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    //显示规范添加页面
    void showAddGuiFan(CriterionData criterionData) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddCriterion.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit criterionData");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        AddCriterion addCriterion = loader.getController();
        addCriterion.setGuiFan(criterionData);

        stage.showAndWait();
    }

    //关闭界面
    private void closeAddGuiFan(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
