package App.controller;

import App.dataModel.ParamAndValueData;
import App.dataModel.ParameterData;
import App.dataModel.VersionData;
import App.database.ParamAndValueDatabase;
import App.database.ParameterDatabase;
import App.database.ProjectDatabase;
import App.database.VersionDatabase;
import App.utile.Docker;
import App.utile.EditingCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.util.List;

public class InputParameter {

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
    void addVersionAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {
        List<ParamAndValueData> list = projParamValueTV.getItems();
        ParamAndValueDatabase.saveValue(selectedProjId, selectedVersionName, list);
    }

    @FXML
    void nextStepAction(ActionEvent event) {

    }

    private int selectedProjId;
    private String selectedProjName;
    private String selectedVersionName;

    @FXML
    void initialize() {
        outfittingNameTC.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        paramNameTC.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        paramTypeTC.setCellValueFactory(new PropertyValueFactory<>("param_type"));
        paramDescriptionTC.setCellValueFactory(new PropertyValueFactory<>("param_description"));
        paramValueTC.setCellValueFactory(new PropertyValueFactory<>("param_value"));

        projChooserCB.setItems(FXCollections.observableArrayList(ProjectDatabase.getProjectNameList()));
        if (Docker.containKey("isCreateProjectNextStep")) {
            projParamValueTV.setPlaceholder(new Label("请选择项目与版本！"));
            projChooserCB.setValue(Docker.get("comboBoxSelection").toString());
            projLabel.setText(Docker.get("comboBoxSelection").toString());
            selectedProjName = Docker.get("comboBoxSelection").toString();
            versionChooserCB.setItems(FXCollections.observableArrayList(VersionDatabase.getVersionNameListOfProj(ProjectDatabase.getIdByName(Docker.get("comboBoxSelection").toString()))));
        }
        projChooserCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedProjName = newValue;
                projLabel.setText(newValue);
                versionChooserCB.setItems(FXCollections.observableArrayList(VersionDatabase.getVersionNameListOfProj(ProjectDatabase.getIdByName(newValue))));
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
                    selectedProjId = ProjectDatabase.getIdByName(selectedProjName);
                    VersionData versionData = VersionDatabase.getOneVersionData(selectedProjId, selectedVersionName);
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

        // 将参数库中的参数取出，放入projParamValueTV.
//        List<ParameterData> list = ParameterDatabase.getParameterList();
    }

}
