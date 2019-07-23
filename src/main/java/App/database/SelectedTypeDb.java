package App.database;

import App.dataModel.ManufacturerData;
import App.dataModel.ParamAndValueData;
import App.dataModel.SelectedTypeData;
import App.utile.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static App.database.DatabaseItem.closeDatabase;
import static App.database.DatabaseItem.connectDB;

public class SelectedTypeDb {

    public static boolean deleteByProjIdAndVersion(int projId, String versionName) {
        boolean flag = false;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        String sql = "delete from " + Constant.selectedTypeTableName + " where proj_id = ? and version_name = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, projId);
            ps.setString(2, versionName);

            int i = ps.executeUpdate();
            if (i == 1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return flag;
    }

    public static boolean delete(SelectedTypeData selectedTypeData) {
        boolean flag = false;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        String sql = "delete from " + Constant.selectedTypeTableName + " where proj_id = ? and version_name = ? and outfitting_name = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(selectedTypeData.getProj_id()));
            ps.setString(2, selectedTypeData.getVersion_name());
            ps.setString(3, selectedTypeData.getOutfitting_name());

            int i = ps.executeUpdate();
            if (i == 1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return flag;
    }

    public static boolean insert(ParamAndValueData paramAndValueData, ManufacturerData manufacturerData) {
        boolean flag = false;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        String sql = "insert into " + Constant.selectedTypeTableName + " (proj_id, version_name, outfitting_name, param_name, param_description, param_value, manufacturer_name, outfitting_type, param_scope, remark) value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(paramAndValueData.getProj_id()));
            ps.setString(2, paramAndValueData.getVersion_name());
            ps.setString(3, paramAndValueData.getOutfitting_name());
            ps.setString(4, paramAndValueData.getParam_name());
            ps.setString(5, paramAndValueData.getParam_description());
            ps.setString(6, paramAndValueData.getParam_value());
            ps.setString(7, manufacturerData.getManufacturer_name());
            ps.setString(8, manufacturerData.getOutfitting_type());
            ps.setString(9, manufacturerData.getParam_scope());
            ps.setString(10, manufacturerData.getRemark());

            int i = ps.executeUpdate();
            if (i == 1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return flag;
    }

    public static List<SelectedTypeData> getSelectedType(int proj_id, String version_name) {
        List<SelectedTypeData> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection conn = connectDB();
        try {
            ps = conn.prepareStatement("select * from " + Constant.selectedTypeTableName + " where proj_id = ? and version_name = ?");
            ps.setInt(1, proj_id);
            ps.setString(2, version_name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SelectedTypeData std = new SelectedTypeData();

                std.setProj_id(String.valueOf(rs.getInt("proj_id")));
                std.setVersion_name(rs.getString("version_name"));
                std.setOutfitting_name(rs.getString("outfitting_name"));
                std.setParam_name(rs.getString("param_name"));
                std.setParam_description(rs.getString("param_description"));
                std.setParam_value(rs.getString("param_value"));
                std.setManufacturer_name(rs.getString("manufacturer_name"));
                std.setOutfitting_type(rs.getString("outfitting_type"));
                std.setParam_scope(rs.getString("param_scope"));
                std.setRemark(rs.getString("remark"));

                list.add(std);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, conn);
        }
        return list;
    }
}
