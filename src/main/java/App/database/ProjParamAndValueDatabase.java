package App.database;

import App.appModel.ProjParamAndValueData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjParamAndValueDatabase extends DatabaseItem {

    public static ObservableList<ProjParamAndValueData> getProjParamAndValueList() {
        ObservableList<ProjParamAndValueData> list = FXCollections.observableArrayList();
        Connection connection = connectDB();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from projparamandvalue order by proj_id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProjParamAndValueData data = new ProjParamAndValueData();
                data.setProj_id(resultSet.getObject("proj_id"));
                data.setParam_name(resultSet.getString("param_name"));
                data.setParam_value(resultSet.getString("param_value"));
                data.setParam_description(resultSet.getString("param_description"));

                list.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取某一个项目的数据，并以ObservableList的形式返回。
     * @param project_id 项目编号。
     * @return 该项目的所有数据的ObservableList。
     */
    public static ObservableList<ProjParamAndValueData> getOneProjParamList(Object project_id) {
        ObservableList<ProjParamAndValueData> list = FXCollections.observableArrayList();
        Connection connection = connectDB();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from projparamandvalue where proj_id = ?");
            statement.setString(1, String.valueOf(project_id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProjParamAndValueData data = new ProjParamAndValueData();
                data.setProj_id(resultSet.getObject("proj_id"));
                data.setParam_name(resultSet.getString("param_name"));
                data.setParam_value(resultSet.getString("param_value"));
                data.setParam_description(resultSet.getString("param_description"));

                list.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
