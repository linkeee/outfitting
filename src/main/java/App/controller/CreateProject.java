package App.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateProject {

    @FXML
    private TextField projModifyTimeTF;

    @FXML
    private TextField projIdTF;

    @FXML
    private TextField projCreateTimeTF;

    @FXML
    private TextField projPersonInChargeTF;

    @FXML
    private TextArea projRemarkTF;

    @FXML
    void saveAction(ActionEvent event) {
//        projIdTF
    }

    @FXML
    void nextStepAction(ActionEvent event) {

    }

    private String createTime = null;
    private String modifyTime = null;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }

}
