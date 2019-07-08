package App.database;

import App.dataModel.ParameterData;
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
                parameterData.setParam_type(resultSet.getInt("param_type") == 1 ? "待求" : "已知");
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
     *
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

    public static List<String> getOutfittingItems() {
        List<String> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection connection = connectDB();

        String sql = "select distinct outfitting_name from parameter";
        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("outfitting_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return list;
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
