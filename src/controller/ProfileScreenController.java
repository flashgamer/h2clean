package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.Profile;

import java.io.IOException;

import static model.FakeDB.database;

/**
 * Controller for Profile Screen
 *
 * Created by Minju on 10/3/2016.
 */
public class ProfileScreenController {

    @FXML
    private Label titleField;

    @FXML
    private Label nameField;

    @FXML
    private Label userField;

    @FXML
    private Label passField;

    @FXML
    private Label emailField;

    @FXML
    private Label addressField;

    @FXML
    private Label accountTypeField;

    @FXML
    private TextField changeTitle;

    @FXML
    private TextField changeFirst;

    @FXML
    private TextField changeLast;

    @FXML
    private TextField changeEmail;

    @FXML
    private TextField changeAddress;

    @FXML
    private TextField changePass;

    private String userKey;

    /**
     * Updates the profile information
     */
    protected void update() {
        //userField.setText(userKey);
        //Account account = new Account(userField.getText(), passField.getText(), accountTypeField.getText());
        System.out.println(database.containsKey(userKey));
        Account account = database.get(userKey);
        Profile profile = account.getUser().getProfile();

        userField.setText(account.getUsername());
        //passField.setText(account.getPassword());
        accountTypeField.setText(account.getAccountType());
        titleField.setText(profile.getTitle());
        nameField.setText(profile.getFirstName() + " " + profile.getLastName());
        emailField.setText(profile.getEmail());
        String line1 = profile.getAddress().getLine1();
        String line2 = profile.getAddress().getLine2();
        String city = profile.getAddress().getCity();
        String zip = profile.getAddress().getZip();
        addressField.setText(line1 + ", " + line2 + ", " + city + ", " + zip);
    }

    /**
     * Called when user clicks Edit and alters profile information
     */
    @FXML
    private void handleEditPressed() {
        titleField.setText(changeTitle.getText());
        nameField.setText(changeFirst.getText() + " " + changeLast.getText());
        emailField.setText(changeEmail.getText());
        addressField.setText(changeAddress.getText());

    }

    /**
     * Called when user clicks Cancel
     */
    @FXML
    private void handleCancelPressed() {
        Stage thisStage = (Stage) userField.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/LandingScreen.fxml"));
            Stage landingStage = new Stage();
            landingStage.setTitle("Landing Screen");
            landingStage.setScene(new Scene(root,600,400));
            landingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void receiveUserKey(String userKey) {
        this.userKey = userKey;
    }
}
