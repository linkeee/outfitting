package App.database;

import App.dataModel.CriterionData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CriterionDb extends DatabaseItem {

    static Connection connection = connectDB();

    public static Map<String, String> getIndexAndContentMap() {
        Map<String, String> map = new HashMap<>();
        for (CriterionData criterionData : getCriterionDataList()) {
            map.put(String.valueOf(criterionData.getCriId()), criterionData.getCriName() + criterionData.getCriOutfittingRegion() + criterionData.getCriContent());
        }
        return map;
    }

    public static Map<String, String> getIndexAndTfIdfMapStr() {
        Map<String, String> map = new HashMap<>();
        for (CriterionData criterionData : getCriterionDataList()) {
            map.put(String.valueOf(criterionData.getCriId()), criterionData.getTfIdfMapStr());
        }
        return map;
    }

    public static List<CriterionData> getOrderedDataList(List<String> linkedListOfIndex) {
        List<CriterionData> list = new LinkedList<>();
        for (String s : linkedListOfIndex) {
            list.add(getDataById(Integer.valueOf(s)));
        }
        return list;
    }

    public static void updateTfIdf(Map<String, String> idAndTfIdfMap) {
        PreparedStatement ps = null;

        String sql = "update criterion set tfidf=? where criId=?";

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

    private static CriterionData getDataById(Integer valueOf) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select * from criterion where criId = ?");
            ps.setInt(1, valueOf);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                CriterionData data = new CriterionData();
                data.setCriId(resultSet.getString("criId"));
                data.setCriShipType(resultSet.getString("criShipType"));
                data.setCriShipCompany(resultSet.getString("criShipCompany"));
                data.setCriOutfittingRegion(resultSet.getString("criOutfittingRegion"));
                data.setCriName(resultSet.getString("criName"));
                data.setCriContent(resultSet.getString("criContent"));
                data.setCriFilePath(resultSet.getString("criFilePath"));
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
            ps = connection.prepareStatement("select distinct criShipType from criterion");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = resultSet.getString("criShipType");
                if (!list.contains(temp))
                    list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getChuanJiSheList() {
        List<String> list = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select distinct criShipCompany from criterion");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = resultSet.getString("criShipCompany");
                if (!list.contains(temp))
                    list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Return all of the criterionData in a list way.
     *
     * @return
     */
    public static List<CriterionData> getCriterionDataList() {
        List<CriterionData> criterionList = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select * from criterion order by criId+0");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                CriterionData criterion = new CriterionData();
                criterion.setCriId(resultSet.getString("criId"));
                criterion.setCriShipType(resultSet.getString("criShipType"));
                criterion.setCriShipCompany(resultSet.getString("criShipCompany"));
                criterion.setCriOutfittingRegion(resultSet.getString("criOutfittingRegion"));
                criterion.setCriName(resultSet.getString("criName"));
                criterion.setCriContent(resultSet.getString("criContent"));
                criterion.setCriFilePath(resultSet.getString("criFilePath"));
                criterion.setTfIdfMapStr(resultSet.getString("tfidf"));

                criterionList.add(criterion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return criterionList;
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

        PreparedStatement preparedStatement = null;

        String sql = "insert into criterion (criShipType, criShipCompany, criOutfittingRegion, criName, criContent, criFilePath) value (?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, criterionData.getCriShipType());
            preparedStatement.setString(2, criterionData.getCriShipCompany());
            preparedStatement.setString(3, criterionData.getCriOutfittingRegion());
            preparedStatement.setString(4, criterionData.getCriName());
            preparedStatement.setString(5, criterionData.getCriContent());
            preparedStatement.setString(6, criterionData.getCriFilePath());

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
     * @param criterionData
     * @param editCriterionDataId
     * @return
     */
    public static boolean update(CriterionData criterionData, String editCriterionDataId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update criterion set criShipType=?, criShipCompany=?, criOutfittingRegion=?, criName=?, criContent=?, criFilePath=? where criId=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, criterionData.getCriShipType());
            preparedStatement.setString(2, criterionData.getCriShipCompany());
            preparedStatement.setString(3, criterionData.getCriOutfittingRegion());
            preparedStatement.setString(4, criterionData.getCriName());
            preparedStatement.setString(5, criterionData.getCriContent());
            preparedStatement.setString(6, criterionData.getCriFilePath());
            preparedStatement.setString(7, editCriterionDataId);

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
     * @param selectedCriterionId
     * @return
     */
    public static boolean delete(String selectedCriterionId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from criterion where criId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedCriterionId);

            int i = preparedStatement.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (flag) System.out.println("操作成功！");
        return flag;
    }
}
