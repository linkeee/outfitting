package App.controller;

import App.dataModel.CriterionData;
import App.database.CriterionDb;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KBCriterion {

    private final String[] shiptype = {"All"};
    private final String[] classification = {"All"};
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
    private HBox filterHbox;
    @FXML
    private Button openfilterBtn, closefilterBtn;
    private JieBaUtils jieBaUtils = JieBaUtils.getInstance();
    //规范搜索增加修改删除
    private AddCriterion acc = AddCriterion.getInstance();

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
//        progressFrom.activateProgressBar();
    }

    @FXML
    void openfilterAction() {
        filterHbox.setDisable(false);
        filterHbox.setVisible(true);
        filterHbox.setMinHeight(20);
        filterHbox.setMaxHeight(25);
        openfilterBtn.setDisable(true);
        openfilterBtn.setVisible(false);
        critShipTypeChoiceBox.setValue("All");
        critClassificationSocietyChoiceBox.setValue("All");

        if (filterHbox.isVisible()) {
            List<CriterionData> allList = CriterionDb.getCriterionDataList();
            critShipTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                shiptype[0] = newValue;
                List<CriterionData> list = new LinkedList<>();
                for (CriterionData c : allList) {
                    if ((c.getCriShipType().equals(shiptype[0]) || shiptype[0].equals("All")) && (c.getCriShipCompany().equals(classification[0]) || classification[0].equals("All")))
                        list.add(c);
                }
                guifanTable.setItems(FXCollections.observableArrayList(list));
            });
            critClassificationSocietyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                classification[0] = newValue;
                List<CriterionData> list = new LinkedList<>();
                for (CriterionData c : allList) {
                    if ((c.getCriShipType().equals(shiptype[0]) || shiptype[0].equals("All")) && (c.getCriShipCompany().equals(classification[0]) || classification[0].equals("All")))
                        list.add(c);
                }
                guifanTable.setItems(FXCollections.observableArrayList(list));
            });
        }
    }

    @FXML
    void closefilterAction() {
        critClassificationSocietyChoiceBox.setValue("All");
        critShipTypeChoiceBox.setValue("All");
        filterHbox.setDisable(true);
        filterHbox.setVisible(false);
        filterHbox.setMinHeight(0);
        filterHbox.setMaxHeight(0);
        openfilterBtn.setDisable(false);
        openfilterBtn.setVisible(true);
        guifanTable.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));
    }

    @FXML
    private void handleCriterionQuery() {
        if (filterHbox.isVisible()) {
            List<CriterionData> allList = CriterionDb.getCriterionDataList();
            List<CriterionData> list = new LinkedList<>();
            for (CriterionData c : allList) {
                if ((c.getCriShipType().equals(shiptype[0]) || shiptype[0].equals("All")) && (c.getCriShipCompany().equals(classification[0]) || classification[0].equals("All")))
                    list.add(c);
            }
            guifanTable.setItems(FXCollections.observableArrayList(list));
        } else {
            guifanTable.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));
        }

        // 将输入进行分词
        String inputStr = criterionQueryTextField.getText().trim();
        if (inputStr.equals("")) return;
        // 拼接目前table中显示的条目的{id:{tfidf}}
        Map<String, String> idtfidfmap = new HashMap<>();
        for (CriterionData c : guifanTable.getItems()) {
            idtfidfmap.put(c.getCriId(), c.getTfIdfMapStr());
        }
        // 文档id的排序
        Map<String, Double> map = jieBaUtils.getSortedRelativityMap(inputStr, idtfidfmap);
        java.util.List<String> list1 = new LinkedList<>(map.keySet());
        List<CriterionData> orderedDataList = CriterionDb.getOrderedDataList(list1);
        guifanTable.setItems(FXCollections.observableArrayList(orderedDataList));
    }

    @FXML
    private void handleResetGuiFan() {
        guifanTable.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));
        guifanTextArea.setText(null);
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
        critShipTypeChoiceBox.setValue("All");
        critClassificationSocietyChoiceBox.setValue("All");
        //添加规范
        g32.setCellValueFactory(new PropertyValueFactory<>("criShipType"));
        criterionClassificationSocietyTC.setCellValueFactory(new PropertyValueFactory<>("criShipCompany"));
        g33.setCellValueFactory(new PropertyValueFactory<>("criOutfittingRegion"));
        g34.setCellValueFactory(new PropertyValueFactory<>("criName"));
        g35.setCellValueFactory(new PropertyValueFactory<>("criContent"));
        criterionUrlTC.setCellValueFactory(new PropertyValueFactory<>("criFilePath"));
        criterionUrlTC.setCellFactory(param -> new HyperlinkTableCell<>());

        guifanTable.setItems(FXCollections.observableArrayList(CriterionDb.getCriterionDataList()));

        final String[] criterionFile = {""};
        MenuItem criterionItem1 = new MenuItem("打开文件");
        criterionItem1.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(criterionFile[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem criterionItem2 = new MenuItem("打开文件夹");
        criterionItem2.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(new File(criterionFile[0]).getParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu criterionMenu = new ContextMenu();
        criterionMenu.getItems().addAll(criterionItem1, criterionItem2);
        guifanTable.setOnContextMenuRequested(event -> criterionMenu.show(guifanTable, event.getScreenX(), event.getScreenY()));
        guifanTable.setOnMouseClicked(event -> criterionMenu.hide());

        guifanTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                guifanTextArea.setText(newValue.getCriContent());
                criterionFile[0] = newValue.getCriFilePath();
            }
        });

        closefilterAction();
    }
}
