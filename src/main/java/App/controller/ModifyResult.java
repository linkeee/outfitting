package App.controller;

import App.database.ProjectDb;
import App.database.VersionDb;
import App.utile.Docker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ModifyResult {

    @FXML
    private TableColumn<?, ?> valueAfterTC;

    @FXML
    private ComboBox<String> versionChooserCB;

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
    private TableColumn<?, ?> valueScopeTC;

    @FXML
    private TableColumn<?, ?> valueBeforeTC;

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
    private TableView<?> modifyTV;

    private String selectedProjName = null;
    private String selectedVersionName = null;

    @FXML
    void modifyAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }

    @FXML
    void nextAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        projChooserCB.setItems(FXCollections.observableArrayList(ProjectDb.getProjectNameList()));

        projChooserCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            projLabel.setText(newValue);
            versionChooserCB.setItems(FXCollections.observableArrayList(VersionDb.getVersionNameListOfProj(ProjectDb.getIdByName(newValue))));
            versionChooserCB.getSelectionModel().selectedItemProperty().addListener(((observable1, oldValue1, newValue1) -> {
                versionLabel.setText(newValue1);
            }));
        });

        if (Docker.containKey("isCalculateNextStep") && (boolean) Docker.get("isCalculateNextStep")) {
            projChooserCB.setValue(Docker.get("selectedProj").toString());
            versionChooserCB.setValue(Docker.get("selectedVersion").toString());
            projLabel.setText(projChooserCB.getValue());
            versionLabel.setText(versionChooserCB.getValue());
        }
    }
}
