package App.dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AnchorRuleData {

    //这个是每个舾装件都有的属性
    private final StringProperty left;
    private final StringProperty center;
    private final StringProperty right;

    public AnchorRuleData() {
        this(null, null, null);
    }

    public AnchorRuleData(String left, String center, String right) {
        this.left = new SimpleStringProperty(left);
        this.center = new SimpleStringProperty(center);
        this.right = new SimpleStringProperty(right);
    }

    public String getLeft() {
        return left.get();
    }

    public StringProperty leftProperty() {
        return left;
    }

    public void setLeft(String left) {
        this.left.set(left);
    }

    public String getCenter() {
        return center.get();
    }

    public StringProperty centerProperty() {
        return center;
    }

    public void setCenter(String center) {
        this.center.set(center);
    }

    public String getRight() {
        return right.get();
    }

    public StringProperty rightProperty() {
        return right;
    }

    public void setRight(String right) {
        this.right.set(right);
    }
}
