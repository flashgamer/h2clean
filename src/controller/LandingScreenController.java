package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static model.ReportDB.database;

/**
 * Controller for landing page once user logs in successfully
 * Some code reused from other files within the package.
 *
 * @author Hotline String
 */
public class LandingScreenController {
    @FXML
    private Button editProfileButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button submitReport;

    private String userKey;

    /**
     * Called when user clicks on Edit Profile button
     *
     * @param event Unused
     */
    @FXML
    private void editProfile(ActionEvent event) {
        Stage thisStage = (Stage) editProfileButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ProfileScreen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage profileStage = new Stage();
            ProfileScreenController psc = fxmlLoader.<ProfileScreenController>getController();
            profileStage.setTitle("Profile Screen");
            profileStage.setScene(new Scene(root, 600, 400));
            psc.receiveUserKey(userKey);
            psc.save();
            profileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Called when user clicks on Logout button.
     *
     * @param event Unused
     */
    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {
        Stage thisStage = (Stage) logoutButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/WelcomeScreen.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root, 600, 400));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when user clicks on Submit Report button. Dialog box will pop up.
     *
     * @param event Unused
     */
    @FXML
    private void handleSubmitReportButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource
                    ("../view/SubmitReportPopup.fxml"));
            Stage submitReportStage = new Stage();
            submitReportStage.setTitle("Submit Report");
            submitReportStage.setScene(new Scene(root, 400, 250));
            submitReportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
