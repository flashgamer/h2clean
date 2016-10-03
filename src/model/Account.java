package model;

/**
 * Created by Lillian on 10/2/2016.
 */
public class Account {
    private User user;
    private String username;
    private String password;
    private String accountType;

    /*
    * no args constructor
     */

    public Account(String username, String password, String accountType) {
        this.user = new User();
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public String getAccountType() {
        return accountType;
    }
}
