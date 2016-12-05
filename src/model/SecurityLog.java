package model;

import controller.LoginScreenController;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton Logger class that tracks:
 * -Login Attempt
 * -Account Delete
 * -User Ban
 * -Unblock Account
 * -Report delete
 * @Author Vaishak Lalsangi
 */
public class SecurityLog {
    private static final SecurityLog securityLog = new SecurityLog();
    private static List<String> log;

    public enum Status {
        UNKNOWN_ID,
        BAD_PASSWORD,
        SUCCESS
    }

    /**
     * No arg constructor for log. Initializes internal List.
     */
    private SecurityLog() {
        this.log = new LinkedList<String>();
    }

    /**
     * records login event
     * @param userId userId of user logging in
     * @param status success status of login
     */
    public static void recordLogin(String userId, Status status) {
        String toAdd = (new Date()).toString() + ": " + "LOGIN "
                + userId + " " + status.toString();
        log.add(toAdd);
    }

    /**
     * records account delete event
     * @param adminId Id of admin deleting userId
     * @param deletedId userId to be deleted
     */
    public static void recordAccountDelete(String adminId, String deletedId) {
        String toAdd = (new Date()).toString() + ": " + "ADMIN " + adminId
                + " DELETED USER " + deletedId;
        log.add(toAdd);
    }

    /**
     * records user banning event
     * @param adminId Id of admin banning user
     * @param blockedId userId to be banned
     */
    public static void recordUserBan(String adminId, String blockedId) {
        String toAdd = (new Date()).toString() + ": " + "ADMIN " + adminId
                + " BANNED USER " + blockedId;
        log.add(toAdd);
    }

    /**
     * records unbanning event
     * @param adminId Id of admin banning user
     * @param ubId userId to be unbanned
     */
    public static void recordUnbanAccount(String adminId, String ubId) {
        String toAdd = (new Date()).toString() + ": " + "ADMIN " + adminId
                + " UNBANNED USER " + ubId;
        log.add(toAdd);
    }

    /**
     * records report deletion
     * @param managerId Id of manager deleting report
     * @param reportNo reportNo being deleted
     */
    public static void recordReportDelete(String managerId, String reportNo) {
        String toAdd = (new Date()).toString() + ": " + "MANAGER " + managerId
                + "DELETED REPORT " + reportNo;
        log.add(toAdd);
    }

    /**
     * gets security log ONLY if logged in user is admin.
     * @return log if logged in user is admin. returns empty list otherwise
     */
    public static List<String> getLog() {
        if (LoginScreenController.account.getAccountType().equals("Administrator")) {
            return log;
        } else {
            return new LinkedList<String>();
        }
    }

}
