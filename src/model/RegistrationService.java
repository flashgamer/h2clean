package model;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jonathan on 11/8/2016.
 */
public class RegistrationService {
    private Logger LOGGER = Logger.getLogger("Registration Service");
    private Connection connection;

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
}
