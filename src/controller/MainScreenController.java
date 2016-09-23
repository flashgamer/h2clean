package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class MainScreenController {
    public Stage primaryStage;

    @FXML
    private Label welcome;

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        //System.out.println("clicked");
        Stage thisStage = (Stage) welcome.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/LoginScreen.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root,600,400));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
