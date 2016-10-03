package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the registration screen.
 * @author Hotline String
 */
public class RegistrationScreenController {
    @FXML
    private TextField userField;

    @FXML
    private TextField passField;

    /**
     * Controls what happens when the user clicks on the register button.
     */
    @FXML
    private void handleRegisterButtonAction() {
        if (isInputValid()) {

        }
    }

    /**
     * Controls what happens when the user clicks on the cancel button.
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

    @FXML
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
            alert.setTitle("Invalid Login");
            alert.setHeaderText("Please try again with the correct login details.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
