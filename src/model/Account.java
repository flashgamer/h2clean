package model;

/**
 * Created by Lillian on 10/2/2016.
 */
public class Account {
    private User user;
    private String username;
    private String password;
    private String accountType;

    public Account(String username, String password, String accountType, String title, String firstName, String lastName, String email, String address) {
        this.user = new User(title, firstName, lastName, email, address);
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    /**
     * gets username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * gets password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets password
     * @param password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets User object
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * gets AccountType
     * @return accountType
     */
    public String getAccountType() {
        return accountType;
    }
}