package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

public class ProjParamAndValueData {
    private final SimpleStringProperty proj_param_value_id;
    private final SimpleStringProperty proj_id;
    private final SimpleStringProperty version;
    private final SimpleStringProperty param_id;
    private final SimpleStringProperty outfitting_name;
    private final SimpleStringProperty param_name;
    private final SimpleStringProperty param_type;
    private final SimpleStringProperty param_description;
    private final SimpleStringProperty param_value;
    private final SimpleStringProperty remark;

    public ProjParamAndValueData() {
        this(null, null, null, null, null, null, null, null, null, null);
    }

    public ProjParamAndValueData(SimpleStringProperty proj_param_value_id, SimpleStringProperty proj_id, SimpleStringProperty version, SimpleStringProperty param_id, SimpleStringProperty outfitting_name, SimpleStringProperty param_name, SimpleStringProperty param_type, SimpleStringProperty param_description, SimpleStringProperty param_value, SimpleStringProperty remark) {
        this.proj_param_value_id = proj_param_value_id;
        this.proj_id = proj_id;
        this.version = version;
        this.param_id = param_id;
        this.outfitting_name = outfitting_name;
        this.param_name = param_name;
        this.param_type = param_type;
        this.param_description = param_description;
        this.param_value = param_value;
        this.remark = remark;
    }

    public String getProj_param_value_id() {
        return proj_param_value_id.get();
    }

    public SimpleStringProperty proj_param_value_idProperty() {
        return proj_param_value_id;
    }

    public void setProj_param_value_id(String proj_param_value_id) {
        this.proj_param_value_id.set(proj_param_value_id);
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

    public String getVersion() {
        return version.get();
    }

    public SimpleStringProperty versionProperty() {
        return version;
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public String getParam_id() {
        return param_id.get();
    }

    public SimpleStringProperty param_idProperty() {
        return param_id;
    }

    public void setParam_id(String param_id) {
        this.param_id.set(param_id);
    }

    public String getOutfitting_name() {
        return outfitting_name.get();
    }

    public SimpleStringProperty outfitting_nameProperty() {
        return outfitting_name;
    }

    public void setOutfitting_name(String outfitting_name) {
        this.outfitting_name.set(outfitting_name);
    }

    public String getParam_name() {
        return param_name.get();
    }

    public SimpleStringProperty param_nameProperty() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name.set(param_name);
    }

    public String getParam_type() {
        return param_type.get();
    }

    public SimpleStringProperty param_typeProperty() {
        return param_type;
    }

    public void setParam_type(String param_type) {
        this.param_type.set(param_type);
    }

    public String getParam_description() {
        return param_description.get();
    }

    public SimpleStringProperty param_descriptionProperty() {
        return param_description;
    }

    public void setParam_description(String param_description) {
        this.param_description.set(param_description);
    }

    public String getParam_value() {
        return param_value.get();
    }

    public SimpleStringProperty param_valueProperty() {
        return param_value;
    }

    public void setParam_value(String param_value) {
        this.param_value.set(param_value);
    }

    public String getRemark() {
        return remark.get();
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }
}
