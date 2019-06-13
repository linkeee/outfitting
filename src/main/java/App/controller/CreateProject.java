package App.controller;

import App.appModel.ProjectData;
import App.database.ProjectDatabase;
import App.function.Dialog;
import App.utile.DateUtile;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private ComboBox projectComboBox;

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
    }

    @FXML
    void nextStepAction(ActionEvent event) {
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        projCreateTimeTF.setText(DateUtile.nowDateFormat());
        projModifyTimeTF.setText(DateUtile.nowDateFormat());
        projectComboBox.setItems(FXCollections.observableArrayList(ProjectDatabase.getProjectNameList()));
    }

}
