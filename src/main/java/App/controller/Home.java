package App.controller;

import App.Main;
import App.database.DBTableCreate;
import App.utile.Docker;
import App.utile.FxmlUtile;
import App.utile.MyDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {

    @FXML
    private Button button1, button2, button3, button4;

    @FXML
    void goKnowledgeManagement(ActionEvent event) throws IOException {
        KnowledgeResourceManagement knowledgeResourceManagement = new KnowledgeResourceManagement();
        knowledgeResourceManagement.showKnowledgeResourceManagement();
        closeHomeWindows(event);
    }

    @FXML
    void goOutfittingDesign(ActionEvent event) throws IOException {
        OutfittingDesign outfittingDesign = new OutfittingDesign();
        outfittingDesign.showOutfittingDesign();
        closeHomeWindows(event);
    }

    @FXML
    void goKnowledgeSupply(ActionEvent event) throws IOException {
        KnowledgeSupply knowledgeSupply = new KnowledgeSupply();
        knowledgeSupply.showKnowledgeSupply();
        closeHomeWindows(event);
    }

    @FXML
    void goUserManagement(ActionEvent event) throws IOException {
        UserManagement userManagement = new UserManagement();
        userManagement.showUserManagement();
        closeHomeWindows(event);
    }

    @FXML
    void backLogin(ActionEvent event) throws IOException {
        Main main = new Main();
        main.showLoginWindows();
        closeHomeWindows(event);
    }

    @FXML
    void initialize() {
        switch (Docker.get("userRole").toString()) {
            case "管理员":
                button1.setDisable(false);
                button2.setDisable(false);
                button3.setDisable(false);
                button4.setDisable(true);
                break;
            case "用户":
                button1.setDisable(true);
                button2.setDisable(false);
                button3.setDisable(false);
                button4.setDisable(true);
                break;
        }
    }

    static Stage stage = new Stage();

    //显示Home界面
    void showHomeWindows() throws IOException{
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/Home.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.getIcons().add(new Image("App/appView/images/used/java图标.png"));
        stage.show();
    }

    //关闭Home界面
    private void closeHomeWindows(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
