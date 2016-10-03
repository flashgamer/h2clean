package model;

/**
 * Created by lol on 10/3/16.
 */
public class Address {
    private String line1;
    private String line2;
    private String state;
    private String zip;
    private String city;

    public Address(String line1, String line2, String state, String zip,
                   String city) {
        this.line1 = line1;
        this.line2 = line2;
        this.state = state;
        this.zip = zip;
        this.city = city;
    }

    public Address() {
        this("", "", "", "", "");
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
