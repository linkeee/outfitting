package App.database;

import App.dataModel.ManufacturerData;
import App.utile.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static App.database.DatabaseItem.closeDatabase;
import static App.database.DatabaseItem.connectDB;

public class ManufacturerDb {

    public static boolean delete(ManufacturerData manufacturerData) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from manufacturer where id = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.valueOf(manufacturerData.getId()));

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

    public static List<ManufacturerData> getManuData() {
        List<ManufacturerData> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection conn = connectDB();
        try {
            ps = conn.prepareStatement("select * from " + Constant.manufacturerTableName + " order by id");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ManufacturerData manu = new ManufacturerData();

                manu.setId(String.valueOf(rs.getInt("id")));
                manu.setOutfitting_name(rs.getString("outfitting_name"));
                manu.setManufacturer_name(rs.getString("manufacturer_name"));
                manu.setOutfitting_type(rs.getString("outfitting_type"));
                manu.setParam_scope(rs.getString("param_scope"));
                manu.setRemark(rs.getString("remark"));

                list.add(manu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, conn);
        }
        return list;
    }

    /**
     * 返回指定舾装件的所有生产厂商信息
     *
     * @param outfitting_name
     * @return
     */
    public static List<ManufacturerData> getManuDataByOutfittiingName(String outfitting_name) {
        List<ManufacturerData> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection conn = connectDB();
        try {
            ps = conn.prepareStatement("select * from " + Constant.manufacturerTableName + " where outfitting_name = ?");
            ps.setString(1, outfitting_name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ManufacturerData manu = new ManufacturerData();

                manu.setId(String.valueOf(rs.getInt("id")));
                manu.setOutfitting_name(rs.getString("outfitting_name"));
                manu.setManufacturer_name(rs.getString("manufacturer_name"));
                manu.setOutfitting_type(rs.getString("outfitting_type"));
                manu.setParam_scope(rs.getString("param_scope"));
                manu.setRemark(rs.getString("remark"));

                list.add(manu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, conn);
        }
        return list;
    }

    public static Boolean update(ManufacturerData manufacturerData, String selectedManuId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update " + Constant.manufacturerTableName + " set outfitting_name=?, manufacturer_name=?, outfitting_type=?, param_scope=?, remark=? where id=?";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, manufacturerData.getOutfitting_name());
            preparedStatement.setString(2, manufacturerData.getManufacturer_name());
            preparedStatement.setString(3, manufacturerData.getOutfitting_type());
            preparedStatement.setString(4, manufacturerData.getParam_scope());
            preparedStatement.setString(5, manufacturerData.getRemark());
            preparedStatement.setInt(6, Integer.valueOf(selectedManuId));

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

    public static Boolean insert(ManufacturerData manufacturerData) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "insert into " + Constant.manufacturerTableName + " (outfitting_name, manufacturer_name, outfitting_type, param_scope, remark) value (?, ?, ?, ?, ?)";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, manufacturerData.getOutfitting_name());
            preparedStatement.setString(2, manufacturerData.getManufacturer_name());
            preparedStatement.setString(3, manufacturerData.getOutfitting_type());
            preparedStatement.setString(4, manufacturerData.getParam_scope());
            preparedStatement.setString(5, manufacturerData.getRemark());

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

    public static List<String> getOutfittingItems() {
        List<String> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection connection = connectDB();

        String sql = "select distinct outfitting_name from manufacturer";
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
}
