package App.controller;

import App.appModel.RuleItemData;
import App.database.RuleItemDatabase;
import App.function.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ItemGongyoudanyuan {

    @FXML
    private TableColumn<RuleItemData, String> zhongyouOutputTemperatureTC;

    @FXML
    private TableColumn<RuleItemData, String> zhongyouOutputViscosityTC;

    @FXML
    private TextField zhongyouOutputViscosityTF;

    @FXML
    private TableColumn<RuleItemData, String> gongyouliangTC;

    @FXML
    private TextField itemAnchorQueryTF;

    @FXML
    private TextField zhongyouOutputPressureTF;

    @FXML
    private TableColumn<RuleItemData, String> itemNameTC;

    @FXML
    private TextField itemHyperLinkTF;

    @FXML
    private TextField itemNameTF;

    @FXML
    private TableColumn<RuleItemData, String> outfittingTypeTC;

    @FXML
    private TextField outfittingTypeTF;

    @FXML
    private TextField ruleIdTF;

    @FXML
    private TableColumn<RuleItemData, String> itemHyperLinkTC;

    @FXML
    private TextField gongyouliangTF;

    @FXML
    private TableView<RuleItemData> itemGongyoudanyuanTableView;

    @FXML
    private TableColumn<RuleItemData, String> ruleIdTC;

    @FXML
    private TextField zhongyouOutputTemperatureTF;

    @FXML
    private TableColumn<RuleItemData, String> zhongyouOutputPressureTC;

    @FXML
    void initialize() {
        ruleIdTC.setCellValueFactory(cellData -> cellData.getValue().ruleIdProperty());
        itemNameTC.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
        gongyouliangTC.setCellValueFactory(cellData -> cellData.getValue().gongyouliangProperty());
        itemHyperLinkTC.setCellValueFactory(cellData -> cellData.getValue().itemHyperLinkProperty());
        outfittingTypeTC.setCellValueFactory(cellData -> cellData.getValue().outfittingTypeProperty());
        zhongyouOutputPressureTC.setCellValueFactory(cellData -> cellData.getValue().zhongyouOutputPressureProperty());
        zhongyouOutputTemperatureTC.setCellValueFactory(cellData -> cellData.getValue().zhongyouOutputTemperatureProperty());
        zhongyouOutputViscosityTC.setCellValueFactory(cellData -> cellData.getValue().zhongyouOutputViscosityProperty());
        itemGongyoudanyuanTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showItemGongyoudanyuanDetail(newValue));
        refreshItemGongyoudanyuanTableView();
    }

    @FXML
    void handleQuery(ActionEvent event) {
        String keyword = ruleIdTF.getText()+itemNameTF.getText()+outfittingTypeTF.getText()+gongyouliangTF.getText()+zhongyouOutputViscosityTF.getText()+zhongyouOutputTemperatureTF.getText()+zhongyouOutputPressureTF.getText()+itemHyperLinkTF.getText();
        try {
            itemGongyoudanyuanTableView.setItems(RuleItemDatabase.query("供油单元", keyword));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAdd(ActionEvent event) {
        RuleItemData tempRuleItemData = new RuleItemData();
        AddItemGongyoudanyuan addItemGongyoudanyuan = new AddItemGongyoudanyuan();
        try {
            addItemGongyoudanyuan.showAddItemGongyoudanyuan(tempRuleItemData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshItemGongyoudanyuanTableView();
    }

    @FXML
    void handleEdit(ActionEvent event) {
        RuleItemData editRuleItemData = itemGongyoudanyuanTableView.getSelectionModel().getSelectedItem();
        AddItemGongyoudanyuan addItemGongyoudanyuan = new AddItemGongyoudanyuan();
        try {
            addItemGongyoudanyuan.showAddItemGongyoudanyuan(editRuleItemData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshItemGongyoudanyuanTableView();
    }

    @FXML
    void handleDelete(ActionEvent event) {
        RuleItemData deleteRuleItemData = itemGongyoudanyuanTableView.getSelectionModel().getSelectedItem();
        Optional<ButtonType> result = Dialog.confirmation("删除确认", null, "确认删除ID为"+deleteRuleItemData.getRuleId()+"的条目吗？");
        if (result.get() == ButtonType.OK) {
            RuleItemDatabase.delete(deleteRuleItemData.getRuleId());
            refreshItemGongyoudanyuanTableView();
        }
        RuleItemDatabase.delete(deleteRuleItemData.getRuleId());
        refreshItemGongyoudanyuanTableView();
    }

    @FXML
    void handleReset(ActionEvent event) {
        refreshItemGongyoudanyuanTableView();
    }

    private void refreshItemGongyoudanyuanTableView() {
        itemGongyoudanyuanTableView.setItems(RuleItemDatabase.getRuleItemDataList("供油单元"));
    }

    private void showItemGongyoudanyuanDetail(RuleItemData ruleItemData) {
        if (ruleItemData != null) {
            ruleIdTF.setText(ruleItemData.getRuleId());
            itemNameTF.setText(ruleItemData.getItemName());
            itemHyperLinkTF.setText(ruleItemData.getItemHyperLink());
            gongyouliangTF.setText(ruleItemData.getGongyouliang());
            zhongyouOutputPressureTF.setText(ruleItemData.getZhongyouOutputPressure());
            zhongyouOutputTemperatureTF.setText(ruleItemData.getZhongyouOutputTemperature());
            zhongyouOutputViscosityTF.setText(ruleItemData.getZhongyouOutputViscosity());
            outfittingTypeTF.setText(ruleItemData.getOutfittingType());
        } else {
            ruleIdTF.setText("");
            itemNameTF.setText("");
            itemHyperLinkTF.setText("");
            gongyouliangTF.setText("");
            zhongyouOutputPressureTF.setText("");
            zhongyouOutputTemperatureTF.setText("");
            zhongyouOutputViscosityTF.setText("");
            outfittingTypeTF.setText("");
        }
    }

}
