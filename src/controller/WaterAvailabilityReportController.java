package controller;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 * Controller for the Water Availability Report screen
 * Shows a map as provided by the Google Map API and GMapsFX API
 * Created by Jonathan on 10/25/2016.
 */
public class WaterAvailabilityReportController implements Initializable, MapComponentInitializedListener{
    @FXML
    private Button button;

    @FXML
    private GoogleMapView mapView;

    private static GoogleMap map;

    public static HashMap<Marker, InfoWindow> markerMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(33.7490, -84.3880))
                .mapMaker(true)
                .overviewMapControl(false)
                .panControl(true)
                .rotateControl(false)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(10);

        map = mapView.createMap(mapOptions);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLong(33.7490, -84.3880));
        Marker mark = new Marker(markerOptions);
        mark.setTitle("Check");
        InfoWindow window = new InfoWindow(new InfoWindowOptions());
        window.open(map, mark);
        map.addMarker(mark);
        map.addUIEventHandler(UIEventType.click, (obj) -> {
            System.out.println("HALP");
        });
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

    /**
     * Adds any markers that have been created into the map to be shown.
     */
    public static void updateMap() {
        if (map != null) {
            Set<Marker> markerSet = markerMap.keySet();
            for (Marker m : markerSet) {
                map.addMarker(m);
            }
        }
        return;
    }
}
