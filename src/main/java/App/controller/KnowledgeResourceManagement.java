package App.controller;

import java.io.IOException;

import App.utile.FxmlUtile;
import App.utile.ProgressFrom;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class KnowledgeResourceManagement {

    @FXML
    private BorderPane knowledgeResourceBorderPane;

    @FXML
    private Button KBBtn;

    @FXML
    private Button DBBtn;

    @FXML
    private Button backHomeBtn;

    @FXML
    void goKBMana(ActionEvent event) throws IOException {
        loadKbManagement();
    }

    @FXML
    void goDBMana(ActionEvent event) throws IOException {
        loadDBMana();
    }

    @FXML
    void backHome(ActionEvent event) throws IOException {
        Home hc = new Home();
        hc.showHomeWindows();
        closeKnowledgeResourceManagement(event);
    }

    @FXML
    void initialize() throws IOException {
        loadKbManagement();
        KBBtn.setTooltip(new Tooltip("知识库管理"));
        DBBtn.setTooltip(new Tooltip("数据库管理"));
        backHomeBtn.setTooltip(new Tooltip("返回上一级"));
    }

    static Stage stage = new Stage();

    //显示企业知识资源管理界面
    void showKnowledgeResourceManagement() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KnowledgeResourceManagement.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("企业数据与知识管理");
        stage.getIcons().add(new Image("App/appView/images/used/java图标.png"));
        stage.show();
    }

    //关闭企业知识资源管理界面
    private void closeKnowledgeResourceManagement(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();

    //用borderpane加载知识库管理界面
    private void loadKbManagement() throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KnowledgeBaseMana.fxml");
        knowledgeResourceBorderPane.setCenter(loader.load());
    }

    private void loadDBMana() throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/DatabaseMana.fxml");
        knowledgeResourceBorderPane.setCenter(loader.load());
    }
}
