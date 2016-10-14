package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Account;

import java.io.IOException;

import static model.FakeDB.database;

/**
 * Created by Jonathan on 10/3/2016.
 */
public class SubmitReportController {

    @FXML
    Label headingText;

    private String userKey;

    /**
     * Called when Submit New Report Button pressed.
     */
    @FXML
    private void handleSubmitNewReportPressed() {
        // TODO: Add new report to database.
        Stage thisStage = (Stage) headingText.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
    }

    @FXML
    private void handleCancelPressed() {
        Stage thisStage = (Stage) headingText.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/LandingScreen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage profileStage = new Stage();
            ProfileScreenController psc = fxmlLoader.<ProfileScreenController>getController();
            profileStage.setTitle("Landing Screen");
            profileStage.setScene(new Scene(root,600,400));
            profileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void receiveUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * Called when user clicks on Submit Report button
     */
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        Account account = database.get(userKey);
        String accType = account.getAccountType();

        if (isReportValid()) {
            String errorMessage = "";
        }

        //Checks if user is authorized to submit a type of report, error
        // message pops up if otherwise.

        if (accType.equals("User")) {
            if ()

        } else if (accType.equals("Worker")) {

        } else if (accType.equals("Manager")) {

        }

    }
}
