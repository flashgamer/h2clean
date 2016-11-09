package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Report;
import model.ReportDB;
import model.WaterPurityReport;
import model.WaterSourceReport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static model.ReportDB.database;

/**
 * Created by lol on 10/16/16.
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
    private Button backButton;

    public static List<String> reportLocations = new ArrayList<>();
    private List<Report> currentReportList;
    private List<Integer> currentReportNumberList;
    private List<String> locations;
    private ReportDB myDB;

    /**
     * Called automatically to initialize the screen.
     */
    @FXML
    private void initialize() {
        try {
            FileInputStream fileIn = new FileInputStream("reportLocations.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            locations = (List<String>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("List class not found");
            c.printStackTrace();
            return;
        }
        try {
            FileInputStream fileIn = new FileInputStream("ReportDB.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            myDB = (ReportDB) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("ReportDB class not found");
            c.printStackTrace();
            return;
        }
        for (String s: locations) {
            Report myReport;
            try {
                String fileName = s + ".ser";
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                myReport = (Report) in.readObject();
                in.close();
                fileIn.close();
            }catch(IOException i) {
                i.printStackTrace();
                return;
            }catch(ClassNotFoundException c) {
                System.out.println("Report class not found");
                c.printStackTrace();
                return;
            }
            myDB.database.insert(myReport);
        }
        locationColumn.getItems().addAll(myDB.database.getKeys());
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
        currentReportList.clear();
        reportNumberColumn.getItems().clear();
        currentReportNumberList.clear();

        List<Report> reportList = myDB.database.get(newValue);
        for (Report r : reportList) {
            reportNumberColumn.getItems().add(r.getReportNumber().toString());
            currentReportNumberList.add(r.getReportNumber());
        }

        this.currentReportList = new LinkedList<>(reportList);
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
        Stage thisStage = (Stage) backButton.getScene().getWindow();
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
