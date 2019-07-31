package App.database;

import App.dataModel.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDb extends DatabaseItem {

    public static UserData getUserByName(String userName) {
        UserData userData = null;
        Connection connection = connectDB();
        PreparedStatement ps = null;
        String sql = "select * from jproject.user where user_name = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userData = new UserData();
                userData.setId(String.valueOf(rs.getInt("id")));
                userData.setName(rs.getString("user_name"));
                userData.setJobNum(rs.getString("user_jobNum"));
                userData.setPosition(rs.getString("user_position"));
                userData.setRole(rs.getString("user_role"));
                userData.setTel(rs.getString("user_tel"));
                userData.setPassword(rs.getString("user_password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return userData;
    }

    public static List<String> getUserNameList() {
        List<String> list = new ArrayList<>();
        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;
        String sql = "select * from user order by id";
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                list.add(resultSet.getString("user_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        return list;
    }

    public static int getSuperAdminNum() {
        int count = 0;
        List<UserData> list = getAllUser();
        for (UserData userData : list) {
            if (userData.getRole().equals("超级管理员"))
                count++;
        }
        return count;
    }

    public static List<UserData> getAllUser() {
        List<UserData> userDataList = new ArrayList<>();

        Connection connection = connectDB();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user order by id");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("user_role").equals("超级管理员"))
                    continue;
                UserData userData = new UserData();
                userData.setId(String.valueOf(resultSet.getInt("id")));
                userData.setJobNum(resultSet.getString("user_jobNum"));
                userData.setName(resultSet.getString("user_name"));
                userData.setTel(resultSet.getString("user_tel"));
                userData.setPosition(resultSet.getString("user_position"));
                userData.setRole(resultSet.getString("user_role"));
                userData.setPassword(resultSet.getString("user_password"));

                userDataList.add(userData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDataList;
    }

    /**
     * query the userTable and return the result of query in observableList way.
     *
     * @param keyword
     * @return
     * @throws SQLException
     */
    public static ObservableList<UserData> query(String keyword) throws SQLException {
        ArrayList<UserData> userDataArrayList = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        String sql = "select * from user where match (user_jobNum, user_name, user_tel, user_position, user_role) against (?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, keyword);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserData userData = new UserData();
            userData.setJobNum(resultSet.getString(1));
            userData.setName(resultSet.getString(2));
            userData.setTel(resultSet.getString(3));
            userData.setPosition(resultSet.getString(4));
            userData.setRole(resultSet.getString(5));

            userDataArrayList.add(userData);
        }
        return FXCollections.observableArrayList(userDataArrayList);
    }

    /**
     * 向users数据库中添加人员信息
     *
     * @param userData
     * @return
     */
    public static boolean insert(UserData userData) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;

        String sql = "insert into user (user_jobNum, user_name, user_tel, user_position, user_role, user_password) value(?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userData.getJobNum());
            preparedStatement.setString(2, userData.getName());
            preparedStatement.setString(3, userData.getTel());
            preparedStatement.setString(4, userData.getPosition());
            preparedStatement.setString(5, userData.getRole());
            preparedStatement.setString(6, userData.getPassword());

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
     *
     * @param userData
     * @return
     */
    public static boolean update(UserData userData, int editUserId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update user set user_jobNum=?, user_name=?, user_tel=?, user_position=?, user_role=? where id=?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userData.getJobNum());
            preparedStatement.setString(2, userData.getName());
            preparedStatement.setString(3, userData.getTel());
            preparedStatement.setString(4, userData.getPosition());
            preparedStatement.setString(5, userData.getRole());
            preparedStatement.setInt(6, editUserId);

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
     *
     * @param jobNum
     * @return
     */
    public static boolean delete(String jobNum) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from user where user_jobNum = ?";
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

    public static boolean updatePwd(String userName, String pwd) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update user set user_password=? where user_name=?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, pwd);
            preparedStatement.setString(2, userName);

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

    public static void setRoleToLow(String userId) {
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("update user set user_role = ? where id = ?");
            ps.setString(1, "用户");
            ps.setInt(2, Integer.valueOf(userId));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
