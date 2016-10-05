package model;

/**
 * Created by Lillian on 9/29/2016.
 * Version 1.0
 */
public class User {

    private Profile profile;

    /**
    * No args constructor
     */
    public User() {

    }

    public User(String title, String firstName, String lastName, String email, String address) {
        this.profile = new Profile(title, firstName, lastName, email, address);
    }

    /**
     * submit a report on water availability
     */
    public void submitWaterReport(){
        System.out.println("water report submitted");
    }

    /**
     * view available water sources
     */
    public void viewWaterSources() {
        System.out.println("viewing water report");
    }

    public Profile getProfile() {
        return profile;
    }

}
