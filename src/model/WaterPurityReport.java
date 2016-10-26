package model;

import com.lynden.gmapsfx.javascript.object.Marker;

/**
 * Model of Water Purity Report as detailed on 2340 Wiki
 * Fields filled in by Controller
 * Created by Jonathan on 10/14/2016.
 */
public class WaterPurityReport extends Report {

    private String userName;
    private String location;
    private PurityCondition condition;
    private double virusPPM;
    private double contaminantPPM;
    private Marker marker;

    public WaterPurityReport() {

    }

    public String getUserName() {
        return userName;
    }

    public String getLocation() {
        return location;
    }

    public PurityCondition getCondition() {
        return condition;
    }

    public double getVirusPPM() {
        return virusPPM;
    }

    public double getContaminantPPM() {
        return contaminantPPM;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCondition(PurityCondition condition) {
        this.condition = condition;
    }

    public void setVirusPPM(double virusPPM) {
        this.virusPPM = virusPPM;
    }

    public void setContaminantPPM(double contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
