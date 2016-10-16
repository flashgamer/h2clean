package model;

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
}
