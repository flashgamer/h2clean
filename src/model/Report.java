package model;

import java.time.LocalDateTime;

/**
 *
 * Created by Lillian on 10/2/2016.
 */
public class Report {

    private LocalDateTime submitTime;
    private int dbLocation;

    public Report() {
        submitTime = LocalDateTime.now();
    }

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setDBLocation(int location) {
        dbLocation = location;
    }

    public int getDBLocation() {
        return dbLocation;
    }
}
