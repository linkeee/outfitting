package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

public class ParameterData {
    private final SimpleStringProperty param_id;
    private final SimpleStringProperty param_name;
    private final SimpleStringProperty param_type;
    private final SimpleStringProperty outfitting_name;
    private final SimpleStringProperty param_description;

    public ParameterData() {
        this(null, null, null, null, null);
    }

    public ParameterData(SimpleStringProperty param_id, SimpleStringProperty param_name, SimpleStringProperty param_type, SimpleStringProperty outfitting_name, SimpleStringProperty param_description) {
        this.param_id = param_id;
        this.param_name = param_name;
        this.param_type = param_type;
        this.outfitting_name = outfitting_name;
        this.param_description = param_description;
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

    public String getOutfitting_name() {
        return outfitting_name.get();
    }

    public SimpleStringProperty outfitting_nameProperty() {
        return outfitting_name;
    }

    public void setOutfitting_name(String outfitting_name) {
        this.outfitting_name.set(outfitting_name);
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
}
