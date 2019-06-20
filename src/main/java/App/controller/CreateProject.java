package App.controller;

import App.utile.Docker;
import App.dataModel.ProjectData;
import App.database.ProjectDb;
import App.utile.MyDialog;
import App.utile.DateUtile;
import App.utile.FxmlUtile;
import App.utile.ProgressFrom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class CreateProject {

    @FXML
    private TextField projModifyTimeTF;

    @FXML
    private TextField projNameTF;

    @FXML
    private TextField projCreateTimeTF;

    @FXML
    private TextField projPersonInChargeTF;

    @FXML
    private TextArea projRemarkTF;

    @FXML
    private Label projectLabel;

    @FXML
    private ComboBox<String> projectComboBox;

    @FXML
    private Button ctrBtn;

    @FXML
    private Button mtrBtn;

    @FXML
    private Button deleteBth;

    @FXML
    void createTimeRefresh(ActionEvent event) {
        projCreateTimeTF.setText(DateUtile.nowDateFormat());
        projModifyTimeTF.setText(DateUtile.nowDateFormat());
    }

    @FXML
    void modifyTimeRefresh(ActionEvent event) {
        projModifyTimeTF.setText(DateUtile.nowDateFormat());
    }

    @FXML
    void displayDBData(ActionEvent event) {
        projNameTF.setText(projectData.getProj_name());
        projCreateTimeTF.setText(projectData.getProj_create_time());
        projModifyTimeTF.setText(projectData.getProj_modify_time());
        projPersonInChargeTF.setText(projectData.getProj_creator());
        projRemarkTF.setText(projectData.getProj_description());
    }

    private String comboBoxSelectedName = null;

    @FXML
    void deleteProjectAction(ActionEvent event) {
        if (comboBoxSelectedName == null) {
            MyDialog.information("未选择项目", "请使用下拉框选择项目。");
        } else {
            Optional<ButtonType> result = MyDialog.confirmation( null, "项目“" + comboBoxSelectedName + "”将被删除，不可恢复，确认删除吗？");
            if (result.get() == ButtonType.OK) {
                ProjectDb.delete(ProjectDb.getIdByName(comboBoxSelectedName));
                initialize();
            }
        }
    }

    @FXML
    void saveAction(ActionEvent event) {
        ProjectData projectData = new ProjectData();
        projectData.setProj_name(projNameTF.getText());
        projectData.setProj_create_time(projCreateTimeTF.getText());
        projectData.setProj_modify_time(projModifyTimeTF.getText());
        projectData.setProj_creator(projPersonInChargeTF.getText());
        projectData.setProj_description(projRemarkTF.getText());

        if (projNameTF.getText().equals("") || projCreateTimeTF.getText().equals("") || projModifyTimeTF.getText().equals("") || projPersonInChargeTF.getText().equals("")) {
            MyDialog.information(null, "项目名称、创建时间、修改时间、负责人为必填项，请填写。");
        } else if (ProjectDb.getProjectNameList().contains(projNameTF.getText())) {
            ProjectData existPd = ProjectDb.getOneProjectData(projNameTF.getText());
            if (
                    existPd.getProj_name().equals(projNameTF.getText()) &&
                    existPd.getProj_create_time().equals(projCreateTimeTF.getText()) &&
                    existPd.getProj_modify_time().equals(projModifyTimeTF.getText()) &&
                    existPd.getProj_creator().equals(projPersonInChargeTF.getText()) &&
                    existPd.getProj_description().equals(projRemarkTF.getText())) {

            } else {
                Optional<ButtonType> result = MyDialog.confirmation("确认修改项目“" + projNameTF.getText() + "”信息？", "");
                if (result.get() == ButtonType.OK) {
                    ProjectDb.update(projectData, ProjectDb.getIdByName(projNameTF.getText()));
                }
            }

        } else {
            ProjectDb.insert(projectData);
            MyDialog.information("项目“" + projNameTF.getText() + "”已保存", null);
        }

        // 更新项目选择框
        projectComboBox.setItems(FXCollections.observableArrayList(ProjectDb.getProjectNameList()));
    }

    @FXML
    void nextStepAction(ActionEvent event) throws IOException {
        saveAction(event);

        Docker.put("isCreateProjectNextStep", true);
        Docker.put("comboBoxSelection", projectComboBox.getValue());

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/InputParameter.fxml");
        BorderPane bp = (BorderPane)Docker.get("selectTypeBorderPane");
        bp.setCenter(loader.load());
        bp.setPadding(new Insets(5, 5, 5, 5));

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    private ProjectData projectData;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        projCreateTimeTF.setText(DateUtile.nowDateFormat());
        projModifyTimeTF.setText(DateUtile.nowDateFormat());
        projectComboBox.setItems(FXCollections.observableArrayList(ProjectDb.getProjectNameList()));
        projectComboBox.setTooltip(new Tooltip("选择项目"));
        ctrBtn.setTooltip(new Tooltip("将项目创建时间更新为当前时间，由于修改时间不能早于创建时间，修改时间也将更新为当前时间。"));
        mtrBtn.setTooltip(new Tooltip("将项目修改时间更新为当前时间。"));
        deleteBth.setTooltip(new Tooltip("删除左侧下拉框选中的项目。"));

        projectComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                projectData = ProjectDb.getOneProjectData(newValue);
                projectLabel.setText(projectData.getProj_name());
                projNameTF.setText(projectData.getProj_name());
                projCreateTimeTF.setText(projectData.getProj_create_time());
                projModifyTimeTF.setText(projectData.getProj_modify_time());
                projPersonInChargeTF.setText(projectData.getProj_creator());
                projRemarkTF.setText(projectData.getProj_description());

                comboBoxSelectedName = newValue;
            }
        });
    }

}
