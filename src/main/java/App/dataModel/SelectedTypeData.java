package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

public class SelectedTypeData {
    private final SimpleStringProperty proj_id;
    private final SimpleStringProperty version_name;
    private final SimpleStringProperty outfitting_name;
    private final SimpleStringProperty param_name;
    private final SimpleStringProperty param_description;
    private final SimpleStringProperty param_value;
    private final SimpleStringProperty manufacturer_name;
    private final SimpleStringProperty outfitting_type;
    private final SimpleStringProperty param_scope;
    private final SimpleStringProperty remark;

    public SelectedTypeData() {
        this(null, null, null, null, null, null, null, null, null, null);
    }

    public SelectedTypeData(String proj_id, String version_name, String outfitting_name, String param_name, String param_description, String param_value, String manufacturer_name, String outfitting_type, String param_scope, String remark) {
        this.proj_id = new SimpleStringProperty(proj_id);
        this.version_name = new SimpleStringProperty(version_name);
        this.outfitting_name = new SimpleStringProperty(outfitting_name);
        this.param_name = new SimpleStringProperty(param_name);
        this.param_description = new SimpleStringProperty(param_description);
        this.param_value = new SimpleStringProperty(param_value);
        this.manufacturer_name = new SimpleStringProperty(manufacturer_name);
        this.outfitting_type = new SimpleStringProperty(outfitting_type);
        this.param_scope = new SimpleStringProperty(param_scope);
        this.remark = new SimpleStringProperty(remark);
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

    public String getOutfitting_name() {
        return outfitting_name.get();
    }

    public void setOutfitting_name(String outfitting_name) {
        this.outfitting_name.set(outfitting_name);
    }

    public SimpleStringProperty outfitting_nameProperty() {
        return outfitting_name;
    }

    public String getParam_name() {
        return param_name.get();
    }

    public void setParam_name(String param_name) {
        this.param_name.set(param_name);
    }

    public SimpleStringProperty param_nameProperty() {
        return param_name;
    }

    public String getParam_description() {
        return param_description.get();
    }

    public void setParam_description(String param_description) {
        this.param_description.set(param_description);
    }

    public SimpleStringProperty param_descriptionProperty() {
        return param_description;
    }

    public String getParam_value() {
        return param_value.get();
    }

    public void setParam_value(String param_value) {
        this.param_value.set(param_value);
    }

    public SimpleStringProperty param_valueProperty() {
        return param_value;
    }

    public String getManufacturer_name() {
        return manufacturer_name.get();
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name.set(manufacturer_name);
    }

    public SimpleStringProperty manufacturer_nameProperty() {
        return manufacturer_name;
    }

    public String getOutfitting_type() {
        return outfitting_type.get();
    }

    public void setOutfitting_type(String outfitting_type) {
        this.outfitting_type.set(outfitting_type);
    }

    public SimpleStringProperty outfitting_typeProperty() {
        return outfitting_type;
    }

    public String getParam_scope() {
        return param_scope.get();
    }

    public void setParam_scope(String param_scope) {
        this.param_scope.set(param_scope);
    }

    public SimpleStringProperty param_scopeProperty() {
        return param_scope;
    }

    public String getRemark() {
        return remark.get();
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }
}
