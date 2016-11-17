package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
public class ReportDB implements Serializable {

    public static final ReportDB database = new ReportDB();
    private final Map<String, LinkedList<Report>> backingMap;
    private int reportNumber;

    /**
     * Default constructor of a ReportDB
     */
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

    /**
     * Returns a linked list with all the locations from reports.
     * @param location Location from each report
     * @return LinkedList with all locations from all reports in database.
     */
    public LinkedList<Report> get(String location) {
        if (null == location || !backingMap.containsKey(location)) {
            throw new IllegalArgumentException("Report does not exist in database");
        }
        return backingMap.get(location);
    }

    /**
     * Returns a set with the keys for the backingMap holding the report
     * information
     * @return A set with the keys for backingMap of reports
     */
    public Set<String> getKeys() {
        return backingMap.keySet();
    }

    /**
     * Serializes this class
     */
    public static void serialize() {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("ReportDB.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(database);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Returns the backingMap
     * @return backingMap
     */
    public Map<String, LinkedList<Report>> getBackingMap() {
        return backingMap;
    }
}
