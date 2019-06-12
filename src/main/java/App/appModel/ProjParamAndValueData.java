package App.appModel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProjParamAndValueData {
    private final SimpleObjectProperty<Object> proj_id;
    private final SimpleStringProperty param_name;
    private final SimpleStringProperty param_value;
    private final SimpleStringProperty param_description;

    public ProjParamAndValueData() {
        this(null, null, null, null);
    }

    public ProjParamAndValueData(Object proj_id, String param_name, String param_value, String param_description) {
        this.proj_id = new SimpleObjectProperty<>(proj_id);
        this.param_name = new SimpleStringProperty(param_name);
        this.param_value = new SimpleStringProperty(param_value);
        this.param_description = new SimpleStringProperty(param_description);
    }

    public Object getProj_id() {
        return proj_id.get();
    }

    public SimpleObjectProperty<Object> proj_idProperty() {
        return proj_id;
    }

    public void setProj_id(Object proj_id) {
        this.proj_id.set(proj_id);
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

    public String getParam_value() {
        return param_value.get();
    }

    public SimpleStringProperty param_valueProperty() {
        return param_value;
    }

    public void setParam_value(String param_value) {
        this.param_value.set(param_value);
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
