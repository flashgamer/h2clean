package test;

import model.LoginDB;
import model.Account;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Lillian on 11/16/16.
 */
public class LoginDBTests {
    
    private LoginDB db;

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
     * Case 3: Gets account that does exist in database
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

    /**
     * tests the insert method of LoginDB.
     * Case 1: entryName null but value not
     * Case 2: value null but entryName not
     * Case 3: both parameters are null
     * Case 4: both parameters are valid
     */
    @Test
    public void testInsert() {
        String entryName = "anEntry";
        Account a = new Account(new User(), "User1", "123", "Worker");

        //Case 1
        try {
            db.insert(null,a);
            Assert.fail();
        }
        catch (IllegalArgumentException i) {
            //intentionally left empty
        }

        //Case 2
        try {
            db.insert(entryName, null);
            Assert.fail();
        }
        catch (IllegalArgumentException i) {
            //intentionally left empty
        }

        Assert.assertFalse(db.containsKey(entryName));

        //Case 3
        try {
            db.insert(null, null);
            Assert.fail();
        }
        catch (IllegalArgumentException i) {
            //intentionally left empty
        }


        //Case 4
        try {
            db.insert(entryName,a);
        }
        catch(Exception e) {
            Assert.fail();
        }

        Assert.assertEquals(db.get(entryName), a);
    }

    /**
     * Tests the contains method of LoginDB
     *
     * Case 1: Value is null
     * Case 2: Key is null
     * Case 3: Key and value does not exist
     * Case 4: Key and value does exist
     *
     * Written by Minju
     */
    @Test
    public void testContains() {
        User dummy = new User();
        Account a = new Account(dummy, "Dum", "my", "User");

        //value is null
        try {
            db.insert("dummy", null);
            Assert.fail("You cannot get a null value");
            //fails if null key
        } catch (IllegalArgumentException i) {
            //SHOULD work here
        }

        //key is null
        try {
            db.insert(null, a);
            Assert.fail("You cannot get a null key");
        } catch (IllegalArgumentException i) {
            //SHOULD work here
        }

        //key and value does not exist
        Assert.assertFalse(db.contains("dummy", a));
        Assert.assertFalse(db.contains("dummy2", a));

        //key and value does exist
        db.insert("dummy", a);
        Assert.assertTrue(db.contains("dummy", a));


    }
}