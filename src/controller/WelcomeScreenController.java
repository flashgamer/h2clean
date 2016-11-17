package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Welcome Screen
 * Some code reused from other files in this project.
 *
 * @author Hotline String
 */
public class WelcomeScreenController {
    private Stage primaryStage;

    @FXML
    private Label welcome;

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Called when user clicks on Login button.
     * @param event unused
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        //System.out.println("clicked");
        Stage thisStage = (Stage) welcome.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("../view/LoginScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root,600,400));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when user clicks on the Registration button.
     * @param event unused.
     */
    @FXML
    private void handleRegistrationButtonAction(ActionEvent event) {
        Stage thisStage = (Stage) welcome.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("../view/RegistrationScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage registerStage = new Stage();
            registerStage.setTitle("Registration Screen");
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
