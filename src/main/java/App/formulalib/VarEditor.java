package App.formulalib;

import App.utile.FxmlUtile;
import App.utile.MyDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VarEditor {
    @FXML
    private TextField tfStringVariableID;
    @FXML
    private TextField tfStringVariable;
    @FXML
    private TextField tfStringVariableType;
    @FXML
    private TextField tfStringOldDescription;
    @FXML
    private TextField tfStringNewDescription;
    @FXML
    private TextField tfStringOldDevice;
    @FXML
    private TextField tfStringNewDevice;
    @FXML
    private TextField lowerTF, upperTF;
    @FXML
    private Button btSubmit;
    @FXML
    private Button btCancel;


    @FXML
    private void initialize() {
        isSubmit = false;
        tfStringVariableID.setText(Integer.toString(inVar.getVariableID()));
        tfStringVariable.setText(inVar.getVarString());
        tfStringVariableType.setText(getVarTypeString(inVar.getIsCalculated()));
        tfStringOldDescription.setText(inVar.getVariableDescription());
        tfStringOldDevice.setText(inVar.getVarDevice());
        lowerTF.setText(inVar.getVarScope().split(",")[0].trim());
        upperTF.setText(inVar.getVarScope().split(",")[1].trim());
        btSubmit.setOnAction(event -> buttonActionExitStage(true));
        btCancel.setOnAction(event -> buttonActionExitStage(false));
    }

    /**
     * 三个静态缓存记得控制
     */
    private static boolean isSubmit;
    private static Vari cacheVari;
    private static Vari inVar;


    /**
     * 显示修改变量定义的面板
     * @param inVar 待修改的变量
     * @return 修改后的Vari，当取消按钮关闭窗口时，返回可能为null请注意处理
     */
    Vari show(Vari inVar) {
        try {
            cacheVari = null;
            isSubmit = false;
            VarEditor.inVar = inVar;

            FxmlUtile fxmlUtile = new FxmlUtile();
            FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/vareditor.fxml");

            AnchorPane root = loader.load();
            Scene newScene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setResizable(false);
            secondStage.setTitle("修改变量定义");
            //modality要使用Modality.APPLICATION_MODEL
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.initStyle(StageStyle.TRANSPARENT);
            secondStage.setScene(newScene);
            secondStage.showAndWait();
            if (isSubmit) {
                return cacheVari;
            } else {
                return null;
            }
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, "面板生成异常", e);
        }
        return null;
    }

    /**
     * 关闭窗口的方法，已带有确认窗口
     *
     * @param isSubmit 窗口内容的提交状态
     */
    private void buttonActionExitStage(boolean isSubmit) {
        if (isSubmit) {
            cacheVari = editedVar(inVar);
            if (AlertWindows.newConfirmWindows("修改变量定义", "确认修改该变量的定义？","旧定义："+inVar.toString()+"\n新定义："+cacheVari.toString())) {
                VarEditor.isSubmit = true;
                inVar = null;
            } else
                return;
        }
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }

    private String getVarTypeString(boolean varType) {
        if (varType) {
            return "待求变量";
        } else
            return "已知变量";
    }

    private Vari editedVar(@NotNull Vari oldVar) {
        String newDescription = tfStringNewDescription.getText();
        String newDevice = tfStringNewDevice.getText();

        if (!lowerTF.getText().equals("")) {
            if (!lowerTF.getText().matches("^[0-9]+\\.{0,1}[0-9]{0,50}$")) {
                MyDialog.error("参数范围下限输入非法", "只能包含数字和小数点");
                return null;
            }
        }

        if (!upperTF.getText().equals("")) {
            if (!upperTF.getText().matches("^[0-9]+\\.{0,1}[0-9]{0,50}$")) {
                MyDialog.error("参数范围上限输入非法", "只能包含数字和小数点");
                return null;
            }
        }

        if (!lowerTF.getText().equals("") && !upperTF.getText().equals("")) {
            if (Double.valueOf(lowerTF.getText()) > Double.valueOf(upperTF.getText())) {
                MyDialog.error("参数范围输入非法", "下限大于上限");
                return null;
            }
        }

        return new Vari(oldVar.getVariableID(), oldVar.getVarString(), oldVar.getIsCalculated(), newDescription, newDevice, lowerTF.getText().trim() + ", " + upperTF.getText().trim());
    }
}
