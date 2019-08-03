package App.formulalib;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 这里定义了包中开放的UI接口和数据库查询接口
 */

public class LibraryManager {

    /**
     * 公式库管理系统面板启动函数
     *
     * @throws IOException
     */
    public void formulaManager() {
        if (isDataBaseFileNull()) {
            AlertWindows alert = new AlertWindows("数据库文件丢失", "根目录下没有已建立的formulalib.db，将自动生成空的数据库");
            DataBase db = new DataBase();
            db.createTables();
            db.close();
            /*if (AlertWindows.newConfirmWindows("数据库文件丢失", "根目录下不存在formulalib.db", "根目录下没有已建立的formulalib.db，是否要新建空的数据库？\n选择取消则将直接退出系统")) {
                DataBase db = new DataBase();
                db.createTables();
                db.close();
            } else {
                return;
            }*/
        }
/*        Parent root = FXMLLoader.load(getClass().getResource("consoler.fxml"));
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
        primaryStage.show();*/
    }

    /**
     * 查询变量的定义的接口
     *
     * @param varStr
     * @return Vari 如果公式库中没有对应定义则返回null
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
     * 获取所有变量类型为“已知变量”的变量对象
     *
     * @return List<Vari>
     */
    public List<Vari> getAllKnownVariable() {
        DataBase db = new DataBase();
        List<Vari> allKnownVar = db.getAllKnownVariable();
        db.close();
        return allKnownVar;
    }

    /**
     * 返回所有参数
     *
     * @return
     */
    public List<Vari> getAllVariable() {
        DataBase db = new DataBase();
        List<Vari> allVar = db.getAllVariable();
        db.createTables();
        db.close();
        return allVar;
    }

    /**
     * 检查数据库文件是否存在
     * @return Boolean 存在与否
     */
    private boolean isDataBaseFileNull() {
        return !(new File(DataBase.getJarPath(this.getClass()) + "formulalib.db").exists());
    }
}
