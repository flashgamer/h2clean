package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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

    @FXML
    ComboBox typeReport;

    private String userKey;

    private Stage _submitReportPopupStage;

    /**
     * Sets the stage of the dialog.
     *
     * @param submitReportPopupStage the stage of the login
     */
    public void setLoginStage(Stage submitReportPopupStage) {
        _submitReportPopupStage =
            submitReportPopupStage; }

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
        if (isUserValid().equals("error")) {

            //show error message
            String errorMessage = "You are not authorized to view this report" +
                    ".\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_submitReportPopupStage);
            alert.setTitle("Invalid Login");
            alert.setHeaderText("Please try again with the correct login details.");
            alert.setContentText(errorMessage);

            alert.showAndWait();
        } else if (isUserValid().equals("noneSelected")) {

        } else if (isUserValid().equals("waterSourceReport")) {

        } else if (isUserValid().equals("waterPurityReport")) {

        }
    }

    private String isUserValid() {
        Account account = database.get(userKey);
        String accType = account.getAccountType();

        // Checks if user is authorized to submit a type of report, error
        // message pops up if otherwise.
        if (accType.equals("User")) {
            if (typeReport.getValue().equals("waterSourceReport")) {
                return "waterSourceReport";
            } else if (typeReport.getValue().equals("waterPurityReport")) {
                return "error";
            }
        } else if (accType.equals("Worker")) {
            if (typeReport.getValue().equals("waterSourceReport")) {
                return "waterSourceReportScreen";
            } else if (typeReport.getValue().equals("waterPurityReport")) {
                return "waterPurityReportScreen";
            }
        } else if (accType.equals("Manager")) {
            if (typeReport.getValue().equals("waterSourceReport")) {
                return "waterSourceReportScreen";
            } else if (typeReport.getValue().equals("waterPurityReport")) {
                return "waterPurityReportScreen";
            }
        }
        return "noneSelected";
    }
}
