package App.database;

import App.dataModel.ExperienceData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExperienceDatabase extends DatabaseItem {

    /**
     * Return all of the sugData in a list way.
     *
     * @return
     */
    public static ObservableList<ExperienceData> getExpDataList() {
        ArrayList<ExperienceData> expList = new ArrayList<>();

        Connection connection = connectDB();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from experience order by expId+0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ExperienceData experienceData = new ExperienceData();
                experienceData.setExpId(resultSet.getString("expId"));
                experienceData.setExpShipType(resultSet.getString("expShipType"));
                experienceData.setExpOutfittingRegion(resultSet.getString("expOutfittingRegion"));
                experienceData.setExpName(resultSet.getString("expName"));
                experienceData.setExpContent(resultSet.getString("expContent"));
                experienceData.setExpFilePath(resultSet.getString("expFilePath"));
                expList.add(experienceData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(expList);
    }

    /**
     * query the experienceTable and return the result of query in observableList way.
     *
     * @param shipType
     * @param outfittingRegion
     * @param expName
     * @param expContent
     * @return
     * @throws SQLException
     */
    public static ObservableList<ExperienceData> query(String shipType, String outfittingRegion, String expName, String expContent) throws SQLException {
        ArrayList<ExperienceData> experienceDataArrayList = new ArrayList<>();

        if (shipType == null) shipType = "";
        if (outfittingRegion == null) outfittingRegion = "";
        if (expName == null) expName = "";
        if (expContent == null) expContent = "";

        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        String sql = "select * from jproject.experience where expShipType like ? and expOutfittingRegion like ? and expName like ? and expContent like ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + shipType.replace(" ", "") + "%");
        preparedStatement.setString(2, "%" + outfittingRegion.replace(" ", "") + "%");
        preparedStatement.setString(3, "%" + expName.replace(" ", "") + "%");
        preparedStatement.setString(4, "%" + expContent.replace(" ", "") + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ExperienceData experienceData = new ExperienceData();
            experienceData.setExpId(resultSet.getString("expId"));
            experienceData.setExpShipType(resultSet.getString("expShipType"));
            experienceData.setExpOutfittingRegion(resultSet.getString("expOutfittingRegion"));
            experienceData.setExpName(resultSet.getString("expName"));
            experienceData.setExpContent(resultSet.getString("expContent"));
            experienceData.setExpFilePath(resultSet.getString("expFilePath"));

            experienceDataArrayList.add(experienceData);
        }
        return FXCollections.observableArrayList(experienceDataArrayList);
    }

    /**
     * insert the new experienceData.
     *
     * @param exp
     * @return
     */
    public static boolean insert(ExperienceData exp) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;

        String sql = "insert into experience (expId, expShipType, expOutfittingRegion, expName, expContent, expFilePath) value(?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, exp.getExpId());
            preparedStatement.setString(2, exp.getExpShipType());
            preparedStatement.setString(3, exp.getExpOutfittingRegion());
            preparedStatement.setString(4, exp.getExpName());
            preparedStatement.setString(5, exp.getExpContent());
            preparedStatement.setString(6, exp.getExpFilePath());

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
     * @param expData
     * @param editExpDataId
     * @return
     */
    public static boolean update(ExperienceData expData, String editExpDataId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update experience set expId=?, expShipType=?, expOutfittingRegion=?, expName=?, expContent=?, expFilePath=? where expId=?";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, expData.getExpId());
            preparedStatement.setString(2, expData.getExpShipType());
            preparedStatement.setString(3, expData.getExpOutfittingRegion());
            preparedStatement.setString(4, expData.getExpName());
            preparedStatement.setString(5, expData.getExpContent());
            preparedStatement.setString(6, expData.getExpFilePath());
            preparedStatement.setString(7, editExpDataId);

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
     * @param selectedExpId
     * @return
     */
    public static boolean delete(String selectedExpId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from experience where expId = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedExpId);

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
