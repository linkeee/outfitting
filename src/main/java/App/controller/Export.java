package App.controller;

import App.dataModel.ParamAndValueData;
import App.dataModel.ProjectData;
import App.database.ProjectDb;
import App.function.CopyFile;
import App.function.DirectoryScanner;
import App.function.ExcelFiller;
import App.utile.ProgressFrom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Export {

    @FXML
    private TextField filePathTF1;

    @FXML
    private TableColumn<ProjectData, String> projDescriptionTC;

    @FXML
    private TableColumn<ProjectData, String> projNameTC;

    @FXML
    private TableColumn<ParamAndValueData, String> paramDescriptionTC;

    @FXML
    private TextArea tipsTextArea1;

    @FXML
    private TableView<ProjectData> projTV;

    @FXML
    private ListView<String> fileScannerListView1;

    @FXML
    private TableView<ParamAndValueData> projParamTV;

    @FXML
    private TableColumn<ProjectData, String> projCreateTimeTC;

    @FXML
    private TableColumn<ProjectData, Integer> projIDTC;

    @FXML
    private TableColumn<ProjectData, String> projModifyTimeTC;

    @FXML
    private TableColumn<ParamAndValueData, String> paramValueTC;

    @FXML
    private TableColumn<ProjectData, String> projCreatorTC;

    @FXML
    private TableColumn<ParamAndValueData, String> paramNameTC;

    private String filePath;//模板文件路径
    private String fileName;//模板文件名
    private String listViewSelectedFile;//用于监听listView选中的文件名
    private ObservableList<ParamAndValueData> paramAndValueList;

    @FXML
    void selectDirectory1() throws NullPointerException {
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

        filePathTF1.setText(filePath + fileName);
        fileScannerListView1.setItems(DirectoryScanner.scanner(filePath));
    }

    @FXML
    private void initialize() throws NullPointerException {
        Map<String, Double> paramAndValueMap = new HashMap<>();

        tipsTextArea1.setText("导出估算书步骤如下:\n" +
                "1.点击右上方区域的‘选择’按钮选择模板文件。\n" +
                "2.右键单击左下角项目表中的某一个项目，然后点击导出。\n" +
                "3.估算书将导出到模板所在文件夹。");
        projIDTC.setCellValueFactory(new PropertyValueFactory<>("proj_id"));
        projNameTC.setCellValueFactory(new PropertyValueFactory<>("proj_name"));
        projCreateTimeTC.setCellValueFactory(new PropertyValueFactory<>("proj_create_time"));
        projModifyTimeTC.setCellValueFactory(new PropertyValueFactory<>("proj_modify_time"));
        projCreatorTC.setCellValueFactory(new PropertyValueFactory<>("proj_creator"));
        projDescriptionTC.setCellValueFactory(new PropertyValueFactory<>("proj_description"));

        projTV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProjParam(newValue.getProj_id()));

        MenuItem projTVCMItem1 = new MenuItem("导出");
        projTVCMItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (fileName == null || filePath == null) selectDirectory1();

                for (ParamAndValueData i : paramAndValueList) {
                    paramAndValueMap.put(i.getParam_name(), Double.valueOf(i.getParam_value()));
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
                    fileScannerListView1.setItems(DirectoryScanner.scanner(filePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ContextMenu projTVContextMenu = new ContextMenu();
        projTVContextMenu.getItems().addAll(projTVCMItem1);
        projTV.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                projTVContextMenu.show(projTV, event.getScreenX(), event.getScreenY());
            }
        });
        projTV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                projTVContextMenu.hide();
            }
        });

        fileScannerListView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                listViewSelectedFile = newValue.split("\\s+")[2];
            }
        });

        MenuItem listViewItem1 = new MenuItem("打开");
        listViewItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(filePath + listViewSelectedFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem listViewItem2 = new MenuItem("打开文件夹");
        listViewItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ContextMenu listViewContextMenu = new ContextMenu();
        listViewContextMenu.getItems().addAll(listViewItem1, listViewItem2);
        fileScannerListView1.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                listViewContextMenu.show(fileScannerListView1, event.getScreenX(), event.getScreenY());
            }
        });
        fileScannerListView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listViewContextMenu.hide();
            }
        });

        paramNameTC.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        paramValueTC.setCellValueFactory(new PropertyValueFactory<>("param_value"));
        paramDescriptionTC.setCellValueFactory(new PropertyValueFactory<>("param_description"));
    }

    void refreshProjectTable() {
        projTV.setItems(FXCollections.observableArrayList(ProjectDb.getProjectList()));
    }

    private void showProjParam(Object project_id) {
//        paramAndValueList = ParamValueDb.getOneProjParamList(project_id);
        projParamTV.setItems(paramAndValueList);
    }

}
