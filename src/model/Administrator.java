package model;

import controller.LoginScreenController;


/**
 * Created by Lillian on 10/2/2016.
 */
class Administrator extends User {

    /**
    * no args constructor
     */
    public Administrator() {
        super();
    }

    /**
     * construct an admin with
     * @param title
     * @param firstName
     * @param lastName
     * @param email
     * @param address
     */
    public Administrator (String title, String firstName, String lastName, String email, String address) {
        super(title, firstName, lastName, email, address);
    }

    /**
     * deletes account
     * @param account user account
     */
    public void deleteAccount(Account account) {
        if (LoginScreenController.account.getUser() == this) {
            SecurityLog.recordAccountDelete(LoginScreenController.account.getUsername(), account.getUsername());
        }
        //TODO: implement
    }

    /**
     * bans user from submitting reports (user can still view water sources)
     * @param account account to ban
     */
    public void banUserSubmission(Account account) {
        if (LoginScreenController.account.getUser() == this) {
            SecurityLog.recordUserBan(LoginScreenController.account.getUsername(), account.getUsername());
            account.getUser().setCanSubmitReport(false);
        }
    }

    /**
     * unblocks an account that has been locked for incorrect login attempts
     * @param account account to unblock
     */
    public void unblockAccount(Account account) {
        if (LoginScreenController.account.getUser() == this) {
            SecurityLog.recordUnbanAccount(LoginScreenController.account.getUsername(), account.getUsername());
        }
        //TODO: implement
    }
}
