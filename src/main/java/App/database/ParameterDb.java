package App.database;

import App.dataModel.ParameterData;

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
        List<ParameterData> list = new ArrayList<>();
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from parameter order by param_id");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ParameterData parameterData = new ParameterData();
                parameterData.setParam_id(String.valueOf(resultSet.getInt("param_id")));
                parameterData.setParam_name(resultSet.getString("param_name"));
                parameterData.setParam_type(String.valueOf(resultSet.getInt("param_type")));
                parameterData.setOutfitting_name(resultSet.getString("outfitting_name"));
                parameterData.setParam_description(resultSet.getString("param_description"));
                parameterData.setParam_scope(resultSet.getString("param_scope"));

                list.add(parameterData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return list;
    }

    /**
     * 以List形式返回参数表中的所有参数名。
     * @return
     */
    public static List<String> getParameterNameList() {
        List<String> list = new ArrayList<>();
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from parameter order by param_id");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("param_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
