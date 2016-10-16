package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Fake database containing Reports
 * Modeled by a HashMap with Key being integer number standing for order submitted
 * and Value of Report
 *
 * @author Jon
 */
public class ReportDB {

    public static ReportDB database = new ReportDB();
    private static int keyCount = 0;
    private Map<Integer, Report> backingMap;

    public ReportDB() {
        backingMap = new HashMap<Integer, Report>();
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
        report.setDBLocation(keyCount);
        backingMap.put(keyCount, report);
        keyCount++;
    }

    public Report get(Report report) {
        if (backingMap.containsValue(report)) {
            throw new IllegalArgumentException("Report does not exist in database");
        }
        return backingMap.get(report.getDBLocation());
    }



}
