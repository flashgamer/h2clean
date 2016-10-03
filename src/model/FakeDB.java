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
    Map<String, Object> databaseBacking;

    public FakeDB() {
        this.databaseBacking = new HashMap<String, Object>();
        this.populateDefaults();
    }

    private void populateDefaults() {
        this.insert("ACCOUNTLIST", FXCollections.emptyObservableList());
    }

    public Object get(String entryName) {
        if (databaseBacking.containsKey(entryName)) {
            return databaseBacking.get(entryName);
        } else {
            throw new NoSuchElementException("Entry does not exist");
        }
    }

    public void insert(String entryName, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        } else if (entryName == null) {
            throw new IllegalArgumentException("Entry cannot be null.");
        } else {
            this.databaseBacking.put(entryName, value);
        }
    }

}
