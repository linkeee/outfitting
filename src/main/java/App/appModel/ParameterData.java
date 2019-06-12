package App.appModel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ParameterData {
    private final SimpleIntegerProperty param_id;
    private final SimpleStringProperty param_name;
    private final SimpleBooleanProperty param_type;
    private final SimpleStringProperty param_description;

    public ParameterData() {
        this(null, null, null, null);
    }

    public ParameterData(SimpleIntegerProperty param_id, SimpleStringProperty param_name, SimpleBooleanProperty param_type, SimpleStringProperty param_description) {
        this.param_id = param_id;
        this.param_name = param_name;
        this.param_type = param_type;
        this.param_description = param_description;
    }

    public int getParam_id() {
        return param_id.get();
    }

    public SimpleIntegerProperty param_idProperty() {
        return param_id;
    }

    public void setParam_id(int param_id) {
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

    public boolean isParam_type() {
        return param_type.get();
    }

    public SimpleBooleanProperty param_typeProperty() {
        return param_type;
    }

    public void setParam_type(boolean param_type) {
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
}
