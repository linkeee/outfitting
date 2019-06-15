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

    /**
     * Default constructor.
     */
    public CriterionData(){
        this(null, null, null, null,  null, null, null);
    }


    /**
     * Constructor.
     */
    public CriterionData(String criId, String criShipType, String criShipCompany, String criOutfittingRegion, String criName, String criContent, String criFilePath) {
        this.criId = new SimpleStringProperty(criId);
        this.criShipType = new SimpleStringProperty(criShipType);
        this.criShipCompany = new SimpleStringProperty(criShipCompany);
        this.criOutfittingRegion = new SimpleStringProperty(criOutfittingRegion);
        this.criName = new SimpleStringProperty(criName);
        this.criContent = new SimpleStringProperty(criContent);
        this.criFilePath = new SimpleStringProperty(criFilePath);
    }

    public String getCriId() {
        return criId.get();
    }

    public StringProperty criIdProperty() {
        return criId;
    }

    public void setCriId(String criId) {
        this.criId.set(criId);
    }

    public String getCriShipType() {
        return criShipType.get();
    }

    public StringProperty criShipTypeProperty() {
        return criShipType;
    }

    public void setCriShipType(String criShipType) {
        this.criShipType.set(criShipType);
    }

    public String getCriShipCompany() {
        return criShipCompany.get();
    }

    public SimpleStringProperty criShipCompanyProperty() {
        return criShipCompany;
    }

    public void setCriShipCompany(String criShipCompany) {
        this.criShipCompany.set(criShipCompany);
    }

    public String getCriOutfittingRegion() {
        return criOutfittingRegion.get();
    }

    public StringProperty criOutfittingRegionProperty() {
        return criOutfittingRegion;
    }

    public void setCriOutfittingRegion(String criOutfittingRegion) {
        this.criOutfittingRegion.set(criOutfittingRegion);
    }

    public String getCriName() {
        return criName.get();
    }

    public StringProperty criNameProperty() {
        return criName;
    }

    public void setCriName(String criName) {
        this.criName.set(criName);
    }

    public String getCriContent() {
        return criContent.get();
    }

    public StringProperty criContentProperty() {
        return criContent;
    }

    public void setCriContent(String criContent) {
        this.criContent.set(criContent);
    }

    public String getCriFilePath() {
        return criFilePath.get();
    }

    public SimpleStringProperty criFilePathProperty() {
        return criFilePath;
    }

    public void setCriFilePath(String criFilePath) {
        this.criFilePath.set(criFilePath);
    }
}
