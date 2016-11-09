package model;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jonathan on 11/8/2016.
 */
public class RegistrationService {
    private Logger LOGGER = Logger.getLogger("Registration Service");
    private Connection connection;

    /**
     * Initializes the database tables needed.
     */
    @PostConstruct
    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            connection.setAutoCommit(false);
            LOGGER.log(Level.INFO, "Opened database successfully");
            Statement stmt = connection.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS ACCOUNTS " +
                    "(USER BLOB NOT NULL, " +
                    " USERNAME      TEXT    NOT NULL, " +
                    " PASSWORD      TEXT    NOT NULL, " +
                    " ACCOUNTTYPE   TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            LOGGER.log(Level.INFO, "Student table made if necessary");

            sql = "CREATE TABLE IF NOT EXISTS REPORTS " +
                    "(REPORT    TEXT    NOT NULL, " +
                    " USERNAME  TEXT    NOT NULL, " +
                    " LOCATION  TEXT    NOT NULL, " +
                    " WATERTYPE TEXT, " +
                    " CONDITION TEXT, " +
                    " PURITY    TEXT, " +
                    " VIRUS     REAL, " +
                    " CONTAMINANT REAL)";
            stmt.executeUpdate(sql);
            LOGGER.log(Level.INFO, "Report table made if necessary");
            stmt.close();
        } catch ( Exception e ) {
            LOGGER.log(Level.SEVERE, "Exception in opening database", e);
        }

    }

    /**
     * Obtains a List of all accounts in the database
     * @return A List of all accounts in the database.
     */
    public List<Account> allAccounts() {
        ArrayList<Account> all = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM ACCOUNTS";
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                User user = (User) res.getBlob("USER");
                String userName = res.getString("USERNAME");
                String password  = res.getString("PASSWORD");
                String accountType = res.getString("ACCOUNTTYPE");

                Account a = new Account(user, userName, password, accountType);
                all.add(a);

            }
            stmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Exception in getting all accounts", e);
        }
        return all;
    }

    /**
     * Obtains a List of all reports in the database.
     * @return a List of all reports in the database.
     */
    public List<Report> allReports() {
        LinkedList<Report> all = new LinkedList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM REPORTS";
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                String reportType = res.getString("REPORT");
                String username = res.getString("USERNAME");
                String location  = res.getString("LOCATION");
                String waterType = res.getString("WATERTYPE");
                String condition = res.getString("CONDITION");
                String purity = res.getString("PURITY");
                double virus = res.getInt("VIRUS");
                double contaminant = res.getInt("CONTAMINANT");

                if (reportType.equals("Water Source")) {
                    WaterSourceReport source = new WaterSourceReport(username,
                            location,
                            WaterType.findByKey(waterType),
                            WaterCondition.findByKey(condition));
                    all.add(source);
                } else if (reportType.equals("Water Purity")) {
                    WaterPurityReport purityReport = new WaterPurityReport(
                            username,
                            location,
                            PurityCondition.findByKey(purity),
                            virus,
                            contaminant
                    );
                    all.add(purityReport);
                }

            }
            stmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Exception in getting all students", e);
        }
        return all;
    }

    /**
     * Saves the given report to the database.
     * @param report the Report to save to the database.
     */
    public void save(Report report) {
        try {
            Statement stmt = connection.createStatement();
            String reportString = null;
            String username = null;
            String location = null;
            String type = null;
            String condition = null;
            String purity = null;
            double virus = 0;
            double contaminant = 0;
            if (report instanceof WaterSourceReport) {
                WaterSourceReport source = (WaterSourceReport) report;
                reportString = "Water Source";
                username = source.getUserName();
                location = source.getLocation();
                type = source.getType().getMyString();
                condition = source.getCondition().getMyString();
            }
            else if (report instanceof WaterPurityReport) {
                WaterPurityReport purityReport = (WaterPurityReport) report;
                reportString = "Water Purity";
                username = purityReport.getUserName();
                location = purityReport.getLocation();
                purity = purityReport.getCondition().getMyString();
                virus = purityReport.getVirusPPM();
                contaminant = purityReport.getContaminantPPM();
            }
            String sql = "INSERT INTO STUDENT (REPORT, USERNAME, LOCATION, WATERTYPE, CONDITION, PURITY, VIRUS, CONTAMINANT) " +
                    "VALUES (" +  "'" + reportString + "', '" +
                    username + "', '" + location + "'," +  type + ", " +
                    condition + ", " + purity + "'," + virus + "'," + contaminant + " );";

            System.out.println(sql);
            stmt.executeUpdate(sql);

            stmt.close();
            connection.commit();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Exception in saving new student", e);
        }

    }

    /**
     * Closes the connection to the database.
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Exception in closing database connection", e);
        }
    }

    /**
     * Saves the given account to the database.
     * @param account the account to be saved to the database.
     */
    public void save(Account account) {
        try {
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO ACCOUNTS (USER, USERNAME, PASSWORD, ACCOUNTTYPE) " +
                    "VALUES (" +  "'" + account.getUser() + "', '" +
                    account.getUsername() + "', '" + account.getPassword() + "'," +  account.getAccountType() + " );";

            System.out.println(sql);
            stmt.executeUpdate(sql);

            stmt.close();
            connection.commit();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Exception in saving new student", e);
        }

    }
}
