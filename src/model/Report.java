package model;

import com.lynden.gmapsfx.javascript.object.Marker;

import java.time.LocalDateTime;

/**
 * Model for Report, used as an extension for all other reports
 * Created by Lillian on 10/2/2016.
 */
public class Report {

    private Account submitAccount;
    private LocalDateTime submitTime;
    private String location;
    private Integer reportNumber;

    /**
     * Constructs a Report object. Sets submit time to current time as defined
     * by the machine.
     */
    public Report() {
        submitTime = LocalDateTime.now();
    }

    /**
     * Gets the LocalDateTime when this Report was submitted
     * @return the LocalDateTime when this Report was submitted.
     */
    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    /**
     * Gets a String representation of the Location of this Report.
     * @return a String representation of the Location of this Report.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of this report to the specified location
     * @param location A String representation of a location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the Account that submitted the Report.
     * @return the Account that submitted the Report.
     */
    public Account getSubmitAccount() {
        return submitAccount;
    }

    /**
     * Sets the Account that submitted the Report to the specified Account.
     * @param submitAccount the Account that submitted the Report.
     */
    public void setSubmitAccount(Account submitAccount) {
        this.submitAccount = submitAccount;
    }

    /**
     * Set the report number to the given number.
     * @param number the Report number to set this Report's number to.
     */
    public void setReportNumber(Integer number) {
        this.reportNumber = number;
    }

    /**
     * Gets the Report number of this Report.
     * @return the Report number of this Report.
     */
    public Integer getReportNumber() {
        return this.reportNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (null == other) { return false; }
        else if (other == this) { return true; }
        else if (!(other instanceof Report)) { return false; }
        else {
            Report another = (Report) other;
            return another.getLocation().equals(this.getLocation());
        }
    }
}
