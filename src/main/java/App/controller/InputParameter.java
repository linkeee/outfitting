package App.controller;

import App.dataModel.VersionData;
import App.database.ProjectDatabase;
import App.database.VersionDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InputParameter {

    @FXML
    private Label versionLabel;

    @FXML
    private TableColumn<?, ?> outfittingNameTC;

    @FXML
    private ComboBox<String> versionChooserCB;

    @FXML
    private TableColumn<?, ?> paramDescriptionTC;

    @FXML
    private TableView<?> projParamValueTV;

    @FXML
    private ComboBox<String> projChooserCB;

    @FXML
    private TableColumn<?, ?> remarkTC;

    @FXML
    private TableColumn<?, ?> paramValueTC;

    @FXML
    private Label projLabel;

    @FXML
    private TableColumn<?, ?> paramNameTC;

    @FXML
    private TableColumn<?, ?> paramTypeTC;

    @FXML
    private TextArea versionDescriptionTA;

    @FXML
    void addVersionAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }

    @FXML
    void nextStepAction(ActionEvent event) {

    }

    private String selectedProjName = null;
    private String selectedVersionName = null;

    @FXML
    void initialize() {
        projChooserCB.setItems(FXCollections.observableArrayList(ProjectDatabase.getProjectNameList()));
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

                if (selectedProjName == null || selectedVersionName == null) {
                    versionDescriptionTA.setText(null);
                } else {
                    int selectedProjId = ProjectDatabase.getIdByName(selectedProjName);
                    VersionData versionData = VersionDatabase.getOneVersionData(selectedProjId, selectedVersionName);
                    versionDescriptionTA.setText(versionData.getVersion_description());
                }
            }
        });
    }

}
