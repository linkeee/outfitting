package App.controller;

import App.dataModel.ParamAndValueData;
import App.database.ParamValueDb;
import App.database.ProjectDb;
import App.utile.Docker;
import App.utile.FxmlUtile;
import App.utile.MyDialog;
import App.utile.ProgressFrom;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;

public class Calculate {
    @FXML
    private Label versionLabel;

    @FXML
    private TableColumn<?, ?> outfittingNameTC;

    @FXML
    private TableColumn<?, ?> paramDescriptionTC;

    @FXML
    private TableView<ParamAndValueData> projParamValueTV;

    @FXML
    private AnchorPane calculateAnchorPane;

    @FXML
    private Button calculateBtn;

    @FXML
    private TableColumn<?, ?> paramValueTC;

    @FXML
    private Button calSaveBtn;

    @FXML
    private Label projLabel;

    @FXML
    private TableColumn<?, ?> paramNameTC;

    @FXML
    private TableColumn<?, ?> paramTypeTC;

    @FXML
    private Button calNextBtn;

    @FXML
    private Label calculateLabel;

    @FXML
    void calculateAction(ActionEvent event) {
        //todo 循环调用计算模块计算待求参数结果

        calculateLabel.setText("请点击保存按钮以保存计算结果" + "\r\n" + "项目: " + projLabel.getText() + "   版本: " + versionLabel.getText());
        calculateLabel.setStyle("-fx-text-fill: #ff0000");
    }

    @FXML
    void calSaveAction(ActionEvent event) {
        if (projLabel.getText() == null || versionLabel.getText() == null) {
            MyDialog.information("没有数据可以保存", "请选择项目和版本。");
        } else {
            List<ParamAndValueData> list = projParamValueTV.getItems();
            ParamValueDb.insertValue(list);
            calculateLabel.setText("计算结果已保存" + "\r\n" + "项目: " + projLabel.getText() + "   版本: " + versionLabel.getText());
            calculateLabel.setStyle("-fx-text-fill: #00c800");
            MyDialog.information("计算结果已保存", "项目: " + projLabel.getText() + "\r\n" + "版本: " + versionLabel.getText());
        }
    }

    @FXML
    void calNextAction(ActionEvent event) throws IOException {
        if (projLabel.getText() == null || projLabel.getText().equals("") || versionLabel.getText() == null || versionLabel.getText().equals("")) {
            MyDialog.information("未选择相应的项目和版本", "请在“输入参数”中选择相应的项目和版本");
            return;
        }

        calSaveAction(event);
        FxmlUtile.setStyle((Button) Docker.get("correctBtn"), (Button) Docker.get("createProjBtn"), (Button) Docker.get("inputParamBtn"), (Button) Docker.get("calculateBtn"), (Button) Docker.get("selectTypeBtn"));

        Docker.put("isCalculateNextStep", true);
        Docker.put("selectedProj", projLabel.getText());
        Docker.put("selectedVersion", versionLabel.getText());

        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/ModifyResult.fxml");
        BorderPane bp = (BorderPane) Docker.get("selectTypeBorderPane");
        bp.setCenter(loader.load());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                loader.getController();
                return null;
            }
        };
        ProgressFrom progressFrom = new ProgressFrom(task, "加载中，请稍后...");
        progressFrom.activateProgressBar();
    }

    @FXML
    void initialize() {
        if (Docker.containKey("isInputParamNextStep") && (boolean) Docker.get("isInputParamNextStep")) {
            projLabel.setText(String.valueOf(Docker.get("selectedProj")));
            versionLabel.setText(String.valueOf(Docker.get("selectedVersion")));
            projParamValueTV.setItems(FXCollections.observableArrayList(
                    ParamValueDb.getParamOfType(ProjectDb.getIdByName(projLabel.getText()), versionLabel.getText(), 1)));
            calculateBtn.setDisable(false); calSaveBtn.setDisable(false); calNextBtn.setDisable(false);
        }

        if (projLabel.getText().equals("") || projLabel.getText() == null) {
            calculateBtn.setDisable(true); calSaveBtn.setDisable(true); calNextBtn.setDisable(true);
            calculateLabel.setText("在“输入参数”模块为所有已知参数赋值后才能进行计算");
            calculateLabel.setStyle("-fx-text-fill: red");
        }

        outfittingNameTC.setCellValueFactory(new PropertyValueFactory<>("outfitting_name"));
        paramNameTC.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        paramTypeTC.setCellValueFactory(new PropertyValueFactory<>("param_type"));
        paramDescriptionTC.setCellValueFactory(new PropertyValueFactory<>("param_description"));
        paramValueTC.setCellValueFactory(new PropertyValueFactory<>("param_value"));

        Docker.set("isInputParamNextStep", false);
    }
}
