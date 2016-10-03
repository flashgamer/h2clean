package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by Jonathan on 10/3/2016.
 */
public class ReportSubmitController {

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

}
