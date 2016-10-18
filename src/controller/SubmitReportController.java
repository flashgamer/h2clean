package controller;

import javafx.collections.FXCollections;
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
import java.util.ArrayList;
import java.util.List;

import static model.LoginDB.database;

/**
 * Created by Jonathan on 10/3/2016.
 */
public class SubmitReportController {

    @FXML
    Label headingText;

    @FXML
    ComboBox typeReport;

    @FXML
    private void initialize() {
        List<String> comboBoxList = new ArrayList<>();
        comboBoxList.add("Water Source Report");
        comboBoxList.add("Water Purity Report");
        typeReport.setItems(FXCollections.observableArrayList(comboBoxList));
        typeReport.setValue("Water Source Report");
    }

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
        Stage thisStage = (Stage) typeReport.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
    }

    protected void receiveUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * Called when user clicks the Proceed button after selecting the report
     * they want to view.
     *
     * @param event Unused
     */
    @FXML
    private void handleProceedPressed(ActionEvent event) {
        if (isUserValid().equals("error")) {
            //show error message
            String errorMessage = "You are not authorized to view this report" +
                    ".\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_submitReportPopupStage);
            alert.setTitle("Invalid Authorization");
            alert.setHeaderText("Authorization required.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        } else if (isUserValid().equals("noneSelected")) {
            //show error message
            String errorMessage = "Please select a report." +
                    ".\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_submitReportPopupStage);
            alert.setTitle("Invalid Authorization");
            alert.setHeaderText("Authorization required.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        } else if (isUserValid().equals("waterSourceReport")) {
            Stage thisStage = (Stage) typeReport.getScene().getWindow();
            thisStage.close();
            thisStage.hide();
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../view/WaterSourceReportScreen.fxml"));
                Stage waterSourceReportStage = new Stage();
                waterSourceReportStage.setTitle("Water Source Report");
                waterSourceReportStage.setScene(new Scene(root, 600, 400));
                waterSourceReportStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (isUserValid().equals("waterPurityReport")) {
            Stage thisStage = (Stage) typeReport.getScene().getWindow();
            thisStage.close();
            thisStage.hide();
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../view/WaterPurityReportScreen.fxml"));
                Stage waterPurityReportStage = new Stage();
                waterPurityReportStage.setTitle("Water Purity Report");
                waterPurityReportStage.setScene(new Scene(root, 600, 400));
                waterPurityReportStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
