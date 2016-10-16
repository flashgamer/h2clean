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
}
