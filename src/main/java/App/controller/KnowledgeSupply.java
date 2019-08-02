package App.controller;

import App.utile.FxmlUtile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class KnowledgeSupply {

    @FXML
    private VBox leftVbox;

    @FXML
    private Button recommendBtn;

    @FXML
    private Button supplyBtn;

    @FXML
    private Button backBtn;

    @FXML
    private BorderPane knowledgeSupplyBorderPane;

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();

    @FXML
    void recommendAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBRecommend.fxml");
        knowledgeSupplyBorderPane.setCenter(loader.load());
    }

    @FXML
    void supplyAction(ActionEvent event) {

    }

    @FXML
    void backAction(ActionEvent event) throws IOException {
        Home hc = new Home();
        hc.showHomeWindows();
        closeKnowledgeSupply(event);
    }

    @FXML
    void initialize() throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KBRecommend.fxml");
        knowledgeSupplyBorderPane.setCenter(loader.load());
        recommendBtn.setTooltip(new Tooltip("知识检索"));
        supplyBtn.setTooltip(new Tooltip("知识主动供应"));
        backBtn.setTooltip(new Tooltip("返回上一级"));
    }

    //显示知识供应界面
    void showKnowledgeSupply() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KnowledgeSupply.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Knowledge supply!");
        stage.getIcons().add(new Image("App/appView/images/used/javaIcon.png"));
        stage.show();
    }

    //关闭知识供应界面
    private void closeKnowledgeSupply(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
