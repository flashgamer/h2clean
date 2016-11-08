package model;

/**
 * Created by lol on 10/3/16.
 */
public class Profile {
    private String email;
    private String address;
    private String title;
    private String firstName;
    private String lastName;

    /**
     * Constructor for a Profile
     * @param title Title of the Profile owner.
     * @param firstName First name of the Profile owner.
     * @param lastName Last name of the Profile owner.
     * @param email Email of the Profile owner.
     * @param address Address of the Profile owner.
     */
    public Profile(String title, String firstName,
                   String lastName, String email, String address) {
        this.email = email;
        this.address = address;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Default no-arg constructor for Profile
     */
    public Profile() {
    }

    /**
     * Gets the email of the owner of this Profile.
     * @return the email of the owner of this Profile.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the owner of this Profile to the specified email.
     * @param email String representation of the email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the address of the owner of this Profile.
     * @return the Address of the owner of this Profile.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the owner of this Profile to the specified address.
     * @param address String representation of the address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the title of the owner of this Profile.
     * @return the Title of the owner of this Profile.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the owner of this Profile to the specified title.
     * @param title String representation of the title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the first name of the owner of this Profile.
     * @return the first name of the owner of this Profile.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the owner of this
     * Profile to the specified first name.
     * @param firstName the first name of the owner of this Profile.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the owner of this Profile.
     * @return the last name of the owner of this Profile.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the owner of this
     * Profile to the specified last name.
     * @param lastName the last name of the owner of this Profile.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
