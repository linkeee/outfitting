package App.controller;

import App.Main;
import App.utile.FxmlUtile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {

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
    void goTemp(ActionEvent event) throws IOException {
        Temp temp = new Temp();
        temp.showTemp();
        closeHomeWindows(event);
    }

    @FXML
    void initialize() {
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
        stage.getIcons().add(new Image("App/appView/images/java图标.png"));
        stage.show();
    }

    //关闭Home界面
    private void closeHomeWindows(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
