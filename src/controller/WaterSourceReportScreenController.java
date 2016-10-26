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
import model.ReportDB;
import model.WaterCondition;
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
public class WaterSourceReportScreenController {
    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<String> waterTypeField;

    @FXML
    private ComboBox<String> waterConditionField;

    private Stage sourceStage;

    private GeocodingService geocodingService;

    @FXML
    private void initialize() {
        List<String> waterTypeList = new ArrayList<>();
        waterTypeList.add("Bottled");
        waterTypeList.add("Well");
        waterTypeList.add("Stream");
        waterTypeList.add("Lake");
        waterTypeList.add("Spring");
        waterTypeList.add("Other");
        List<String> waterConditionList = new ArrayList<>();
        waterConditionList.add("Waste");
        waterConditionList.add("Treatable-Clear");
        waterConditionList.add("Treatable-Muddy");
        waterConditionList.add("Potable");
        waterTypeField.setItems(FXCollections.observableArrayList(waterTypeList));
        waterConditionField.setItems(FXCollections.observableArrayList(waterConditionList));
    }

    /**
     * Method for storing a new report in the Report Database
     * <p>
     * Generates a new report, stores the information from the fields in the
     * report, inserts the report into the database.
     */
    private void store() {
        WaterSourceReport myReport = new WaterSourceReport();
        myReport.setSubmitAccount(LoginScreenController.account);
        myReport.setUserName(myReport.getSubmitAccount().getUsername());
        myReport.setLocation(locationField.getText());
        myReport.setType(WaterType.findByKey(waterTypeField.getValue()));
        myReport.setCondition(WaterCondition.findByKey(waterConditionField.getValue()));
        ReportDB.database.insert(myReport);
        if (WaterAvailabilityReportController.markerMap.containsKey(locationField.getText())) {
            WaterAvailabilityReportController.markerMap.get(locationField.getText())
                    .setContent(WaterAvailabilityReportController.markerMap.get(locationField.getText()) + generateInfoWindowContent());
        } else {
            InfoWindowOptions myOps = new InfoWindowOptions();
            myOps.content(generateInfoWindowContent());
            WaterAvailabilityReportController.markerMap.put(generateMarker(locationField.getText()), new InfoWindow(myOps));
        }
        WaterAvailabilityReportController.updateMap();
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
            Stage thisStage = (Stage) locationField.getScene().getWindow();
            thisStage.close();
            thisStage.hide();
        }
    }

    /**
     * Ensures the data entered for the report is valid
     *
     * @return true if none of the fields are null (i.e. not filled out)
     */
    private boolean validateData() {

        String errorMessage = "";
        if (locationField.getText() == null) {
            errorMessage += "Location cannot be empty! \n";
        }
        if (waterTypeField.getValue() == null) {
            errorMessage += "Water type must be chosen! \n";
        }
        if (waterConditionField.getValue() == null) {
            errorMessage += "Water condition must be chosen! \n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(sourceStage);
            alert.setTitle("Invalid Login");
            alert.setHeaderText("Please try again with the correct values.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
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
     * @param location the Location to generate the marker at
     * @return a Marker positioned at the specified location
     */
    private Marker generateMarker(String location) {
        geocodingService = new GeocodingService();
        MarkerOptions myOptions = new MarkerOptions();
        geocodingService.geocode(location, (GeocodingResult[] results, GeocoderStatus status) -> {

            LatLong latLong = null;

            if( status == GeocoderStatus.ZERO_RESULTS) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                alert.show();
                return;
            } else if( results.length > 1 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, using the first one.");
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            myOptions.position(latLong);
        });

        return new Marker(myOptions);
    }

    /**
     * Generates a String with the information from the report
     * @return a String containing the information from the report.
     */
    private String generateInfoWindowContent() {
        String content = "";
        String waterType = "Type: " + waterTypeField.getValue();
        String condition = "Condition: " + waterConditionField.getValue();
        content = waterType + "\n" + condition + "\n";
        return content;
    }
}