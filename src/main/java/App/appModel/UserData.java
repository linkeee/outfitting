package App.appModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class UserData {

    private final StringProperty name;
    private final StringProperty tel;
    private final StringProperty jobNum;
    private final StringProperty position;
    private final StringProperty role;

    /**
     * Default constructor.
     */
    public UserData(){
        this(null,null,null,null,null);
    }


    /**
     * Constructor with some initial data.
     *
     * @param name
     * @param accountNum
     * @param jobNum
     * @param position
     * @param role
     */
    public UserData(String name, String accountNum, String jobNum, String position, String role) {
        this.name = new SimpleStringProperty(name);
        this.tel = new SimpleStringProperty(accountNum);

        // Some initial dummy data, just for convenient testing.
        this.jobNum = new SimpleStringProperty(jobNum);
        this.position = new SimpleStringProperty(position);
        this.role = new SimpleStringProperty(role);
    }

  public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
        return name;
    }

    public String getTelNum() {
        return tel.get();
    }

    public void setTelNum(String telNum) {
        this.tel.set(telNum);
    }

    public StringProperty telProperty() {
        return tel;
    }

    public String getJobNum() {
        return jobNum.get();
    }

    public void setJobNum(String jobNum) {
        this.jobNum.set(jobNum);
    }

    public StringProperty jobNumProperty() {
        return jobNum;
    }
    
    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public StringProperty positionProperty() {
        return position;
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public StringProperty roleProperty() {
        return role;
    }

}
