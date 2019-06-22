package App.controller;

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

import java.io.IOException;

public class OutfittingDesign {

    @FXML
    private BorderPane outfittingDesignBorderPane;

    @FXML
    private Button outfittingItemSelectionButton;

    @FXML
    private Button layoutDesignButton;

    @FXML
    private Button designReviewButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button backHomeBtn;

    @FXML
    void initialize() throws IOException {
        loadSelectType();
        setStyle(outfittingItemSelectionButton, layoutDesignButton, designReviewButton, exportButton);
    }

    private void setStyle(Button btn, Button... buttons) {
        btn.setStyle("-fx-background-size: 25px; -fx-background-color: #646464");
        for (Button button : buttons) button.setStyle("-fx-background-size: 20px; -fx-background-color: #464646");
    }

    @FXML
    void goOutfittingItemSelection(ActionEvent event) throws IOException {
        loadSelectType();
        setStyle(outfittingItemSelectionButton, layoutDesignButton, designReviewButton, exportButton);
    }

    @FXML
    void goLayoutDesign(ActionEvent event) throws IOException {
        loadLayoutDesign();
        setStyle(layoutDesignButton, outfittingItemSelectionButton, designReviewButton, exportButton);
    }

    @FXML
    void goDesignReview(ActionEvent event) throws IOException {
        loadDesignReview();
        setStyle(designReviewButton, outfittingItemSelectionButton, layoutDesignButton, exportButton);
    }

    @FXML
    void goExport(ActionEvent event) throws IOException {
        loadExport();
        setStyle(exportButton, outfittingItemSelectionButton, layoutDesignButton, designReviewButton);
    }

    @FXML
    void backHome(ActionEvent event) throws IOException {
        Home hc = new Home();
        hc.showHomeWindows();
        closeOutfittingDesign(event);
    }

    //显示舾装智能设计界面
    void showOutfittingDesign() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/OutfittingDesign.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("舾装设计");
        stage.getIcons().add(new Image("App/appView/images/used/java图标.png"));
        stage.show();
    }

    //关闭舾装智能设计界面
    private void closeOutfittingDesign(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    //用borderpane加载选型界面
    private void loadSelectType() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/SelectTypeDesign.fxml");
        outfittingDesignBorderPane.setCenter(loader.load());
    }

    //用borderpane加载布局设计界面
    private void loadLayoutDesign() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/LayoutDesignSearch.fxml");
        outfittingDesignBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                OutfittingDesignLayoutDesignSearch controller = loader.getController();
                controller.refreshLayoutDesignTable();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "数据加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    //用borderpane加载设计校审界面
    private void loadDesignReview() {

    }

    //用borderpane加载导出界面
    private void loadExport() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/Export.fxml");
        outfittingDesignBorderPane.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Export controller = loader.getController();
                controller.refreshProjectTable();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "数据加载中，请稍后...");
        progressFrom.activateProgressBar();
    }
}
