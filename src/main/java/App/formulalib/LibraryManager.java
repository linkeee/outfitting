package App.formulalib;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * 这里定义了包中所有的开放接口
 */

public class LibraryManager {

    /**
     * 公式库管理系统面板启动函数
     * @throws IOException
     */
    public void formulaManager() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../resources/App/appView/consoler.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("公式库管理系统");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            if (AlertWindows.newConfirmWindows("确认退出", "确认退出公式库管理系统？", "退出前请确保已对改动应用检查")) {
                primaryStage.close();
            } else
                event.consume();
        });
        primaryStage.show();
    }

    /**
     * 查询变量的定义的接口
     *
     * @param varStr
     * @return Vari
     * @throws LogicalException
     */
    public Vari getVar(@NotNull String varStr) throws LogicalException {
        DataBase db = new DataBase();
        Vari var = db.getVar(varStr);
        db.close();
        return var;
    }

    /**
     * 查询变量的定义的接口
     *
     * @param varID
     * @return Vari
     * @throws LogicalException
     */
    public Vari getVar(int varID) throws LogicalException {
        DataBase db = new DataBase();
        Vari var = db.getVar(varID);
        db.close();
        return var;
    }

    /**
     * 计算入口
     *
     * @param varStr String
     * @return double
     * @throws LogicalException
     */
    public double varToValue(@NotNull String varStr) throws LogicalException {
        Interpreter interpreter = new Interpreter();
        return interpreter.varToValue(varStr);
    }

    /**
     * 计算入口
     *
     * @param var Vari
     * @return double
     * @throws LogicalException
     */
    public double varToValue(@NotNull Vari var) throws LogicalException {
        return varToValue(var.getVarString());
    }

    /**
     *  需要项目库实现的函数
     * @param var
     * @return
     */
    static double getValue(@NotNull Vari var) {
        if (var.getIsCalculated()) {
            throw new StringIndexOutOfBoundsException("非人工赋值变量，无法查询");
        } else {
            switch (var.getVariableID()) {
                case 7:
                    return 45;
                case 8:
                    return 3;
                case 9:
                    return 2.92;
                default:
                    throw new IllegalArgumentException("没有这样的变量ID");
            }
        }
    }
}
