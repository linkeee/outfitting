package App.controller;

import App.utile.FxmlUtile;
import App.utile.ProgressFrom;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class KnowledgeSupply {

    @FXML
    private BorderPane knowledgeSupplyBorderPane;

    @FXML
    void goKnowledgeSearch(ActionEvent event) throws IOException {
        loadKnowledgeSupplySearch();
    }

    @FXML
    void goKnowledgeActiveSupply(ActionEvent event) {

    }

    @FXML
    void backHome(ActionEvent event) throws IOException {
        Home hc = new Home();
        hc.showHomeWindows();
        closeKnowledgeSupply(event);
    }

    @FXML
    void initialize() throws IOException {
        loadKnowledgeSupplySearch();
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
        stage.getIcons().add(new Image("App/appView/images/java图标.png"));
        stage.show();
    }

    //关闭知识供应界面
    private void closeKnowledgeSupply(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    //用borderpane加载知识检索界面
    private void loadKnowledgeSupplySearch() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/KnowledgeSupplySearch.fxml");
        knowledgeSupplyBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                KnowledgeSupplySearch controller = loader.getController();
                controller.refreshAllTable();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "数据加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

}
