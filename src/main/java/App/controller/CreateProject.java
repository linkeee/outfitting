package App.controller;

import App.appModel.ProjectData;
import App.database.ProjectDatabase;
import App.function.Dialog;
import App.utile.DateUtile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    void saveAction(ActionEvent event) {
        ProjectData projectData = new ProjectData();
        projectData.setProj_name(projNameTF.getText());
        projectData.setProj_create_time(projCreateTimeTF.getText());
        projectData.setProj_modify_time(projModifyTimeTF.getText());
        projectData.setProj_creator(projPersonInChargeTF.getText());
        projectData.setProj_description(projRemarkTF.getText());

        if (ProjectDatabase.getProjectNameList().contains(projNameTF.getText())) {
            Optional<ButtonType> result = Dialog.confirmation("项目信息修改确认", "确认修改项目\"" + projNameTF.getText() + "\"信息？", "");
            if (result.get() == ButtonType.OK) {
                ProjectDatabase.update(projectData, ProjectDatabase.getIdByName(projNameTF.getText()));
            }
        } else {
            ProjectDatabase.insert(projectData);
            Dialog.information("新建项目成功", "项目\"" + projNameTF.getText() + "\"已保存", "");
        }
        projectComboBox.setItems(FXCollections.observableArrayList(ProjectDatabase.getProjectNameList()));
    }

    @FXML
    void nextStepAction(ActionEvent event) {
    }

    private ProjectData projectData;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        projCreateTimeTF.setText(DateUtile.nowDateFormat());
        projModifyTimeTF.setText(DateUtile.nowDateFormat());
        projectComboBox.setItems(FXCollections.observableArrayList(ProjectDatabase.getProjectNameList()));
        projectComboBox.setTooltip(new Tooltip("选择项目"));
        ctrBtn.setTooltip(new Tooltip("将项目创建时间更新为当前时间，由于修改时间不能早于创建时间，修改时间也将更新为当前时间。"));
        mtrBtn.setTooltip(new Tooltip("将项目修改时间更新为当前时间。"));

        projectComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                projectData = ProjectDatabase.getProjectDataByName(newValue);
                projectLabel.setText(projectData.getProj_name());
                projNameTF.setText(projectData.getProj_name());
                projCreateTimeTF.setText(projectData.getProj_create_time());
                projModifyTimeTF.setText(projectData.getProj_modify_time());
                projPersonInChargeTF.setText(projectData.getProj_creator());
                projRemarkTF.setText(projectData.getProj_description());
            }
        });
    }

}
