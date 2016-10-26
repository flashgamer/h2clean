package model;

import java.time.Year;

/**
 * Model of Historical Report as detailed on 2340 Wiki
 * Fields filled in by controller
 * Created by Jonathan on 10/14/2016.
 */
public class HistoricalReport extends Report {
    private String location;
    private double virus;
    private double contaminant;
    private Year year;

    public HistoricalReport() {

    }

    /**
     * Returns the location of where the data was pulled from for the report.
     * @return String representation of the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the source of data for a historical report.
     * @param location The location of where the data was pulled from.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the virus information from the historical report.
     * @return Double representing the PPM concentration of the virus.
     */
    public double getVirus() {
        return virus;
    }

    /**
     * Sets the virus information for the historical report.
     * @param virus The PPM of the virus represented by a double.
     */
    public void setVirus(double virus) {
        this.virus = virus;
    }

    /**
     * Returns the contaminant information for the historical report.
     * @return The PPM of the contaminant.
     */
    public double getContaminant() {
        return contaminant;
    }

    /**
     * Set the contaminant information for the historical report.
     * @param contaminant The PPM of the contaminant represented by a double.
     */
    public void setContaminant(double contaminant) {
        this.contaminant = contaminant;
    }

    /**
     * Returns year the report was created.
     * @return The year the report was created.
     */
    public Year getYear() {
        return year;
    }

    /**
     * Sets the year that the report was created.
     * @param year Year the report was created.
     */
    public void setYear(Year year) {
        this.year = year;
    }
}
