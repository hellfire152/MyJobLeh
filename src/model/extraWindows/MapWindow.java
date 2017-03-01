package model.extraWindows;

import java.io.IOException;

import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.javafx.MapView;

import application.MyJobLeh;
import controller.employee.ApplyJobController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.account.EmployeeAccount;
import model.event.WorkEvent;

public class MapWindow {
	
	public static void display(WorkEvent work) {
		Stage window = new Stage();
		
		final MapView mapView = new MapView();
        String location = work.getAddress();
        
        mapView.setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                if (status == MapStatus.MAP_STATUS_OK) {
                    final Map map = mapView.getMap();
                    map.setZoom(25.0);
                    GeocoderRequest request = new GeocoderRequest();
                    request.setAddress(location);

                    mapView.getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
                        @Override
                        public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
                            if (status == GeocoderStatus.OK) {
                                map.setCenter(result[0].getGeometry().getLocation());
                                Marker marker = new Marker(map);
                                marker.setPosition(result[0].getGeometry().getLocation());

                                final InfoWindow window = new InfoWindow(map);
                                window.setContent(location);
                                window.open(map, marker);
                                System.out.println(window);
                            }
                        }
                    });
                }
            }
        });

        Scene scene = new Scene(new BorderPane(mapView), 700, 500);
        
        window.setScene(scene);
        window.show();
	}
}
