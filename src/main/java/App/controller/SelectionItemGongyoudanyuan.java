package App.controller;

import App.dataModel.RuleItemData;
import App.database.RuleItemDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SelectionItemGongyoudanyuan {

    @FXML
    private TableColumn<RuleItemData, String> zhongyouOutputTemperatureTC;

    @FXML
    private TableColumn<RuleItemData, String> zhongyouOutputViscosityTC;

    @FXML
    private TextField zhongyouOutputViscosityTF;

    @FXML
    private TableColumn<RuleItemData, String> gongyouliangTC;

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
    private TextField zhongyouOutputPressureTF;

    @FXML
    private TableColumn<RuleItemData, String> ruleIdTC;

    @FXML
    private TextField zhongyouOutputTemperatureTF;

    @FXML
    private TableColumn<RuleItemData, String> zhongyouOutputPressureTC;

    @FXML
    private TableView<RuleItemData> selectionItemGongyoudanyuanTableView;

    @FXML
    private void initialize() {
        ruleIdTC.setCellValueFactory(cellData -> cellData.getValue().ruleIdProperty());
        itemNameTC.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
        gongyouliangTC.setCellValueFactory(cellData -> cellData.getValue().gongyouliangProperty());
        itemHyperLinkTC.setCellValueFactory(cellData -> cellData.getValue().itemHyperLinkProperty());
        outfittingTypeTC.setCellValueFactory(cellData -> cellData.getValue().outfittingTypeProperty());
        zhongyouOutputPressureTC.setCellValueFactory(cellData -> cellData.getValue().zhongyouOutputPressureProperty());
        zhongyouOutputTemperatureTC.setCellValueFactory(cellData -> cellData.getValue().zhongyouOutputTemperatureProperty());
        zhongyouOutputViscosityTC.setCellValueFactory(cellData -> cellData.getValue().zhongyouOutputViscosityProperty());
        selectionItemGongyoudanyuanTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTextFieldDetail(newValue));
        refreshSelectionItemGongyoudanyuanTableView();
    }

    @FXML
    void handleQuery(ActionEvent event) {
        String keyword = ruleIdTF.getText()+itemNameTF.getText()+outfittingTypeTF.getText()+gongyouliangTF.getText()+zhongyouOutputViscosityTF.getText()+zhongyouOutputTemperatureTF.getText()+zhongyouOutputPressureTF.getText()+itemHyperLinkTF.getText();
        try {
            selectionItemGongyoudanyuanTableView.setItems(RuleItemDatabase.query("供油单元", keyword));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleReset(ActionEvent event) {
        refreshSelectionItemGongyoudanyuanTableView();
    }

    private void refreshSelectionItemGongyoudanyuanTableView() {
        selectionItemGongyoudanyuanTableView.setItems(RuleItemDatabase.getRuleItemDataList("供油单元"));
    }

    private void showTextFieldDetail(RuleItemData ruleItemData) {
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
