package App.dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class SuggestionData {

    private final StringProperty sugId;//意见ID
    private final StringProperty sugShipCompany;//船东船检公司
    private final StringProperty sugShipType;//船舶类型
    private final StringProperty sugOutfittingRegion;//舾装区域
    private final StringProperty sugProblemDescribe;
    private final StringProperty sugSolutionDescribe;
    private final StringProperty sugContent;
    private final StringProperty sugFilePath;
    /**
     * Default constructor.
     */
    public SuggestionData(){
        this(null, null, null,null,null,null,null,null);
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
    public SuggestionData(String sugId, String shipCompany, String sugShipType, String outfittingType, String problemDescribe, String solutionDescribe, String sugContent, String sugFilePath) {
        this.sugId = new SimpleStringProperty(sugId);
        this.sugShipCompany = new SimpleStringProperty(shipCompany);
        this.sugShipType = new SimpleStringProperty(sugShipType);
        this.sugOutfittingRegion = new SimpleStringProperty(outfittingType);
        this.sugProblemDescribe =new SimpleStringProperty(problemDescribe);
        this.sugSolutionDescribe = new SimpleStringProperty(solutionDescribe);
        this.sugContent = new SimpleStringProperty(sugContent);
        this.sugFilePath = new SimpleStringProperty(sugFilePath);
    }

    public String getSugShipType() {
        return sugShipType.get();
    }

    public StringProperty sugShipTypeProperty() {
        return sugShipType;
    }

    public void setSugShipType(String sugShipType) {
        this.sugShipType.set(sugShipType);
    }

    public String getSugId() {
        return sugId.get();
    }

    public StringProperty sugIdProperty() {
        return sugId;
    }

    public void setSugId(String sugId) {
        this.sugId.set(sugId);
    }

    public String getSugShipCompany() {
        return sugShipCompany.get();
    }

    public StringProperty sugShipCompanyProperty() {
        return sugShipCompany;
    }

    public void setSugShipCompany(String sugShipCompany) {
        this.sugShipCompany.set(sugShipCompany);
    }

    public String getSugOutfittingRegion() {
        return sugOutfittingRegion.get();
    }

    public StringProperty sugOutfittingRegionProperty() {
        return sugOutfittingRegion;
    }

    public void setSugOutfittingRegion(String sugOutfittingRegion) {
        this.sugOutfittingRegion.set(sugOutfittingRegion);
    }

    public String getSugProblemDescribe() {
        return sugProblemDescribe.get();
    }

    public StringProperty sugProblemDescribeProperty() {
        return sugProblemDescribe;
    }

    public void setSugProblemDescribe(String sugProblemDescribe) {
        this.sugProblemDescribe.set(sugProblemDescribe);
    }

    public String getSugSolutionDescribe() {
        return sugSolutionDescribe.get();
    }

    public StringProperty sugSolutionDescribeProperty() {
        return sugSolutionDescribe;
    }

    public void setSugSolutionDescribe(String sugSolutionDescribe) {
        this.sugSolutionDescribe.set(sugSolutionDescribe);
    }

    public String getSugContent() {
        return sugContent.get();
    }

    public StringProperty sugContentProperty() {
        return sugContent;
    }

    public void setSugContent(String sugContent) {
        this.sugContent.set(sugContent);
    }

    public String getSugFilePath() {
        return sugFilePath.get();
    }

    public StringProperty sugFilePathProperty() {
        return sugFilePath;
    }

    public void setSugFilePath(String sugFilePath) {
        this.sugFilePath.set(sugFilePath);
    }
}
