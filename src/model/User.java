package model;

/**
 * Created by Lillian on 9/29/2016.
 * Version 1.0
 */
public class User {

    private Profile profile;
    /*
    * No args constructor
     */
    public User() {
        this.profile = new Profile();
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

}
