package App.appModel;

import javafx.beans.property.SimpleStringProperty;

public class TempModel {
    private final SimpleStringProperty myTemp;

    public TempModel() {
        this(null);
    }

    public TempModel(String myTemp) {
        this.myTemp = new SimpleStringProperty(myTemp);
    }

    public String getMyTemp() {
        return myTemp.get();
    }

    public SimpleStringProperty myTempProperty() {
        return myTemp;
    }

    public void setMyTemp(String myTemp) {
        this.myTemp.set(myTemp);
    }
}
