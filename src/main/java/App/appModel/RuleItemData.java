package App.appModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for items.
 *
 */
public class RuleItemData {

    //这个是每个舾装件都有的属性
    private final StringProperty ruleId;
    private final StringProperty itemName;
    private final StringProperty itemHyperLink;
    private final StringProperty outfittingType;

    //锚设备
    private final StringProperty anchorType;
    private final StringProperty manufacturer;

    //供油单元
    private final StringProperty gongyouliang;
    private final StringProperty zhongyouOutputPressure;
    private final StringProperty zhongyouOutputTemperature;
    private final StringProperty zhongyouOutputViscosity;

    public RuleItemData() {
        this(null, null, null, null, null, null, null, null, null, null);
    }

    public RuleItemData(String ruleId, String itemName, String itemHyperLink, String outfittingType, String anchorType, String manufacturer, String gongyouliang, String zhongyouOutputPressure, String zhongyouOutputTemperature, String zhongyouOutputViscosity) {
        this.ruleId = new SimpleStringProperty(ruleId);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemHyperLink = new SimpleStringProperty(itemHyperLink);
        this.outfittingType = new SimpleStringProperty(outfittingType);
        this.anchorType = new SimpleStringProperty(anchorType);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.gongyouliang = new SimpleStringProperty(gongyouliang);
        this.zhongyouOutputPressure = new SimpleStringProperty(zhongyouOutputPressure);
        this.zhongyouOutputTemperature = new SimpleStringProperty(zhongyouOutputTemperature);
        this.zhongyouOutputViscosity = new SimpleStringProperty(zhongyouOutputViscosity);
    }

    public String getRuleId() {
        return ruleId.get();
    }

    public StringProperty ruleIdProperty() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId.set(ruleId);
    }

    public String getItemName() {
        return itemName.get();
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getItemHyperLink() {
        return itemHyperLink.get();
    }

    public StringProperty itemHyperLinkProperty() {
        return itemHyperLink;
    }

    public void setItemHyperLink(String itemHyperLink) {
        this.itemHyperLink.set(itemHyperLink);
    }

    public String getOutfittingType() {
        return outfittingType.get();
    }

    public StringProperty outfittingTypeProperty() {
        return outfittingType;
    }

    public void setOutfittingType(String outfittingType) {
        this.outfittingType.set(outfittingType);
    }

    public String getAnchorType() {
        return anchorType.get();
    }

    public StringProperty anchorTypeProperty() {
        return anchorType;
    }

    public void setAnchorType(String anchorType) {
        this.anchorType.set(anchorType);
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public StringProperty manufacturerProperty() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public String getGongyouliang() {
        return gongyouliang.get();
    }

    public StringProperty gongyouliangProperty() {
        return gongyouliang;
    }

    public void setGongyouliang(String gongyouliang) {
        this.gongyouliang.set(gongyouliang);
    }

    public String getZhongyouOutputPressure() {
        return zhongyouOutputPressure.get();
    }

    public StringProperty zhongyouOutputPressureProperty() {
        return zhongyouOutputPressure;
    }

    public void setZhongyouOutputPressure(String zhongyouOutputPressure) {
        this.zhongyouOutputPressure.set(zhongyouOutputPressure);
    }

    public String getZhongyouOutputTemperature() {
        return zhongyouOutputTemperature.get();
    }

    public StringProperty zhongyouOutputTemperatureProperty() {
        return zhongyouOutputTemperature;
    }

    public void setZhongyouOutputTemperature(String zhongyouOutputTemperature) {
        this.zhongyouOutputTemperature.set(zhongyouOutputTemperature);
    }

    public String getZhongyouOutputViscosity() {
        return zhongyouOutputViscosity.get();
    }

    public StringProperty zhongyouOutputViscosityProperty() {
        return zhongyouOutputViscosity;
    }

    public void setZhongyouOutputViscosity(String zhongyouOutputViscosity) {
        this.zhongyouOutputViscosity.set(zhongyouOutputViscosity);
    }
}
