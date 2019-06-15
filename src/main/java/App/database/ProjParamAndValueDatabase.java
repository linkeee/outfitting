package App.database;

import App.dataModel.ProjParamAndValueData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * private final SimpleStringProperty proj_param_value_id;
 *     private final SimpleStringProperty proj_id;
 *     private final SimpleStringProperty version;
 *     private final SimpleStringProperty param_id;
 *     private final SimpleStringProperty outfitting_name;
 *     private final SimpleStringProperty param_name;
 *     private final SimpleStringProperty param_type;
 *     private final SimpleStringProperty param_description;
 *     private final SimpleStringProperty param_value;
 *     private final SimpleStringProperty remark;
 */
public class ProjParamAndValueDatabase extends DatabaseItem {

    public static ObservableList<ProjParamAndValueData> getProjParamAndValueList() {
        ObservableList<ProjParamAndValueData> list = FXCollections.observableArrayList();
        Connection connection = connectDB();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from projparamandvalue order by proj_id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProjParamAndValueData data = new ProjParamAndValueData();
                data.setProj_id(resultSet.getString("proj_id"));
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
                data.setProj_id(resultSet.getString("proj_id"));
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

    public static List<String> getSingleVersionList() {
        List<String> list = new ArrayList<>();
        try {
            PreparedStatement ps = connectDB().prepareStatement("select * from projparamandvalue order by ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据项目id和版本返回参数结果。
     * @param proj_id 项目id
     * @param version 版本号
     * @return 参数list
     */
    public static List<ProjParamAndValueData> getParamByProjAndVersion(int proj_id, String version) {
        List<ProjParamAndValueData> list = new ArrayList<>();
        try {
            PreparedStatement ps = connectDB().prepareStatement("select * from projparamandvalue where proj_id = ? and version = ?");
            ps.setInt(1, proj_id);
            ps.setString(2, version);
            ResultSet rs = ps.executeQuery();

            ProjParamAndValueData projParamAndValueData = new ProjParamAndValueData();
            while (rs.next()) {
                projParamAndValueData.setProj_param_value_id(String.valueOf(rs.getInt("id")));
                projParamAndValueData.setProj_id(String.valueOf(rs.getInt("proj_id")));
                projParamAndValueData.setVersion(rs.getString("version"));
                projParamAndValueData.setParam_id(String.valueOf(rs.getInt("param_id")));
                projParamAndValueData.setOutfitting_name(rs.getString("outfitting_name"));
                projParamAndValueData.setParam_name(rs.getString("param_name"));
                projParamAndValueData.setParam_type(String.valueOf(rs.getInt("param_type")));
                projParamAndValueData.setParam_description(rs.getString("param_description"));
                projParamAndValueData.setParam_value(rs.getString("param_value"));
                projParamAndValueData.setRemark(rs.getString("remark"));

                list.add(projParamAndValueData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
