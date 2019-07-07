package App.controller;

import App.Main;
import App.database.UserDb;
import App.utile.MyDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginWindows {

    @FXML
    private CheckBox rememberPwd;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField userName;

    @FXML
    private Button signInButton;

    @FXML
    void goHome(ActionEvent event) throws IOException {

        if (userName.getText() == null || password.getText() == null || userName.getText().equals("") || password.getText().equals("")) {
            MyDialog.information("登录失败", "用户名或密码未输入");
            return;
        }

        String userNameText = userName.getText();
        String userPassword = password.getText();

        if (!UserDb.getUserNameList().contains(userNameText)) {
            MyDialog.information("该用户不存在", "请先注册");
            return;
        }

        if (!userPassword.matches("^[A-Za-z0-9]+$")) {
            MyDialog.error("密码格式错误", "密码只能包含字母和数字");
            return;
        }

        if (!UserDb.getPasswordOfUser(userNameText).equals(userPassword)) {
            MyDialog.error("登录失败", "密码输入有误");
            return;
        }

        Main main = new Main();
        main.closeLoginWindows(event);
        Home hc = new Home();
        hc.showHomeWindows();
    }

}
