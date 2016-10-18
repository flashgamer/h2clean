package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import model.Report;
import model.WaterPurityReport;
import model.WaterSourceReport;

import java.util.List;

import static model.ReportDB.database;

/**
 * Created by lol on 10/16/16.
 */
public class AllReportsScreenController {

    @FXML
    ListView<String> locationColumn;

    @FXML
    ListView<String> reportNumberColumn;

    @FXML
    Label reportNumber;

    @FXML
    Label dateTime;

    @FXML
    Label reporterName;

    @FXML
    Label location;

    @FXML
    Label waterType;

    @FXML
    Label waterCondition;

    @FXML
    private void initialize() {
        locationColumn.getItems().addAll(database.getKeys());
        System.out.println("fuck");
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
        List<Report> reportList = database.get(newValue);
        Integer reportCount = 1;
        for (Report r : reportList) {
            reportNumberColumn.getItems().add(reportCount.toString());
            reportCount++;
        }
    }

    private void onNumberSelect(String newValue) {
        Report report = database.get(newValue).get(new Integer(newValue));
        reportNumber.setText(newValue);
        dateTime.setText(report.getSubmitTime().toString());
        reporterName.setText(report.getSubmitAccount().getUser().getProfile().getFirstName()
                + " " + report.getSubmitAccount().getUser().getProfile().getLastName());
        location.setText(report.getLocation());
        if (report instanceof WaterSourceReport) {
            waterType.setText(((WaterSourceReport) report).getType().toString());
            waterCondition.setText(((WaterSourceReport) report).getCondition().toString());
        } else if (report instanceof WaterPurityReport) {
            waterCondition.setText(((WaterSourceReport) report).getCondition().toString());
            waterType.setText("--");
        }
    }

}
