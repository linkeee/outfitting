package App.database;

import App.appModel.ParameterData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParameterDatabase extends DatabaseItem {

    /**
     * 获取参数表中的所有内容。
     *
     * @return
     */
    public static ObservableList<ParameterData> getParameterList() {
        ObservableList<ParameterData> parameterList = FXCollections.observableArrayList();
        Connection connection = connectDB();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from parameter order by param_id");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ParameterData parameterData = new ParameterData();
                parameterData.setParam_id(resultSet.getInt("param_id"));
                parameterData.setParam_name(resultSet.getString("param_name"));
                parameterData.setParam_type(resultSet.getBoolean("param_type"));
                parameterData.setParam_description(resultSet.getString("param_description"));

                parameterList.add(parameterData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parameterList;
    }

    /**
     * 以ObservableList形式返回参数表中的所有参数名。
     * @return
     */
    public static ObservableList<String> getParameterNameList() {
        ObservableList<String> parameterNameList = FXCollections.observableArrayList();
        Connection connection = connectDB();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from parameter order by param_id");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                parameterNameList.add(resultSet.getString("param_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parameterNameList;
    }
}
