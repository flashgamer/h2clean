package model;

/**
 * Model of a Water Source Report as detailed by 2340 Wiki
 * Fields are filled in from the controller
 * Created by Jonathan on 10/14/2016.
 */
public class WaterSourceReport extends Report {

    private String userName;
    private String location;
    private WaterType type;
    private WaterCondition condition;

    public WaterSourceReport() {

    }


    public String getUserName() {
        return userName;
    }

    public String getLocation() {
        return location;
    }

    public WaterType getType() {
        return type;
    }

    public WaterCondition getCondition() {
        return condition;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(WaterType type) {
        this.type = type;
    }

    public void setCondition(WaterCondition condition) {
        this.condition = condition;
    }
}
