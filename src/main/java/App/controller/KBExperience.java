package App.controller;

import App.dataModel.ExperienceData;
import App.database.ExperienceDb;
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
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class KBExperience {

    @FXML
    private TableColumn<?, ?> e53;

    @FXML
    private TextField experienceNameTextField1;

    @FXML
    private TableColumn<?, ?> e52;

    @FXML
    private TableColumn<?, ?> e55;

    @FXML
    private ComboBox<String> experienceshipType1;

    @FXML
    private TextArea experienceTextArea1;

    @FXML
    private TableColumn<?, ?> e54;

    @FXML
    private TextField experienceQueryTextField;

    @FXML
    private TableView<ExperienceData> experienceTable1;

    @FXML
    private TableColumn<ExperienceData, String> expFilePathTC;

    @FXML
    private TextField experienceOutfittingTypeTextField1;

    private String expFile;

    //经验修改，增加，删除
    private AddExperience aec = AddExperience.getInstance();
    private JieBaUtils jieBaUtils = JieBaUtils.getInstance();

    private void refreshTfIdf() {
        Task task = new Task() {
            @Override
            protected Object call() {
                Map<String, String> map = jieBaUtils.getDocumentsTfIdfMap(ExperienceDb.getIndexAndContentMap());
                ExperienceDb.updateTfIdf(map);
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "正在更新文本TF-IDF值，请稍后...");
        progressFrom.activateProgressBar();
    }

    private void refresh() {
        experienceTable1.setItems(FXCollections.observableArrayList(ExperienceDb.getExpDataList()));
        experienceshipType1.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        showExperienceDetails(null);
        refreshTfIdf();
    }

    @FXML
    private void handleExperienceQuery() throws SQLException {
        experienceTable1.setItems(ExperienceDb.query(
                experienceshipType1.getValue(),
                experienceOutfittingTypeTextField1.getText(),
                experienceNameTextField1.getText(),
                experienceQueryTextField.getText()
        ));
    }

    @FXML
    private void handleResetExperience() {
        refresh();
    }

    @FXML
    private void handleDeleteExperience() {
        ExperienceData deletedExp = experienceTable1.getSelectionModel().getSelectedItem();
        String deletedExpId = deletedExp.getExpId();
        ExperienceDb.delete(deletedExpId);
        refresh();
    }

    @FXML
    private void showAddExperience() throws IOException {
        ExperienceData tempExperienceData = new ExperienceData();
        aec.showAddExperience(tempExperienceData);
        refresh();
    }

    @FXML
    private void handleEditExperience() throws Exception {
        ExperienceData selectedExperience = experienceTable1.getSelectionModel().getSelectedItem();
        aec.showAddExperience(selectedExperience);
        refresh();
    }

    @FXML
    void initialize() {
        //经验表里添加经验内容
        experienceshipType1.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        e52.setCellValueFactory(new PropertyValueFactory<>("expShipType"));
        e53.setCellValueFactory(new PropertyValueFactory<>("expOutfittingRegion"));
        e54.setCellValueFactory(new PropertyValueFactory<>("expName"));
        e55.setCellValueFactory(new PropertyValueFactory<>("expContent"));
        expFilePathTC.setCellValueFactory(new PropertyValueFactory<>("expFilePath"));
        expFilePathTC.setCellFactory(param -> new HyperlinkTableCell<>());

        experienceTable1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showExperienceDetails(newValue);
                expFile = newValue.getExpFilePath();
            }
        });

        experienceTable1.setItems(FXCollections.observableArrayList(ExperienceDb.getExpDataList()));

        MenuItem expItem1 = new MenuItem("打开文件");
        expItem1.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(expFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem expItem2 = new MenuItem("打开文件夹");
        expItem2.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(new File(expFile).getParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu expMenu = new ContextMenu();
        expMenu.getItems().addAll(expItem1, expItem2);
        experienceTable1.setOnContextMenuRequested(event -> expMenu.show(experienceTable1, event.getScreenX(), event.getScreenY()));
        experienceTable1.setOnMouseClicked(event -> expMenu.hide());
    }

    //在对应格子中显示选择的属性
    private void showExperienceDetails(ExperienceData experienceData) {
        if (experienceData != null) {
            experienceNameTextField1.setText(experienceData.getExpName());
            experienceOutfittingTypeTextField1.setText(experienceData.getExpOutfittingRegion());
            experienceTextArea1.setText(experienceData.getExpContent());
            experienceshipType1.setValue(experienceData.getExpShipType());
        } else {
            experienceNameTextField1.setText("");
            experienceOutfittingTypeTextField1.setText("");
            experienceTextArea1.setText("");
            experienceshipType1.setValue(null);
        }
    }

}
