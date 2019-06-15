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

public class SelectionItemAnchor {

    @FXML
    private TableColumn<AnchorRuleData, String> zhiliangTC;

    @FXML
    private TableView<AnchorRuleData> selectionItemAnchorTableView;

    @FXML
    private TableColumn<AnchorRuleData, String> xizhuangshuTC;

    @FXML
    private TextField chuankuanTF;

    @FXML
    private TextField paishuiliangTF;

    @FXML
    private TextField gaoduTF;

    @FXML
    private TableColumn<AnchorRuleData, String> xinghaoTC;

    @FXML
    private TextField mianjiTF;

    final ObservableList<AnchorRuleData> data = FXCollections.observableArrayList(
            new AnchorRuleData("[70, 90]", "普通锚: [194.6, 212.6]", "[型号7, 型号8]"),
            new AnchorRuleData("[70, 90]", "大抓力锚: [150.95, 164.45]", "[型号9]"),
            new AnchorRuleData("[70, 90]", "超大抓力锚: [107.3, 116.3]", "[型号10]")
    );

    @FXML
    private void initialize() {
        xizhuangshuTC.setCellValueFactory(cellValue -> cellValue.getValue().leftProperty());
        zhiliangTC.setCellValueFactory(cellValue -> cellValue.getValue().centerProperty());
        xinghaoTC.setCellValueFactory(cellValue -> cellValue.getValue().rightProperty());
        selectionItemAnchorTableView.setItems(data);
    }

    @FXML
    void handleQuery(ActionEvent event) {
//        String keyword = ruleIdTF.getText()+itemNameTF.getText()+anchorTypeTF.getText()+manufacturerTF.getText()+outfittingTypeTF.getText()+itemHyperLinkTF.getText();
//        try {
//            selectionItemAnchorTableView.setItems(RuleItemDatabase.query("锚设备", keyword));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void handleReset(ActionEvent event) {
        refreshSelectionItemAnchorTableView();
    }

    private void refreshSelectionItemAnchorTableView() {
//        selectionItemAnchorTableView.setItems(RuleItemDatabase.getRuleItemDataList("锚设备"));
    }

    private void showTextFieldDetail(RuleItemData ruleItemData) {
//        if (ruleItemData != null) {
//            ruleIdTF.setText(ruleItemData.getRuleId());
//            anchorTypeTF.setText(ruleItemData.getAnchorType());
//            itemNameTF.setText(ruleItemData.getItemName());
//            manufacturerTF.setText(ruleItemData.getManufacturer());
//            outfittingTypeTF.setText(ruleItemData.getExpOutfittingRegion());
//            itemHyperLinkTF.setText(ruleItemData.getItemHyperLink());
//        } else {
//            ruleIdTF.setText("");
//            anchorTypeTF.setText("");
//            itemNameTF.setText("");
//            manufacturerTF.setText("");
//            outfittingTypeTF.setText("");
//            itemHyperLinkTF.setText("");
//        }
    }

}
