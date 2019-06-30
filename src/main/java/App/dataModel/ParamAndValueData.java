package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

public class ParamAndValueData {
//    private final SimpleStringProperty param_value_id;
    private final SimpleStringProperty proj_id;
    private final SimpleStringProperty version_name;
    private final SimpleStringProperty param_id;
    private final SimpleStringProperty outfitting_name;
    private final SimpleStringProperty param_name;
    // 已知为0，待求为1
    private final SimpleStringProperty param_type;
    private final SimpleStringProperty param_description;
    private final SimpleStringProperty param_value;
//    private final SimpleStringProperty remark;

    public ParamAndValueData() {
        this(null, null, null, null, null, null, null, null);
    }

    private ParamAndValueData(String proj_id, String version_name, String param_id, String outfitting_name, String param_name, String param_type, String param_description, String param_value) {
//        this.param_value_id = new SimpleStringProperty(param_value_id);
        this.proj_id = new SimpleStringProperty(proj_id);
        this.version_name = new SimpleStringProperty(version_name);
        this.param_id = new SimpleStringProperty(param_id);
        this.outfitting_name = new SimpleStringProperty(outfitting_name);
        this.param_name = new SimpleStringProperty(param_name);
        this.param_type = new SimpleStringProperty(param_type);
        this.param_description = new SimpleStringProperty(param_description);
        this.param_value = new SimpleStringProperty(param_value);
//        this.remark = new SimpleStringProperty(remark);
    }

//    public String getParam_value_id() {
//        return param_value_id.get();
//    }

//    public SimpleStringProperty param_value_idProperty() {
//        return param_value_id;
//    }

//    public void setParam_value_id(String param_value_id) {
//        this.param_value_id.set(param_value_id);
//    }

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

//    public String getRemark() {
//        return remark.get();
//    }

//    public SimpleStringProperty remarkProperty() {
//        return remark;
//    }

//    public void setRemark(String remark) {
//        this.remark.set(remark);
//    }
}
