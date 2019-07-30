package App.controller;

import App.dataModel.SuggestionData;
import App.database.SuggestionDb;
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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KBSuggestion {

    @FXML
    private ComboBox<String> schuanDongChoiceBox;
    @FXML
    private TableView<SuggestionData> suggestionTable;
    @FXML
    private TableColumn<?, ?> c30;
    @FXML
    private TableColumn<?, ?> c35;
    @FXML
    private TextArea ssolutionDecribeTextArea;
    @FXML
    private TextField suggestionQueryTextField;
    @FXML
    private TableColumn<?, ?> c37;
    @FXML
    private TableColumn<?, ?> c39;
    @FXML
    private TableColumn<?, ?> c38;
    @FXML
    private TableColumn<SuggestionData, String> sugFilePathTC;
    @FXML
    private TextArea sugContentTA;
    @FXML
    private TableColumn<?, ?> sugContentTC;
    @FXML
    private TextArea sproblemDescribeTextArea;
    @FXML
    private TextField soutfittingTypeTextField;
    @FXML
    private ComboBox<String> schuanboTypeChoiceBox;

    @FXML
    private HBox filterHbox;

    @FXML
    private Button closefilterBtn;

    @FXML
    private Button filterBtn;

    private JieBaUtils jieBaUtils = JieBaUtils.getInstance();

    private void refreshTfIdf() {
        Task task = new Task() {
            @Override
            protected Object call() {
                Map<String, String> map = jieBaUtils.getDocumentsTfIdfMap(SuggestionDb.getIndexAndContentMap());
                SuggestionDb.updateTfIdf(map);
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "正在更新文本TF-IDF值，请稍后...");
//        progressFrom.activateProgressBar();
    }

    private final String[] chuandongchoice = {"All"};
    private final String[] shiptype = {"All"};

    @FXML
    void filterAction() {
        filterHbox.setDisable(false);
        filterHbox.setVisible(true);
        filterHbox.setMinHeight(20);
        filterHbox.setMaxHeight(25);
        filterBtn.setDisable(true);
        filterBtn.setVisible(false);
        schuanboTypeChoiceBox.setValue("All");
        schuanDongChoiceBox.setValue("All");

        if (filterHbox.isVisible()) {
            List<SuggestionData> allList = SuggestionDb.getSugDataList();
            schuanDongChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                chuandongchoice[0] = newValue;
                List<SuggestionData> list = new LinkedList<>();
                for (SuggestionData s : allList) {
                    if ((s.getSugShipCompany().equals(chuandongchoice[0]) || chuandongchoice[0].equals("All")) && (s.getSugShipType().equals(shiptype[0]) || shiptype[0].equals("All")))
                        list.add(s);
                }
                suggestionTable.setItems(FXCollections.observableArrayList(list));
            });
            schuanboTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                shiptype[0] = newValue;
                List<SuggestionData> list = new LinkedList<>();
                for (SuggestionData s : allList) {
                    if ((s.getSugShipCompany().equals(chuandongchoice[0]) || chuandongchoice[0].equals("All")) && (s.getSugShipType().equals(shiptype[0]) || shiptype[0].equals("All")))
                        list.add(s);
                }
                suggestionTable.setItems(FXCollections.observableArrayList(list));
            });
        }
    }

    @FXML
    void closefilterAction() {
        schuanboTypeChoiceBox.setValue("All");
        schuanDongChoiceBox.setValue("All");
        filterHbox.setDisable(true);
        filterHbox.setVisible(false);
        filterHbox.setMinHeight(0);
        filterHbox.setMaxHeight(0);
        filterBtn.setDisable(false);
        filterBtn.setVisible(true);
        suggestionTable.setItems(FXCollections.observableArrayList(SuggestionDb.getSugDataList()));
    }

    //船东船检增加修改删除
    private final AddSuggestion asc = AddSuggestion.getInstance();

    @FXML
    private void handleSuggestionQuery() {
        if (filterHbox.isVisible()) {
            List<SuggestionData> allList = SuggestionDb.getSugDataList();
            List<SuggestionData> list = new LinkedList<>();
            for (SuggestionData s : allList) {
                if ((s.getSugShipCompany().equals(chuandongchoice[0]) || chuandongchoice[0].equals("All")) && (s.getSugShipType().equals(shiptype[0]) || shiptype[0].equals("All")))
                    list.add(s);
            }
            suggestionTable.setItems(FXCollections.observableArrayList(list));
        } else {
            suggestionTable.setItems(FXCollections.observableArrayList(SuggestionDb.getSugDataList()));
        }

        // 将输入进行分词
        String inputStr = suggestionQueryTextField.getText().trim();
        if (inputStr.equals("")) return;
        // 拼接目前table中显示的条目的{id:{tfidf}}
        Map<String, String> idtfidfmap = new HashMap<>();
        for (SuggestionData s : suggestionTable.getItems()) {
            idtfidfmap.put(s.getSugId(), s.getTfIdfMapStr());
        }
        // 文档id的排序
        Map<String, Double> map = jieBaUtils.getSortedRelativityMap(inputStr, idtfidfmap);
        java.util.List<String> list1 = new LinkedList<>(map.keySet());
        List<SuggestionData> orderedDataList = SuggestionDb.getOrderedDataList(list1);
        suggestionTable.setItems(FXCollections.observableArrayList(orderedDataList));
    }

    @FXML
    private void showAddSuggestion() throws IOException {
        SuggestionData tempSuggestion = new SuggestionData();
        asc.showAddSuggestion(tempSuggestion);
        suggestionTable.setItems(FXCollections.observableArrayList(SuggestionDb.getSugDataList()));
        refresh();
    }

    @FXML
    private void handleEditSuggestion() throws IOException {
        SuggestionData selectedSuggestion = suggestionTable.getSelectionModel().getSelectedItem();
        asc.showAddSuggestion(selectedSuggestion);
        suggestionTable.setItems(FXCollections.observableArrayList(SuggestionDb.getSugDataList()));
        refresh();
    }

    @FXML
    private void handleDeleteSuggestion() {
        SuggestionData deletedSugData = suggestionTable.getSelectionModel().getSelectedItem();
        String deletedSugDataId = deletedSugData.getSugId();
        SuggestionDb.delete(deletedSugDataId);
        suggestionTable.setItems(FXCollections.observableArrayList(SuggestionDb.getSugDataList()));
        refresh();
    }

    @FXML
    private void handleResetSuggestion() {
        suggestionTable.setItems(FXCollections.observableArrayList(SuggestionDb.getSugDataList()));
        ssolutionDecribeTextArea.setText(null);
        sugContentTA.setText(null);
        sproblemDescribeTextArea.setText(null);
    }

    private void refresh() {
        schuanboTypeChoiceBox.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        schuanDongChoiceBox.setItems(FXCollections.observableArrayList(Constant.getShipOwnerCompany()));
        refreshTfIdf();
    }

    @FXML
    void initialize() {
        schuanboTypeChoiceBox.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        schuanDongChoiceBox.setItems(FXCollections.observableArrayList(Constant.getShipOwnerCompany()));
        schuanDongChoiceBox.setValue("All");
        schuanboTypeChoiceBox.setValue("All");
        //船东船检意见表显示内容
        c37.setCellValueFactory(new PropertyValueFactory<>("sugShipType"));
        c38.setCellValueFactory(new PropertyValueFactory<>("sugOutfittingRegion"));
        c39.setCellValueFactory(new PropertyValueFactory<>("sugProblemDescribe"));
        c30.setCellValueFactory(new PropertyValueFactory<>("sugSolutionDescribe"));
        c35.setCellValueFactory(new PropertyValueFactory<>("sugShipCompany"));
        sugContentTC.setCellValueFactory(new PropertyValueFactory<>("sugContent"));
        sugFilePathTC.setCellValueFactory(new PropertyValueFactory<>("sugFilePath"));
        sugFilePathTC.setCellFactory(param -> new HyperlinkTableCell<>());

        suggestionTable.setItems(FXCollections.observableArrayList(SuggestionDb.getSugDataList()));

        final String[] filepath = {""};
        MenuItem sugItem1 = new MenuItem("打开文件");
        sugItem1.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(filepath[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem sugItem2 = new MenuItem("打开文件夹");
        sugItem2.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(new File(filepath[0]).getParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu sugMenu = new ContextMenu();
        sugMenu.getItems().addAll(sugItem1, sugItem2);
        suggestionTable.setOnContextMenuRequested(event -> sugMenu.show(suggestionTable, event.getScreenX(), event.getScreenY()));
        suggestionTable.setOnMouseClicked(event -> sugMenu.hide());

        suggestionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                sproblemDescribeTextArea.setText(newValue.getSugProblemDescribe());
                ssolutionDecribeTextArea.setText(newValue.getSugSolutionDescribe());
                sugContentTA.setText(newValue.getSugContent());
                filepath[0] = newValue.getSugFilePath();
            }
        });

        closefilterAction();
    }

}
