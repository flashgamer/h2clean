package model;

/**
 * Model of Water Availability Report as detailed by 2340 Wiki
 * Fields are filled in from Controller
 * Map functionality to be implemented by October 19th
 * Created by Jonathan on 10/14/2016.
 */
public class WaterAvailabilityReport extends Report {

    // TODO: Google Maps functionality for M7
    private WaterType type;
    private WaterCondition condition;

    public WaterAvailabilityReport() {

    }

    public WaterType getType() {
        return type;
    }

    public WaterCondition getCondition() {
        return condition;
    }
}
