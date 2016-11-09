package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.HistoricalLineGraph;
import model.Report;
import model.WaterPurityReport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


/**
 * Created by Lillian on 11/9/2016.
 */
public class HistoricalReportScreenController {
    @FXML
    private TextField locationInput;

    @FXML
    private CheckBox virusCheck;

    @FXML
    private CheckBox contaminantCheck;

    @FXML
    private DatePicker datePick;

    private Stage historicalStage;

    private HistoricalLineGraph historicalGraph;

    private WaterPurityReport myReport;

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
            if (virusCheck.isSelected()) {
                try {
                    String fileName = locationInput.getText() + ".ser";
                    FileInputStream fileIn = new FileInputStream(fileName);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    myReport = (WaterPurityReport) in.readObject();
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
                virusData[0] = myReport.getVirusPPM();
            }
            if (contaminantCheck.isSelected()) {
                //set contaminantData int array to real data
            }
            //set timeData int array to real time data points
            historicalGraph = new HistoricalLineGraph(virusCheck.isSelected(), contaminantCheck.isSelected(), virusData, contaminantData, timeData);
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
