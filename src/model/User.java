package model;

import controller.LoginScreenController;

import java.io.Serializable;

/**
 * Created by Lillian on 9/29/2016.
 * Version 1.0
 */
public class User implements Serializable{

    private Profile profile;
    private boolean canSubmitReport;

    /**
    * No args constructor
     */
    public User() {

    }

    /**
     * Constructor for a user that takes in a title, first name, last name,
     * email and address
     * @param title String representation of title i.e. Mr., Ms., etc.
     * @param firstName String representation of first name of user
     * @param lastName String representation of last name of user
     * @param email String representation of user's email
     * @param address String representation of user's address
     */
    public User(String title, String firstName, String lastName, String email, String address) {
        this.profile = new Profile(title, firstName, lastName, email, address);
        this.canSubmitReport = true;
    }

// --Commented out by Inspection START (11/16/16, 3:12 PM):
//    /**
//     * submit a report on water availability
//     */
//    public void submitWaterReport(){
//        System.out.println("water report submitted");
//    }
// --Commented out by Inspection STOP (11/16/16, 3:12 PM)

// --Commented out by Inspection START (11/16/16, 3:12 PM):
//    /**
//     * view available water sources
//     */
//    public void viewWaterSources() {
//        System.out.println("viewing water report");
//    }
// --Commented out by Inspection STOP (11/16/16, 3:12 PM)

    /**
     * Returns the profile for a user.
     * @return Profile of user
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * checks if user can submit report
     * @return true if user can submit report
     */
    public boolean canSubmitReport() { return this.canSubmitReport; }

    /**
     * sets submit status if current user is admin
     * @param b submit status
     */
    public void setCanSubmitReport(boolean b) {
        if (LoginScreenController.account.getAccountType().equals("Administrator")) {
            this.canSubmitReport = b;
        }
    }

}
