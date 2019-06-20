package App.database;

import App.dataModel.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class UserDb extends DatabaseItem {

    /**
     * The getter and setter methods of variable userDataList.
     * @return
     */
    public static ObservableList<UserData> getUserDataList() {
        ArrayList<UserData> list = getAllUser();
        return FXCollections.observableArrayList(list);
    }

    private static ArrayList<UserData> getAllUser() {
        ArrayList<UserData> userDataList = new ArrayList<>();

        Connection connection = connectDB();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user order by jobNum+0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserData userData = new UserData();
                userData.setJobNum(resultSet.getString(1));
                userData.setName(resultSet.getString(2));
                userData.setTelNum(resultSet.getString(3));
                userData.setPosition(resultSet.getString(4));
                userData.setRole(resultSet.getString(5));

                userDataList.add(userData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDataList;
    }

    /**
     * query the userTable and return the result of query in observableList way.
     * @param keyword
     * @return
     * @throws SQLException
     */
    public static ObservableList<UserData> query(String keyword) throws SQLException {
        ArrayList<UserData> userDataArrayList = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        String sql = "select * from user where match (jobNum, name, tel, position, role) against (?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, keyword);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserData userData = new UserData();
            userData.setJobNum(resultSet.getString(1));
            userData.setName(resultSet.getString(2));
            userData.setTelNum(resultSet.getString(3));
            userData.setPosition(resultSet.getString(4));
            userData.setRole(resultSet.getString(5));

            userDataArrayList.add(userData);
        }
        return FXCollections.observableArrayList(userDataArrayList);
    }

    /**
     * 向users数据库中添加人员信息
     * @param userData
     * @return
     */
    public static boolean insert(UserData userData) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;

        String sql = "insert into user (jobNum, name, tel, position, role) value(?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userData.getJobNum());
            preparedStatement.setString(2, userData.getName());
            preparedStatement.setString(3, userData.getTelNum());
            preparedStatement.setString(4, userData.getPosition());
            preparedStatement.setString(5, userData.getRole());

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
     * 修改users数据库中的人员信息
     * @param userData
     * @return
     */
    public static boolean update(UserData userData, String editUserJobNum) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update user set jobNum=?, name=?, tel=?, position=?, role=? where jobNum=?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userData.getJobNum());
            preparedStatement.setString(2, userData.getName());
            preparedStatement.setString(3, userData.getTelNum());
            preparedStatement.setString(4, userData.getPosition());
            preparedStatement.setString(5, userData.getRole());
            preparedStatement.setString(6, editUserJobNum);

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
     * 删除users数据库中的人员条目
     * @param jobNum
     * @return
     */
    public static boolean delete(String jobNum) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from user where jobNum = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, jobNum);

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
