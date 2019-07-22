package App.controller;

import App.dataModel.ParamAndValueData;
import App.dataModel.ParameterData;
import App.dataModel.VersionData;
import App.database.ParamValueDb;
import App.database.ParameterDb;
import App.database.ProjectDb;
import App.database.VersionDb;
import App.utile.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InputParameter {

    @FXML
    private Button inputParamSaveBtn;

    @FXML
    private Button inputParamNextBtn;

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

    @FXML
    private Label inputLabel;

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
                            parameterData.getParam_type(),
//                            Integer.valueOf(parameterData.getParam_type()) == 1 ? "待求" : "已知",
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
                projParamValueTV.setItems(FXCollections.observableArrayList(ParamValueDb.getParamOfType(selectedProjId, newVersionName, 0)));

                inputLabel.setText("已在项目：" + projLabel.getText() + "  中增加了版本：" + versionLabel.getText() + "\r\n" + "请双击参数值列单元格为参数赋值");
                inputLabel.setStyle("-fx-text-fill: #ffc800");
            }

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
                    inputLabel.setText("数据已删除" + "\r\n" + "项目：" + projLabel.getText() + "   版本：" + confirmVersionName);
                    inputLabel.setStyle("-fx-text-fill: red");
                    versionChooserCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(selectedProjId)));
                    versionChooserCB.setValue(VersionDb.getLargestVersionName(selectedProjId));
                }
            }
        }

    }

    @FXML
    void saveAction(ActionEvent event) {
        if (selectedProjName == null || selectedVersionName == null) {
            MyDialog.information("没有数据可以保存", "请使用下拉框选择项目和版本，或新增版本，并输入参数。");
        } else {
            List<ParamAndValueData> list = projParamValueTV.getItems();
            ParamValueDb.insertValue(list);
            inputLabel.setText("参数值已保存" + "\r\n" + "项目：" + projLabel.getText() + "   版本：" + versionLabel.getText());
            inputLabel.setStyle("-fx-text-fill: #00c800");
            MyDialog.information("输入参数已保存", "项目: " + selectedProjName + "\r\n" + "版本: " + selectedVersionName);
        }
    }

    @FXML
    void nextStepAction(ActionEvent event) throws IOException {
        if (projChooserCB.getValue() == null || versionChooserCB.getValue() == null) {
            MyDialog.information("未选择相应的项目和版本", "请选择项目和版本后再进行下一步操作");
            return;
        } else {
            saveAction(event);
            if (!ParamValueDb.isInputParamHaveValue(ProjectDb.getIdByName(projChooserCB.getValue()), versionChooserCB.getValue(), 0)) {
                inputLabel.setText("有已知参数未赋值，必须为所有已知参数赋值后才能进行下一步操作。" + "\r\n" + "项目: " + selectedProjName + "   版本: " + selectedVersionName);
                inputLabel.setStyle("-fx-text-fill: red");
                MyDialog.information("有已知参数未赋值", "请为所有已知参数赋值后，再进行下一步操作");
                return;
            }
        }

        FxmlUtile.setStyle((Button) Docker.get("calculateBtn"), (Button) Docker.get("createProjBtn"), (Button) Docker.get("inputParamBtn"), (Button) Docker.get("correctBtn"), (Button) Docker.get("selectTypeBtn"));

        Docker.put("isInputParamNextStep", true);
        Docker.put("selectedProj", projChooserCB.getValue());
        Docker.put("selectedVersion", versionChooserCB.getValue());

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/Calculate.fxml");
        BorderPane bp = (BorderPane) Docker.get("selectTypeBorderPane");
        bp.setCenter(loader.load());
    }

    @FXML
    void initialize() {
        outfittingNameTC.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        paramNameTC.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        paramTypeTC.setCellValueFactory(new PropertyValueFactory<>("param_type"));
        paramDescriptionTC.setCellValueFactory(new PropertyValueFactory<>("param_description"));
        paramValueTC.setCellValueFactory(new PropertyValueFactory<>("param_value"));

        projChooserCB.setItems(FXCollections.observableArrayList(ProjectDb.getProjectNameList()));
        if (Docker.containKey("isCreateProjectNextStep") && (boolean) Docker.get("isCreateProjectNextStep")) {
            projParamValueTV.setPlaceholder(new Label("请选择项目与版本！"));
            projChooserCB.setValue(Docker.get("selectedProj").toString());
            projLabel.setText(Docker.get("selectedProj").toString());
            selectedProjName = Docker.get("selectedProj").toString();
            selectedProjId = ProjectDb.getIdByName(selectedProjName);
            versionChooserCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(ProjectDb.getIdByName(Docker.get("selectedProj").toString()))));
        }
        projChooserCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedProjName = newValue;
            selectedProjId = ProjectDb.getIdByName(selectedProjName);
            projLabel.setText(newValue);
            if (newValue == null) {
                versionChooserCB.setItems(null);
                versionChooserCB.setValue(null);
            } else {
                versionChooserCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(ProjectDb.getIdByName(newValue))));
            }
        });
        versionChooserCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                versionLabel.setText(newValue);
                selectedVersionName = newValue;

                // 项目和版本没有同时确定
                if (selectedProjName == null || newValue == null) {
                    versionDescriptionTA.setText(null);
                    projParamValueTV.setItems(null);
                    projParamValueTV.setPlaceholder(new Label("请选择项目与版本！"));
                } else {// 项目和版本都已选择
                    selectedProjId = ProjectDb.getIdByName(selectedProjName);
                    VersionData versionData = VersionDb.getOneVersionData(selectedProjId, selectedVersionName, 0);
                    versionDescriptionTA.setText(versionData.getVersion_description());

                    projParamValueTV.setItems(FXCollections.observableArrayList(versionData.getParam_value_list()));
                }
            }
        });

        Docker.set("isCreateProjectNextStep", false);

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
