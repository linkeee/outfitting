package App.controller;

import App.dataModel.ParamAndValueData;
import App.dataModel.ParameterData;
import App.database.ParamValueDb;
import App.database.ParameterDb;
import App.database.ProjectDb;
import App.database.VersionDb;
import App.utile.Docker;
import App.utile.FxmlUtile;
import App.utile.MyDialog;
import App.utile.ProgressFrom;
import com.sun.javafx.css.Style;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.*;

public class ModifyResult {

    @FXML
    private TableView<ParameterData> paramScopeTV;

    @FXML
    private ComboBox<String> versionChooserCB;

    @FXML
    private TableColumn<?, ?> scopeParamNameTC;

    @FXML
    private Label isModifyLabel;

    @FXML
    private TableView<ParamAndValueData> modifyTV;

    @FXML
    private TableColumn<?, ?> scopeParamScopeTC;

    @FXML
    private TableColumn<?, ?> paramDescTC;

    @FXML
    private AnchorPane modifyResultAnchorPane;

    @FXML
    private Label versionLabel;

    @FXML
    private TableColumn<?, ?> outfittingNameTC;

    @FXML
    private Button modifyBtn;

    @FXML
    private ComboBox<String> projChooserCB;

    @FXML
    private TableColumn<?, ?> valueTC;

    @FXML
    private Button nextBtn;

    @FXML
    private Label projLabel;

    @FXML
    private TableColumn<?, ?> paramNameTC;

    @FXML
    private TableColumn<?, ?> paramTypeTC;

    @FXML
    private Button saveBtn;

    @FXML
    private Button resetBtn;

    @FXML
    void resetAction(ActionEvent event) {
        modifyTV.setItems(FXCollections.observableArrayList(ParamValueDb.getParamByProjAndVersion(ProjectDb.getIdByName(projChooserCB.getValue()), versionChooserCB.getValue())));
        isModifyLabel.setText("将未在参数范围内的参数修改为参数范围的下限");
        isModifyLabel.setStyle("-fx-text-fill: black");
    }

    @FXML
    void modifyAction(ActionEvent event) {
        List<ParameterData> paramList = paramScopeTV.getItems();
        Map<String, String> scopeMap = new HashMap<>();
        for (ParameterData param : paramList) {
            if (param.getParam_scope() == null || param.getParam_scope().equals("")) continue;
            scopeMap.put(param.getParam_name(), param.getParam_scope());
        }

        List<ParamAndValueData> pvList = modifyTV.getItems();
        for (ParamAndValueData pv : pvList) {
            String pName = pv.getParam_name();

            if (scopeMap.containsKey(pName)) {
                String tempScope = scopeMap.get(pName);
                String leftScope = tempScope.split(", ")[0];
                if (pv.getParam_value() == null || pv.getParam_value().equals("") || Double.valueOf(pv.getParam_value()) < Double.valueOf(leftScope)) {
                    pv.setParam_value(leftScope);
                }
            }
        }

        modifyTV.setItems(FXCollections.observableArrayList(pvList));
        isModifyLabel.setText("参数值已根据参数范围进行修改，请点击保存按钮将其保存至数据库");
        isModifyLabel.setStyle("-fx-text-fill: red");
    }

    @FXML
    void saveAction(ActionEvent event) {

        Optional<ButtonType> result = MyDialog.confirmation("确认修改计算结果？", "项目: " + projLabel.getText() + "\r\n" + "版本: " + versionLabel.getText());
        if (result.isPresent() && result.get() == ButtonType.OK) {
            List<ParamAndValueData> list = modifyTV.getItems();
            ParamValueDb.insertValue(list);
            isModifyLabel.setText("修改已保存");
            isModifyLabel.setStyle("-fx-text-fill: #00c800");
//            MyDialog.information("修改结果已保存", "项目: " + projLabel.getText() + "\r\n" + "版本: " + versionLabel.getText());
        }
    }

    @FXML
    void nextAction(ActionEvent event) throws IOException {
        if (projChooserCB.getValue() == null || versionChooserCB.getValue() == null) {
            MyDialog.information("未选择相应的项目和版本", "请选择项目和版本后再进行下一步操作");
            return;
        }

        saveAction(event);
        FxmlUtile.setStyle((Button) Docker.get("selectTypeBtn"), (Button) Docker.get("createProjBtn"), (Button) Docker.get("inputParamBtn"), (Button) Docker.get("calculateBtn"), (Button) Docker.get("correctBtn"));

        Docker.put("isModifyNextStep", true);
        Docker.put("selectedProj", projChooserCB.getValue());
        Docker.put("selectedVersion", versionChooserCB.getValue());

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/SelectTheType.fxml");
        BorderPane bp = (BorderPane) Docker.get("selectTypeBorderPane");
        bp.setCenter(loader.load());
    }

    @FXML
    void initialize() {
        scopeParamNameTC.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        scopeParamScopeTC.setCellValueFactory(new PropertyValueFactory<>("param_scope"));

        outfittingNameTC.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        paramNameTC.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        paramTypeTC.setCellValueFactory(new PropertyValueFactory<>("param_type"));
        paramDescTC.setCellValueFactory(new PropertyValueFactory<>("param_description"));
        valueTC.setCellValueFactory(new PropertyValueFactory<>("param_value"));
        // (.*?)\d+(.*?)
        List<ParameterData> list = new ArrayList<>();
        for (ParameterData p : ParameterDb.getParameterList()) {
            if (!p.getParam_scope().equals("")) {
                list.add(p);
            }
        }
        paramScopeTV.setItems(FXCollections.observableArrayList(list));

        projChooserCB.setItems(FXCollections.observableArrayList(ProjectDb.getProjectNameList()));
        projChooserCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            projLabel.setText(newValue);
            versionChooserCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(ProjectDb.getIdByName(newValue))));
            versionChooserCB.getSelectionModel().selectedItemProperty().addListener(((observable1, oldValue1, newValue1) -> {
                versionLabel.setText(newValue1);
                modifyTV.setItems(FXCollections.observableArrayList(ParamValueDb.getParamByProjAndVersion(ProjectDb.getIdByName(projLabel.getText()), versionLabel.getText())));
            }));
        });

        // 如果是下一步进入的话，直接加载出项目和版本
        if (Docker.containKey("isCalculateNextStep") && (boolean) Docker.get("isCalculateNextStep")) {
            projChooserCB.setValue(Docker.get("selectedProj").toString());
            versionChooserCB.setValue(Docker.get("selectedVersion").toString());
            projLabel.setText(projChooserCB.getValue());
            versionLabel.setText(versionChooserCB.getValue());
        }
        Docker.set("isCalculateNextStep", false);

    }
}
