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

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();

    @FXML
    void initialize() throws IOException {
        outfittingItemSelectionButton.setTooltip(new Tooltip("舾装件智能选型"));
        layoutDesignButton.setTooltip(new Tooltip("智能布局设计"));
        designReviewButton.setTooltip(new Tooltip("设计校审"));
        exportButton.setTooltip(new Tooltip("导出文件"));
        backHomeBtn.setTooltip(new Tooltip("返回上一级"));

        loadSelectType();
        setStyle(outfittingItemSelectionButton, layoutDesignButton, designReviewButton, exportButton);
    }

    private void setStyle(Button btn, Button... buttons) {
        btn.setStyle("-fx-background-size: 25px; -fx-background-color: rgb(16, 110, 190)");
        for (Button button : buttons) button.setStyle("-fx-background-size: 20px; -fx-background-color: rgb(0, 95, 190)");
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
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/OutfittingDesign.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("舾装设计");
        stage.getIcons().add(new Image("App/appView/images/used/javaIcon.png"));
        stage.show();
    }

    //关闭舾装智能设计界面
    private void closeOutfittingDesign(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    //用borderpane加载选型界面
    private void loadSelectType() throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/SelectTypeDesign.fxml");
        outfittingDesignBorderPane.setCenter(loader.load());
    }

    //用borderpane加载布局设计界面
    private void loadLayoutDesign() throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/LayoutDesign.fxml");
        outfittingDesignBorderPane.setCenter(loader.load());
    }

    //用borderpane加载设计校审界面
    private void loadDesignReview() {

    }

    //用borderpane加载导出界面
    private void loadExport() throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/Export.fxml");
        outfittingDesignBorderPane.setCenter(loader.load());
    }
}
