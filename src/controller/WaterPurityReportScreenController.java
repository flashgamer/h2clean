package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ReportDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the submit water report screen.
 *
 * @author Hotline String
 */
public class WaterPurityReportScreenController implements MapComponentInitializedListener {
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<String> conditionField;

    @FXML
    private TextField virusField;

    @FXML
    private TextField contaminantField;

    private Stage purityStage;

    private boolean allowReport;
    private Connection connection;

    /**
     * Automatically called to initialize the screen.
     */
    @FXML
    private void initialize() {
        List<String> waterConditionList = new ArrayList<>();
        waterConditionList.add("Safe");
        waterConditionList.add("Treatable");
        waterConditionList.add("Unsafe");
        conditionField.setItems(FXCollections.observableArrayList(waterConditionList));
        GoogleMapView mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
    }

    /**
     * Initializes a Google Map for Markers
     */
    @Override
    public void mapInitialized() {
        GoogleMap map = new GoogleMap();
    }
    /**
     * Method for storing a new report in the Report Database
     * Generates a new report, stores the information from the fields in the
     * report, inserts the report into the database.
     */
    private void store() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:purityReportsDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("create table if not exists purityReportsDB (id integer, username string, " +
                    "date string, location string, condition string, contaminantPPM double, virusPPM double, " +
                    "reportNum " +
                    "integer, " +
                    "submitDate datetime)");
            String sqlStatement = "insert into purityReportsDB (username, date, location, condition, contaminantPPM, " +
                    "virusPPM, reportNum, submitDate) values ('" + LoginScreenController.account.getUsername() + "', " +
                    "'" + datePicker.getValue() + "', '" +
                    locationField.getText() + "', '" + conditionField.getValue() + "', " + new Double(contaminantField.getText()) + ", " + new Double(virusField.getText()) + ", " + LoginScreenController.reportNum++
                    + ", " + LocalDate.now() + ")";
            statement. executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
//        WaterPurityReport myReport = new WaterPurityReport();
//        myReport.setSubmitAccount(LoginScreenController.account);
//        myReport.setUserName(myReport.getSubmitAccount().getUsername());
//        myReport.setLocation(locationField.getText());
//        myReport.setCondition(PurityCondition.findByKey(conditionField.getValue()));
//        myReport.setContaminantPPM(new Double(contaminantField.getText()));
//        myReport.setVirusPPM(new Double(virusField.getText()));
//        myReport.setReportNumber(LoginScreenController.reportNum++);
//        ReportDB.database.insert(myReport);
//        try {
//            String fileName = myReport.getLocation() + ".ser";
//            FileOutputStream fileOut =
//                    new FileOutputStream(fileName);
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(myReport);
//            out.close();
//            fileOut.close();
//        }catch(IOException i) {
//            i.printStackTrace();
//        }
        AllReportsScreenController.reportLocations.add(locationField.getText());
        AllReportsScreenController.updateSerFile();
        ReportDB.serialize();
        // Facade.getInstance().getReports().add(myReport);
        // saveReportJson();
    }


    /**
     * Called when Confirm button is pressed.
     * Checks if the report data is valid, then stores the new report in the
     * Report database, closes the current window and returns the user to the
     * Landing Screen
     */
    @FXML
    private void handleConfirmButtonAction() {
        validateData();
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
            alert.setTitle("Invalid Data");
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
     * @param location the Location to generate the marker at
     */
    private void generateMarker(String location) {
        GeocodingService geocodingService = new GeocodingService();
        MarkerOptions myOptions = new MarkerOptions();
        Marker marker = new Marker(myOptions);

        geocodingService.geocode(location, (GeocodingResult[] results, GeocoderStatus status) -> {

            LatLong latLong = null;

            if(status == GeocoderStatus.ZERO_RESULTS) {
                this.allowReport = false;
                return;
            } else if(results.length > 1 ) {
                this.allowReport = true;
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                this.allowReport = true;
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            myOptions.position(latLong);
        });
    }

//     private void saveReportJson() {
//        Facade.getInstance().saveReportJson();
//    }
}