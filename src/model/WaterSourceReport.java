package model;

import com.lynden.gmapsfx.javascript.object.Marker;

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
    private Marker marker;

    /**
     * Default no-args constructor
     */
    public WaterSourceReport() {

    }


    /**
     * Gets the Username of the Account that submitted this WaterSourceReport.
     * @return the Username of the Account
     * that submitted this WaterSourceReport.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets a String representation of the Location of this WaterSourceReport.
     * @return a String representation of the
     * Location of this WaterSourceReport.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the WaterType of this WaterSourceReport.
     * @return the WaterType of this WaterSourceReport.
     */
    public WaterType getType() {
        return type;
    }

    /**
     * Gets the WaterCondition of this WaterSourceReport.
     * @return the WaterCondition of this WaterSourceReport.
     */
    public WaterCondition getCondition() {
        return condition;
    }

    /**
     * Sets the username of the Account that submitted this WaterSourceReport
     * to the specified username.
     * @param userName the username to set this WaterSourceReport's username to.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the location of this WaterSourceReport to the specified Location.
     * @param location A String representation of a location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the WaterType of the WaterSourceReport to the specified WaterType.
     * @param type the WaterType of the WaterSourceReport.
     */
    public void setType(WaterType type) {
        this.type = type;
    }

    /**
     * Sets the WaterCondition of the
     * WaterSourceReport to the specified WaterCondition.
     * @param condition the WaterCondition of the WaterSourceReport.
     */
    public void setCondition(WaterCondition condition) {
        this.condition = condition;
    }

    /**
     * Gets the Marker that represents this report on the Availability Report.
     * @return the Marker that represents this report on the Availability Report
     */
    public Marker getMarker() {
        return marker;
    }

    /**
     * Sets the Marker that represents this report on the Availability Report
     * to the specified Marker.
     * @param marker the Marker to set this Report's Marker to.
     */
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public boolean equals(Object other) {
        if (null == other) { return false; }
        if (other == this) { return true; }
        if (!(other instanceof WaterSourceReport)) { return false; }
        else {
            WaterSourceReport another = (WaterSourceReport) other;
            boolean reportCheck = super.equals(another);
            boolean typeCheck = another.getType().equals(this.getType());
            boolean conditionCheck = another.getCondition().equals(this.getCondition());
            return reportCheck && typeCheck && conditionCheck;
        }
    }
}
