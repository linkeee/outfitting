package App.formulalib;

import App.utile.FxmlUtile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewVarPane {
    private final ToggleGroup variableType = new ToggleGroup();
    @FXML
    private TextField tfStringNewVariable;
    @FXML
    private TextField tfStringNewDescription;
    @FXML
    private TextField tfStringNewDevice;
    @FXML
    private RadioButton rbBooleanVariableType1, rbBooleanVariableType2;
    @FXML
    private Button btCheckNewVariable;
    @FXML
    private Button btAddNewVariable;
    @FXML
    private Button btCancel;

    @FXML
    private void initialize() {
        rbBooleanVariableType1.setToggleGroup(variableType);
        rbBooleanVariableType2.setToggleGroup(variableType);
        //初始化使Type1被选中，保证两项必有一项被选中
        rbBooleanVariableType1.setSelected(true);
        rbBooleanVariableType1.requestFocus();

        btCheckNewVariable.setOnAction(event -> buttonActionCheckNewVariable());
        btAddNewVariable.setOnAction(event -> buttonActionAddNewVariable());
        btCancel.setOnAction(event -> buttonActionExitStage());
    }

    public void show() {
        try {
            FxmlUtile fxmlUtile = FxmlUtile.getInstance();
            FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/varpane.fxml");
            AnchorPane root = loader.load();

//            AnchorPane root = FXMLLoader.load(getClass().getResource("App/appView/varpane.fxml"));
            Scene newScene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setResizable(false);
            secondStage.setTitle("新增变量定义");
            //modality要使用Modality.APPLICATION_MODEL
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.initStyle(StageStyle.TRANSPARENT);
            secondStage.setScene(newScene);
            secondStage.showAndWait();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, "面板生成异常", e);
        }
    }

    /**
     * 检查按钮动作，检查输入内容合理性
     */
    private void buttonActionCheckNewVariable() {
        String varSting = Checker.removeSpace(tfStringNewVariable.getText());
        try {
            if (checkInput(varSting)) {
                AlertWindows alert = new AlertWindows("变量命名检查", "变量命名检查通过");
            }
        } catch (LogicalException e) {
            AlertWindows alert = new AlertWindows(e);
        }
    }

    /**
     * 添加变量按钮动作
     */
    private void buttonActionAddNewVariable() {
        try {
            String varString = Checker.removeSpace(tfStringNewVariable.getText());
            checkInput(varString);
            String varDescription = tfStringNewDescription.getText();
            String varDevice = tfStringNewDevice.getText();
            boolean varType = (variableType.getSelectedToggle() == rbBooleanVariableType2);
            StringBuilder confirmInfo = new StringBuilder("变量名：" + varString + "\n变量类型：");
            if (varType)
                confirmInfo.append("中间变量");
            else
                confirmInfo.append("人工赋值变量");
            confirmInfo.append("\n变量描述：" + varDescription + "\n设备说明：" + varDevice);
            if (AlertWindows.newConfirmWindows("确认添加变量", "确认添加新变量？", confirmInfo.toString())) {
                DataBase db = new DataBase();
                Vari result = db.addVariable(varString, varType, varDescription, varDevice);
                if (result == null) {
                    AlertWindows alert = new AlertWindows("添加变量", "数据库通讯错误，添加变量失败！");
                } else {
                    AlertWindows alert = new AlertWindows("添加变量", "添加变量成功！在数据库中的记录如下", result.toString());
                    clear();
                }
                db.close();
            } else return;
        } catch (LogicalException e) {
            AlertWindows alert = new AlertWindows(e);
        }

    }

    private void buttonActionExitStage() {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * 检查输入的变量命名，本方法返回结果只有true和异常两种
     *
     * @param varString String这里需要是处理过的变量String,但可为空
     * @return true
     * @throws LogicalException
     */
    private boolean checkInput(@NotNull String varString) throws LogicalException {
        if (varString.isEmpty())
            throw new LogicalException("变量名错误", "变量命名不能为空");
        if (Checker.isVarSpellCorrected(varString)) {
            DataBase db = new DataBase();
            if (db.isVarExist(varString)) {
                db.close();
                throw new LogicalException("变量名错误", "同名变量已存在！");
            } else {
                db.close();
                return true;
            }
        } else throw new LogicalException("变量名错误", "变量命名不符合要求，请检查");
    }

    private void clear() {
        tfStringNewVariable.clear();
        tfStringNewDescription.clear();
        tfStringNewDevice.clear();
        //初始化使Type1被选中，保证两项必有一项被选中

        rbBooleanVariableType1.setSelected(true);
        rbBooleanVariableType1.requestFocus();
    }
}
