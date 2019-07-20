package App.controller;

import App.dataModel.CriterionData;
import App.database.CriterionDb;
import App.utile.Constant;
import App.utile.HyperlinkTableCell;
import App.utile.JieBaUtils;
import App.utile.ProgressFrom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class KBCriterion {

    @FXML
    private TableColumn<?, ?> g33;

    @FXML
    private TableColumn<?, ?> g32;

    @FXML
    private TextArea guifanTextArea;

    @FXML
    private TableColumn<?, ?> g35;

    @FXML
    private ComboBox<String> critShipTypeChoiceBox;

    @FXML
    private TableColumn<?, ?> g34;

    @FXML
    private TableView<CriterionData> guifanTable;

    @FXML
    private TableColumn<CriterionData, String> criterionUrlTC;

    @FXML
    private ComboBox<String> critClassificationSocietyChoiceBox;

    @FXML
    private TableColumn<?, ?> criterionClassificationSocietyTC;

    @FXML
    private TextField criterionQueryTextField;

    @FXML
    private TextField guifanNameTextField;

    @FXML
    private TextField guifanOutfittingTypeTextField;
    private String criterionFile;
    private JieBaUtils jieBaUtils = JieBaUtils.getInstance();

    private void refreshTfIdf() {
        Task task = new Task() {
            @Override
            protected Object call() {
                Map<String, String> map = jieBaUtils.getDocumentsTfIdfMap(CriterionDb.getIndexAndContentMap());
                CriterionDb.updateTfIdf(map);
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "正在更新文本TF-IDF值，请稍后...");
        progressFrom.activateProgressBar();
    }

    //规范搜索增加修改删除
    private AddCriterion acc = AddCriterion.getInstance();

    @FXML
    private void handleCriterionQuery() throws SQLException {
        guifanTable.setItems(CriterionDb.query(
                criterionQueryTextField.getText(),
                critShipTypeChoiceBox.getValue(),
                critClassificationSocietyChoiceBox.getValue(),
                guifanNameTextField.getText(),
                guifanOutfittingTypeTextField.getText()
        ));
    }

    @FXML
    private void handleResetGuiFan() {
        guifanTable.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));
        showGuiFanDetails(null);
        refresh();
    }

    @FXML
    private void handleDeleteGuiFan() {
        CriterionData deletedCrit = guifanTable.getSelectionModel().getSelectedItem();
        String deletedCritId = deletedCrit.getCriId();
        CriterionDb.delete(deletedCritId);
        guifanTable.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));
        refresh();
    }

    @FXML
    private void showAddGuiFan() throws IOException {
        CriterionData tempCriterionData = new CriterionData();
        acc.showAddGuiFan(tempCriterionData);
        guifanTable.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));
        refresh();
    }

    @FXML
    private void handleEditGuiFan() throws IOException {
        CriterionData selectedCrit = guifanTable.getSelectionModel().getSelectedItem();
        acc.showAddGuiFan(selectedCrit);
        guifanTable.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));
        refresh();
    }

    private void refresh() {
        critShipTypeChoiceBox.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        critClassificationSocietyChoiceBox.setItems(FXCollections.observableArrayList(Constant.getChuanjisheList()));
        refreshTfIdf();
    }

    @FXML
    void initialize() {
        critShipTypeChoiceBox.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        critClassificationSocietyChoiceBox.setItems(FXCollections.observableArrayList(Constant.getChuanjisheList()));
        //添加规范
        g32.setCellValueFactory(new PropertyValueFactory<>("criShipType"));
        criterionClassificationSocietyTC.setCellValueFactory(new PropertyValueFactory<>("criShipCompany"));
        g33.setCellValueFactory(new PropertyValueFactory<>("criOutfittingRegion"));
        g34.setCellValueFactory(new PropertyValueFactory<>("criName"));
        g35.setCellValueFactory(new PropertyValueFactory<>("criContent"));
        criterionUrlTC.setCellValueFactory(new PropertyValueFactory<>("criFilePath"));
        criterionUrlTC.setCellFactory(param -> new HyperlinkTableCell<>());

        guifanTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CriterionData>() {
            @Override
            public void changed(ObservableValue<? extends CriterionData> observable, CriterionData oldValue, CriterionData newValue) {
                if (newValue != null) {
                    showGuiFanDetails(newValue);
                    criterionFile = newValue.getCriFilePath();
                }
            }
        });

        guifanTable.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));

        MenuItem criterionItem1 = new MenuItem("打开文件");
        criterionItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(criterionFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem criterionItem2 = new MenuItem("打开文件夹");
        criterionItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(new File(criterionFile).getParent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ContextMenu criterionMenu = new ContextMenu();
        criterionMenu.getItems().addAll(criterionItem1, criterionItem2);
        guifanTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                criterionMenu.show(guifanTable, event.getScreenX(), event.getScreenY());
            }
        });
        guifanTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                criterionMenu.hide();
            }
        });
    }

    //在对应格子中显示选择的属性
    private void showGuiFanDetails(CriterionData guifanData) {
        if (guifanData != null) {
            guifanNameTextField.setText(guifanData.getCriName());
            critShipTypeChoiceBox.setValue(guifanData.getCriShipType());
            guifanOutfittingTypeTextField.setText(guifanData.getCriOutfittingRegion());
            critClassificationSocietyChoiceBox.setValue(guifanData.getCriShipCompany());
            guifanTextArea.setText(guifanData.getCriContent());
            System.out.println(guifanData.getCriFilePath());
        } else {
            guifanNameTextField.setText("");
            critShipTypeChoiceBox.setValue(null);
            guifanOutfittingTypeTextField.setText("");
            critClassificationSocietyChoiceBox.setValue(null);
            guifanTextArea.setText("");
        }
    }
}
