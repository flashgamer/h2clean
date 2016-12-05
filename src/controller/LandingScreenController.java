package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.scene.layout.AnchorPane;
import model.Facade;
import model.Location;
import model.Report;
import model.ReportDB;
import model.WaterPurityReport;
import model.WaterSourceReport;
import netscape.javascript.JSObject;


import java.util.HashMap;
import java.util.List;
import java.util.Set;



/**
 * Controller for landing page once user logs in successfully
 * Some code reused from other files within the package.
 *
 * @author Hotline String
 */
public class LandingScreenController implements MapComponentInitializedListener{
    @FXML
    private JFXButton editProfileButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton submitReport;

    @FXML
    private JFXButton viewReportButton;

    @FXML
    private Button button;

    @FXML
    private GoogleMapView mapView;

    @FXML
    private AnchorPane mapField;

    private GoogleMap map;

    private final HashMap<Marker, InfoWindow> markerMap = new HashMap<>();


    /**
     * Automatically called to initialize the screen.
     */

    @FXML
    private void initialize() {
        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
        mapField.getChildren().add(mapView);
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
        Facade fc = Facade.getInstance();
        List<Location> locations = fc.getLocations();

        for (Location l: locations) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(l.getLatitude(), l.getLongitude());

            markerOptions.position( loc )
                    .visible(Boolean.TRUE)
                    .title(l.getTitle());

            Marker marker = new Marker( markerOptions );

            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindow window = markerMap.get(marker);
                        window.open(map, marker);});

            map.addMarker(marker);

        }
        map.addUIEventHandler(UIEventType.click, (JSObject e) -> {
            JSObject clicked = (JSObject) e.getMember("latLng");
            Double lat = (Double) clicked.call("lat");
            Double lng = (Double) clicked.call("lng");
            MarkerOptions myOps = new MarkerOptions();
            myOps.position(new LatLong(lat, lng));
            Marker clickMarker = new Marker(myOps);
            markerMap.put(clickMarker, null);
            map.addMarker(clickMarker);
        });

        updateMap();
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
                    map.addMarker(m);
                    map.addUIEventHandler(m,
                            UIEventType.click,
                            (JSObject obj) -> {
                                window.open(map, m);
                            });
                }
            }
        }
    }

    /**
     * Generates a Marker from a specified location(address) to be shown on the map
     * @param location the Location to generate the marker at
     * @return a Marker positioned at the specified location
     */
    private Marker generateMarker(String location) {
        GeocodingService geocodingService = new GeocodingService();
        MarkerOptions myOptions = new MarkerOptions();
        Marker marker = new Marker(myOptions);
        geocodingService.geocode(location, (GeocodingResult[] results, GeocoderStatus status) -> {

            LatLong latLong = null;

            if(results.length > 1 ) {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                        results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                        results[0].getGeometry().getLocation().getLongitude());
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

    /**
     * Called when user clicks on Edit Profile button
     * @param event Unused
     */
    @FXML
    private void editProfile(ActionEvent event) {
        Stage thisStage = (Stage) editProfileButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/ProfileScreen.fxml"));
            Stage profileStage = new Stage();
            profileStage.setTitle("Profile Screen");
            profileStage.setScene(new Scene(root,600,400));
            profileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Called when user clicks on Logout button.
     *
     * @param event Unused
     */
    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {
        Stage thisStage = (Stage) logoutButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/WelcomeScreen.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root, 600, 400));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when user clicks on Submit Report button. Dialog box will pop up.
     *
     * @param event Unused
     */
    @FXML
    private void handleSubmitReportButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("../view/SubmitReportPopup.fxml"));
            Parent root = fxmlLoader.load();
            Stage submitReportStage = new Stage();
            //SubmitReportController src = fxmlLoader.<SubmitReportController>getController();
            submitReportStage.setTitle("Submit Report");
            submitReportStage.setScene(new Scene(root,390,250));
            submitReportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when user clicks on View Report button.
     *
     * @param event Unused
     */
    @FXML
    private void handleViewReportButtonAction(ActionEvent event) {
        Stage thisStage = (Stage) viewReportButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("../view/ViewReportPopup.fxml"));
            Parent root = fxmlLoader.load();
            Stage reportStage = new Stage();
            reportStage.setTitle("All Reports");
            reportStage.setScene(new Scene(root, 390, 250));
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}