package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Fake database containing Reports
 * Modeled by a HashMap with Key being integer number standing for order submitted
 * and Value of Report
 *
 * @author Jon
 */
public class ReportDB {

    public static ReportDB database = new ReportDB();
    private Map<String, LinkedList<Report>> backingMap;
    private int reportNumber;

    public ReportDB() {
        backingMap = new HashMap<String, LinkedList<Report>>();
        this.reportNumber = 1;
    }

    /**
     * Inserts a report into the database with the Key specified by the current
     * keyCount and with the value of the report specified.
     * @param report the report to be inserted into the database.
     */
    public void insert(Report report) {
        if (report == null) {
            throw new IllegalArgumentException("Cannot submit null report");
        }
        if (backingMap.containsKey(report.getLocation())) {
            report.setReportNumber(reportNumber);
            backingMap.get(report.getLocation()).add(report);
            reportNumber++;
        } else {
            report.setReportNumber(reportNumber);
            backingMap.put(report.getLocation(), new LinkedList<Report>());
            backingMap.get(report.getLocation()).add(report);
            reportNumber++;
        }

    }

    public LinkedList<Report> get(String location) {
        if (!backingMap.containsKey(location)) {
            throw new IllegalArgumentException("Report does not exist in database");
        }
        return backingMap.get(location);
    }

    public Set<String> getKeys() {
        return backingMap.keySet();
    }

}