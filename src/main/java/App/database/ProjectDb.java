package App.database;

import App.dataModel.ProjectData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDb extends DatabaseItem {

    /**
     * get the project table.
     *
     * @return 以ObservableList返回项目表内容。
     */
    public static List<ProjectData> getProjectList() {
        List<ProjectData> projectList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement("select * from project order by proj_id+0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProjectData projectData = new ProjectData();
                projectData.setProj_id(String.valueOf(resultSet.getInt("proj_id")));
                projectData.setProj_name(resultSet.getString("proj_name"));
                projectData.setProj_create_time(resultSet.getString("proj_create_time"));
                projectData.setProj_modify_time(resultSet.getString("proj_modify_time"));
                projectData.setProj_creator(resultSet.getString("proj_creator"));
                projectData.setProj_description(resultSet.getString("proj_description"));
                projectData.setVersionList(VersionDb.getVersionDataListOfProj(Integer.valueOf(projectData.getProj_id())));

                projectList.add(projectData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connectDB());
        }
        return projectList;
    }

    /**
     * 向project数据库中添加项目
     *
     * @param projectData
     * @return
     */
    public static boolean insert(ProjectData projectData) {
        boolean flag = false;

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
            if (i == 1) flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        return flag;
    }

    /**
     * 修改project数据库
     *
     * @param projectData
     * @param editProjectId 项目名称
     * @return
     */
    public static boolean update(ProjectData projectData, int editProjectId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update project set proj_name=?, proj_create_time=?, proj_modify_time=?, proj_creator=?, proj_description=? where proj_id=?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, projectData.getProj_name());
            preparedStatement.setString(2, projectData.getProj_create_time());
            preparedStatement.setString(3, projectData.getProj_modify_time());
            preparedStatement.setString(4, projectData.getProj_creator());
            preparedStatement.setString(5, projectData.getProj_description());
            preparedStatement.setInt(6, editProjectId);

            int i = preparedStatement.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        return flag;
    }

    /**
     * 删除
     *
     * @param projectId 项目id
     * @return
     */
    public static boolean deleteAProjAndVersionParam(int projectId) {
        List<String> versionNameList = VersionDb.getVersionNameListOfProj(projectId);
        for (String versionName : versionNameList) {
            VersionDb.deleteAVersionAndParam(projectId, versionName);
        }

        boolean flag = false;

        PreparedStatement preparedStatement = null;
        String sql = "delete from project where proj_id = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);

            int i = preparedStatement.executeUpdate();
            if (i == 1) flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        return flag;
    }

    /**
     * 以list返回所有项目的名称
     *
     * @return 项目名称列表
     */
    public static List<String> getProjectNameList() {
        List<String> projectNameList = new ArrayList<>();
        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from project order by proj_id+0");
            ResultSet resultSet = preparedStatement.executeQuery();

            projectNameList.add("");

            while (resultSet.next()) {
                projectNameList.add(resultSet.getString("proj_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        return projectNameList;
    }

    /**
     * 根据项目名称获得项目id
     *
     * @param projectName 项目名称
     * @return 项目id
     */
    public static int getIdByName(String projectName) {
        int ans = 0;
        Connection connection = connectDB();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select proj_id from project where proj_name = ?");
            ps.setString(1, projectName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ans = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return ans;
    }

    /**
     * 根据项目名称返回项目数据模型
     *
     * @param projectName 项目名称
     * @return 返回项目数据模型
     */
    public static ProjectData getOneProjectData(String projectName) {
        ProjectData projectData = new ProjectData();
        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from project where proj_name = ?");
            ps.setString(1, projectName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                projectData.setProj_id(String.valueOf(rs.getString("proj_id")));
                projectData.setProj_name(rs.getString("proj_name"));
                projectData.setProj_create_time(rs.getString("proj_create_time"));
                projectData.setProj_modify_time(rs.getString("proj_modify_time"));
                projectData.setProj_creator(rs.getString("proj_creator"));
                projectData.setProj_description(rs.getString("proj_description"));
                projectData.setVersionList(VersionDb.getVersionDataListOfProj(Integer.valueOf(projectData.getProj_id())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return projectData;
    }

}
