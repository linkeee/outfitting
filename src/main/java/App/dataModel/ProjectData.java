package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class ProjectData {
    private SimpleStringProperty proj_id;
    private SimpleStringProperty proj_name;
    private SimpleStringProperty proj_create_time;
    private SimpleStringProperty proj_modify_time;
    private SimpleStringProperty proj_creator;
    private SimpleStringProperty proj_description;
    private List<VersionData> versionList;

    public ProjectData() {
        this(null, null, null, null, null, null);
    }

    public ProjectData(String proj_id, String proj_name, String proj_create_time, String proj_modify_time, String proj_creator, String proj_description) {
        this.proj_id = new SimpleStringProperty(proj_id);
        this.proj_name = new SimpleStringProperty(proj_name);
        this.proj_create_time = new SimpleStringProperty(proj_create_time);
        this.proj_modify_time = new SimpleStringProperty(proj_modify_time);
        this.proj_creator = new SimpleStringProperty(proj_creator);
        this.proj_description = new SimpleStringProperty(proj_description);
        this.versionList = new ArrayList<>();
    }

    public String getProj_id() {
        return proj_id.get();
    }

    public SimpleStringProperty proj_idProperty() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id.set(proj_id);
    }

    public String getProj_name() {
        return proj_name.get();
    }

    public SimpleStringProperty proj_nameProperty() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name.set(proj_name);
    }

    public String getProj_create_time() {
        return proj_create_time.get();
    }

    public SimpleStringProperty proj_create_timeProperty() {
        return proj_create_time;
    }

    public void setProj_create_time(String proj_create_time) {
        this.proj_create_time.set(proj_create_time);
    }

    public String getProj_modify_time() {
        return proj_modify_time.get();
    }

    public SimpleStringProperty proj_modify_timeProperty() {
        return proj_modify_time;
    }

    public void setProj_modify_time(String proj_modify_time) {
        this.proj_modify_time.set(proj_modify_time);
    }

    public String getProj_creator() {
        return proj_creator.get();
    }

    public SimpleStringProperty proj_creatorProperty() {
        return proj_creator;
    }

    public void setProj_creator(String proj_creator) {
        this.proj_creator.set(proj_creator);
    }

    public String getProj_description() {
        return proj_description.get();
    }

    public SimpleStringProperty proj_descriptionProperty() {
        return proj_description;
    }

    public void setProj_description(String proj_description) {
        this.proj_description.set(proj_description);
    }

    public List<VersionData> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<VersionData> versionList) {
//        this.versionList = versionList;
        this.versionList.clear();
        this.versionList.addAll(versionList);
    }
}
