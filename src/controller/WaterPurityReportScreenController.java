package controller;

import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
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

    private GeocodingService geocodingService;

    private boolean allowReport;

    @FXML
    private void initialize() {
        List<String> waterConditionList = new ArrayList<>();
        waterConditionList.add("Safe");
        waterConditionList.add("Treatable");
        waterConditionList.add("Unsafe");
        conditionField.setItems(FXCollections.observableArrayList(waterConditionList));
    }

    /**
     * Method for storing a new report in the Report Database
     * Generates a new report, stores the information from the fields in the
     * report, inserts the report into the database.
     */
    private void store() {
        WaterPurityReport myReport = new WaterPurityReport();
        myReport.setSubmitAccount(LoginScreenController.account);
        myReport.setUserName(myReport.getSubmitAccount().getUsername());
        myReport.setLocation(locationField.getText());
        myReport.setCondition(PurityCondition.findByKey(conditionField.getValue()));
        myReport.setContaminantPPM(new Double(contaminantField.getText()));
        myReport.setVirusPPM(new Double(virusField.getText()));
        ReportDB.database.insert(myReport);
        /*if (WaterAvailabilityReportController.markerMap.containsKey(locationField.getText())) {
            WaterAvailabilityReportController.markerMap.get(locationField.getText())
                    .setContent(WaterAvailabilityReportController.markerMap.get(locationField.getText()) + generateInfoWindowContent());
        } else {
            InfoWindowOptions myOps = new InfoWindowOptions();
            myOps.content(generateInfoWindowContent());
            WaterAvailabilityReportController.markerMap.put(generateMarker(locationField.getText()), new InfoWindow(myOps));
        }
        WaterAvailabilityReportController.updateMap();*/
    }


    /**
     * Called when Confirm button is pressed.
     * Checks if the report data is valid, then stores the new report in the
     * Report database, closes the current window and returns the user to the
     * Landing Screen
     */
    @FXML
    private void handleConfirmButtonAction() {
        if (validateData()) {
            store();
            Stage thisStage = (Stage) locationField.getScene().getWindow();
            thisStage.close();
            thisStage.hide();
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

        generateMarker(locationField.getText());

        if (!allowReport) {
            errorMessage += "No location found! \n";
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
            alert.setHeaderText("Please try again with the correct values.");
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
    }

    /**
     * Generates a Marker from a specified location(address) to be shown on the map
     *
     * @param location the Location to generate the marker at
     * @return a Marker positioned at the specified location
     */
    private Marker generateMarker(String location) {
        geocodingService = new GeocodingService();
        MarkerOptions myOptions = new MarkerOptions();
        geocodingService.geocode(location, (GeocodingResult[] results, GeocoderStatus status) -> {

            LatLong latLong = null;

            if( status == GeocoderStatus.ZERO_RESULTS) {
                //Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                //System.out.println("none found");
                this.allowReport = false;
                //alert.show();
                return;
            } else if( results.length > 1 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, using the first one.");
                //System.out.println("some found");
                this.allowReport = true;
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                this.allowReport = true;
                //System.out.println("one found");
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            myOptions.position(latLong);
        });
        Marker marker = new Marker(myOptions);
        return marker;
    }

    /**
     * Generates a String with the information from the report
     *
     * @return a String containing the information from the report.
     */
    private String generateInfoWindowContent() {
        String content = "";

        return content;
    }
}