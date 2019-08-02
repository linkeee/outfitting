package App.database;

import App.dataModel.ExperienceData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExperienceDb extends DatabaseItem {
    static Connection connection = connectDB();

    public static Map<String, String> getIndexAndContentMap() {
        Map<String, String> map = new HashMap<>();
        for (ExperienceData experienceData : getExpDataList()) {
            map.put(String.valueOf(experienceData.getExpId()), experienceData.getExpName() + experienceData.getExpOutfittingRegion() + experienceData.getExpContent());
        }
        return map;
    }

    public static Map<String, String> getIndexAndTfIdfMapStr() {
        Map<String, String> map = new HashMap<>();
        for (ExperienceData experienceData : getExpDataList()) {
            map.put(String.valueOf(experienceData.getExpId()), experienceData.getTfIdfMapStr());
        }
        return map;
    }

    public static List<ExperienceData> getOrderedDataList(List<String> linkedListOfIndex) {
        List<ExperienceData> list = new LinkedList<>();
        for (String s : linkedListOfIndex) {
            list.add(getDataById(Integer.valueOf(s)));
        }
        return list;
    }

    public static void updateTfIdf(Map<String, String> idAndTfIdfMap) {
        PreparedStatement ps = null;

        String sql = "update experience set tfidf=? where expId=?";

        for (Map.Entry<String, String> entry : idAndTfIdfMap.entrySet()) {
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(1, entry.getValue());
                ps.setInt(2, Integer.valueOf(entry.getKey()));
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static ExperienceData getDataById(Integer valueOf) {
        PreparedStatement ps = null;
        try {
            ps= connection.prepareStatement("select * from experience where expId = ?");
            ps.setInt(1, valueOf);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                ExperienceData data = new ExperienceData();
                data.setExpId(resultSet.getString("expId"));
                data.setExpShipType(resultSet.getString("expShipType"));
                data.setExpOutfittingRegion(resultSet.getString("expOutfittingRegion"));
                data.setExpName(resultSet.getString("expName"));
                data.setExpContent(resultSet.getString("expContent"));
                data.setExpFilePath(resultSet.getString("expFilePath"));
                data.setTfIdfMapStr(resultSet.getString("tfidf"));
                return data;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getShipTypeList() {
        List<String> list = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select distinct expShipType from experience");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = resultSet.getString("expShipType");
                if (!list.contains(temp))
                    list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Return all of the sugData in a list way.
     *
     * @return
     */
    public static List<ExperienceData> getExpDataList() {
        List<ExperienceData> expList = new ArrayList<>();

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
                experienceData.setTfIdfMapStr(resultSet.getString("tfidf"));
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

        String sql = "insert into experience (expShipType, expOutfittingRegion, expName, expContent, expFilePath) value(?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, exp.getExpShipType());
            preparedStatement.setString(2, exp.getExpOutfittingRegion());
            preparedStatement.setString(3, exp.getExpName());
            preparedStatement.setString(4, exp.getExpContent());
            preparedStatement.setString(5, exp.getExpFilePath());

            int i = preparedStatement.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
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

        String sql = "update experience set expShipType=?, expOutfittingRegion=?, expName=?, expContent=?, expFilePath=? where expId=?";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, expData.getExpShipType());
            preparedStatement.setString(2, expData.getExpOutfittingRegion());
            preparedStatement.setString(3, expData.getExpName());
            preparedStatement.setString(4, expData.getExpContent());
            preparedStatement.setString(5, expData.getExpFilePath());
            preparedStatement.setString(6, editExpDataId);

            int i = preparedStatement.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
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
        }
        if (flag) System.out.println("操作成功！");
        return flag;
    }
}
