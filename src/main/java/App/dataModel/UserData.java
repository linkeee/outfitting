package App.dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class UserData {

    private final SimpleStringProperty name;
    private final SimpleStringProperty tel;
    private final SimpleStringProperty jobNum;
    private final SimpleStringProperty position;
    private final SimpleStringProperty role;
    private final SimpleStringProperty password;

    /**
     * Default constructor.
     */
    public UserData(){
        this(null,null,null,null,null, null);
    }


    /**
     * Constructor with some initial data.
     *
     * @param name
     * @param tel
     * @param jobNum
     * @param position
     * @param role
     * @param password
     */
    public UserData(String name, String tel, String jobNum, String position, String role, String password) {
        this.name = new SimpleStringProperty(name);
        this.tel = new SimpleStringProperty(tel);
        this.jobNum = new SimpleStringProperty(jobNum);
        this.position = new SimpleStringProperty(position);
        this.role = new SimpleStringProperty(role);
        this.password = new SimpleStringProperty(password);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getTel() {
        return tel.get();
    }

    public SimpleStringProperty telProperty() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }

    public String getJobNum() {
        return jobNum.get();
    }

    public SimpleStringProperty jobNumProperty() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum.set(jobNum);
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
