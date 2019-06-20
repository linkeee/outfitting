package App.database;

import App.dataModel.RuleItemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RuleItemDb extends DatabaseItem {

    public static ObservableList<RuleItemData> getRuleItemDataList(String itemName) {
        ArrayList<RuleItemData> list = getAllRuleItemData(itemName);
        return FXCollections.observableList(list);
    }

    private static ArrayList<RuleItemData> getAllRuleItemData(String itemName) {
        ArrayList<RuleItemData> ruleItemDataList = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement preparedStatement;
        String sql = "select * from ruleItem where itemName=? order by ruleId+0";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, itemName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RuleItemData ruleItemData = new RuleItemData();
                ruleItemData.setRuleId(resultSet.getString(1));
                ruleItemData.setItemName(resultSet.getString(2));
                ruleItemData.setItemHyperLink(resultSet.getString(3));
                ruleItemData.setOutfittingType(resultSet.getString(4));
                ruleItemData.setAnchorType(resultSet.getString(5));
                ruleItemData.setManufacturer(resultSet.getString(6));
                ruleItemData.setGongyouliang(resultSet.getString(7));
                ruleItemData.setZhongyouOutputPressure(resultSet.getString(8));
                ruleItemData.setZhongyouOutputTemperature(resultSet.getString(9));
                ruleItemData.setZhongyouOutputViscosity(resultSet.getString(10));

                ruleItemDataList.add(ruleItemData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ruleItemDataList;
    }

    public static ObservableList<RuleItemData> query(String itemName, String keyword) throws SQLException {
        ArrayList<RuleItemData> ruleItemDataArrayList = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        String sql = "select * from ruleItem where itemName=? and match (ruleId, itemName, itemHyperLink, outfittingType, anchorType, manufacturer, gongyouliang, zhongyouOutputPressure, zhongyouOutputTemperature, zhongyouOutputViscosity) against (?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, itemName);
        preparedStatement.setString(2, keyword);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            RuleItemData ruleItemData = new RuleItemData();
            ruleItemData.setRuleId(resultSet.getString(1));
            ruleItemData.setItemName(resultSet.getString(2));
            ruleItemData.setItemHyperLink(resultSet.getString(3));
            ruleItemData.setOutfittingType(resultSet.getString(4));
            ruleItemData.setAnchorType(resultSet.getString(5));
            ruleItemData.setManufacturer(resultSet.getString(6));
            ruleItemData.setGongyouliang(resultSet.getString(7));
            ruleItemData.setZhongyouOutputPressure(resultSet.getString(8));
            ruleItemData.setZhongyouOutputTemperature(resultSet.getString(9));
            ruleItemData.setZhongyouOutputViscosity(resultSet.getString(10));

            ruleItemDataArrayList.add(ruleItemData);
        }
        return FXCollections.observableArrayList(ruleItemDataArrayList);
    }

    public static boolean insert(RuleItemData ruleItemData) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;

        String sql = "insert into ruleItem (ruleId, itemName, itemHyperLink, outfittingType, anchorType, manufacturer, gongyouliang, zhongyouOutputPressure, zhongyouOutputTemperature, zhongyouOutputViscosity) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ruleItemData.getRuleId());
            preparedStatement.setString(2, ruleItemData.getItemName());
            preparedStatement.setString(3, ruleItemData.getItemHyperLink());
            preparedStatement.setString(4, ruleItemData.getOutfittingType());
            preparedStatement.setString(5, ruleItemData.getAnchorType());
            preparedStatement.setString(6, ruleItemData.getManufacturer());
            preparedStatement.setString(7, ruleItemData.getGongyouliang());
            preparedStatement.setString(8, ruleItemData.getZhongyouOutputPressure());
            preparedStatement.setString(9, ruleItemData.getZhongyouOutputTemperature());
            preparedStatement.setString(10, ruleItemData.getZhongyouOutputViscosity());

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

    public static boolean update(RuleItemData ruleItemData, String editRuleId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update ruleItem set ruleId=?, itemName=?, itemHyperLink=?, outfittingType=?, anchorType=?, manufacturer=?, gongyouliang=?, zhongyouOutputPressure=?, zhongyouOutputTemperature=?, zhongyouOutputViscosity=? where ruleId=?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, ruleItemData.getRuleId());
            preparedStatement.setString(2, ruleItemData.getItemName());
            preparedStatement.setString(3, ruleItemData.getItemHyperLink());
            preparedStatement.setString(4, ruleItemData.getOutfittingType());
            preparedStatement.setString(5, ruleItemData.getAnchorType());
            preparedStatement.setString(6, ruleItemData.getManufacturer());
            preparedStatement.setString(7, ruleItemData.getGongyouliang());
            preparedStatement.setString(8, ruleItemData.getZhongyouOutputPressure());
            preparedStatement.setString(9, ruleItemData.getZhongyouOutputTemperature());
            preparedStatement.setString(10, ruleItemData.getZhongyouOutputViscosity());
            preparedStatement.setString(11, editRuleId);

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

    public static boolean delete(String deleteRuleId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from ruleItem where ruleId = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, deleteRuleId);

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
