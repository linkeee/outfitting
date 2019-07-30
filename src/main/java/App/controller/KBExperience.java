package App.controller;

import App.dataModel.ExperienceData;
import App.database.ExperienceDb;
import App.utile.Constant;
import App.utile.HyperlinkTableCell;
import App.utile.JieBaUtils;
import App.utile.ProgressFrom;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KBExperience {

    private final String[] shiptype = {"All"};
    @FXML
    private TableColumn<?, ?> e53;
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
    private Button filterBtn, closefilterBtn;
    @FXML
    private HBox filterHbox;
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
//        progressFrom.activateProgressBar();
    }

    @FXML
    void filterAction() {
        filterHbox.setDisable(false);
        filterHbox.setVisible(true);
        filterHbox.setMinHeight(20);
        filterHbox.setMaxHeight(25);
        filterBtn.setDisable(true);
        filterBtn.setVisible(false);
        experienceshipType1.setValue("All");

        if (filterHbox.isVisible()) {
            List<ExperienceData> allList = ExperienceDb.getExpDataList();
            experienceshipType1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                shiptype[0] = newValue;
                List<ExperienceData> list = new LinkedList<>();
                for (ExperienceData e : allList) {
                    if ((e.getExpShipType().equals(shiptype[0]) || shiptype[0].equals("All")))
                        list.add(e);
                }
                experienceTable1.setItems(FXCollections.observableArrayList(list));
            });
        }
    }

    @FXML
    void closefilterAction() {
        experienceshipType1.setValue("All");
        filterHbox.setDisable(true);
        filterHbox.setVisible(false);
        filterHbox.setMinHeight(0);
        filterHbox.setMaxHeight(0);
        filterBtn.setDisable(false);
        filterBtn.setVisible(true);
        experienceTable1.setItems(FXCollections.observableArrayList(ExperienceDb.getExpDataList()));
    }

    private void refresh() {
        experienceTable1.setItems(FXCollections.observableArrayList(ExperienceDb.getExpDataList()));
        experienceshipType1.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        refreshTfIdf();
    }

    @FXML
    private void handleExperienceQuery() throws SQLException {
        if (filterHbox.isVisible()) {
            List<ExperienceData> allList = ExperienceDb.getExpDataList();
            List<ExperienceData> list = new LinkedList<>();
            for (ExperienceData e : allList) {
                if ((e.getExpShipType().equals(shiptype[0]) || shiptype[0].equals("All")))
                    list.add(e);
            }
            experienceTable1.setItems(FXCollections.observableArrayList(list));
        } else {
            experienceTable1.setItems(FXCollections.observableArrayList(ExperienceDb.getExpDataList()));
        }

        // 将输入进行分词
        String inputStr = experienceQueryTextField.getText().trim();
        if (inputStr.equals("")) return;
        // 拼接目前table中显示的条目的{id:{tfidf}}
        Map<String, String> idtfidfmap = new HashMap<>();
        for (ExperienceData e : experienceTable1.getItems()) {
            idtfidfmap.put(e.getExpId(), e.getTfIdfMapStr());
        }
        // 文档id的排序
        Map<String, Double> map = jieBaUtils.getSortedRelativityMap(inputStr, idtfidfmap);
        java.util.List<String> list1 = new LinkedList<>(map.keySet());
        List<ExperienceData> orderedDataList = ExperienceDb.getOrderedDataList(list1);
        experienceTable1.setItems(FXCollections.observableArrayList(orderedDataList));
    }

    @FXML
    private void handleResetExperience() {
        experienceTable1.setItems(FXCollections.observableArrayList(ExperienceDb.getExpDataList()));
        experienceTextArea1.setText(null);
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
        experienceshipType1.setValue("All");
        e52.setCellValueFactory(new PropertyValueFactory<>("expShipType"));
        e53.setCellValueFactory(new PropertyValueFactory<>("expOutfittingRegion"));
        e54.setCellValueFactory(new PropertyValueFactory<>("expName"));
        e55.setCellValueFactory(new PropertyValueFactory<>("expContent"));
        expFilePathTC.setCellValueFactory(new PropertyValueFactory<>("expFilePath"));
        expFilePathTC.setCellFactory(param -> new HyperlinkTableCell<>());

        experienceTable1.setItems(FXCollections.observableArrayList(ExperienceDb.getExpDataList()));

        final String[] filepath = {""};
        MenuItem expItem1 = new MenuItem("打开文件");
        expItem1.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(filepath[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem expItem2 = new MenuItem("打开文件夹");
        expItem2.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(new File(filepath[0]).getParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu expMenu = new ContextMenu();
        expMenu.getItems().addAll(expItem1, expItem2);
        experienceTable1.setOnContextMenuRequested(event -> expMenu.show(experienceTable1, event.getScreenX(), event.getScreenY()));
        experienceTable1.setOnMouseClicked(event -> expMenu.hide());

        experienceTable1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                experienceTextArea1.setText(newValue.getExpContent());
                filepath[0] = newValue.getExpFilePath();
            }
        });

        closefilterAction();
    }

}
