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
}
