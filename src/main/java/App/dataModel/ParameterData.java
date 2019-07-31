package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

public class ParameterData {
    private final SimpleStringProperty param_id;
    private final SimpleStringProperty param_name;
    // 已知类型在数据库中存0，待求类型在数据库中存1
    private final SimpleStringProperty param_type;
    private final SimpleStringProperty outfitting_name;
    private final SimpleStringProperty param_description;
    private final SimpleStringProperty param_scope;
    private final SimpleStringProperty param_accuracy;
    private final SimpleStringProperty param_unit;

    public ParameterData() {
        this(null, null, null, null, null, null, null, null);
    }

    public ParameterData(String param_id, String param_name, String param_type, String outfitting_name, String param_description, String param_scope, String param_accuracy, String param_unit) {
        this.param_id = new SimpleStringProperty(param_id);
        this.param_name = new SimpleStringProperty(param_name);
        this.param_type = new SimpleStringProperty(param_type);
        this.outfitting_name = new SimpleStringProperty(outfitting_name);
        this.param_description = new SimpleStringProperty(param_description);
        this.param_scope = new SimpleStringProperty(param_scope);
        this.param_accuracy = new SimpleStringProperty(param_accuracy);
        this.param_unit = new SimpleStringProperty(param_unit);
    }

    public String getParam_id() {
        return param_id.get();
    }

    public void setParam_id(String param_id) {
        this.param_id.set(param_id);
    }

    public SimpleStringProperty param_idProperty() {
        return param_id;
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

    public String getParam_type() {
        return param_type.get();
    }

    public void setParam_type(String param_type) {
        this.param_type.set(param_type);
    }

    public SimpleStringProperty param_typeProperty() {
        return param_type;
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

    public String getParam_description() {
        return param_description.get();
    }

    public void setParam_description(String param_description) {
        this.param_description.set(param_description);
    }

    public SimpleStringProperty param_descriptionProperty() {
        return param_description;
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

    public String getParam_accuracy() {
        return param_accuracy.get();
    }

    public void setParam_accuracy(String param_accuracy) {
        this.param_accuracy.set(param_accuracy);
    }

    public SimpleStringProperty param_accuracyProperty() {
        return param_accuracy;
    }

    public String getParam_unit() {
        return param_unit.get();
    }

    public void setParam_unit(String param_unit) {
        this.param_unit.set(param_unit);
    }

    public SimpleStringProperty param_unitProperty() {
        return param_unit;
    }
}
