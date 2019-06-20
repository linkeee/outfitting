package App.database;

import App.dataModel.LayoutData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LayoutDb extends DatabaseItem {

    //该变量储存区域布局设计实例数据库中的条目列表
    private ObservableList<LayoutData> layoutDataList = FXCollections.observableArrayList();

    /**
     * The getter and setter methods of variable layoutDataList.
     * @return
     */
    public static ObservableList<LayoutData> getLayoutDataList() {
        ArrayList<LayoutData> list = getAllLayoutData();
        return FXCollections.observableArrayList(list);
    }

    public void setJinJiDataList(LayoutData layoutData) {
        layoutDataList.add(layoutData);
    }

    /**
     * Return all of the layoutData in a list way.
     * @return
     */
    private static ArrayList<LayoutData> getAllLayoutData() {
        ArrayList<LayoutData> layoutList = new ArrayList<>();

        Connection connection = connectDB();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from layout order by layoutId+0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LayoutData layoutData = new LayoutData();
                layoutData.setLayoutDesignID(resultSet.getString(1));
                layoutData.setLayoutDesignName(resultSet.getString(2));
                layoutData.setLayoutDesignType(resultSet.getString(3));
                layoutData.setLayoutShipType(resultSet.getString(4));
                layoutData.setLayoutShipNum(resultSet.getString(5));
                layoutData.setLayoutDesignContent(resultSet.getString(6));
                layoutData.setShipLength(resultSet.getString(7));
                layoutData.setShipWidth(resultSet.getString(8));
                layoutData.setShipDepth(resultSet.getString(9));
                layoutData.setShipDraught(resultSet.getString(10));
                layoutData.setShipCoefficients(resultSet.getString(11));
                layoutData.setShipWeight(resultSet.getString(12));

                layoutList.add(layoutData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return layoutList;
    }

    /**
     * query the layoutTable and return the result of query in observableList way.
     * @param keyword
     * @return
     * @throws SQLException
     */
    public static ObservableList<LayoutData> query(String keyword) throws SQLException {
        ArrayList<LayoutData> layoutDataArrayList = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        String sql = "select * from layout where match (layoutId, layoutName, layoutType, layoutShipType, layoutShipNum, layoutContent, layoutShipLength, layoutShipWidth, layoutShipDepth, layoutShipDraught, layoutShipCoefficient, layoutShipWeight) against (?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, keyword);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            LayoutData layoutData = new LayoutData();
            layoutData.setLayoutDesignID(resultSet.getString(1));
            layoutData.setLayoutDesignName(resultSet.getString(2));
            layoutData.setLayoutDesignType(resultSet.getString(3));
            layoutData.setLayoutShipType(resultSet.getString(4));
            layoutData.setLayoutShipNum(resultSet.getString(5));
            layoutData.setLayoutDesignContent(resultSet.getString(6));
            layoutData.setShipLength(resultSet.getString(7));
            layoutData.setShipWidth(resultSet.getString(8));
            layoutData.setShipDepth(resultSet.getString(9));
            layoutData.setShipDraught(resultSet.getString(10));
            layoutData.setShipCoefficients(resultSet.getString(11));
            layoutData.setShipWeight(resultSet.getString(12));

            layoutDataArrayList.add(layoutData);
        }
        return FXCollections.observableArrayList(layoutDataArrayList);
    }

    /**
     * insert the new layoutData.
     * @param layoutData
     * @return
     */
    public static boolean insert (LayoutData layoutData) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;

        String sql = "insert into layout (layoutId, layoutName, layoutType, layoutShipType, layoutShipNum, layoutContent, layoutShipLength, layoutShipWidth, layoutShipDepth, layoutShipDraught, layoutShipCoefficient, layoutShipWeight) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, layoutData.getLayoutDesignID());
            preparedStatement.setString(2, layoutData.getLayoutDesignName());
            preparedStatement.setString(3, layoutData.getLayoutDesignType());
            preparedStatement.setString(4, layoutData.getLayoutShipType());
            preparedStatement.setString(5, layoutData.getLayoutShipNum());
            preparedStatement.setString(6, layoutData.getLayoutDesignContent());
            preparedStatement.setString(7, layoutData.getShipLength());
            preparedStatement.setString(8, layoutData.getShipWidth());
            preparedStatement.setString(9, layoutData.getShipDepth());
            preparedStatement.setString(10, layoutData.getShipDraught());
            preparedStatement.setString(11, layoutData.getShipCoefficients());
            preparedStatement.setString(12, layoutData.getShipWeight());

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

    /**
     * Update the content of the selected item.
     *
     * @param layoutData
     * @param editLayoutDataId
     * @return
     */
    public static boolean update (LayoutData layoutData, String editLayoutDataId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update layout set layoutId=?, layoutName=?, layoutType=?, layoutShipType=?, layoutShipNum=?, layoutContent=?, layoutShipLength=?, layoutShipWidth=?, layoutShipDepth=?, layoutShipDraught=?, layoutShipCoefficient=?, layoutShipWeight=? where layoutId=?";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, layoutData.getLayoutDesignID());
            preparedStatement.setString(2, layoutData.getLayoutDesignName());
            preparedStatement.setString(3, layoutData.getLayoutDesignType());
            preparedStatement.setString(4, layoutData.getLayoutShipType());
            preparedStatement.setString(5, layoutData.getLayoutShipNum());
            preparedStatement.setString(6, layoutData.getLayoutDesignContent());
            preparedStatement.setString(7, layoutData.getShipLength());
            preparedStatement.setString(8, layoutData.getShipWidth());
            preparedStatement.setString(9, layoutData.getShipDepth());
            preparedStatement.setString(10, layoutData.getShipDraught());
            preparedStatement.setString(11, layoutData.getShipCoefficients());
            preparedStatement.setString(12, layoutData.getShipWeight());
            preparedStatement.setString(13, editLayoutDataId);

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

    /**
     * Delete the item that has been chosen.
     *
     * @param selectedLayoutId
     * @return
     */
    public static boolean delete(String selectedLayoutId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from layout where layoutId = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedLayoutId);

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
}
