package App.controller;

import App.dataModel.ParamAndValueData;
import App.dataModel.ParameterData;
import App.database.ParamValueDb;
import App.database.ParameterDb;
import App.database.ProjectDb;
import App.formulalib.DataBase;
import App.formulalib.Equation;
import App.formulalib.LogicalException;
import App.formulalib.Vari;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculate {
    @FXML
    private TableView<Equation> equationTV;

    @FXML
    private TableColumn<?, ?> equaParamNameTC, equaEquaTC, equaDescTC;

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
        List<ParameterData> paramlist = ParameterDb.getParameterList();
        Map<String, Integer> paramaccu = new HashMap<>();
        for (ParameterData p : paramlist) {
            paramaccu.put(p.getParam_id(), Integer.valueOf(p.getParam_accuracy()));
        }

        App.formulalib.Calculate cal = new App.formulalib.Calculate(ParamValueDb.getParamOfType(ProjectDb.getIdByName(projLabel.getText()), versionLabel.getText(), 0));
        List<ParamAndValueData> list = projParamValueTV.getItems();
        for (ParamAndValueData p : list) {
            try {
                double d = cal.varToValue(p.getParam_name());
                BigDecimal b = new BigDecimal(d);
                d = b.setScale(paramaccu.get(p.getParam_id()), BigDecimal.ROUND_HALF_UP).doubleValue();
                p.setParam_value(String.valueOf(d));
            } catch (LogicalException e) {
                MyDialog.error("错误", e.toString());
                e.printStackTrace();
            }
        }
        projParamValueTV.setItems(FXCollections.observableArrayList(list));
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

        equaParamNameTC.setCellValueFactory(new PropertyValueFactory<>("equationVarStr"));
        equaEquaTC.setCellValueFactory(new PropertyValueFactory<>("equationRight"));
        equaDescTC.setCellValueFactory(new PropertyValueFactory<>("description"));
        DataBase db = new DataBase();
        List<Equation> list = new ArrayList<>();
        for (Vari vari : db.getAllVariable()) {
            try {
                list.addAll(db.getFormulaList(vari));
            } catch (LogicalException e) {
                e.printStackTrace();
            }
        }
        db.close();
        equationTV.setItems(FXCollections.observableArrayList(list));

        Docker.set("isInputParamNextStep", false);
    }
}
