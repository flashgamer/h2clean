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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getVirus() {
        return virus;
    }

    public void setVirus(double virus) {
        this.virus = virus;
    }

    public double getContaminant() {
        return contaminant;
    }

    public void setContaminant(double contaminant) {
        this.contaminant = contaminant;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
