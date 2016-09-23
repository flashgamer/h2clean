package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for user login
 * Some code reused from CS2340 Resources
 *
 * @author mkwon41
 */
public class UserEditController {
    /*
        References to the FXML widgets in .fxml files
     */
    @FXML
    private TextField userField;

    @FXML
    private TextField passField;

    private Stage _loginStage;

    /** flag to signal if login clicked **/
    private boolean _loginClicked = false;

    /**
     * called automatically after load
     */
    @FXML
    private void initialize() {

    }

    /**
     * Sets the stage of the dialog.
     *
     * @param loginStage the stage of the login
     */
    public void setLoginStage(Stage loginStage) { _loginStage = loginStage; }

    /**
     * Returns true if the user clicked Login, false otherwise.
     *
     * @return true if the user clicked the Login button
     */
    public boolean isLoginClicked() { return _loginClicked; }

    /**
     * Called when user clicks Login
     */
    @FXML
    private void handleSignInButtonAction(ActionEvent event) {
        if (isInputValid()) {
            _loginClicked = true;
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

    /**
     * Called when user clicks Cancel
     */
    @FXML
    private void handleCancelPressed() {
        Stage thisStage = (Stage) userField.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/WelcomeScreen.fxml"));
            Stage landingStage = new Stage();
            landingStage.setTitle("Welcome Screen");
            landingStage.setScene(new Scene(root,600,400));
            landingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Checks if username and password fields have been filled in
     * and then checks if the username and password are in the database.
     *
     * @return true if input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        // Checks if username field has been filled in
        if (userField.getText() == null || userField.getText().length() == 0) {
            errorMessage += "Not a valid username!\n";
        }
        // Checks if username field is one in database
        if (!userField.getText().equals("user")) {
            errorMessage += "Not a valid username!\n";
        }
        // Checks if password field has been filled in
        if (passField.getText() == null || passField.getText().length() == 0) {
            errorMessage += "Not a valid password!\n";
        }
        // Checks if password field is one in database
        if (!passField.getText().equals("pass")) {
            errorMessage += "Not a valid password!\n";
        }

        //successful login
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_loginStage);
            alert.setTitle("Invalid Login");
            alert.setHeaderText("Please try again with the correct login details.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
