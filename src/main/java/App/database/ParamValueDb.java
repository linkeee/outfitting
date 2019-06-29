package App.database;

import App.dataModel.ParamAndValueData;
import App.utile.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParamValueDb extends DatabaseItem {

    /**
     * 返回特定类型的参数。
     *
     * @param proj_id 项目id
     * @param version 版本号
     * @param param_type 参数类型，0为需要输入的已知参数，1为待求参数
     * @return
     */
    public static List<ParamAndValueData> getParamOfType(int proj_id, String version, int param_type) {
        List<ParamAndValueData> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection conn = connectDB();
        try {
            ps = conn.prepareStatement("select * from " + Constant.paramValueTableName + " where proj_id = ? and version_name = ? and param_type = ?");
            ps.setInt(1, proj_id);
            ps.setString(2, version);
            ps.setInt(3, param_type);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ParamAndValueData pvd = new ParamAndValueData();

                pvd.setProj_id(String.valueOf(rs.getInt("proj_id")));
                pvd.setVersion_name(rs.getString("version_name"));
                pvd.setParam_id(String.valueOf(rs.getInt("param_id")));
                pvd.setOutfitting_name(rs.getString("outfitting_name"));
                pvd.setParam_name(rs.getString("param_name"));
                if (rs.getInt("param_type") == 0) {
                    pvd.setParam_type("已知");
                } else if (rs.getInt("param_type") == 1) {
                    pvd.setParam_type("待求");
                } else {
                    pvd.setParam_type("未知参数类型, 请修正!");
                }
                pvd.setParam_description(rs.getString("param_description"));
                pvd.setParam_value(rs.getString("param_value"));

                list.add(pvd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, conn);
        }
        return list;
    }

    /**
     * 根据项目id和版本返回参数结果。
     *
     * @param proj_id 项目id
     * @param version 版本号
     * @return 参数list
     */
    public static List<ParamAndValueData> getParamByProjAndVersion(int proj_id, String version) {
        List<ParamAndValueData> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from paramandvalue where proj_id = ? and version_name = ?");
            ps.setInt(1, proj_id);
            ps.setString(2, version);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ParamAndValueData paramAndValueData = new ParamAndValueData();

                paramAndValueData.setProj_id(String.valueOf(rs.getInt("proj_id")));
                paramAndValueData.setVersion_name(rs.getString("version_name"));
                paramAndValueData.setParam_id(String.valueOf(rs.getInt("param_id")));
                paramAndValueData.setOutfitting_name(rs.getString("outfitting_name"));
                paramAndValueData.setParam_name(rs.getString("param_name"));
                if (rs.getInt("param_type") == 0) {
                    paramAndValueData.setParam_type("已知");
                } else if (rs.getInt("param_type") == 1) {
                    paramAndValueData.setParam_type("待求");
                } else {
                    paramAndValueData.setParam_type("未知参数类型，请修正!");
                }
                paramAndValueData.setParam_description(rs.getString("param_description"));
                paramAndValueData.setParam_value(rs.getString("param_value"));

                list.add(paramAndValueData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return list;
    }

    /**
     * 插入paramandvalue数据库，param_value可以为空，也可以不为空。
     *
     * @param list
     * @return
     */
    public static boolean insert(List<ParamAndValueData> list) {
        boolean flag = false;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        String sql = "insert into jproject.paramandvalue (proj_id, version_name, param_id, outfitting_name, param_name, param_type, param_description, param_value) value(?, ?, ?, ?, ?, ?, ?, ?)";

        for (ParamAndValueData pvd : list) {
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.valueOf(pvd.getProj_id()));
                ps.setString(2, pvd.getVersion_name());
                ps.setInt(3, Integer.valueOf(pvd.getParam_id()));
                ps.setString(4, pvd.getOutfitting_name());
                ps.setString(5, pvd.getParam_name());
                if (pvd.getParam_type().equals("已知")) {
                    ps.setInt(6, 0);
                } else if (pvd.getParam_type().equals("待求")) {
                    ps.setInt(6, 1);
                } else {
                    throw new Exception("参数类型输入非法，仅支持“已知”和“待求”。");
                }
                ps.setString(7, pvd.getParam_description());
                if (pvd.getParam_value() == null || pvd.getParam_value().equals("")) {
                    ps.setString(8, null);
                } else {
                    ps.setString(8, pvd.getParam_value());
                }

                if (ps.executeUpdate() == 1) {
                    flag = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        closeDatabase(ps, null, connection);

        return flag;
    }

    /**
     * 插入参数值。
     *
     * @param list
     * @return
     */
    public static boolean insertValue(List<ParamAndValueData> list) {
        boolean flag = false;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        String sql = "update " + Constant.paramValueTableName + " set param_value = ? where proj_id = ? and version_name = ? and param_id = ?";

        for (ParamAndValueData pvd : list) {
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(1, pvd.getParam_value());
                ps.setInt(2, Integer.valueOf(pvd.getProj_id()));
                ps.setString(3, pvd.getVersion_name());
                ps.setInt(4, Integer.valueOf(pvd.getParam_id()));
                int i = ps.executeUpdate();
                if (i == 1) flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        closeDatabase(ps, null, connection);

        return flag;
    }

    public static boolean deleteByProjVersion(int proj_id, String version_name) {
        boolean flag = false;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        String sql = "delete from " + Constant.paramValueTableName + " where proj_id = ? and version_name = ?";
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
