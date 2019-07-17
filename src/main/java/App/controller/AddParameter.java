package App.controller;

import App.dataModel.ParameterData;
import App.database.ParameterDb;
import App.utile.Constant;
import App.utile.MyDialog;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class AddParameter {

    @FXML
    private ComboBox<String> paramTypeCB;

    @FXML
    private TextField paramNameTF;

    @FXML
    private ComboBox<String> outfittingNameCB;

    @FXML
    private TextField lowerTF;

    @FXML
    private TextField upperTF;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label addManuLabel;

    @FXML
    private TextArea paramDescTA;

    @FXML
    private Button okBtn;

    @FXML
    void okAction(ActionEvent event) {

        if (!lowerTF.getText().equals("")) {
            if (!lowerTF.getText().matches("^[0-9]+\\.{0,1}[0-9]{0,2}$")) {
                MyDialog.error("参数范围下限输入非法", "只能包含数字和小数点");
                return;
            }
        }

        if (!upperTF.getText().equals("")) {
            if (!upperTF.getText().matches("^[0-9]+\\.{0,1}[0-9]{0,2}$")) {
                MyDialog.error("参数范围上限输入非法", "只能包含数字和小数点");
                return;
            }
        }

        if (!lowerTF.getText().equals("") && !upperTF.getText().equals("")) {
            if (Integer.valueOf(lowerTF.getText()) > Integer.valueOf(upperTF.getText())) {
                MyDialog.error("参数范围输入非法", "下限大于上限");
                return;
            }
        }

        ParameterData parameterData = new ParameterData();
        parameterData.setOutfitting_name(outfittingNameCB.getValue());
        parameterData.setParam_name(paramNameTF.getText());
        parameterData.setParam_type(paramTypeCB.getValue());
        parameterData.setParam_scope(lowerTF.getText() + ", " + upperTF.getText());
        parameterData.setParam_description(paramDescTA.getText());

        if (isModify) {
            ParameterDb.update(parameterData, selectedId);
        } else {
            ParameterDb.insert(parameterData);
        }
        close(event);
    }

    @FXML
    void cancelAction(ActionEvent event) {
        close(event);
    }

    @FXML
    void initialize() {
        outfittingNameCB.setItems(FXCollections.observableArrayList(Constant.getOutfittingName()));
        paramTypeCB.setItems(FXCollections.observableArrayList(Constant.paramType));
    }

    private boolean isModify = false;
    private String selectedId = null;

    void show(ParameterData parameterData, String selectedParamId) {
        isModify = true;
        selectedId = selectedParamId;
        outfittingNameCB.setValue(parameterData.getOutfitting_name());
        paramNameTF.setText(parameterData.getParam_name());
        paramTypeCB.setValue(parameterData.getParam_type());
        String[] temp = parameterData.getParam_scope().split("[, ， ]", 2);
        lowerTF.setText(temp[0].trim());
        if (temp.length < 2) {
            upperTF.setText("");
        } else {
            upperTF.setText(temp[1].trim());
        }
        paramDescTA.setText(parameterData.getParam_description());
    }

    private void close(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
