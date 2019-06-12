package App.controller;

import App.Main;
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
        Main main = new Main();
        main.closeLoginWindows(event);
        Home hc = new Home();
        hc.showHomeWindows();
    }

}
