package App.database;

import App.appModel.ProjectData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDatabase extends DatabaseItem {

    /**
     * get the project table.
     * @return 以ObservableList返回项目表内容。
     */
    public static ObservableList<ProjectData> getProjectList() {
        ArrayList<ProjectData> projectList = new ArrayList<>();
        Connection connection = connectDB();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from project order by proj_id+0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProjectData projectData = new ProjectData();
                projectData.setProj_id(resultSet.getObject("proj_id"));
                projectData.setProj_name(resultSet.getString("proj_name"));
                projectData.setProj_create_time(resultSet.getString("proj_create_time"));
                projectData.setProj_modify_time(resultSet.getString("proj_modify_time"));
                projectData.setProj_creator(resultSet.getString("proj_creator"));
                projectData.setProj_description(resultSet.getString("proj_description"));

                projectList.add(projectData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(projectList);
    }

    /**
     * 向project数据库中添加项目
     * @param projectData
     * @return
     */
    public static boolean insert(ProjectData projectData) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;

        String sql = "insert into project (proj_name, proj_create_time, proj_modify_time, proj_creator, proj_description) value(?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, projectData.getProj_name());
            preparedStatement.setString(2, projectData.getProj_create_time());
            preparedStatement.setString(3, projectData.getProj_modify_time());
            preparedStatement.setString(4, projectData.getProj_creator());
            preparedStatement.setString(5, projectData.getProj_description());

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
     * 修改project数据库
     * @param projectData
     * @param editProjectName 项目名称
     * @return
     */
    public static boolean update(ProjectData projectData, String editProjectName) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update project set proj_name=?, proj_create_time=?, proj_modify_time=?, proj_creator=?, proj_description=? where proj_name=?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, projectData.getProj_name());
            preparedStatement.setString(2, projectData.getProj_create_time());
            preparedStatement.setString(3, projectData.getProj_modify_time());
            preparedStatement.setString(4, projectData.getProj_creator());
            preparedStatement.setString(5, projectData.getProj_description());
            preparedStatement.setString(6, editProjectName);

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
     * 删除
     * @param projectId
     * @return
     */
    public static boolean delete(int projectId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from project where proj_id = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);

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
     * 以list返回所有项目的名称
     * @return 项目名称列表
     */
    public static List<String> getProjectNameList() {
        List<String> projectNameList = new ArrayList<>();
        Connection connection = connectDB();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from project order by proj_id+0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projectNameList.add(resultSet.getString("proj_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectNameList;
    }

    public static int getIdByName(String projectName) {
        try {
            PreparedStatement ps = connectDB().prepareStatement("select proj_id from project where proj_name = ?");
            ps.setString(1, projectName);
            ps.execute();
            ps.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

}
