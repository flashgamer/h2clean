package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by robertwaters on 10/7/16.
 * Just a class to hold some data we might want to display on the map
 */
public class Location implements Serializable {

    private static final Logger LOGGER = Logger.getLogger("Location");

    static {
        LOGGER.setLevel(Level.FINER);
        try {
            FileHandler logFileHandler = new FileHandler("LogFile");
            logFileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(logFileHandler);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to create log file", ex);
        }
    }

    private final double longitude;
    private final double latitude;
    private final String description;
    private final String title;

    private Location(double lat, double lg, String ti, String desc) {
        LOGGER.entering("Location", "Constructor");
        longitude = lg;
        latitude = lat;
        description = desc;
        title = ti;
        LOGGER.exiting("Location", "Constructor");
    }

    public double getLongitude() { return longitude; }
    public double getLatitude() {return latitude; }
    // --Commented out by Inspection (11/16/16, 3:07 PM):public String getDescription() {return description;}
    public String getTitle() { return title; }


// --Commented out by Inspection START (11/16/16, 3:07 PM):
//    public void saveToText(PrintWriter pw) {
//        LOGGER.setLevel(Level.FINEST);
//        LOGGER.entering("Location", "saveToText");
//        pw.println(longitude + "\t" + latitude + "\t" +  description + "\t" + title);
//        LOGGER.exiting("Location", "saveToText");
//    }
// --Commented out by Inspection STOP (11/16/16, 3:07 PM)

}
