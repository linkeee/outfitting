package App.controller;

import App.dataModel.ParamAndValueData;
import App.dataModel.ParameterData;
import App.dataModel.VersionData;
import App.database.ParamValueDb;
import App.database.ParameterDb;
import App.database.ProjectDb;
import App.database.VersionDb;
import App.utile.MyDialog;
import App.utile.DataModelUtil;
import App.utile.Docker;
import App.utile.EditingCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InputParameter {

    @FXML
    private Button addVersionBtn;

    @FXML
    private Button deleteVersionBtn;

    @FXML
    private Label versionLabel;

    @FXML
    private TableColumn<ParamAndValueData, String> outfittingNameTC;

    @FXML
    private ComboBox<String> versionChooserCB;

    @FXML
    private TableColumn<ParamAndValueData, String> paramDescriptionTC;

    @FXML
    private TableView<ParamAndValueData> projParamValueTV;

    @FXML
    private ComboBox<String> projChooserCB;

    @FXML
    private TableColumn<ParamAndValueData, String> paramValueTC;

    @FXML
    private Label projLabel;

    @FXML
    private TableColumn<ParamAndValueData, String> paramNameTC;

    @FXML
    private TableColumn<ParamAndValueData, String> paramTypeTC;

    @FXML
    private TextArea versionDescriptionTA;

    private List<ParamAndValueData> paramDataListContainer = new ArrayList<>();

    private int selectedProjId = -1;
    private String selectedProjName = null;
    private String selectedVersionName = null;

    @FXML
    void addVersionAction(ActionEvent event) {
        if (selectedProjId == -1) {
            MyDialog.information(null, "请选择项目。");
        } else {
            Optional<String> result = MyDialog.inputText("请输入新增版本的描述");
            String newVersionDesc = null;
            if (result.isPresent()) {
                newVersionDesc = result.get();
            }

            // 版本号自动加1。   v1 -> v2
            String newVersionName = VersionDb.getVersionNameNeedToAdd(selectedProjId);

            // 将List<ParameterData>转换为List<ParamAndValueData>
            paramDataListContainer.clear();
            // Todo 参数库的接口，提供需要用到的参数list
            for (ParameterData parameterData : ParameterDb.getParameterList()) {
                // 将ParameterData的值对应转换到ParamAndValueData中
                paramDataListContainer.add(DataModelUtil.getParamAndValueData(
                        String.valueOf(selectedProjId),
                        newVersionName,
                        parameterData.getParam_id(),
                        parameterData.getOutfitting_name(),
                        parameterData.getParam_name(),
                        Integer.valueOf(parameterData.getParam_type()) == 1 ? "待求" : "已知",
                        parameterData.getParam_description(),
                        null   // 参数值列留空等待用户输入
                ));
            }

            VersionDb.insert(DataModelUtil.getVersionData(
                    String.valueOf(selectedProjId),
                    newVersionName,
                    newVersionDesc,
                    paramDataListContainer
            ));
            versionChooserCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(selectedProjId)));
            versionChooserCB.setValue(newVersionName);

            ParamValueDb.insert(paramDataListContainer);
            projParamValueTV.setItems(FXCollections.observableArrayList(ParamValueDb.getParamByProjAndVersion(selectedProjId, newVersionName)));
        }

    }

    @FXML
    void deleteVersionAction(ActionEvent event) {
        if (selectedProjName == null || selectedProjName.equals("") || selectedVersionName == null || selectedVersionName.equals("")) {
            MyDialog.information("未选择项目&版本", "请使用下拉框选择项目和版本。");
        } else {
            Optional<String> result = MyDialog.inputText("该版本下的所有参数以及参数值都将被永久删除，且该操作不可恢复! " + "\r\n" + "请手动输入版本号确认!" + "\r\n" + "版本号: " + selectedVersionName);
            String confirmVersionName = null;
            if (result.isPresent()) {
                confirmVersionName = result.get();

                if (!confirmVersionName.equals(selectedVersionName)) {
                    MyDialog.information("版本号输入有误", "删除失败");
                } else {
                    VersionDb.deleteAVersionAndParam(selectedProjId, selectedVersionName);
                    versionChooserCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(selectedProjId)));
                    versionChooserCB.setValue(VersionDb.getLargestVersionName(selectedProjId));
                }
            }
        }

    }

    @FXML
    void saveAction(ActionEvent event) {
        List<ParamAndValueData> list = projParamValueTV.getItems();
        ParamValueDb.insertValue(list);
        MyDialog.information("项目“" + selectedProjName + "”的“" + selectedVersionName + "”版本数据已保存", null);
    }

    @FXML
    void nextStepAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        outfittingNameTC.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        paramNameTC.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        paramTypeTC.setCellValueFactory(new PropertyValueFactory<>("param_type"));
        paramDescriptionTC.setCellValueFactory(new PropertyValueFactory<>("param_description"));
        paramValueTC.setCellValueFactory(new PropertyValueFactory<>("param_value"));

        projChooserCB.setItems(FXCollections.observableArrayList(ProjectDb.getProjectNameList()));
        if (Docker.containKey("isCreateProjectNextStep")) {
            projParamValueTV.setPlaceholder(new Label("请选择项目与版本！"));
            projChooserCB.setValue(Docker.get("comboBoxSelection").toString());
            projLabel.setText(Docker.get("comboBoxSelection").toString());
            selectedProjName = Docker.get("comboBoxSelection").toString();
            selectedProjId = ProjectDb.getIdByName(selectedProjName);
            versionChooserCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(ProjectDb.getIdByName(Docker.get("comboBoxSelection").toString()))));
        }
        projChooserCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedProjName = newValue;
                selectedProjId = ProjectDb.getIdByName(selectedProjName);
                projLabel.setText(newValue);
                versionChooserCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(ProjectDb.getIdByName(newValue))));
            }
        });
        versionChooserCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                versionLabel.setText(newValue);
                selectedVersionName = newValue;

                // 项目和版本没有同时确定
                if (selectedProjName == null || selectedVersionName == null) {
                    versionDescriptionTA.setText(null);
                    projParamValueTV.setItems(null);
                    projParamValueTV.setPlaceholder(new Label("请选择项目与版本！"));
                } else {// 项目和版本都已选择
                    selectedProjId = ProjectDb.getIdByName(selectedProjName);
                    VersionData versionData = VersionDb.getOneVersionData(selectedProjId, selectedVersionName);
                    versionDescriptionTA.setText(versionData.getVersion_description());
                    projParamValueTV.setItems(FXCollections.observableArrayList(versionData.getParam_value_list()));
                }
            }
        });

        Docker.remove("isCreateProjectNextStep");

        // 第一种让TableView可双击编辑的方法，但必须回车提交
//        projParamValueTV.setEditable(true);
//        paramValueTC.setCellFactory(TextFieldTableCell.forTableColumn());
//        paramValueTC.setOnEditCommit(
//                (TableColumn.CellEditEvent<ParamAndValueData, String> t) -> (t.getTableView().getItems().get(
//                        t.getTablePosition().getRow()
//                )).setParam_value(t.getNewValue())
//        );

        // 第二种让TableView可双击编辑的方法，失焦提交
        paramValueTC.setCellFactory(param -> new EditingCell<>());

    }

}
