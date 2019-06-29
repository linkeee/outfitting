package App.dataModel;

import javafx.beans.DefaultProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a ExperienceData.
 *
 */
public class ExperienceData {

    private final StringProperty expId;
    private final StringProperty expShipType;
    private final StringProperty expOutfittingRegion;
    private final StringProperty expName;
    private final StringProperty expContent;
    private final StringProperty expFilePath;

    /**
     * Default constructor.
     */
    public ExperienceData(){
        this(null,null,null,null,null,null);
    }


    /**
     * Constructor with some initial data.
     *
     */
    public ExperienceData(String expId, String expShipType, String expOutfittingRegion, String expName, String expContent, String expFilePath) {
        this.expId = new SimpleStringProperty(expId);
        this.expShipType = new SimpleStringProperty(expShipType);
        this.expOutfittingRegion = new SimpleStringProperty(expOutfittingRegion);
        this.expName = new SimpleStringProperty(expName);
        this.expContent = new SimpleStringProperty(expContent);
        this.expFilePath = new SimpleStringProperty(expFilePath);
    }

    public String getExpId() {
        return expId.get();
    }

    public StringProperty expIdProperty() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId.set(expId);
    }

    public String getExpShipType() {
        return expShipType.get();
    }

    public StringProperty expShipTypeProperty() {
        return expShipType;
    }

    public void setExpShipType(String expShipType) {
        this.expShipType.set(expShipType);
    }

    public String getExpOutfittingRegion() {
        return expOutfittingRegion.get();
    }

    public StringProperty expOutfittingRegionProperty() {
        return expOutfittingRegion;
    }

    public void setExpOutfittingRegion(String expOutfittingRegion) {
        this.expOutfittingRegion.set(expOutfittingRegion);
    }

    public String getExpName() {
        return expName.get();
    }

    public StringProperty expNameProperty() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName.set(expName);
    }

    public String getExpContent() {
        return expContent.get();
    }

    public StringProperty expContentProperty() {
        return expContent;
    }

    public void setExpContent(String expContent) {
        this.expContent.set(expContent);
    }

    public String getExpFilePath() {
        return expFilePath.get();
    }

    public StringProperty expFilePathProperty() {
        return expFilePath;
    }

    public void setExpFilePath(String expFilePath) {
        this.expFilePath.set(expFilePath);
    }
}
