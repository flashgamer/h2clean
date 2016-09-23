package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class LandingScreenController {
    @FXML
    private Button logoutButton;

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {
        Stage thisStage = (Stage) logoutButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/WelcomeScreen.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root,600,400));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
