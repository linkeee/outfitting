package App.database;

import App.dataModel.VersionData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VersionDatabase extends DatabaseItem {

    /**
     * 根据项目id返回该项目的所有版本。
     *
     * @param proj_id 项目id
     * @return 所有版本list
     */
    public static List<VersionData> getVersionDataListOfProj(int proj_id) {
        List<VersionData> list = new ArrayList<>();
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from version where proj_id = ?");
            ps.setInt(1, proj_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                VersionData versionData = new VersionData();

                versionData.setVersion_id(String.valueOf(rs.getInt("id")));
                versionData.setProj_id(String.valueOf(rs.getInt("proj_id")));
                versionData.setVersion_name(rs.getString("version_name"));
                versionData.setVersion_description(rs.getString("version_description"));
                versionData.setParam_value_list(ParamAndValueDatabase.getParamByProjAndVersion(Integer.valueOf(versionData.getProj_id()), versionData.getVersion_name()));
                list.add(versionData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return list;
    }

    /**
     * 根据项目id返回该项目的所有版本名称。
     *
     * @param proj_id 项目id
     * @return 所有版本名称list
     */
    public static List<String> getVersionNameListOfProj(int proj_id) {
        List<String> list = new ArrayList<>();
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from version where proj_id = ?");
            ps.setInt(1, proj_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("version_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }

        return list;
    }

    /**
     * 返回项目某版本的说明
     *
     * @param proj_id     项目id
     * @param versionName 版本名称
     * @return 版本说明
     */
    public static VersionData getOneVersionData(int proj_id, String versionName) {
        VersionData versionData = new VersionData();
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from version where proj_id = ? and version_name = ?");
            ps.setInt(1, proj_id);
            ps.setString(2, versionName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                versionData.setVersion_id(String.valueOf(rs.getInt("id")));
                versionData.setProj_id(String.valueOf(rs.getInt("proj_id")));
                versionData.setVersion_name(rs.getString("version_name"));
                versionData.setVersion_description(rs.getString("version_description"));
                versionData.setParam_value_list(ParamAndValueDatabase.getParamByProjAndVersion(Integer.valueOf(versionData.getProj_id()), versionData.getVersion_name()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return versionData;
    }

}
