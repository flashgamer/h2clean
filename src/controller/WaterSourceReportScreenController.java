package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
}