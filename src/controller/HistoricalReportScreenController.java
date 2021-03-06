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

    /**
     * called when the confirm button is pressed
     * checks if the entered data is valid then generates a historical line graph
     */
    @FXML
    private void handleConfirmButtonAction() {
        validateData();
        if (validateData()) {
            double[] virusData = {1, 2, 3, 4, 5};
            double[] contaminantData = {2, 3, 4, 5, 6};
            int[] timeData = {1, 2, 3, 4, 5};
            //set timeData int array to real time data points
            HistoricalLineGraph historicalGraph = new HistoricalLineGraph(virusCheck.isSelected(), contaminantCheck.isSelected(), virusData, contaminantData, timeData);
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
