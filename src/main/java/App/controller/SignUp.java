package App.controller;

import App.dataModel.UserData;
import App.database.UserDb;
import App.utile.Constant;
import App.utile.MyDialog;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUp {

    @FXML
    private TextField telTF;

    @FXML
    private PasswordField pwdPF;

    @FXML
    private PasswordField pwdConfirmPF;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField userNameTF;

    @FXML
    private ComboBox<String> positionCB;

    @FXML
    private TextField jobNumTF;

    @FXML
    private Button okBtn;

    @FXML
    void okAction(ActionEvent event) {
        System.out.println(userNameTF.getText());
        System.out.println(pwdPF.getText());
        System.out.println(positionCB.getValue());
        System.out.println("-----------------");

        if (userNameTF.getText().trim().length() < 1 ||
                pwdPF.getText().trim().length() < 1 ||
                pwdConfirmPF.getText().trim().length() < 1 ||
                jobNumTF.getText().trim().length() < 1 ||
                positionCB.getValue().trim().length() < 1) {
            MyDialog.warning("警告", "有必填项未填写");
            return;
        }

        if (!pwdPF.getText().trim().equals(pwdConfirmPF.getText().trim())) {
            MyDialog.warning("警告", "两次密码输入不一致");
            return;
        }

        if (!jobNumTF.getText().trim().matches("^[0-9]*$")) {
            MyDialog.warning("警告", "工号只能包含数字");
            return;
        }

        UserData userData = new UserData(userNameTF.getText(), telTF.getText().trim().length() < 1 ? null : telTF.getText().trim(), jobNumTF.getText(), positionCB.getValue(), null, pwdPF.getText().trim());
        UserDb.insert(userData);
        close(event);
    }

    @FXML
    void cancelAction(ActionEvent event) {
        close(event);
    }

    @FXML
    void initialize() {
        positionCB.setItems(FXCollections.observableArrayList(Constant.positionList));
    }

    private void close(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
