package App.dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a ExperienceData.
 */
public class ExperienceData {

    private final SimpleStringProperty expId;
    private final SimpleStringProperty expShipType;
    private final SimpleStringProperty expOutfittingRegion;
    private final SimpleStringProperty expName;
    private final SimpleStringProperty expContent;
    private final SimpleStringProperty expFilePath;
    private final SimpleStringProperty tfIdfMapStr;

    /**
     * Default constructor.
     */
    public ExperienceData() {
        this(null, null, null, null, null, null, null);
    }


    /**
     * Constructor with some initial data.
     */
    public ExperienceData(String expId, String expShipType, String expOutfittingRegion, String expName, String expContent, String expFilePath, String tfIdfMapStr) {
        this.expId = new SimpleStringProperty(expId);
        this.expShipType = new SimpleStringProperty(expShipType);
        this.expOutfittingRegion = new SimpleStringProperty(expOutfittingRegion);
        this.expName = new SimpleStringProperty(expName);
        this.expContent = new SimpleStringProperty(expContent);
        this.expFilePath = new SimpleStringProperty(expFilePath);
        this.tfIdfMapStr = new SimpleStringProperty(tfIdfMapStr);
    }

    public String getExpId() {
        return expId.get();
    }

    public void setExpId(String expId) {
        this.expId.set(expId);
    }

    public StringProperty expIdProperty() {
        return expId;
    }

    public String getExpShipType() {
        return expShipType.get();
    }

    public void setExpShipType(String expShipType) {
        this.expShipType.set(expShipType);
    }

    public StringProperty expShipTypeProperty() {
        return expShipType;
    }

    public String getExpOutfittingRegion() {
        return expOutfittingRegion.get();
    }

    public void setExpOutfittingRegion(String expOutfittingRegion) {
        this.expOutfittingRegion.set(expOutfittingRegion);
    }

    public StringProperty expOutfittingRegionProperty() {
        return expOutfittingRegion;
    }

    public String getExpName() {
        return expName.get();
    }

    public void setExpName(String expName) {
        this.expName.set(expName);
    }

    public StringProperty expNameProperty() {
        return expName;
    }

    public String getExpContent() {
        return expContent.get();
    }

    public void setExpContent(String expContent) {
        this.expContent.set(expContent);
    }

    public StringProperty expContentProperty() {
        return expContent;
    }

    public String getExpFilePath() {
        return expFilePath.get();
    }

    public void setExpFilePath(String expFilePath) {
        this.expFilePath.set(expFilePath);
    }

    public StringProperty expFilePathProperty() {
        return expFilePath;
    }

    public String getTfIdfMapStr() {
        return tfIdfMapStr.get();
    }

    public void setTfIdfMapStr(String tfIdfMapStr) {
        this.tfIdfMapStr.set(tfIdfMapStr);
    }

    public SimpleStringProperty tfIdfMapStrProperty() {
        return tfIdfMapStr;
    }
}
