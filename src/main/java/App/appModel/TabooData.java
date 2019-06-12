package App.appModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class TabooData {

    private final StringProperty jinjiID;
    private final StringProperty shipType;
    private final StringProperty outfittingType;
    private final StringProperty jinjiName;
    private final StringProperty jinjiContent;

    private final StringProperty jkeyword1;
    private final StringProperty jkeyword2;
    private final StringProperty jkeyword3;
    private final StringProperty jjinjiLevel;

    /**
     * Default constructor.
     */
    public TabooData(){
        this(null,null,null,null,null,null,null,null,null);
    }


    /**
     * Constructor with some initial data.
     *
     * @param jinjiID
     * @param shipType
     * @param outfittingType
     * @param jkeyword1
     * @param jkeyword2
     * @param jkeyword3
     * @param jjinjiaLevel
     * @param jinjiName
     * @param jinjiContent
     */
    public TabooData(String jinjiID, String shipType, String outfittingType, String jkeyword1, String jkeyword2, String jkeyword3, String jjinjiaLevel, String jinjiName, String jinjiContent) {
        this.jinjiID = new SimpleStringProperty(jinjiID);
        this.shipType = new SimpleStringProperty(shipType);

        // Some initial dummy data, just for convenient testing.
        this.outfittingType = new SimpleStringProperty(outfittingType);
        this.jinjiName = new SimpleStringProperty(jinjiName);
        this.jinjiContent = new SimpleStringProperty(jinjiContent);

        this.jkeyword1=new SimpleStringProperty(jkeyword1);
        this.jkeyword2=new SimpleStringProperty(jkeyword2);
        this.jkeyword3=new SimpleStringProperty(jkeyword3);
        this.jjinjiLevel=new SimpleStringProperty(jjinjiaLevel);

    }

    public String getJinjiID() {
        return jinjiID.get();
    }

    public StringProperty jinjiIDProperty() {
        return jinjiID;
    }

    public void setJinjiID(String jinjiID) {
        this.jinjiID.set(jinjiID);
    }

    public String getShipType() {
        return shipType.get();
    }

    public StringProperty shipTypeProperty() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType.set(shipType);
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

    public String getJinjiName() {
        return jinjiName.get();
    }

    public StringProperty jinjiNameProperty() {
        return jinjiName;
    }

    public void setJinjiName(String jinjiName) {
        this.jinjiName.set(jinjiName);
    }

    public String getJinjiContent() {
        return jinjiContent.get();
    }

    public StringProperty jinjiContentProperty() {
        return jinjiContent;
    }

    public void setJinjiContent(String jinjiContent) {
        this.jinjiContent.set(jinjiContent);
    }
    public String getJkeyword1() {
        return jkeyword1.get();
    }

    public StringProperty jkeyword1Property() {
        return jkeyword1;
    }

    public void setJkeyword1(String jkeyword1) {
        this.jkeyword1.set(jkeyword1);
    }

    public String getJkeyword2() {
        return jkeyword2.get();
    }

    public StringProperty jkeyword2Property() {
        return jkeyword2;
    }

    public void setJkeyword2(String jkeyword2) {
        this.jkeyword2.set(jkeyword2);
    }

    public String getJkeyword3() {
        return jkeyword3.get();
    }

    public StringProperty jkeyword3Property() {
        return jkeyword3;
    }

    public void setJkeyword3(String jkeyword3) {
        this.jkeyword3.set(jkeyword3);
    }

    public String getJjinjiLevel() {
        return jjinjiLevel.get();
    }

    public StringProperty jjinjiLevelProperty() {
        return jjinjiLevel;
    }

    public void setJjinjiLevel(String jjinjiLevel) {
        this.jjinjiLevel.set(jjinjiLevel);
    }
}
