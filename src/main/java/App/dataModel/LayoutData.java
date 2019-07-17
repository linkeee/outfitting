package App.dataModel;

import javafx.beans.property.SimpleStringProperty;

public class LayoutData {

    private final SimpleStringProperty id;
    private final SimpleStringProperty outfitting_name;
    private final SimpleStringProperty shipType;
    private final SimpleStringProperty shipNum;
    private final SimpleStringProperty shipTypeCoefficient;
    private final SimpleStringProperty shipLoad;
    private final SimpleStringProperty shipLength;
    private final SimpleStringProperty shipWidth;
    private final SimpleStringProperty shipDepth;
    private final SimpleStringProperty shipDraught;
    private final SimpleStringProperty layoutContent;
    private final SimpleStringProperty filePath;

    public LayoutData() {
        this(null, null, null, null, null, null, null, null, null, null, null, null);
    }

    private LayoutData(String id, String outfitting_name, String shipType, String shipNum, String shipTypeCoefficient, String shipLoad, String shipLength, String shipWidth, String shipDepth, String shipDraught, String layoutContent, String filePath) {
        this.id = new SimpleStringProperty(id);
        this.outfitting_name = new SimpleStringProperty(outfitting_name);
        this.shipType = new SimpleStringProperty(shipType);
        this.shipNum = new SimpleStringProperty(shipNum);
        this.shipTypeCoefficient = new SimpleStringProperty(shipTypeCoefficient);
        this.shipLoad = new SimpleStringProperty(shipLoad);
        this.shipLength = new SimpleStringProperty(shipLength);
        this.shipWidth = new SimpleStringProperty(shipWidth);
        this.shipDepth = new SimpleStringProperty(shipDepth);
        this.shipDraught = new SimpleStringProperty(shipDraught);
        this.layoutContent = new SimpleStringProperty(layoutContent);
        this.filePath = new SimpleStringProperty(filePath);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty() {
        return id;
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

    public String getShipType() {
        return shipType.get();
    }

    public void setShipType(String shipType) {
        this.shipType.set(shipType);
    }

    public SimpleStringProperty shipTypeProperty() {
        return shipType;
    }

    public String getShipNum() {
        return shipNum.get();
    }

    public void setShipNum(String shipNum) {
        this.shipNum.set(shipNum);
    }

    public SimpleStringProperty shipNumProperty() {
        return shipNum;
    }

    public String getShipTypeCoefficient() {
        return shipTypeCoefficient.get();
    }

    public void setShipTypeCoefficient(String shipTypeCoefficient) {
        this.shipTypeCoefficient.set(shipTypeCoefficient);
    }

    public SimpleStringProperty shipTypeCoefficientProperty() {
        return shipTypeCoefficient;
    }

    public String getShipLoad() {
        return shipLoad.get();
    }

    public void setShipLoad(String shipLoad) {
        this.shipLoad.set(shipLoad);
    }

    public SimpleStringProperty shipLoadProperty() {
        return shipLoad;
    }

    public String getShipLength() {
        return shipLength.get();
    }

    public void setShipLength(String shipLength) {
        this.shipLength.set(shipLength);
    }

    public SimpleStringProperty shipLengthProperty() {
        return shipLength;
    }

    public String getShipWidth() {
        return shipWidth.get();
    }

    public void setShipWidth(String shipWidth) {
        this.shipWidth.set(shipWidth);
    }

    public SimpleStringProperty shipWidthProperty() {
        return shipWidth;
    }

    public String getShipDepth() {
        return shipDepth.get();
    }

    public void setShipDepth(String shipDepth) {
        this.shipDepth.set(shipDepth);
    }

    public SimpleStringProperty shipDepthProperty() {
        return shipDepth;
    }

    public String getShipDraught() {
        return shipDraught.get();
    }

    public void setShipDraught(String shipDraught) {
        this.shipDraught.set(shipDraught);
    }

    public SimpleStringProperty shipDraughtProperty() {
        return shipDraught;
    }

    public String getLayoutContent() {
        return layoutContent.get();
    }

    public void setLayoutContent(String layoutContent) {
        this.layoutContent.set(layoutContent);
    }

    public SimpleStringProperty layoutContentProperty() {
        return layoutContent;
    }

    public String getFilePath() {
        return filePath.get();
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    public SimpleStringProperty filePathProperty() {
        return filePath;
    }
}
