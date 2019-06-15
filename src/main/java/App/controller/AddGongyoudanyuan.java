package App.controller;


import App.Main;
import App.dataModel.RuleItemData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class AddGongyoudanyuan {



    @FXML
    private TextField zhongyoushuchuyaliTextField;

    @FXML
    private HBox hbox;

    @FXML
    private TextField xinghaoTextField;

    @FXML
    private TextField youxiangyaliTextField;

    @FXML
    private TextField shuchunianduTextField;

    @FXML
    private TextField shebeigongchengxiangTextField;

    @FXML
    private TextField chaiyoushuchuyaliTextField;

    @FXML
    private TextField zhongyoushuchuwenduTextField;

    @FXML
    private TextField ruleIDTextField;

    @FXML
    private TextField gongyouliangTextField;

    @FXML
    private TextField chaiyouzonggonglvTextField;

    @FXML
    private TextField zhongyouxunhuanliangTextField;



    private RuleItemData ruleItemData;
    private boolean okClicked=false;
    private Main main=new Main();

    private Stage dialogStage;
    public  void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }


    public void setGongyoudanyuan(RuleItemData ruleItemData){
        this.ruleItemData = ruleItemData;


//        ruleIDTextField.setText(ruleItemData.getRuleID());
//        xinghaoTextField.setText(ruleItemData.getExpOutfittingRegion());
//        youxiangyaliTextField.setText(ruleItemData.getXunhuanyouxiangyali());
//        //shipType.setItems(jinJiData.getLayoutShipType());
//        //keyword1TextField.setText();
//        chaiyouzonggonglvTextField.setText(ruleItemData.getChaiyoujizonggonglv());
//        zhongyoushuchuyaliTextField.setText(ruleItemData.getZhongyoushuchuyali());
//        gongyouliangTextField.setText(ruleItemData.getGongyouliang());
//        zhongyouxunhuanliangTextField.setText(ruleItemData.getZhongyouxunhuanliang());
//        shuchunianduTextField.setText(ruleItemData.getZhongyoushuchuniandu());
//        chaiyoushuchuyaliTextField.setText(ruleItemData.getZhongyoushuchuyali());
//        zhongyoushuchuwenduTextField.setText(ruleItemData.getZhongyoushuchuwendu());
//        shebeigongchengxiangTextField.setText(ruleItemData.getGongchengxiang());

    }
    public boolean isOkClicked(){
        return okClicked;
    }

    @FXML
    public void handleOk() throws IOException {
//        ruleItemData.setRuleID(ruleIDTextField.getText());
//        ruleItemData.setOutfittingName("供油单元");
//        ruleItemData.setOutfittingModel("机舱舾装");
//
//        ruleItemData.setExpOutfittingRegion(xinghaoTextField.getText());
//        ruleItemData.setXunhuanyouxiangyali(youxiangyaliTextField.getText());
//        ruleItemData.setChaiyoujizonggonglv(chaiyouzonggonglvTextField.getText());
//        ruleItemData.setZhongyoushuchuyali(zhongyoushuchuyaliTextField.getText());
//        ruleItemData.setGongyouliang(gongyouliangTextField.getText());
//        ruleItemData.setZhongyouxunhuanliang(zhongyouxunhuanliangTextField.getText());
//        ruleItemData.setZhongyoushuchuniandu(shuchunianduTextField.getText());
//        ruleItemData.setChaiyoushuchuyali(chaiyoushuchuyaliTextField.getText());
//        ruleItemData.setZhongyoushuchuwendu(zhongyoushuchuwenduTextField.getText());
//        ruleItemData.setGongchengxiang(shebeigongchengxiangTextField.getText());


//        main.setGongyoudanyuanData(ruleItemData);
        okClicked=true;
        dialogStage.close();
//        System.out.println("getpersion:"+main.getUserDataList().size());

    }








}
