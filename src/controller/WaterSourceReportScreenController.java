package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the submit water report screen.
 * @author Hotline String
 */
public class WaterSourceReportScreenController {
    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<String> waterTypeField;

    @FXML
    private ComboBox<String> waterConditionField;

    private String userKey;

    @FXML
    private void initialize() {
        List<String> waterTypeList = new ArrayList<>();
        waterTypeList.add("Bottled");
        waterTypeList.add("Well");
        waterTypeList.add("Stream");
        waterTypeList.add("Lake");
        waterTypeList.add("Spring");
        waterTypeList.add("Other");
        List<String> waterConditionList = new ArrayList<>();
        waterConditionList.add("Waste");
        waterConditionList.add("Treatable-Clear");
        waterConditionList.add("Treatable-Muddy");
        waterConditionList.add("Potable");
        waterTypeField.setItems(FXCollections.observableArrayList(waterTypeList));
        waterConditionField.setItems(FXCollections.observableArrayList(waterConditionList));
    }

    @FXML
    private void handleCancelPressed() {
        Stage thisStage = (Stage) locationField.getScene().getWindow();
        thisStage.close();
        thisStage.hide();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("../view/SubmitReportPopup.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage submitReportStage = new Stage();
            SubmitReportController src = fxmlLoader.<SubmitReportController>getController();
            src.receiveUserKey(userKey);
            submitReportStage.setTitle("Submit Report");
            submitReportStage.setScene(new Scene(root,400,250));
            submitReportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
