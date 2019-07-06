package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

public class ManufacturerData {
    private final SimpleStringProperty id;
    private final SimpleStringProperty outfitting_name;
    private final SimpleStringProperty manufacturer_name;
    private final SimpleStringProperty outfitting_type;
    private final SimpleStringProperty param_scope;
    private final SimpleStringProperty remark;

    public ManufacturerData() {
        this(null, null, null, null, null, null);
    }

    private ManufacturerData(String id, String outfitting_name, String manufacturer_name, String outfitting_type, String param_scope, String remark) {
        this.id = new SimpleStringProperty(id);
        this.outfitting_name = new SimpleStringProperty(outfitting_name);
        this.manufacturer_name = new SimpleStringProperty(manufacturer_name);
        this.outfitting_type = new SimpleStringProperty(outfitting_type);
        this.param_scope = new SimpleStringProperty(param_scope);
        this.remark = new SimpleStringProperty(remark);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
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

    public String getManufacturer_name() {
        return manufacturer_name.get();
    }

    public SimpleStringProperty manufacturer_nameProperty() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name.set(manufacturer_name);
    }

    public String getOutfitting_type() {
        return outfitting_type.get();
    }

    public SimpleStringProperty outfitting_typeProperty() {
        return outfitting_type;
    }

    public void setOutfitting_type(String outfitting_type) {
        this.outfitting_type.set(outfitting_type);
    }

    public String getParam_scope() {
        return param_scope.get();
    }

    public SimpleStringProperty param_scopeProperty() {
        return param_scope;
    }

    public void setParam_scope(String param_scope) {
        this.param_scope.set(param_scope);
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
