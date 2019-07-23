package App.database;

import App.dataModel.VersionData;
import App.utile.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VersionDb extends DatabaseItem {

    public static boolean insert(VersionData versionData) {
        boolean flag = false;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("insert into " + Constant.versionTableName + " (proj_id, version_name, version_description) value (?, ?, ?)");
            ps.setInt(1, Integer.valueOf(versionData.getProj_id()));
            ps.setString(2, versionData.getVersion_name());
            ps.setString(3, versionData.getVersion_description());
            int i = ps.executeUpdate();
            if (i == 1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return flag;
    }

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
                versionData.setParam_value_list(ParamValueDb.getParamByProjAndVersion(Integer.valueOf(versionData.getProj_id()), versionData.getVersion_name()));
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

            list.add(null);

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

    public static String getVersionNameNeedToAdd(int proj_id) {
        List<String> list = getVersionNameListOfProj(proj_id);
        list.remove(0); // 去除第一个null
        int max = 0;
        for (String s : list) {
            max = Integer.valueOf(s.substring(1)) > max ? Integer.valueOf(s.substring(1)) : max;
        }
        return "v" + (max + 1);
    }

    public static String getLargestVersionName(int proj_id) {
        List<String> list = getVersionNameListOfProj(proj_id);
        list.remove(0);
        int max = -1;
        for (String s : list) {
            max = Integer.valueOf(s.substring(1)) > max ? Integer.valueOf(s.substring(1)) : max;
        }
        return max == -1 ? null : "v" + max;
    }

    /**
     * 返回项目某版本的说明
     *
     * @param proj_id      项目id
     * @param version_Name 版本名称
     * @return 版本说明
     */
    public static VersionData getOneVersionData(int proj_id, String version_Name) {
        VersionData versionData = new VersionData();
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from version where proj_id = ? and version_name = ?");
            ps.setInt(1, proj_id);
            ps.setString(2, version_Name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                versionData.setVersion_id(String.valueOf(rs.getInt("id")));
                versionData.setProj_id(String.valueOf(rs.getInt("proj_id")));
                versionData.setVersion_name(rs.getString("version_name"));
                versionData.setVersion_description(rs.getString("version_description"));
                versionData.setParam_value_list(ParamValueDb.getParamByProjAndVersion(Integer.valueOf(versionData.getProj_id()), versionData.getVersion_name()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return versionData;
    }

    /**
     *
     * @param proj_id 项目id
     * @param version_Name 版本号
     * @param param_type 0为需要输入的已知参数，1为待求参数
     * @return
     */
    public static VersionData getOneVersionData(int proj_id, String version_Name, int param_type) {
        VersionData versionData = new VersionData();
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from version where proj_id = ? and version_name = ?");
            ps.setInt(1, proj_id);
            ps.setString(2, version_Name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                versionData.setVersion_id(String.valueOf(rs.getInt("id")));
                versionData.setProj_id(String.valueOf(rs.getInt("proj_id")));
                versionData.setVersion_name(rs.getString("version_name"));
                versionData.setVersion_description(rs.getString("version_description"));
                versionData.setParam_value_list(ParamValueDb.getParamOfType(Integer.valueOf(versionData.getProj_id()), versionData.getVersion_name(), param_type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return versionData;
    }

    /**
     * 根据项目id和版本名称删除版本，以及该版本下的所有参数参数值。
     *
     * @param proj_id
     * @param version_name
     * @return
     */
    public static boolean deleteAVersionAndParam(int proj_id, String version_name) {
        ParamValueDb.deleteByProjVersion(proj_id, version_name);
        SelectedTypeDb.deleteByProjIdAndVersion(proj_id, version_name);
        boolean flag = false;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        String sql = "delete from " + Constant.versionTableName + " where proj_id = ? and version_name = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, proj_id);
            ps.setString(2, version_name);
            int i = ps.executeUpdate();
            if (i == 1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return flag;
    }
}
