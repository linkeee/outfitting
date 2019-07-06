package App.controller;


import App.dataModel.ParamAndValueData;
import App.dataModel.ProjectData;
import App.database.ParamValueDb;
import App.database.ProjectDb;
import App.database.VersionDb;
import App.function.CopyFile;
import App.function.DirectoryScanner;
import App.function.ExcelFiller;
import App.utile.Docker;
import App.utile.MyDialog;
import App.utile.ProgressFrom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportResult {

    @FXML
    private Label templeteFileLabel;

    @FXML
    private TableColumn<ParamAndValueData, String> paramDescriptionTC;

    @FXML
    private TextArea tipsTA;

    @FXML
    private ComboBox<String> projCB;

    @FXML
    private TextField projCreateTimeTF;

    @FXML
    private TableView<ParamAndValueData> projParamTV;

    @FXML
    private TextField projNameTF;

    @FXML
    private TextArea projRemarkTA;

    @FXML
    private TextField projModifyTimeTF;

    @FXML
    private ComboBox<String> versionCB;

    @FXML
    private TextArea versionDescTA;

    @FXML
    private Label resultFileLabel;

    @FXML
    private TableColumn<ParamAndValueData, String> paramValueTC;

    @FXML
    private TextField projPersonInChargeTF;

    @FXML
    private TableColumn<ParamAndValueData, String> paramNameTC;

    private String filePath;//模板文件路径
    private String fileName;//模板文件名
    private List<ParamAndValueData> paramAndValueList;

    @FXML
    void selectDirectoryAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("EXCEL模板文件", "*.xlsx")
        );
        fileChooser.setTitle("请选择模板文件...");
        fileChooser.setInitialDirectory(new File("D:\\"));
        File selectedFile = fileChooser.showOpenDialog(null);
        this.filePath = selectedFile.getParent();
        this.fileName = selectedFile.getName();

        templeteFileLabel.setText(fileName);
    }

    @FXML
    void exportAction(ActionEvent event) {
        if (fileName == null || filePath == null) {
            selectDirectoryAction(event);
        } else if (selectedProjName == null || selectedVersionName == null) {
            MyDialog.information("未选择项目&版本", "请使用下拉框选择项目和版本。");
        } else {
            Map<String, String> paramAndValueMap = new HashMap<>();
            for (ParamAndValueData i : paramAndValueList) {
                paramAndValueMap.put(i.getParam_name(), i.getParam_value());
            }
            try {
                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        String newName = CopyFile.copyFile(filePath + fileName);
                        ExcelFiller.filler(filePath + newName, paramAndValueMap);
                        return null;
                    }
                };
                ProgressFrom progressFrom = new ProgressFrom(task, "正在导出估算书，请稍后...");
                progressFrom.activateProgressBar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            resultFileLabel.setText(String.valueOf(Docker.get("newTemplateFileName")));
        }

    }

    private String selectedProjName;
    private String selectedVersionName;

    @FXML
    void initialize() {
        tipsTA.setText("导出估算书步骤如下:\n" +
                "1.点击右上方区域的‘选择’按钮选择模板文件。\n" +
                "2.右键单击左下角项目表中的某一个项目，然后点击导出。\n" +
                "3.估算书将导出到模板所在文件夹。");

        projCB.setItems(FXCollections.observableArrayList(ProjectDb.getProjectNameList()));
        projCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> displayProj(newValue));
        versionCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> displayVersionDesc(newValue));

        paramNameTC.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        paramValueTC.setCellValueFactory(new PropertyValueFactory<>("param_value"));
        paramDescriptionTC.setCellValueFactory(new PropertyValueFactory<>("param_description"));
    }

    private void displayVersionDesc(String newValue) {
        selectedVersionName = newValue;
        paramAndValueList = ParamValueDb.getParamByProjAndVersion(ProjectDb.getIdByName(selectedProjName), selectedVersionName);

        versionDescTA.setText(VersionDb.getOneVersionData(ProjectDb.getIdByName(selectedProjName), newValue).getVersion_description());
        projParamTV.setItems(FXCollections.observableArrayList(ParamValueDb.getParamByProjAndVersion(ProjectDb.getIdByName(selectedProjName), selectedVersionName)));
    }

    private void displayProj(String newValue) {
        selectedProjName = newValue;

        ProjectData projectData = ProjectDb.getOneProjectData(newValue);
        projNameTF.setText(projectData.getProj_name());
        projCreateTimeTF.setText(projectData.getProj_create_time());
        projModifyTimeTF.setText(projectData.getProj_modify_time());
        projPersonInChargeTF.setText(projectData.getProj_creator());
        projRemarkTA.setText(projectData.getProj_description());
        versionCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(ProjectDb.getIdByName(newValue))));
    }

}
