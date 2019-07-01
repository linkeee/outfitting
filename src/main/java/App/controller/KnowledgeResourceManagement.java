package App.controller;

import java.io.IOException;

import App.utile.FxmlUtile;
import App.utile.ProgressFrom;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class KnowledgeResourceManagement {

    @FXML
    private BorderPane knowledgeResourceBorderPane;

    @FXML
    private Button KBButton;

    @FXML
    private Button statisticButton;

    @FXML
    void goKbManagement(ActionEvent event) throws IOException {
        loadKbManagement();
    }

    @FXML
    void goStatistic(ActionEvent event) {
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
    }

    static Stage stage = new Stage();

    //显示企业知识资源管理界面
    void showKnowledgeResourceManagement() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KnowledgeResourceManagement.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Enterprise knowledge resource management!");
        stage.getIcons().add(new Image("App/appView/images/used/java图标.png"));
        stage.show();
    }

    //关闭企业知识资源管理界面
    private void closeKnowledgeResourceManagement(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    //用borderpane加载知识库管理界面
    private void loadKbManagement() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KnowledgeBaseManage.fxml");
        knowledgeResourceBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                KnowledgeBaseManage controller = loader.getController();
                controller.refreshAllTable();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "数据加载中，请稍后...");
        progressFrom.activateProgressBar();
    }
}
