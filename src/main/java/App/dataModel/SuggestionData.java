package App.dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class SuggestionData {

    private final SimpleStringProperty sugId;//意见ID
    private final SimpleStringProperty sugShipCompany;//船东船检公司
    private final SimpleStringProperty sugShipType;//船舶类型
    private final SimpleStringProperty sugOutfittingRegion;//舾装区域
    private final SimpleStringProperty sugProblemDescribe;
    private final SimpleStringProperty sugSolutionDescribe;
    private final SimpleStringProperty sugContent;
    private final SimpleStringProperty sugFilePath;
    private final SimpleStringProperty tfIdfMapStr;

    /**
     * Default constructor.
     */
    public SuggestionData() {
        this(null, null, null, null, null, null, null, null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param sugId
     * @param shipCompany
     * @param sugShipType
     * @param outfittingType
     * @param problemDescribe
     * @param solutionDescribe
     */
    public SuggestionData(String sugId, String shipCompany, String sugShipType, String outfittingType, String problemDescribe, String solutionDescribe, String sugContent, String sugFilePath, String tfIdfMapStr) {
        this.sugId = new SimpleStringProperty(sugId);
        this.sugShipCompany = new SimpleStringProperty(shipCompany);
        this.sugShipType = new SimpleStringProperty(sugShipType);
        this.sugOutfittingRegion = new SimpleStringProperty(outfittingType);
        this.sugProblemDescribe = new SimpleStringProperty(problemDescribe);
        this.sugSolutionDescribe = new SimpleStringProperty(solutionDescribe);
        this.sugContent = new SimpleStringProperty(sugContent);
        this.sugFilePath = new SimpleStringProperty(sugFilePath);
        this.tfIdfMapStr = new SimpleStringProperty(tfIdfMapStr);
    }

    public String getSugShipType() {
        return sugShipType.get();
    }

    public void setSugShipType(String sugShipType) {
        this.sugShipType.set(sugShipType);
    }

    public StringProperty sugShipTypeProperty() {
        return sugShipType;
    }

    public String getSugId() {
        return sugId.get();
    }

    public void setSugId(String sugId) {
        this.sugId.set(sugId);
    }

    public StringProperty sugIdProperty() {
        return sugId;
    }

    public String getSugShipCompany() {
        return sugShipCompany.get();
    }

    public void setSugShipCompany(String sugShipCompany) {
        this.sugShipCompany.set(sugShipCompany);
    }

    public StringProperty sugShipCompanyProperty() {
        return sugShipCompany;
    }

    public String getSugOutfittingRegion() {
        return sugOutfittingRegion.get();
    }

    public void setSugOutfittingRegion(String sugOutfittingRegion) {
        this.sugOutfittingRegion.set(sugOutfittingRegion);
    }

    public StringProperty sugOutfittingRegionProperty() {
        return sugOutfittingRegion;
    }

    public String getSugProblemDescribe() {
        return sugProblemDescribe.get();
    }

    public void setSugProblemDescribe(String sugProblemDescribe) {
        this.sugProblemDescribe.set(sugProblemDescribe);
    }

    public StringProperty sugProblemDescribeProperty() {
        return sugProblemDescribe;
    }

    public String getSugSolutionDescribe() {
        return sugSolutionDescribe.get();
    }

    public void setSugSolutionDescribe(String sugSolutionDescribe) {
        this.sugSolutionDescribe.set(sugSolutionDescribe);
    }

    public StringProperty sugSolutionDescribeProperty() {
        return sugSolutionDescribe;
    }

    public String getSugContent() {
        return sugContent.get();
    }

    public void setSugContent(String sugContent) {
        this.sugContent.set(sugContent);
    }

    public StringProperty sugContentProperty() {
        return sugContent;
    }

    public String getSugFilePath() {
        return sugFilePath.get();
    }

    public void setSugFilePath(String sugFilePath) {
        this.sugFilePath.set(sugFilePath);
    }

    public StringProperty sugFilePathProperty() {
        return sugFilePath;
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
