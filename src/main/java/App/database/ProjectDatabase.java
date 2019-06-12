package App.database;

import App.appModel.ProjectData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

}
