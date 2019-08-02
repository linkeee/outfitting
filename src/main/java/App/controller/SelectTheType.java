package App.controller;

import App.dataModel.ManufacturerData;
import App.dataModel.ParamAndValueData;
import App.dataModel.SelectedTypeData;
import App.database.*;
import App.utile.Docker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class SelectTheType {

    @FXML
    private TableColumn<?, ?> TV3TCParamScope;

    @FXML
    private TableColumn<?, ?> TV1TCParamValue;

    @FXML
    private TableView<ManufacturerData> TVType;

    @FXML
    private TableColumn<?, ?> TV2TCManu;

    @FXML
    private TableColumn<?, ?> TV3TCOutfittingName;

    @FXML
    private TableColumn<?, ?> TV2TCParamScope;

    @FXML
    private TableColumn<?, ?> TV3TCParamValue;

    @FXML
    private TableView<ParamAndValueData> TVParam;

    @FXML
    private TableColumn<?, ?> TV3TCRemark;

    @FXML
    private TableColumn<?, ?> TV3TCParamDesc;

    @FXML
    private TableColumn<?, ?> TV1TCParamName;

    @FXML
    private TableColumn<?, ?> TV1TCParamDesc;

    @FXML
    private TableColumn<?, ?> TV3TCParamName;

    @FXML
    private Button BtnAdd;

    @FXML
    private AnchorPane selectAnchorPane;

    @FXML
    private TableColumn<?, ?> TV2TCRemark;

    @FXML
    private TableView<SelectedTypeData> TVList;

    @FXML
    private TableColumn<?, ?> TV3TCManu;

    @FXML
    private TableColumn<?, ?> TV2TCType;

    @FXML
    private Label selectLabel;

    @FXML
    private TableColumn<?, ?> TV1TCOutfittingName;

    @FXML
    private TableColumn<?, ?> TV3TCType;

    @FXML
    private TableColumn<SelectedTypeData, String> TV3TCDeleteBtn;

    @FXML
    private ComboBox<String> projCB;

    @FXML
    private ComboBox<String> versionCB;

    @FXML
    private Label projVersionLabel, manuLabel;

    private ParamAndValueData selectedPv;
    private ManufacturerData selectedMf;

    private void refreshTwoTV() {
        assert projCB.getValue() != null;
        assert versionCB.getValue() != null;

        // 该项目该版本下已经选择的厂商
        List<SelectedTypeData> selectedList = SelectedTypeDb.getSelectedType(ProjectDb.getIdByName(projCB.getValue()), versionCB.getValue());

        // 已经选择的厂商列表中所有舾装件名称
        List<String> selectedOutfittingNameList = new ArrayList<>();
        for (SelectedTypeData s : selectedList) {
            selectedOutfittingNameList.add(s.getOutfitting_name());
        }

        // 左上角现在需要显示的：还未选择厂商的舾装件
        List<ParamAndValueData> nowNeedToSelect = new ArrayList<>();
        for (ParamAndValueData p : ParamValueDb.getParamByProjAndVersion(ProjectDb.getIdByName(projCB.getValue()), versionCB.getValue())) {
            if (p.getOutfitting_name() == null || p.getOutfitting_name().equals("") || selectedOutfittingNameList.contains(p.getOutfitting_name()))
                continue;
            nowNeedToSelect.add(p);
        }
        TVParam.setItems(FXCollections.observableArrayList(nowNeedToSelect));
        TVList.setItems(FXCollections.observableArrayList(selectedList));
    }

    @FXML
    void addAction(ActionEvent event) {
        SelectedTypeDb.insert(selectedPv, selectedMf);
        refreshTwoTV();
    }

    @FXML
    void initialize() {
        projCB.setItems(FXCollections.observableArrayList(ProjectDb.getProjectNameList()));
        projCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> versionCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(ProjectDb.getIdByName(newValue)))));
        versionCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            refreshTwoTV();
            projVersionLabel.setText("项目：" + projCB.getValue() + "\r\n" + "版本：" + versionCB.getValue() + "\r\n" + "计算结果");
        });

        TV1TCOutfittingName.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        TV1TCParamName.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        TV1TCParamDesc.setCellValueFactory(new PropertyValueFactory<>("param_description"));
        TV1TCParamValue.setCellValueFactory(new PropertyValueFactory<>("param_value"));

        TV2TCManu.setCellValueFactory(new PropertyValueFactory<>("manufacturer_name"));
        TV2TCType.setCellValueFactory(new PropertyValueFactory<>("outfitting_type"));
        TV2TCParamScope.setCellValueFactory(new PropertyValueFactory<>("param_scope"));
        TV2TCRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));

        TV3TCOutfittingName.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        TV3TCParamName.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        TV3TCParamDesc.setCellValueFactory(new PropertyValueFactory<>("param_description"));
        TV3TCParamValue.setCellValueFactory(new PropertyValueFactory<>("param_value"));
        TV3TCManu.setCellValueFactory(new PropertyValueFactory<>("manufacturer_name"));
        TV3TCType.setCellValueFactory(new PropertyValueFactory<>("outfitting_type"));
        TV3TCParamScope.setCellValueFactory(new PropertyValueFactory<>("param_scope"));
        TV3TCRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));
        TV3TCDeleteBtn.setCellFactory(delCol -> new TableCell<SelectedTypeData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    this.setText(null);
                    this.setGraphic(null);
                } else {
                    ImageView view = new ImageView(new Image("App/appView/images/used/litterbin.png"));
                    Button delBtn = new Button("", view);
                    delBtn.setStyle("-fx-pref-height: 10px; -fx-pref-width: 10px; -fx-background-size: 5px, 5px; -fx-background-radius: 5; -fx-background-color: #ffa500");
                    this.setGraphic(delBtn);
                    delBtn.setOnMouseClicked(click -> {
                        SelectedTypeData std = this.getTableView().getItems().get(this.getIndex());
                        SelectedTypeDb.delete(std);
                        refreshTwoTV();
                    });
                }
            }
        });

        TVParam.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                manuLabel.setText("");
                TVType.setItems(null);
            } else {
                selectedPv = newValue;
                manuLabel.setText(newValue.getOutfitting_name() + "-->厂商列表");
                TVType.setItems(FXCollections.observableArrayList(ManufacturerDb.getManuDataByOutfittiingName(newValue.getOutfitting_name())));
            }
        });

        TVType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedMf = newValue);

        if (Docker.containKey("isModifyNextStep") && (boolean) Docker.get("isModifyNextStep")) {
            projCB.setValue(Docker.get("selectedProj").toString());
            versionCB.setValue(Docker.get("selectedVersion").toString());
            TVParam.setItems(FXCollections.observableArrayList(ParamValueDb.getParamByProjAndVersion(ProjectDb.getIdByName(projCB.getValue()), versionCB.getValue())));
        }
        Docker.set("isModifyNextStep", false);
    }
}
