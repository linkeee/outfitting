package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

public class VersionData {
    private final SimpleStringProperty version_id;
    private final SimpleStringProperty proj_id;
    private final SimpleStringProperty version_name;
    private final SimpleStringProperty version_description;
    private final Map<String, String> param_value;

    public VersionData() {
        this(null, null, null, null, null);
    }

    public VersionData(SimpleStringProperty version_id, SimpleStringProperty proj_id, SimpleStringProperty version_name, SimpleStringProperty version_description, Map<String, String> param_value) {
        this.version_id = version_id;
        this.proj_id = proj_id;
        this.version_name = version_name;
        this.version_description = version_description;
        this.param_value = param_value;
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

    public Map<String, String> getParam_value() {
        return param_value;
    }

    public void setParam_value(Map<String, String> param_value) {
        this.param_value.putAll(param_value);
    }
}
