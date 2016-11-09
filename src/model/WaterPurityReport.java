package model;

import com.lynden.gmapsfx.javascript.object.Marker;

import java.io.Serializable;

/**
 * Model of Water Purity Report as detailed on 2340 Wiki
 * Fields filled in by Controller
 * Created by Jonathan on 10/14/2016.
 */
public class WaterPurityReport extends Report implements Serializable{

    private String userName;
    private String location;
    private PurityCondition condition;
    private double virusPPM;
    private double contaminantPPM;
    private transient Marker marker;


    /**
     * No args constructor for a water purity report
     */
    public WaterPurityReport() {

    }


    /**
     * Constructor for WaterPurityReport.
     * @param userName username associated with the WaterPurityReport.
     * @param location Location associated with the WaterPurityReport.
     * @param condition WaterCondition associated with the WaterPurityReport.
     * @param virusPPM Amount of Virus associated with the WaterPurityReport.
     * @param contaminantPPM Amount of contaminant associated with the report.
     */
    public WaterPurityReport(String userName, String location, PurityCondition condition, double virusPPM, double contaminantPPM) {
        this.userName = userName;
        this.location = location;
        this.condition = condition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * Gets the username of the user who submitted the report.
     * @return String representation of userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the location from the report
     * @return String representation of location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the purity condiiton from the report
     * @return PurityCondition of water from report
     */
    public PurityCondition getCondition() {
        return condition;
    }

    /**
     * Gets the virus information from the report
     * @return virusPPM of water from report
     */
    public double getVirusPPM() {
        return virusPPM;
    }

    /**
     * Gets the contaminant information from the report
     * @return contaminantPPM of water from report
     */
    public double getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * Sets the username for the report
     * @param userName user who submitted report
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the location for the report
     * @param location location of water in report
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the purity condition information for the report
     * @param condition the purity of the water
     */
    public void setCondition(PurityCondition condition) {
        this.condition = condition;
    }

    /**
     * Sets the virus information for the report
     * @param virusPPM the ppm of a virus represented by a double
     */
    public void setVirusPPM(double virusPPM) {
        this.virusPPM = virusPPM;
    }

    /**
     * Sets the contaminant information for the report
     * @param contaminantPPM the ppm of a contaminant represented by a double
     */
    public void setContaminantPPM(double contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * Gets a marker.
     * @return marker for this report
     */
    public Marker getMarker() {
        return marker;
    }

    /**
     * Sets a marker
     * @param marker marker for this report
     */
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public boolean equals(Object other) {
        if (null == other) { return false; }
        else if (other == this) { return true; }
        else if (!(other instanceof WaterPurityReport)) { return false; }
        else {
            WaterPurityReport another = (WaterPurityReport) other;
            boolean reportCheck = super.equals(other);
            boolean conditionCheck = another.getCondition().equals(this.getCondition());
            boolean virusCheck = another.getVirusPPM() == this.getVirusPPM();
            boolean contaminantCheck = another.getContaminantPPM() == this.getContaminantPPM();
            return reportCheck && conditionCheck && virusCheck && contaminantCheck;
        }
    }
}
