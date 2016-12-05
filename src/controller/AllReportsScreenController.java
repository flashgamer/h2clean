package controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Report;
import model.ReportDB;
import model.WaterPurityReport;
import model.WaterSourceReport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vaishak on 10/16/16.
 */
public class AllReportsScreenController {

    @FXML
    private ListView<String> locationColumn;

    @FXML
    private ListView<String> reportNumberColumn;

    @FXML
    private Label reportNumber;

    @FXML
    private Label dateTime;

    @FXML
    private Label reporterName;

    @FXML
    private Label waterLocation;

    @FXML
    private Label waterType;

    @FXML
    private Label waterCondition;

    @FXML
    private Label virusPPM;

    @FXML
    private Label contaminantPPM;

    @FXML
    private Label reportType;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton availabilityButton;

    public static final List<String> reportLocations = new ArrayList<>();
    private List<Report> currentReportList;
    private List<Integer> currentReportNumberList;
    private ReportDB myDB;
    private Connection connection;

    /**
     * Called automatically to initialize the screen.
     */
    @FXML
    private void initialize() {
        LinkedList<String> locations = new LinkedList<>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:waterReportsDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select location from waterReportsDB");
            while (rs.next()) {
                locations.add(rs.getString("location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:purityReportsDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select location from purityReportsDB");
            while (rs.next()) {
                if (!locations.contains(rs.getString("location"))) {
                    locations.add(rs.getString("location"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        locationColumn.setItems(FXCollections.observableArrayList(locations));
        this.currentReportList = new LinkedList<Report>();
        this.currentReportNumberList = new LinkedList<Integer>();

        locationColumn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onLocationSelect(newValue);
            }
        });

        reportNumberColumn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onNumberSelect(newValue);
            }
        });

    }

    /**
     * Called when user clicks on the Location of the report they wish to view.
     * @param newValue String representation of the Location.
     */
    private void onLocationSelect(String newValue) {
        List<String> reportList = new LinkedList<>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:waterReportsDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select reportNum from waterReportsDB where location = '" +
                    newValue + "'");
            while (rs.next()) {
                reportList.add(rs.getString("reportNum"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:purityReportsDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select reportNum from purityReportsDB where location = '" +
                    newValue + "'");
            while (rs.next()) {
                reportList.add(rs.getString("reportNum"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reportNumberColumn.setItems(FXCollections.observableList(reportList));
//        for (Report r : reportList) {
//            reportNumberColumn.getItems().add(r.getReportNumber().toString());
//            currentReportNumberList.add(r.getReportNumber());
//        }
//
//        this.currentReportList = new LinkedList<>(reportList);
    }

    /**
     * Called when user clicks on the number of the report they wish to view.
     * @param newValue String representation of the number of report they want
     *                 to view.
     */
    private void onNumberSelect(String newValue) {

        if (newValue == null) {
            return;
        }

        Report report = currentReportList.get(currentReportNumberList.indexOf(new Integer(newValue)));
        reportNumber.setText(newValue);
        dateTime.setText(report.getSubmitTime().toString());
        reporterName.setText(report.getSubmitAccount().getUser().getProfile().getFirstName()
                + " " + report.getSubmitAccount().getUser().getProfile().getLastName());
        waterLocation.setText(report.getLocation());
        if (report instanceof WaterSourceReport) {
            waterType.setText(((WaterSourceReport) report).getType().toString());
            waterCondition.setText(((WaterSourceReport) report).getCondition().toString());
            reportType.setText("Water Source Report");
            virusPPM.setText("--");
            contaminantPPM.setText("--");
        } else if (report instanceof WaterPurityReport) {
            waterCondition.setText(((WaterPurityReport) report).getCondition().toString());
            waterType.setText("--");
            reportType.setText("Water Purity Report");
            virusPPM.setText(Double.toString(((WaterPurityReport) report).getVirusPPM()));
            contaminantPPM.setText(Double.toString(((WaterPurityReport) report).getContaminantPPM()));
        }
    }

    /**
     * Called when user clicks on Back button.
     *
     * @param event Unused
     */
    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        Stage thisStage = (Stage) backButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/LandingScreen.fxml"));
            Stage reportStage = new Stage();
            reportStage.setTitle("Landing Screen");
            reportStage.setScene(new Scene(root, 600, 400));
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when user clicks View Availability Reports.
     */
    @FXML
    private void handleViewAvailabilityReportsAction() {
        Stage thisStage = (Stage) availabilityButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource
                    ("../view/WaterAvailabilityReportScreen.fxml"));
            Stage waterPurityReportStage = new Stage();
            waterPurityReportStage.setTitle("Water Availability Report");
            waterPurityReportStage.setScene(new Scene(root, 600, 400));
            waterPurityReportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the reportLocations serialization file
     */
    public static void updateSerFile() {
        try {
            String fileName = "reportLocations.ser";
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(reportLocations);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }
}
