package App.dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class CriterionData {

    private final SimpleStringProperty criId;
    private final SimpleStringProperty criShipType;
    private final SimpleStringProperty criShipCompany;
    private final SimpleStringProperty criOutfittingRegion;
    private final SimpleStringProperty criName;
    private final SimpleStringProperty criContent;
    private final SimpleStringProperty criFilePath;
    private final SimpleStringProperty tfIdfMapStr;

    /**
     * Default constructor.
     */
    public CriterionData() {
        this(null, null, null, null, null, null, null, null);
    }


    /**
     * Constructor.
     */
    public CriterionData(String criId, String criShipType, String criShipCompany, String criOutfittingRegion, String criName, String criContent, String criFilePath, String tfIdfMapStr) {
        this.criId = new SimpleStringProperty(criId);
        this.criShipType = new SimpleStringProperty(criShipType);
        this.criShipCompany = new SimpleStringProperty(criShipCompany);
        this.criOutfittingRegion = new SimpleStringProperty(criOutfittingRegion);
        this.criName = new SimpleStringProperty(criName);
        this.criContent = new SimpleStringProperty(criContent);
        this.criFilePath = new SimpleStringProperty(criFilePath);
        this.tfIdfMapStr = new SimpleStringProperty(tfIdfMapStr);
    }

    public String getCriId() {
        return criId.get();
    }

    public void setCriId(String criId) {
        this.criId.set(criId);
    }

    public StringProperty criIdProperty() {
        return criId;
    }

    public String getCriShipType() {
        return criShipType.get();
    }

    public void setCriShipType(String criShipType) {
        this.criShipType.set(criShipType);
    }

    public StringProperty criShipTypeProperty() {
        return criShipType;
    }

    public String getCriShipCompany() {
        return criShipCompany.get();
    }

    public void setCriShipCompany(String criShipCompany) {
        this.criShipCompany.set(criShipCompany);
    }

    public SimpleStringProperty criShipCompanyProperty() {
        return criShipCompany;
    }

    public String getCriOutfittingRegion() {
        return criOutfittingRegion.get();
    }

    public void setCriOutfittingRegion(String criOutfittingRegion) {
        this.criOutfittingRegion.set(criOutfittingRegion);
    }

    public StringProperty criOutfittingRegionProperty() {
        return criOutfittingRegion;
    }

    public String getCriName() {
        return criName.get();
    }

    public void setCriName(String criName) {
        this.criName.set(criName);
    }

    public StringProperty criNameProperty() {
        return criName;
    }

    public String getCriContent() {
        return criContent.get();
    }

    public void setCriContent(String criContent) {
        this.criContent.set(criContent);
    }

    public StringProperty criContentProperty() {
        return criContent;
    }

    public String getCriFilePath() {
        return criFilePath.get();
    }

    public void setCriFilePath(String criFilePath) {
        this.criFilePath.set(criFilePath);
    }

    public SimpleStringProperty criFilePathProperty() {
        return criFilePath;
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
