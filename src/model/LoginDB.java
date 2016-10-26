package model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Backed by a map with the key being the username string
 * and the value being the associated account.
 * Created by lol on 10/3/16.
 */
public class LoginDB {

    public final static LoginDB database = new LoginDB();
    private Map<String, Account> databaseBacking;
    public static Account currentUser;


    /**
     * No args constructor for the login database.
     */
    public LoginDB() {
        this.databaseBacking = new HashMap<String, Account>();
        this.populateDefaults();
    }

    /**
     * default entries in database go here
     */
    private void populateDefaults() {
        //this.insert("ACCOUNTLIST", FXCollections.observableArrayList());
    }

    /**
     * gets a value from the LoginDB provided a key
     * @param entryName key of value
     * @return value for given key
     */
    public Account get(String entryName) {
        if (databaseBacking.containsKey(entryName)) {
            return databaseBacking.get(entryName);
        } else {
            throw new NoSuchElementException("Entry does not exist");
        }
    }

    /**
     * inserts key and value pair into LoginDB
     * @param entryName key of entry
     * @param value value of entry
     */
    public void insert(String entryName, Account value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        } else if (entryName == null) {
            throw new IllegalArgumentException("Entry cannot be null.");
        } else {
            this.databaseBacking.put(entryName, value);
        }
    }

    /**
     * Checks if a specific key-value pair exists within the database.
     * @param entryName key of entry
     * @param value value of entry
     * @return whether the key-value pair exists within the database.
     */
    public boolean contains(String entryName, Account value) {
        return databaseBacking.containsKey(entryName) &&
                databaseBacking.get(entryName).equals(value);
    }

    /**
     * Checks if a specific key exists within the database.
     * @param entryName key to be checked
     * @return whether the key exists within the database.
     */
    public boolean containsKey(String entryName) {
        return databaseBacking.containsKey(entryName);
    }

    /**
     * Returns the current user account logged in.
     * @return Current account that is logged in.
     */
    public static Account getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user of the account.
     * @param currentUser Current user account logged in.
     */
    public static void setCurrentUser(Account currentUser) {
        LoginDB.currentUser = currentUser;
    }
}
