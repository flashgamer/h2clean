package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.PurityCondition;
import model.ReportDB;
import model.WaterCondition;
import model.WaterPurityReport;
import model.WaterSourceReport;
import model.WaterType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the submit water report screen.
 *
 * @author Hotline String
 */
public class WaterPurityReportScreenController {
    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<String> conditionField;

    @FXML
    private TextField virusField;

    @FXML
    private TextField contaminantField;

    private Stage purityStage;


    private void initialize() {
        List<String> waterConditionList = new ArrayList<>();
        waterConditionList.add("Safe");
        waterConditionList.add("Treatable");
        waterConditionList.add("Unsafe");
        conditionField.setItems(FXCollections.observableArrayList(waterConditionList));

    }

    /**
     * Method for storing a new report in the Report Database
     * <p>
     * Generates a new report, stores the information from the fields in the
     * report, inserts the report into the database.
     */
    private void store() {
        WaterPurityReport myReport = new WaterPurityReport();
        myReport.setSubmitAccount(LoginScreenController.account);
        myReport.setUserName(myReport.getSubmitAccount().getUsername());
        myReport.setLocation(locationField.getText());
        myReport.setCondition(PurityCondition.findByKey(conditionField.getValue()));
        ReportDB.database.insert(myReport);
    }

    /**
     * Called when Confirm button is pressed.
     * <p>
     * Checks if the report data is valid, then stores the new report in the
     * Report database, closes the current window and returns the user to the
     * Landing Screen
     */
    @FXML
    private void handleConfirmButtonAction() {
        if (validateData()) {
            store();
        }
        Stage thisStage = (Stage) locationField.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/LandingScreen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage landingStage = new Stage();
            LandingScreenController lsc = fxmlLoader.<LandingScreenController>getController();
            landingStage.setTitle("Landing Screen");
            landingStage.setScene(new Scene(root, 600, 400));
            landingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ensures the data entered for the report is valid
     *
     * @return true if none of the fields are null (i.e. not filled out) or contain invalid data
     */
    private boolean validateData() {
        String errorMessage = "";
        if (locationField.getText() == null) {
            errorMessage += "Location cannot be empty! \n";
        }
        if (virusField.getText() == null) {
            errorMessage += "Virus amount cannot be empty! \n";
        }
        if (virusField.getText() != null && !isNumeric(virusField.getText())) {
            errorMessage += "Virus amount must be numeric! \n";
        }
        if (contaminantField.getText() == null) {
            errorMessage += "Contaminant amount cannot be empty! \n";
        }
        if (contaminantField.getText() != null && !isNumeric(contaminantField.getText())) {
            errorMessage += "Contaminant amount must be numeric! \n";
        }
        if (conditionField.getValue() == null) {
            errorMessage += "Condition of the water must be chosen! \n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(purityStage);
            alert.setTitle("Invalid Login");
            alert.setHeaderText("Please try again with the correct login details.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    /**
     * Checks whether or not the given string is all numbers
     *
     * @param str The string to be checked
     * @return true if str only contains numerals
     */
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    /**
     * Called when Cancel button is pressed.
     * <p>
     * Returns to previous screen, no change is made to database.
     */
    @FXML
    private void handleCancelButtonAction() {
        Stage thisStage = (Stage) locationField.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/LandingScreen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage landingStage = new Stage();
            LandingScreenController lsc = fxmlLoader.<LandingScreenController>getController();
            landingStage.setTitle("Landing Screen");
            landingStage.setScene(new Scene(root, 600, 400));
            landingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}