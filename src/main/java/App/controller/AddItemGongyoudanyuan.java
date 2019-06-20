package App.controller;

import App.dataModel.RuleItemData;
import App.database.RuleItemDb;
import App.utile.FxmlUtile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddItemGongyoudanyuan {

    @FXML
    private TextField itemHyperLinkTF;

    @FXML
    private TextField itemNameTF;

    @FXML
    private TextField outfittingTypeTF;

    @FXML
    private TextField ruleIdTF;

    @FXML
    private TextField zhongyouOutputViscosityTF;

    @FXML
    private TextField gongyouliangTF;

    @FXML
    private TextField zhongyouOutputPressureTF;

    @FXML
    private TextField zhongyouOutputTemperatureTF;

    @FXML
    private void initialize() {}

    @FXML
    void handleOk(ActionEvent event) {
        RuleItemData ruleItemData = new RuleItemData();
        ruleItemData.setRuleId(ruleIdTF.getText());
//        ruleItemData.setItemName(itemNameTF.getText());
        ruleItemData.setItemName("供油单元");
        ruleItemData.setGongyouliang(gongyouliangTF.getText());
        ruleItemData.setItemHyperLink(itemHyperLinkTF.getText());
        ruleItemData.setZhongyouOutputPressure(zhongyouOutputPressureTF.getText());
        ruleItemData.setZhongyouOutputTemperature(zhongyouOutputTemperatureTF.getText());
        ruleItemData.setZhongyouOutputViscosity(zhongyouOutputViscosityTF.getText());
        ruleItemData.setOutfittingType(outfittingTypeTF.getText());

        if (this.editRuleId != null) {
            RuleItemDb.update(ruleItemData, this.editRuleId);
            closeAddItemGongyoudanyuan(event);
        } else {
            RuleItemDb.insert(ruleItemData);
            closeAddItemGongyoudanyuan(event);
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeAddItemGongyoudanyuan(event);
    }

    private String editRuleId;

    private void setTextField(RuleItemData ruleItemData) {
        this.editRuleId = ruleItemData.getRuleId();
        if (ruleItemData.getRuleId() != null) {
            ruleIdTF.setText(ruleItemData.getRuleId());
//            itemNameTF.setText(ruleItemData.getItemName());
            itemNameTF.setText("供油单元");
            gongyouliangTF.setText(ruleItemData.getGongyouliang());
            itemHyperLinkTF.setText(ruleItemData.getItemHyperLink());
            outfittingTypeTF.setText(ruleItemData.getOutfittingType());
            zhongyouOutputPressureTF.setText(ruleItemData.getZhongyouOutputPressure());
            zhongyouOutputTemperatureTF.setText(ruleItemData.getZhongyouOutputTemperature());
            zhongyouOutputViscosityTF.setText(ruleItemData.getZhongyouOutputViscosity());
        }
    }

    //显示界面
    void showAddItemGongyoudanyuan(RuleItemData ruleItemData) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddItemGongyoudanyuan.fxml");
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        AddItemGongyoudanyuan addItemGongyoudanyuan = loader.getController();
        addItemGongyoudanyuan.setTextField(ruleItemData);
        if (ruleItemData.getRuleId() != null) {
            stage.setTitle("修改!");
        } else {
            stage.setTitle("添加!");
        }
        stage.showAndWait();
    }

    //关闭界面
    private void closeAddItemGongyoudanyuan(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
