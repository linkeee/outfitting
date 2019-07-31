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
            parameterData.setParam_accuracy(String.valueOf(vari.getAccuracyDigit()));
            parameterData.setParam_unit(vari.getVarUnit());
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

}
