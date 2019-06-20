package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class VersionData {
    private SimpleStringProperty version_id;
    private SimpleStringProperty proj_id;
    private SimpleStringProperty version_name;
    private SimpleStringProperty version_description;
    private List<ParamAndValueData> param_value_list;

    public VersionData() {
        this(null, null, null, null);
    }

    public VersionData(String version_id, String proj_id, String version_name, String version_description) {
        this.version_id = new SimpleStringProperty(version_id);
        this.proj_id = new SimpleStringProperty(proj_id);
        this.version_name = new SimpleStringProperty(version_name);
        this.version_description = new SimpleStringProperty(version_description);
        this.param_value_list = new ArrayList<>();
    }

    public String getVersion_id() {
        return version_id.get();
    }

    public SimpleStringProperty version_idProperty() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id.set(version_id);
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

    public String getVersion_name() {
        return version_name.get();
    }

    public SimpleStringProperty version_nameProperty() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name.set(version_name);
    }

    public String getVersion_description() {
        return version_description.get();
    }

    public SimpleStringProperty version_descriptionProperty() {
        return version_description;
    }

    public void setVersion_description(String version_description) {
        this.version_description.set(version_description);
    }

    public List<ParamAndValueData> getParam_value_list() {
        return param_value_list;
    }

    public void setParam_value_list(List<ParamAndValueData> param_value_list) {
        this.param_value_list.clear();
        this.param_value_list.addAll(param_value_list);
    }
}
