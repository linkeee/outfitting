package App.controller;

import App.dataModel.AnchorRuleData;
import App.dataModel.RuleItemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ItemAnchor {

    @FXML
    private TableColumn<AnchorRuleData, String> leftIfTC;

    @FXML
    private TableColumn<AnchorRuleData, String> rightThenTC;

    @FXML
    private TableColumn<AnchorRuleData, String> leftThenTC;

    @FXML
    private TextField queryTF;

    @FXML
    private TableColumn<AnchorRuleData, String> rightIfTC;

    @FXML
    private TableView<AnchorRuleData> leftTableView;

    @FXML
    private TableView<AnchorRuleData> rightTableView;

    final ObservableList<AnchorRuleData> data = FXCollections.observableArrayList(
            new AnchorRuleData("[50, 70]", "普通锚: [174.6, 192.6]", "[型号1, 型号2, 型号3]"),
            new AnchorRuleData("[50, 70]", "大抓力锚: [130.95, 144.45]", "[型号4, 型号5]"),
            new AnchorRuleData("[50, 70]", "超大抓力锚: [87.3, 96.3]", "[型号6]"),
            new AnchorRuleData("[70, 90]", "普通锚: [194.6, 212.6]", "[型号7, 型号8]"),
            new AnchorRuleData("[70, 90]", "大抓力锚: [150.95, 164.45]", "[型号9]"),
            new AnchorRuleData("[70, 90]", "超大抓力锚: [107.3, 116.3]", "[型号10]"),
            new AnchorRuleData("[90, 110]", "普通锚: [214.6, 232.6]", "[型号11, 型号12]"),
            new AnchorRuleData("[90, 110]", "大抓力锚: [170.95, 184.45]", "[型号13, 型号14]"),
            new AnchorRuleData("[90, 110]", "超大抓力锚: [127.3, 136.3]", "[型号15]")
    );

    @FXML
    void initialize() {
        leftIfTC.setCellValueFactory(cellValue -> cellValue.getValue().leftProperty());
        leftThenTC.setCellValueFactory(cellValue -> cellValue.getValue().centerProperty());
        rightIfTC.setCellValueFactory(cellValue -> cellValue.getValue().centerProperty());
        rightThenTC.setCellValueFactory(cellValue -> cellValue.getValue().rightProperty());
        leftTableView.setItems(data);
        rightTableView.setItems(data);
    }

    @FXML
    void handleQuery(ActionEvent event) {
//        String keyword = queryTF.getText();
//        try {
//            leftTableView.setItems(RuleItemDb.query("锚设备", keyword));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void handleAdd(ActionEvent event) {
        RuleItemData tempRuleItemData = new RuleItemData();
        AddItemAnchor addItemAnchor = new AddItemAnchor();
        try {
            addItemAnchor.showAddItemAnchor(tempRuleItemData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshItemAnchorTableView();
    }

    @FXML
    void handleEdit(ActionEvent event) {
//        RuleItemData editRuleItemData = itemAnchorTableView.getSelectionModel().getSelectedItem();
//        AddItemAnchor addItemAnchorController = new AddItemAnchor();
//        try {
//            addItemAnchorController.showAddItemAnchor(editRuleItemData);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        refreshItemAnchorTableView();
    }

    @FXML
    void handleDelete(ActionEvent event) {
//        RuleItemData deleteRuleItemData = itemAnchorTableView.getSelectionModel().getSelectedItem();
//        Optional<ButtonType> result = MyDialog.confirmation("删除确认", null, "确认删除ID为"+deleteRuleItemData.getRuleId()+"的条目吗？");
//        if (result.get() == ButtonType.OK) {
//            RuleItemDb.delete(deleteRuleItemData.getRuleId());
//            refreshItemAnchorTableView();
//        }
//        RuleItemDb.delete(deleteRuleItemData.getRuleId());
//        refreshItemAnchorTableView();
    }

    @FXML
    void handleReset(ActionEvent event) {
        refreshItemAnchorTableView();
    }

    private void refreshItemAnchorTableView() {
//        itemAnchorTableView.setItems(RuleItemDb.getRuleItemDataList("锚设备"));
    }

    private void showItemAnchorDetail(RuleItemData ruleItemData) {
        if (ruleItemData != null) {
//            ruleIdTF.setText(ruleItemData.getRuleId());
//            anchorTypeTF.setText(ruleItemData.getAnchorType());
//            itemNameTF.setText(ruleItemData.getItemName());
//            manufacturerTF.setText(ruleItemData.getManufacturer());
//            outfittingTypeTF.setText(ruleItemData.getExpOutfittingRegion());
//            itemHyperlinkTF.setText(ruleItemData.getItemHyperLink());
        } else {
//            ruleIdTF.setText("");
//            anchorTypeTF.setText("");
//            itemNameTF.setText("");
//            manufacturerTF.setText("");
//            outfittingTypeTF.setText("");
//            itemHyperlinkTF.setText("");
        }
    }

}
