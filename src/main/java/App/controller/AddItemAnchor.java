package App.controller;

import App.appModel.RuleItemData;
import App.database.RuleItemDatabase;
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

public class AddItemAnchor {

    @FXML
    private TextField itemNameTF;

    @FXML
    private TextField outfittingTypeTF;

    @FXML
    private TextField anchorTypeTF;

    @FXML
    private TextField ruleIdTF;

    @FXML
    private TextField manufacturerTF;

    @FXML
    private TextField itemHyperlinkTF;

    @FXML
    private void initialize() {}

    @FXML
    void handleOk(ActionEvent event) {
        RuleItemData ruleItemData = new RuleItemData();
        ruleItemData.setRuleId(ruleIdTF.getText());
        ruleItemData.setAnchorType(anchorTypeTF.getText());
        ruleItemData.setItemHyperLink(itemHyperlinkTF.getText());
//        ruleItemData.setItemName(itemNameTF.getText());
        ruleItemData.setItemName("锚设备");
        ruleItemData.setOutfittingType(outfittingTypeTF.getText());
        ruleItemData.setManufacturer(manufacturerTF.getText());

        if (this.editRuleId != null) {
            RuleItemDatabase.update(ruleItemData, this.editRuleId);
            closeAddItemAnchor(event);
        } else {
            RuleItemDatabase.insert(ruleItemData);
            closeAddItemAnchor(event);
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeAddItemAnchor(event);
    }

    private String editRuleId;

    /**
     * 将要修改的信息添加进文本框中。
     */
    private void setTextField(RuleItemData ruleItemData) {
        this.editRuleId = ruleItemData.getRuleId();
        if (ruleItemData.getRuleId() != null) {
            ruleIdTF.setText(ruleItemData.getRuleId());
            anchorTypeTF.setText(ruleItemData.getAnchorType());
            itemHyperlinkTF.setText(ruleItemData.getItemHyperLink());
//            itemNameTF.setText(ruleItemData.getItemName());
            itemNameTF.setText("锚设备");
            manufacturerTF.setText(ruleItemData.getManufacturer());
            outfittingTypeTF.setText(ruleItemData.getOutfittingType());
        }
    }

    //显示界面
    void showAddItemAnchor(RuleItemData ruleItemData) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddItemAnchor.fxml");
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        AddItemAnchor addItemAnchor = loader.getController();
        addItemAnchor.setTextField(ruleItemData);
        if (ruleItemData.getRuleId() != null) {
            stage.setTitle("修改!");
        } else {
            stage.setTitle("添加!");
        }
        stage.showAndWait();
    }

    //关闭界面
    private void closeAddItemAnchor(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
