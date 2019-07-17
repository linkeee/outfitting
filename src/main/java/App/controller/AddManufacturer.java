package App.controller;

import App.dataModel.ManufacturerData;
import App.database.ManufacturerDb;
import App.database.ParamValueDb;
import App.database.ParameterDb;
import App.utile.Constant;
import App.utile.MyDialog;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class AddManufacturer {

    @FXML
    private ComboBox<String> outfittingNameCB;

    @FXML
    private TextField lowerTF;

    @FXML
    private TextField upperTF;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField manuTF;

    @FXML
    private TextArea remarkTA;

    @FXML
    private TextField itemTypeTF;

    @FXML
    private Button okBtn;

    @FXML
    Label addManuLabel;

    private Boolean isModify = false;

    private static final AddManufacturer Instance = new AddManufacturer();

    public static AddManufacturer getInstance() {
        return Instance;
    }

    private String selectedId = null;

    @FXML
    void okAction(ActionEvent event) {

        if (outfittingNameCB.getValue() == null) {
            MyDialog.information("舾装件名称为空", "请选择舾装件名称");
            return;
        }

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

        ManufacturerData manufacturerData = new ManufacturerData();
        manufacturerData.setOutfitting_name(outfittingNameCB.getValue());
        manufacturerData.setManufacturer_name(manuTF.getText());
        manufacturerData.setOutfitting_type(itemTypeTF.getText());
        manufacturerData.setParam_scope(lowerTF.getText() + ", " + upperTF.getText());
        manufacturerData.setRemark(remarkTA.getText());

        if (isModify) {
            ManufacturerDb.update(manufacturerData, selectedId);
        } else {
            ManufacturerDb.insert(manufacturerData);
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
    }

    public void showModify(ManufacturerData manufacturerData, String selectedManuId) {
        isModify = true;
        selectedId = selectedManuId;
        outfittingNameCB.setValue(manufacturerData.getOutfitting_name());
        itemTypeTF.setText(manufacturerData.getOutfitting_type());
        manuTF.setText(manufacturerData.getManufacturer_name());
        String[] temp = manufacturerData.getParam_scope().split("[, ， ]", 2);
        lowerTF.setText(temp[0].trim());
        upperTF.setText(temp[1].trim());
        remarkTA.setText(manufacturerData.getRemark());
    }

    private void close(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
