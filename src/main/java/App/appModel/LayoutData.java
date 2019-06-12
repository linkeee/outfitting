package App.appModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a LayoutData.
 *
 */
public class LayoutData {

    private final StringProperty layoutDesignID;
    private final StringProperty layoutDesignName;
    private final StringProperty layoutDesignType;
    private final StringProperty layoutShipType;
    private final StringProperty layoutShipNum;
    private final StringProperty layoutDesignContent;
    private final StringProperty shipLength;
    private final StringProperty shipWidth;
    private final StringProperty shipDepth;
    private final StringProperty shipDraught;
    private final StringProperty shipCoefficients;
    private final StringProperty shipWeight;

    /**
     * Default constructor.
     */
    public LayoutData(){
        this(null,null,null,null,null,null,null,null,null,null,null,null);
    }


    /**
     * Constructor.
     * @param layoutDesignID
     * @param layoutDesignName
     * @param layoutDesignType
     * @param layoutShipType
     * @param layoutShipNum
     * @param shipLength
     * @param shipWidth
     * @param shipDepth
     * @param shipDraught
     * @param shipCoefficients
     * @param shipWeight
     * @param layoutDesignContent
     */
    public LayoutData(String layoutDesignID, String layoutDesignName, String layoutDesignType, String layoutShipType, String layoutShipNum, String shipLength, String shipWidth, String shipDepth, String shipDraught, String shipCoefficients, String shipWeight, String layoutDesignContent) {
        this.layoutDesignID = new SimpleStringProperty(layoutDesignID);
        this.layoutShipType = new SimpleStringProperty(layoutShipType);

        // Some initial dummy data, just for convenient testing.
        this.layoutDesignName = new SimpleStringProperty(layoutDesignName);
        this.layoutDesignType = new SimpleStringProperty(layoutDesignType);
        this.layoutShipNum = new SimpleStringProperty(layoutShipNum);

        this.shipLength=new SimpleStringProperty(shipLength);
        this.shipWidth=new SimpleStringProperty(shipWidth);
        this.shipDepth=new SimpleStringProperty(shipDepth);
        this.shipDraught=new SimpleStringProperty(shipDraught);
        this.shipCoefficients=new SimpleStringProperty(shipCoefficients);
        this.shipWeight=new SimpleStringProperty(shipWeight);
        this.layoutDesignContent=new SimpleStringProperty(layoutDesignContent);

    }

    public String getLayoutDesignID() {
        return layoutDesignID.get();
    }

    public StringProperty layoutDesignIDProperty() {
        return layoutDesignID;
    }

    public void setLayoutDesignID(String layoutDesignID) {
        this.layoutDesignID.set(layoutDesignID);
    }

    public String getLayoutDesignName() {
        return layoutDesignName.get();
    }

    public StringProperty layoutDesignNameProperty() {
        return layoutDesignName;
    }

    public void setLayoutDesignName(String layoutDesignName) {
        this.layoutDesignName.set(layoutDesignName);
    }

    public String getLayoutDesignType() {
        return layoutDesignType.get();
    }

    public StringProperty layoutDesignTypeProperty() {
        return layoutDesignType;
    }

    public void setLayoutDesignType(String layoutDesignType) {
        this.layoutDesignType.set(layoutDesignType);
    }

    public String getLayoutShipType() {
        return layoutShipType.get();
    }

    public StringProperty layoutShipTypeProperty() {
        return layoutShipType;
    }

    public void setLayoutShipType(String layoutShipType) {
        this.layoutShipType.set(layoutShipType);
    }

    public String getLayoutShipNum() {
        return layoutShipNum.get();
    }

    public StringProperty layoutShipNumProperty() {
        return layoutShipNum;
    }

    public void setLayoutShipNum(String layoutShipNum) {
        this.layoutShipNum.set(layoutShipNum);
    }

    public String getLayoutDesignContent() {
        return layoutDesignContent.get();
    }

    public StringProperty layoutDesignContentProperty() {
        return layoutDesignContent;
    }

    public void setLayoutDesignContent(String layoutDesignContent) {
        this.layoutDesignContent.set(layoutDesignContent);
    }

    public String getShipLength() {
        return shipLength.get();
    }

    public StringProperty shipLengthProperty() {
        return shipLength;
    }

    public void setShipLength(String shipLength) {
        this.shipLength.set(shipLength);
    }

    public String getShipWidth() {
        return shipWidth.get();
    }

    public StringProperty shipWidthProperty() {
        return shipWidth;
    }

    public void setShipWidth(String shipWidth) {
        this.shipWidth.set(shipWidth);
    }

    public String getShipDepth() {
        return shipDepth.get();
    }

    public StringProperty shipDepthProperty() {
        return shipDepth;
    }

    public void setShipDepth(String shipDepth) {
        this.shipDepth.set(shipDepth);
    }

    public String getShipDraught() {
        return shipDraught.get();
    }

    public StringProperty shipDraughtProperty() {
        return shipDraught;
    }

    public void setShipDraught(String shipDraught) {
        this.shipDraught.set(shipDraught);
    }

    public String getShipCoefficients() {
        return shipCoefficients.get();
    }

    public StringProperty shipCoefficientsProperty() {
        return shipCoefficients;
    }

    public void setShipCoefficients(String shipCoefficients) {
        this.shipCoefficients.set(shipCoefficients);
    }

    public String getShipWeight() {
        return shipWeight.get();
    }

    public StringProperty shipWeightProperty() {
        return shipWeight;
    }

    public void setShipWeight(String shipWeight) {
        this.shipWeight.set(shipWeight);
    }
}
