package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 10/3/2016.
 */
public class SubmitReportController {

    @FXML
    private Label headingText;

    @FXML
    private JFXComboBox<String> typeReport;

    private Stage _submitReportPopupStage;


    @FXML
    private void initialize() {
        List<String> comboBoxList = new ArrayList<>();
        comboBoxList.add("Water Source Report");
        comboBoxList.add("Water Purity Report");
        typeReport.setItems(FXCollections.observableArrayList(comboBoxList));
        typeReport.setValue("Water Source Report");
    }

    /**
     * Sets the stage of the dialog.
     *
     * @param submitReportPopupStage the stage of the login
     */
    public void setLoginStage(Stage submitReportPopupStage) {
        _submitReportPopupStage =
            submitReportPopupStage; }

    /**
     * Called when Submit New Report Button pressed.
     */
    @FXML
    private void handleSubmitNewReportPressed() {
        Stage thisStage = (Stage) headingText.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
    }

    @FXML
    private void handleCancelPressed() {
        Stage thisStage = (Stage) typeReport.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
    }

    /**
     * Called when user clicks the Proceed button after selecting the report
     * they want to submit.
     *
     * @param event Unused
     */
    @FXML
    private void handleProceedPressed(ActionEvent event) {
        if (isUserValid().equals("banned")) {
            String errorMessage = "You are not allowed to submit this report " +
                    "because you are banned.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_submitReportPopupStage);
            alert.setTitle("Invalid Status");
            alert.setHeaderText("Unban required.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        } else if (isUserValid().equals("error")) {
            //show error message
            String errorMessage = "You are not authorized to submit this report" +
                    ".\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_submitReportPopupStage);
            alert.setTitle("Invalid Authorization");
            alert.setHeaderText("Authorization required.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        } else if (isUserValid().equals("WaterSourceReportScreen")) {
            Stage thisStage = (Stage) typeReport.getScene().getWindow();
            thisStage.close();
            thisStage.hide();
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../view/WaterSourceReportScreen.fxml"));
                Stage waterSourceReportStage = new Stage();
                waterSourceReportStage.setTitle("Water Source Report");
                waterSourceReportStage.setScene(new Scene(root, 600, 400));
                waterSourceReportStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (isUserValid().equals("WaterPurityReportScreen")) {
            Stage thisStage = (Stage) typeReport.getScene().getWindow();
            thisStage.close();
            thisStage.hide();
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../view/WaterPurityReportScreen.fxml"));
                Stage waterPurityReportStage = new Stage();
                waterPurityReportStage.setTitle("Water Purity Report");
                waterPurityReportStage.setScene(new Scene(root, 600, 400));
                waterPurityReportStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String isUserValid() {

        // Checks if user is authorized to submit a type of report, error
        // message pops up if otherwise.
        if (LoginScreenController.account.getAccountType().equals("User")) {
            if (typeReport.getValue().equals("Water Source Report")) {
                return ("WaterSourceReportScreen");
            } else if (typeReport.getValue().equals("Water Purity Report")) {
                return ("error");
            } else if (!LoginScreenController.account.getUser().canSubmitReport()) {
                return ("banned");
            }
        } else if (LoginScreenController.account.getAccountType().equals
                ("Worker")) {
            if (typeReport.getValue().equals("Water Source Report")) {
                return ("WaterSourceReportScreen");
            } else if (typeReport.getValue().equals("Water Purity Report")) {
                return ("WaterPurityReportScreen");
            }
        } else if (LoginScreenController.account.getAccountType().equals
                ("Manager")) {
            if (typeReport.getValue().equals("Water Source Report")) {
                return ("WaterSourceReportScreen");
            } else if (typeReport.getValue().equals("Water Purity Report")) {
                return ("WaterPurityReportScreen");
            }
        }
        return ("WaterSourceReportScreen");
    }


}
