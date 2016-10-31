package model;

/**
 * Created by Lillian on 10/2/2016.
 */
public class Administrator {
    /**
    * no args constructor
     */
    public Administrator() {
    }

    /**
     * deletes account
     * @param account user account
     */
    public void deleteAccount(Account account) {
        System.out.println("account deleted");
    }

    /**
     * bans user from submitting reports (user can still view water sources)
     * @param user user to ban
     */
    public void banUserSubmission(User user) {
        System.out.println("user banned from submitting reports");
    }

    /**
     * unblocks an account that has been locked for incorrect login attempts
     * @param account account to unlock
     */
    public void unblockAccount(Account account) {
        System.out.println("account unblocked");
    }

    /**
     * view the security log
     */
    public void viewSecurityLog() {
        System.out.println("viewing security log");
    }
}
