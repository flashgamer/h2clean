package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Profile;

import java.io.IOException;

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



// --Commented out by Inspection START (11/16/16, 3:11 PM):
//    /**
//     * Updates the profile information
//     */
//    protected void save() {
//        Profile profile = LoginScreenController.account.getUser().getProfile();
//        userField.setText(LoginScreenController.account.getUsername());
//        accountTypeField.setText(LoginScreenController.account.getAccountType
//                ());
//        titleField.setText(profile.getTitle());
//        nameField.setText(profile.getFirstName() + " " + profile.getLastName());
//        emailField.setText(profile.getEmail());
//        addressField.setText(profile.getAddress());
//    }
// --Commented out by Inspection STOP (11/16/16, 3:11 PM)

    /**
     * Called when user clicks Edit and alters profile information
     */
    @FXML
    private void handleEditPressed() {
        Profile profile = LoginScreenController.account.getUser().getProfile();

        if (!changeTitle.getText().equals("")) {
            titleField.setText(changeTitle.getText());
            profile.setTitle(changeTitle.getText());
        }
        if (!(changeFirst.getText().equals("") || changeLast.getText().equals(""))) {
            nameField.setText(changeFirst.getText() + " " + changeLast.getText());
            profile.setFirstName(changeFirst.getText());
            profile.setLastName(changeLast.getText());
        }
        if (!changeEmail.getText().equals("")) {
            emailField.setText(changeEmail.getText());
            profile.setEmail(changeEmail.getText());
        }
        if (!changeAddress.getText().equals("")) {
            addressField.setText(changeAddress.getText());
            profile.setAddress(changeAddress.getText());
        }
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

}
