package App.controller;

import App.dataModel.UserData;
import App.database.UserDb;
import App.utile.Constant;
import App.utile.FxmlUtile;
import App.utile.MyDialog;
import App.utile.ProgressFrom;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class UserManagement {

    Stage stage = new Stage();
    @FXML
    private TextField userQueryTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField jobNumTextField;
    @FXML
    private ComboBox<String> positionComboBox;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private TextField telTextField;
    @FXML
    private TableView<UserData> personTable;
    @FXML
    private TableColumn<UserData, String> nameColumn;
    @FXML
    private TableColumn<UserData, String> telColumn;
    @FXML
    private TableColumn<UserData, String> jobNumColumn;
    @FXML
    private TableColumn<UserData, String> positionColumn;
    @FXML
    private TableColumn<UserData, String> roleColumn;

    @FXML
    void initialize() {
        positionComboBox.setItems(FXCollections.observableArrayList(Constant.getPositionList()));
        roleComboBox.setItems(FXCollections.observableArrayList(Constant.roleList));

        jobNumColumn.setCellValueFactory(new PropertyValueFactory<>("jobNum"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        personTable.setItems(FXCollections.observableArrayList(UserDb.getAllUser()));
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showUserDetail(newValue));
    }

    @FXML
    private void handleUserQuery() throws SQLException {
        String keyword = userQueryTextField.getText();
        personTable.setItems(UserDb.query(keyword));
    }

    @FXML
    private void handleResetPerson() {
        refreshPersonTable();
    }

    @FXML
    private void handleDeletePerson(ActionEvent event) throws IOException {
        UserData deletedUserData = personTable.getSelectionModel().getSelectedItem();
        String deletedUserJobNum = deletedUserData.getJobNum();
        Optional<ButtonType> result = MyDialog.confirmation(null, "确认删除" + deletedUserData.getName() + "吗？");
        if (result.get() == ButtonType.OK) {
            if (UserDb.getSuperAdminNum() == 1 && deletedUserData.getRole().equals("超级管理员")) {
                MyDialog.warning("您正在删除系统中唯一的超级管理员账号", "系统必须保证至少有一个超级管理员账号，因此该操作不能进行");
                return;
            }
            UserDb.delete(deletedUserJobNum);
            refreshPersonTable();
        }
    }

    @FXML
    private void showAddNewPerson(ActionEvent event) throws IOException {
        UserData tempUserData = new UserData();
        AddEditPerson addEditPerson = new AddEditPerson();
        addEditPerson.showAddEditPerson(tempUserData);
        refreshPersonTable();
    }

    @FXML
    private void handleEditPerson(ActionEvent event) throws IOException {
        UserData editUserData = personTable.getSelectionModel().getSelectedItem();
        AddEditPerson addEditPerson = new AddEditPerson();
        addEditPerson.showAddEditPerson(editUserData);
        refreshPersonTable();
    }

    @FXML
    private void backHome(ActionEvent event) throws IOException {
        Home hc = new Home();
        hc.showHomeWindows();
        closeUserManagement(event);
    }

    private void showUserDetail(UserData userData) {
        if (userData != null) {
            jobNumTextField.setText(userData.getJobNum());
            userNameTextField.setText(userData.getName());
            roleComboBox.setValue(userData.getRole());
            positionComboBox.setValue(userData.getPosition());
            telTextField.setText(userData.getTel());
        } else {
            jobNumTextField.setText("");
            userNameTextField.setText("");
            roleComboBox.setValue("");
            positionComboBox.setValue("");
            telTextField.setText("");
        }
    }

    //刷新personTable的显示
    private void refreshPersonTable() {
        personTable.setItems(FXCollections.observableArrayList(UserDb.getAllUser()));
    }

    //显示用户管理界面
    UserManagement showUserManagement() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/UserManagement.fxml");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                UserManagement userManagement = loader.getController();
                userManagement.refreshPersonTable();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "数据加载中，请稍后...");
        progressFrom.activateProgressBar();

        stage.setTitle("User management!");
        stage.getIcons().add(new Image("App/appView/images/used/javaIcon.png"));
        stage.show();
        return loader.getController();
    }

    //关闭用户管理界面
    private void closeUserManagement(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
