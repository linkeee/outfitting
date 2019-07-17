package App.database;

import App.dataModel.LayoutData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutDb extends DatabaseItem {

    /**
     * Return all of the layoutData in a list way.
     *
     * @return
     */
    public static List<LayoutData> getAllLayoutData() {
        List<LayoutData> layoutList = new ArrayList<>();

        Connection connection = connectDB();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from layout order by Id+0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LayoutData layoutData = new LayoutData();
                layoutData.setId(String.valueOf(resultSet.getInt("id")));
                layoutData.setOutfitting_name(resultSet.getString("outfitting_name"));
                layoutData.setShipType(resultSet.getString("ship_type"));
                layoutData.setShipNum(resultSet.getString("ship_num"));
                layoutData.setShipTypeCoefficient(resultSet.getString("ship_type_coefficient"));
                layoutData.setShipLoad(resultSet.getString("ship_load"));
                layoutData.setShipLength(resultSet.getString("ship_length"));
                layoutData.setShipWidth(resultSet.getString("ship_width"));
                layoutData.setShipDepth(resultSet.getString("ship_depth"));
                layoutData.setShipDraught(resultSet.getString("ship_draught"));
                layoutData.setLayoutContent(resultSet.getString("layout_content"));
                layoutData.setFilePath(resultSet.getString("file_path"));

                layoutList.add(layoutData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return layoutList;
    }

    /**
     * query the layoutTable and return the result of query in observableList way.
     *
     * @param keywords
     * @return
     * @throws SQLException
     */
    public static List<LayoutData> query(List<String> keywords) {
        Map<String, LayoutData> map = new HashMap<>();

        Connection connection = connectDB();
        PreparedStatement ps = null;

        String sql = "SELECT * FROM jproject.layout where layout_content like ?";

        for (String keyword : keywords) {
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(1, "%" + keyword + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    LayoutData layoutData = new LayoutData();
                    if (map.containsKey(String.valueOf(rs.getInt("id")))) continue;
                    layoutData.setId(String.valueOf(rs.getInt("id")));
                    layoutData.setOutfitting_name(rs.getString("outfitting_name"));
                    layoutData.setShipType(rs.getString("ship_type"));
                    layoutData.setShipNum(rs.getString("ship_num"));
                    layoutData.setShipTypeCoefficient(rs.getString("ship_type_coefficient"));
                    layoutData.setShipLoad(rs.getString("ship_load"));
                    layoutData.setShipLength(rs.getString("ship_length"));
                    layoutData.setShipWidth(rs.getString("ship_width"));
                    layoutData.setShipDepth(rs.getString("ship_depth"));
                    layoutData.setShipDraught(rs.getString("ship_draught"));
                    layoutData.setLayoutContent(rs.getString("layout_content"));
                    layoutData.setFilePath(rs.getString("file_path"));
                    map.put(layoutData.getId(), layoutData);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeDatabase(ps, null, connection);

        return new ArrayList<>(map.values());
    }

    /**
     * insert the new layoutData.
     *
     * @param layoutData
     * @return
     */
    public static boolean insert(LayoutData layoutData) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement ps = null;

        String sql = "insert into layout (" +
                "outfitting_name, ship_type, ship_num, ship_type_coefficient, " +
                "ship_load, ship_length, ship_width, ship_depth, ship_draught, layout_content, file_path) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, layoutData.getOutfitting_name());
            ps.setString(2, layoutData.getShipType());
            ps.setString(3, layoutData.getShipNum());
            ps.setString(4, layoutData.getShipTypeCoefficient());
            ps.setString(5, layoutData.getShipLoad());
            ps.setString(6, layoutData.getShipLength());
            ps.setString(7, layoutData.getShipWidth());
            ps.setString(8, layoutData.getShipDepth());
            ps.setString(9, layoutData.getShipDraught());
            ps.setString(10, layoutData.getLayoutContent());
            ps.setString(11, layoutData.getFilePath());

            int i = ps.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        if (flag) System.out.println("操作成功！");
        return flag;
    }

    /**
     * Update the content of the selected item.
     *
     * @param layoutData
     * @param editLayoutDataId
     * @return
     */
    public static boolean update(LayoutData layoutData, int editLayoutDataId) {
        boolean flag = true;

        PreparedStatement ps = null;

        String sql = "update layout set outfitting_name=?, ship_type=?, ship_num=?, " +
                "ship_type_coefficient=?, ship_load=?, ship_length=?, ship_width=?, ship_depth=?, " +
                "ship_draught=?, layout_content=?, file_path=? where id=?";
        Connection connection = connectDB();

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, layoutData.getOutfitting_name());
            ps.setString(2, layoutData.getShipType());
            ps.setString(3, layoutData.getShipNum());
            ps.setString(4, layoutData.getShipTypeCoefficient());
            ps.setString(5, layoutData.getShipLoad());
            ps.setString(6, layoutData.getShipLength());
            ps.setString(7, layoutData.getShipWidth());
            ps.setString(8, layoutData.getShipDepth());
            ps.setString(9, layoutData.getShipDraught());
            ps.setString(10, layoutData.getLayoutContent());
            ps.setString(11, layoutData.getFilePath());
            ps.setInt(12, editLayoutDataId);
            int i = ps.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        if (flag) System.out.println("操作成功！");
        return flag;
    }

    /**
     * Delete the item that has been chosen.
     *
     * @param selectedLayoutId
     * @return
     */
    public static boolean delete(int selectedLayoutId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from layout where id = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, selectedLayoutId);

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

    public static List<String> getShipTypeList() {
        List<String> list = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select distinct ship_type from layout");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = resultSet.getString("ship_type");
                if (!list.contains(temp))
                    list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return list;
    }

    public static List<String> getOutfittingItems() {
        List<String> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection connection = connectDB();

        String sql = "select distinct outfitting_name from layout";
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
