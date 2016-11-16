package test;

import model.LoginDB;
import model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Lillian on 11/16/16.
 */
public class LoginDBTests {
    
    LoginDB db;

    /**
     * sets up LoginDB test with an empty database.
     */
    @Before
    public void setup() {
        this.db = new LoginDB();
    }

    /**
     * Tests the get method of LoginDB
     * Case 1: gets null
     * Case 2: Gets account that does not exist in database
     * Case 3: Gets accoutn that does exist in database
     */
    @Test
    public void testGet() {
        Map myMap = db.getDatabaseBacking();
        // tests getting null value
        try {
            db.get(null);
            Assert.fail("You cannot get a null value from the login database.");
        } catch (IllegalArgumentException e) {
            // fall through   
        }

        // tests getting non existent login
        try {
            db.get("Lillian");
            Assert.fail("No login exists fro Lillian.");
        } catch (NoSuchElementException e) {
            // fall through
        }

        // tests an existent login in the database
        try {
            Account newAcc = new Account("a", "b", "c", "d", "e", "f", "g", "h");
            db.insert("Lillian", newAcc);
            Account account = db.get("Lillian");
            Assert.assertEquals(account, myMap.get("Lillian"));
        } catch (IllegalArgumentException e) {
            Assert.fail("An accounts exists for Lillian.");
        }
    }
}