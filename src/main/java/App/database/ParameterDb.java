package App.database;

import App.dataModel.ParameterData;
import App.formulalib.DataBase;
import App.formulalib.Vari;
import App.utile.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParameterDb extends DatabaseItem {


    public static List<ParameterData> getInputParamList() {
        DataBase db = new DataBase();
        List<Vari> knownVari = db.getAllKnownVariable();
        List<ParameterData> ret = new ArrayList<>();
        for (Vari vari : knownVari) {
            if (vari.getVarType().equals("已知")) {
                ParameterData parameterData = new ParameterData();
                parameterData.setParam_id(String.valueOf(vari.getVariableID()));
                parameterData.setParam_name(vari.getVarString());
                parameterData.setOutfitting_name(vari.getVarDevice());
                parameterData.setParam_description(vari.getVariableDescription());
                parameterData.setParam_scope(vari.getVarScope());
                parameterData.setParam_type(vari.getVarType());
                ret.add(parameterData);
            }
        }
        db.close();
        return ret;
    }

    /**
     * 获取参数表中的所有内容。
     *
     * @return
     */
    public static List<ParameterData> getParameterList() {
        DataBase db = new DataBase();
        List<Vari> allVar = db.getAllVariable();
        List<ParameterData> ret = new ArrayList<>();
        for (Vari vari : allVar) {
            ParameterData parameterData = new ParameterData();
            parameterData.setParam_id(String.valueOf(vari.getVariableID()));
            parameterData.setParam_name(vari.getVarString());
            parameterData.setOutfitting_name(vari.getVarDevice());
            parameterData.setParam_description(vari.getVariableDescription());
            parameterData.setParam_scope(vari.getVarScope().matches("(.*?)\\d+(.*?)") ? vari.getVarScope() : "");
            parameterData.setParam_type(vari.getVarType());
            ret.add(parameterData);
        }
        db.close();
        return ret;
    }

    /**
     * 以List形式返回参数表中的所有参数名。
     *
     * @return
     */
    public static List<String> getParameterNameList() {
        List<String> ret = new ArrayList<>();
        DataBase db = new DataBase();
        for (Vari vari : db.getAllVariable()) {
            ret.add(vari.getVarString());
        }
        db.close();
        return ret;
    }

    public static List<String> getOutfittingName() {
        List<String> ret = new ArrayList<>();
        DataBase db = new DataBase();
        for (Vari vari : db.getAllVariable()) {
            ret.add(vari.getVarDevice());
        }
        db.close();
        return ret;
    }

    public static Boolean update(ParameterData parameterData, String selectedParamId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update " + Constant.parameterTableName + " set outfitting_name=?, param_name=?, param_type=?, param_description=?, param_scope=? where param_id=?";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, parameterData.getOutfitting_name());
            preparedStatement.setString(2, parameterData.getParam_name());
            preparedStatement.setInt(3, parameterData.getParam_type().equals("已知") ? 0 : 1);
            preparedStatement.setString(4, parameterData.getParam_description());
            preparedStatement.setString(5, parameterData.getParam_scope());
            preparedStatement.setInt(6, Integer.valueOf(selectedParamId));

            int i = preparedStatement.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        if (flag) System.out.println("操作成功！");
        return flag;
    }

    public static Boolean insert(ParameterData parameterData) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "insert into " + Constant.parameterTableName + " (outfitting_name, param_name, param_type, param_scope, param_description) value (?, ?, ?, ?, ?)";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, parameterData.getOutfitting_name());
            preparedStatement.setString(2, parameterData.getParam_name());
            preparedStatement.setInt(3, parameterData.getParam_type().equals("已知") ? 0 : 1);
            preparedStatement.setString(4, parameterData.getParam_scope());
            preparedStatement.setString(5, parameterData.getParam_description());

            int i = preparedStatement.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        if (flag) System.out.println("操作成功！");
        return flag;
    }
}
