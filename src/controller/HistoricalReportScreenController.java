package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.HistoricalLineGraph;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lillian on 11/9/2016.
 */
public class HistoricalReportScreenController {
    @FXML
    private  JFXTextField locationInput;

    @FXML
    private JFXCheckBox virusCheck;

    @FXML
    private JFXCheckBox contaminantCheck;

    @FXML
    private JFXDatePicker datePick;

    private Stage historicalStage;
    private Connection connection;

    /**
     * called when the confirm button is pressed
     * checks if the entered data is valid then generates a historical line graph
     */
    @FXML
    private void handleConfirmButtonAction() {
        validateData();
        if (validateData()) {
            List<Double> virusData = new ArrayList<>();
            List<Double> contaminantData = new ArrayList<>();
            List<String> timeData = new ArrayList<>();
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:purityReportsDB.db");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from purityReportsDB where location = '" +
                        locationInput.getText() + "'");
                while (rs.next()) {
                    virusData.add(rs.getDouble("virusPPM"));
                    contaminantData.add(rs.getDouble("contaminantPPM"));
                    timeData.add(rs.getString("date"));
                }
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
            List<Integer> timeList = new ArrayList<>();

            for (String date : timeData) {
                String year = date.substring(0, 4);
                String month = date.substring(5, 7);
                String strDate = year + month;
                timeList.add(Integer.parseInt(strDate));
            }
            //set timeData int array to real time data points
            HistoricalLineGraph historicalGraph = new HistoricalLineGraph(virusCheck.isSelected(), contaminantCheck
                    .isSelected(), virusData, contaminantData, timeList);
            historicalGraph.showGraph();
        }
    }

    /**
     * checks that user entered valid data for the historical report
     * @return whether data is valid or not
     */
    private boolean validateData() {
        String errorMessage = "";
        if (locationInput.getText() == null) {
            errorMessage += "Location cannot be empty! \n";
        }
        if (!virusCheck.isSelected() && !contaminantCheck.isSelected()) {
            errorMessage += "Please select at either to display virus or contaminant levels or both! \n";
        }
        if (datePick.getValue() == null) {
            errorMessage += "Please choose a time frame! \n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(historicalStage);
            alert.setTitle("Invalid Information");
            alert.setHeaderText("Please try again with the correct values.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    /**
     * called when the cancel button is pressed
     * returns to previous screen
     */
    @FXML
    private void handleCancelPressed() {
        Stage thisStage = (Stage) locationInput.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/LandingScreen.fxml"));
            Stage landingStage = new Stage();
            landingStage.setTitle("Landing Screen");
            landingStage.setScene(new Scene(root,600,400));
            landingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
