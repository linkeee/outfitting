package App.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InputParameter {

    @FXML
    private Label versionLabel;

    @FXML
    private TableColumn<?, ?> outfittingNameTC;

    @FXML
    private ComboBox<?> versionChooserCB;

    @FXML
    private TableColumn<?, ?> paramDescriptionTC;

    @FXML
    private TableView<?> projParamValueTV;

    @FXML
    private ComboBox<?> projChooserCB;

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
    void addVersionAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }

    @FXML
    void nextStepAction(ActionEvent event) {

    }

}
