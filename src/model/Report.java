package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Model for Report, used as an extension for all other reports
 * Created by Lillian on 10/2/2016.
 */
public class Report implements Serializable{

    private Account submitAccount;
    private LocalDate submitTime;
    private String location;
    private Integer reportNumber;

    /**
     * Constructs a Report object. Sets submit time to current time as defined
     * by the machine.
     */
    public Report() {
        submitTime = LocalDate.now();
    }

    /**
     * Gets the LocalDateTime when this Report was submitted
     * @return the LocalDateTime when this Report was submitted.
     */
    public LocalDate getSubmitTime() {
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
            return another.getLocation().equals(this.getLocation())
                    && another.getSubmitTime().equals(this.getSubmitTime());
        }
    }

    /**
     * Sets the Submit time to the specified LocalDate
     * @param date the LocalDate that this report was submitted.
     */
    void setSubmitTime(LocalDate date) {
        this.submitTime = date;
    }

    /**
     * Sets the Submit time to a LocalDate specified by the year, month and day
     * specified by the user. FOR TESTING
     * @param year the year of the report
     * @param month the month of the report
     * @param day the day of the report.
     */
    public void setSubmitTime(int year, int month, int day) {
        this.submitTime = LocalDate.of(year, month, day);
    }
}
