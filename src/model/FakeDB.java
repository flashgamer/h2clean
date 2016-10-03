package model;

import javafx.collections.FXCollections;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by lol on 10/3/16.
 */
public class FakeDB {

    public final static FakeDB database = new FakeDB();
    private Map<String, Object> databaseBacking;

    public FakeDB() {
        this.databaseBacking = new HashMap<String, Object>();
        this.populateDefaults();
    }

    /**
     * default entries in database go here
     */
    private void populateDefaults() {
        this.insert("ACCOUNTLIST", FXCollections.observableArrayList());
    }

    /**
     * gets a value from the FakeDB provided a key
     * @param entryName key of value
     * @return value for given key
     */
    public Object get(String entryName) {
        if (databaseBacking.containsKey(entryName)) {
            return databaseBacking.get(entryName);
        } else {
            throw new NoSuchElementException("Entry does not exist");
        }
    }

    /**
     * inserts key and value pair into FakeDB
     * @param entryName key of entry
     * @param value value of entry
     */
    public void insert(String entryName, Object value) {
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
    public boolean isValid(String entryName, Object value) {
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
}
