package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jonathan on 10/3/2016.
 */
public class SubmitReportController {

    @FXML
    Label headingText;

    /**
     * Called when Submit New Report Button pressed.
     */
    @FXML
    private void handleSubmitNewReportPressed() {
        // TODO: Add new report to database.
        Stage thisStage = (Stage) headingText.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
    }

    @FXML
    private void handleCancelPressed() {
        Stage thisStage = (Stage) headingText.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/LandingScreen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage profileStage = new Stage();
            ProfileScreenController psc = fxmlLoader.<ProfileScreenController>getController();
            profileStage.setTitle("Landing Screen");
            profileStage.setScene(new Scene(root,600,400));
            profileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
