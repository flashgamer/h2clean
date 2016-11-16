package test;

import model.Report;
import model.ReportDB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Vaishak on 11/15/16.
 */
public class ReportDBTests {

    ReportDB db;

    /**
     * sets up ReportDB tests with an empty database.
     */
    @Before
    public void setup() {
        this.db = new ReportDB();
    }

    /**
     * tests the insert method of ReportDB.
     * Case 1: null report
     * Case 2: Creates a report, creates another with the same location
     * Case 3: Creates another report with another location.
     * Written by Vaishak.
     */
    @Test
    public void testInsert() {
        Report nullReport = null;
        try {
            db.insert(nullReport);
            Assert.fail(); //fails if inserting a null report works
        } catch (IllegalArgumentException i) {
            //SHOULD transition here
        }

        String location = "Georgia Tech";
        Report report = new Report();
        report.setLocation(location);
        report.setSubmitTime(0, 1, 1);

        try {
            db.insert(report); //should work
        } catch (Exception e) {
            Assert.fail(); //fails if it throws an exception
        }

        Assert.assertEquals(db.get(location).get(0), report); //assert that report is added into db

        Report another = new Report();
        another.setLocation(location);
        another.setSubmitTime(1, 1, 1); // ensures that report and another are not equal

        try {
            db.insert(another); // should work
        } catch (Exception e) {
            Assert.fail(); //fails if it throws an exception
        }

        Assert.assertEquals(db.get(location).get(1), another); //assert that another is added into db
        Assert.assertNotEquals(db.get(location).get(0), another); //assert that reports are not considered equal

        Report gross = new Report();
        gross.setLocation("UGA");
        gross.setSubmitTime(2, 1, 1);

        try {
            db.insert(gross); //should work
        } catch (Exception e) {
            Assert.fail(); //fails if throws an exception
        }

        Assert.assertEquals(db.getKeys().size(), 2); //asserts that there are two keys in the ReportDB
    }

    /**
     * Tests the get method of ReportDB
     * Case 1: Gets null
     * Case 2: Gets location that does not exist in database
     * Case 3: Gets location that does exist in database
     */
    @Test
    public void testGet() {
        // Attempts to get a report in a place that is not in the database.
        Map myMap = db.getBackingMap();
        try {
            db.get(null);
            Assert.fail("You cannot get null from the database");
        } catch (IllegalArgumentException i) {
            // fall through
        }
        try {
            db.get("Seoul");
            Assert.fail("No report exists at Seoul");
        } catch(IllegalArgumentException i) {
            // fall through
        }

        // Attempts to get a report in a place that is in the database.
        try {
            Report fake = new Report();
            fake.setLocation("Taipei");
            db.insert(fake);
            LinkedList taipeiList = db.get("Taipei");
            Assert.assertEquals(taipeiList, myMap.get("Taipei"));
        } catch (IllegalArgumentException i) {
            Assert.fail("A report exists at Taipei");
        }

    }
}
