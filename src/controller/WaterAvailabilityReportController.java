package controller;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Report;
import model.ReportDB;
import model.WaterPurityReport;
import model.WaterSourceReport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Controller for the Water Availability Report screen
 * Shows a map as provided by the Google Map API and GMapsFX API
 * Created by Jonathan on 10/25/2016.
 */
public class WaterAvailabilityReportController implements MapComponentInitializedListener{
    @FXML
    private Button button;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    public HashMap<Marker, InfoWindow> markerMap = new HashMap<>();

    private GeocodingService geocodingService;

    @FXML
    private void initialize() {
        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(33.7490, -84.3880))
                .mapMarker(true)
                .overviewMapControl(false)
                .panControl(true)
                .rotateControl(false)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(10);

        map = mapView.createMap(mapOptions);

//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(new LatLong(33.7490, -84.3880));
//        Marker mark = new Marker(markerOptions);
//        mark.setTitle("Check");
//        InfoWindow window = new InfoWindow(new InfoWindowOptions());
//        window.setContent("test");
//        window.open(map, mark);
//        map.addMarker(mark);
//        map.addUIEventHandler(UIEventType.click, (obj) -> {
//            System.out.println("HALP");
//        });
        updateMap();
    }


    /**
     * Called when Back button is pressed.
     * Returns user to All Reports screen and closes out of current screen
     */
    @FXML
    private void handleBackPressed() {
        Stage thisStage = (Stage) mapView.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource
                    ("../view/AllReportsScreen.fxml"));
            Stage waterPurityReportStage = new Stage();
            waterPurityReportStage.setTitle("All Reports");
            waterPurityReportStage.setScene(new Scene(root, 600, 400));
            waterPurityReportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMarkerMap() {
        //this.markerMap =
    }

    /**
     * Adds any markers that have been created into the map to be shown.
     */
    private void updateMap() {
        int count = 0;
        if (map != null) {
            Set<String> locationSet = ReportDB.database.getKeys();
            for (String l : locationSet) {
                for (Report r : ReportDB.database.get(l)) {
                    Marker m = generateMarker(l);
                    m.setVisible(true);
                    m.setTitle(Integer.toString(count));
                    InfoWindow window = new InfoWindow(
                            (new InfoWindowOptions()).content(generateInfoWindowContent(r)));
                    window.setContent(generateInfoWindowContent(r));
                    window.open(map, m);
                    map.addMarker(m);
                }
            }
        }
        return;
    }

    /**
     * Generates a Marker from a specified location(address) to be shown on the map
     * @param location the Location to generate the marker at
     * @return a Marker positioned at the specified location
     */
    private Marker generateMarker(String location) {
        geocodingService = new GeocodingService();
        MarkerOptions myOptions = new MarkerOptions();
        Marker marker = new Marker(myOptions);
        geocodingService.geocode(location, (GeocodingResult[] results, GeocoderStatus status) -> {

            LatLong latLong = null;

            if( results.length > 1 ) {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            marker.setPosition(latLong);
        });

        return marker;
    }


    /**
     * Generates a String with the information from the report
     * @return a String containing the information from the report.
     */
    private String generateInfoWindowContent(Report report) {
        String content = "";

        if (report instanceof WaterSourceReport) {
            WaterSourceReport waterSourceReport = ((WaterSourceReport) report);
            String waterType = "Type: " + waterSourceReport.getType();
            String condition = "Condition: " + waterSourceReport.getCondition();
            content = waterType + "\n" + condition + "\n";
        } else if (report instanceof WaterPurityReport) {
            WaterPurityReport waterPurityReport = ((WaterPurityReport) report);
            String waterCondition = "Condition: " + waterPurityReport.getCondition();
            String virus = "Virus (PPM): " + waterPurityReport.getVirusPPM();
            String contaminant = "Contaminant (PPM): " + waterPurityReport.getContaminantPPM();
            content = waterCondition + "\n" + virus + "\n" + contaminant + "\n";
        }

        return content;

    }
}
