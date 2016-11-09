package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by lol on 11/9/16.
 */
public class ViewReportController {
    @FXML
    JFXComboBox<String> viewReportType;

    @FXML
    Button proceedButton;

    @FXML
    Button cancelButton;

    private Stage _viewReportPopupStage;

    @FXML
    private void initialize() {
        viewReportType.getItems().addAll("All Reports", "Historical Reports");
        viewReportType.setValue("All Reports");
    }

    @FXML
    private void handleCancelButton() {
        Stage thisStage = (Stage) cancelButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("../view/LandingScreen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage reportStage = new Stage();
            reportStage.setTitle("Landing Screen");
            reportStage.setScene(new Scene(root, 600, 400));
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProceedButton() {
        String filePath;
        if (viewReportType.getValue().equals("All Reports")) {
            filePath = "../view/AllReportsScreen.fxml";
        } else if (viewReportType.getValue().equals("Historical Reports")
                && LoginScreenController.account.getAccountType().equals
                ("Manager")){
            filePath = "../view/HistoricalReportScreen.fxml";
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_viewReportPopupStage);
            alert.setTitle("Invalid Authorization");
            alert.setHeaderText("Authorization required.");
            alert.setContentText("You are not a manager!");
            alert.showAndWait();
            return;
        }

        Stage thisStage = (Stage) cancelButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    (filePath));
            Parent root = (Parent) fxmlLoader.load();
            Stage reportStage = new Stage();
            reportStage.setTitle("Landing Screen");
            reportStage.setScene(new Scene(root, 600, 400));
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
