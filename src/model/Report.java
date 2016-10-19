package model;

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

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Account getSubmitAccount() {
        return submitAccount;
    }

    public void setSubmitAccount(Account submitAccount) {
        this.submitAccount = submitAccount;
    }

    public void setReportNumber(Integer number) {
        this.reportNumber = number;
    }

    public Integer getReportNumber() {
        return this.reportNumber;
    }
}
