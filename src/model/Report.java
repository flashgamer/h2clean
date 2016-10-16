package model;

import java.time.LocalDateTime;

/**
 * Model for Report, used as an extension for all other reports
 * Created by Lillian on 10/2/2016.
 */
public class Report {

    private Account submitAccount;
    private LocalDateTime submitTime;
    private int dbLocation;

    /**
     * Constructs a Report object. Sets submit time to current time as defined
     * by the machine.
     */
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

    public Account getSubmitAccount() {
        return submitAccount;
    }

    public void setSubmitAccount(Account submitAccount) {
        this.submitAccount = submitAccount;
    }
}
