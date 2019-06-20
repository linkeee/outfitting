package App.database;

import App.dataModel.CriterionData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class CriterionDb extends DatabaseItem {

    /**
     * Return all of the criterionData in a list way.
     *
     * @return
     */
    public static ObservableList<CriterionData> getCriterionDataList() {
        ArrayList<CriterionData> criterionList = new ArrayList<>();

        Connection connection = connectDB();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from criterion order by criId+0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CriterionData criterion = new CriterionData();
                criterion.setCriId(resultSet.getString("criId"));
                criterion.setCriShipType(resultSet.getString("criShipType"));
                criterion.setCriShipCompany(resultSet.getString("criShipCompany"));
                criterion.setCriOutfittingRegion(resultSet.getString("criOutfittingRegion"));
                criterion.setCriName(resultSet.getString("criName"));
                criterion.setCriContent(resultSet.getString("criContent"));
                criterion.setCriFilePath(resultSet.getString("criFilePath"));

                criterionList.add(criterion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(criterionList);
    }

    /**
     * search the criterion table to management.
     *
     * @param keyword
     * @return
     * @throws SQLException
     */
    public static ObservableList<CriterionData> search(String keyword) throws SQLException {
        ArrayList<CriterionData> criterionList = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        String sql = "select * from criterion where match (criId, criShipType, criShipCompany, criOutfittingRegion, criName, criContent, criFilePath) against (?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, keyword);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            CriterionData criterionData = new CriterionData();
            criterionData.setCriId(resultSet.getString("criId"));
            criterionData.setCriShipType(resultSet.getString("criShipType"));
            criterionData.setCriShipCompany(resultSet.getString("criShipCompany"));
            criterionData.setCriOutfittingRegion(resultSet.getString("criOutfittingRegion"));
            criterionData.setCriName(resultSet.getString("criName"));
            criterionData.setCriContent(resultSet.getString("criContent"));
            criterionData.setCriFilePath(resultSet.getString("criFilePath"));

            criterionList.add(criterionData);
        }
        return FXCollections.observableArrayList(criterionList);
    }

    /**
     * query the criterionTable and return the result of query in observableList way.
     *
     * @param content
     * @param shipType
     * @param ccs
     * @param name
     * @param region
     * @return
     * @throws SQLException
     */
    public static ObservableList<CriterionData> query(String content, String shipType, String ccs, String name, String region) throws SQLException {
        ArrayList<CriterionData> criterionList = new ArrayList<>();

        if (content == null) content = "";
        if (shipType == null) shipType = "";
        if (ccs == null) ccs = "";
        if (name == null) name = "";
        if (region == null) region = "";

        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        String sql = "SELECT * FROM jproject.criterion where criContent like ? and criShipType like ? and criShipCompany like ? and criName like ? and criOutfittingRegion like ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + content.replace(" ", "") + "%");
        preparedStatement.setString(2, "%" + shipType.replace(" ", "") + "%");
        preparedStatement.setString(3, "%" + ccs.replace(" ", "") + "%");
        preparedStatement.setString(4, "%" + name.replace(" ", "") + "%");
        preparedStatement.setString(5, "%" + region.replace(" ", "") + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            CriterionData criterionData = new CriterionData();
            criterionData.setCriId(resultSet.getString("criId"));
            criterionData.setCriShipType(resultSet.getString("criShipType"));
            criterionData.setCriShipCompany(resultSet.getString("criShipCompany"));
            criterionData.setCriOutfittingRegion(resultSet.getString("criOutfittingRegion"));
            criterionData.setCriName(resultSet.getString("criName"));
            criterionData.setCriContent(resultSet.getString("criContent"));
            criterionData.setCriFilePath(resultSet.getString("criFilePath"));

            criterionList.add(criterionData);
        }
        return FXCollections.observableArrayList(criterionList);
    }

    /**
     * insert the new criterionData.
     *
     * @param criterionData
     * @return
     */
    public static boolean insert(CriterionData criterionData) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;

        String sql = "insert into criterion (criId, criShipType, criShipCompany, criOutfittingRegion, criName, criContent, criFilePath) value(?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, criterionData.getCriId());
            preparedStatement.setString(2, criterionData.getCriShipType());
            preparedStatement.setString(3, criterionData.getCriShipCompany());
            preparedStatement.setString(4, criterionData.getCriOutfittingRegion());
            preparedStatement.setString(5, criterionData.getCriName());
            preparedStatement.setString(6, criterionData.getCriContent());
            preparedStatement.setString(7, criterionData.getCriFilePath());

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
     * @param criterionData
     * @param editCriterionDataId
     * @return
     */
    public static boolean update(CriterionData criterionData, String editCriterionDataId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update criterion set criId=?, criShipType=?, criShipCompany=?, criOutfittingRegion=?, criName=?, criContent=?, criFilePath=? where criId=?";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, criterionData.getCriId());
            preparedStatement.setString(2, criterionData.getCriShipType());
            preparedStatement.setString(3, criterionData.getCriShipCompany());
            preparedStatement.setString(4, criterionData.getCriOutfittingRegion());
            preparedStatement.setString(5, criterionData.getCriName());
            preparedStatement.setString(6, criterionData.getCriContent());
            preparedStatement.setString(7, criterionData.getCriFilePath());
            preparedStatement.setString(8, editCriterionDataId);

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
     * @param selectedCriterionId
     * @return
     */
    public static boolean delete(String selectedCriterionId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from criterion where criId = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedCriterionId);

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
