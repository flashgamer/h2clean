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
import model.WaterPurityReport;
import model.WaterSourceReport;

import java.io.IOException;
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
    private Button backButton;

    private List<Report> currentReportList;
    private List<Integer> currentReportNumberList;

    @FXML
    private void initialize() {
        locationColumn.getItems().addAll(database.getKeys());
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

    private void onLocationSelect(String newValue) {
        currentReportList.clear();
        reportNumberColumn.getItems().clear();
        currentReportNumberList.clear();

        List<Report> reportList = database.get(newValue);
        for (Report r : reportList) {
            reportNumberColumn.getItems().add(r.getReportNumber().toString());
            currentReportNumberList.add(r.getReportNumber());
        }

        this.currentReportList = new LinkedList<>(reportList);
    }

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
        } else if (report instanceof WaterPurityReport) {
            waterCondition.setText(((WaterPurityReport) report).getCondition().toString());
            waterType.setText("--");
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

}
