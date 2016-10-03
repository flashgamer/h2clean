package model;

/**
 * Created by lol on 10/3/16.
 */
public class Profile {
    private String email;
    private Address address;
    private String title;
    private String firstName;
    private String lastName;

    public Profile(String title, String firstName,
                   String lastName, String email, Address address) {
        this.email = email;
        this.address = address;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Profile() {
        this("", "John", "Smith", "", new Address());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
