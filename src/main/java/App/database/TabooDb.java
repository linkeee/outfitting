package App.database;

import App.dataModel.TabooData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class TabooDb extends DatabaseItem {

    //该变量储存禁忌数据库中的禁忌条目列表
    private ObservableList<TabooData> tabooDataList = FXCollections.observableArrayList();

    /**
     * The getter and setter methods of variable tabooDataList.
     * @return
     */
    public static ObservableList<TabooData> getTabooDataList() {
        ArrayList<TabooData> list = getAllJinJiData();
        return FXCollections.observableArrayList(list);
    }

    public void setTabooDataList(TabooData tabooData) {
        tabooDataList.add(tabooData);
    }

    /**
     * Return all of the TabooData in a list way.
     * @return
     */
    private static ArrayList<TabooData> getAllJinJiData() {
        ArrayList<TabooData> jinJiList = new ArrayList<>();

        Connection connection = connectDB();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from taboo order by tabooId+0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TabooData tabooData = new TabooData();
                tabooData.setJinjiID(resultSet.getString(1));
                tabooData.setShipType(resultSet.getString(2));
                tabooData.setOutfittingType(resultSet.getString(3));
                tabooData.setJkeyword1(resultSet.getString(4));
                tabooData.setJkeyword2(resultSet.getString(5));
                tabooData.setJkeyword3(resultSet.getString(6));
                tabooData.setJjinjiLevel(resultSet.getString(7));
                tabooData.setJinjiName(resultSet.getString(8));
                tabooData.setJinjiContent(resultSet.getString(9));

                jinJiList.add(tabooData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jinJiList;
    }

    /**
     * query the tabooTable and return the result of query in observableList way.
     * @param keyword
     * @return
     * @throws SQLException
     */
    public static ObservableList<TabooData> query(String keyword) throws SQLException {
        ArrayList<TabooData> tabooDataArrayList = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        String sql = "select * from taboo where match (tabooId, shipType, outfittingType, tabooKeyword1, tabooKeyword2, tabooKeyword3, tabooLevel, tabooName, tabooContent) against (?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, keyword);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            TabooData tabooData = new TabooData();
            tabooData.setJinjiID(resultSet.getString(1));
            tabooData.setShipType(resultSet.getString(2));
            tabooData.setOutfittingType(resultSet.getString(3));
            tabooData.setJkeyword1(resultSet.getString(4));
            tabooData.setJkeyword2(resultSet.getString(5));
            tabooData.setJkeyword3(resultSet.getString(6));
            tabooData.setJjinjiLevel(resultSet.getString(7));
            tabooData.setJinjiName(resultSet.getString(8));
            tabooData.setJinjiContent(resultSet.getString(9));

            tabooDataArrayList.add(tabooData);
        }
        return FXCollections.observableArrayList(tabooDataArrayList);
    }

    /**
     * Insert the new tabooData.
     * @param jinJi
     * @return
     */
    public static boolean insert (TabooData jinJi) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;

        String sql = "insert into taboo (tabooId, shipType, outfittingType, tabooKeyword1, tabooKeyword2, tabooKeyword3, tabooLevel, tabooName, tabooContent) value(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, jinJi.getJinjiID());
            preparedStatement.setString(2, jinJi.getShipType());
            preparedStatement.setString(3, jinJi.getOutfittingType());
            preparedStatement.setString(4, jinJi.getJkeyword1());
            preparedStatement.setString(5, jinJi.getJkeyword2());
            preparedStatement.setString(6, jinJi.getJkeyword3());
            preparedStatement.setString(7, jinJi.getJjinjiLevel());
            preparedStatement.setString(8, jinJi.getJinjiName());
            preparedStatement.setString(9, jinJi.getJinjiContent());

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
     * @param tabooData
     * @param editJinJiDataId
     * @return
     */
    public static boolean update (TabooData tabooData, String editJinJiDataId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update taboo set tabooId=?, shipType=?, outfittingType=?, tabooKeyword1=?, tabooKeyword2=?, tabooKeyword3=?, tabooLevel=?, tabooName=?, tabooContent=? where tabooId=?";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, tabooData.getJinjiID());
            preparedStatement.setString(2, tabooData.getShipType());
            preparedStatement.setString(3, tabooData.getOutfittingType());
            preparedStatement.setString(4, tabooData.getJkeyword1());
            preparedStatement.setString(5, tabooData.getJkeyword2());
            preparedStatement.setString(6, tabooData.getJkeyword3());
            preparedStatement.setString(7, tabooData.getJjinjiLevel());
            preparedStatement.setString(8, tabooData.getJinjiName());
            preparedStatement.setString(9, tabooData.getJinjiContent());
            preparedStatement.setString(10, editJinJiDataId);

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
     * @param selectedTabooId
     * @return
     */
    public static boolean delete(String selectedTabooId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from taboo where tabooId = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedTabooId);

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
