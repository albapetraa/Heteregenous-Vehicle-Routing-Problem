package com.example.appson2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appson2.directionhelpers.FetchURL;
import com.example.appson2.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class Admin_Rota_Goruntuleme extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    DatabaseHelper db;
    GoogleMap map;
    private Polyline currentPolyline;
    private ArrayList<Node2> duraklar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rota_goruntuleme);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.adminMap);
        mapFragment.getMapAsync(this);

        ArrayList<Node2> rota = (ArrayList<Node2>) getIntent().getExtras().getSerializable("aracRotasi");
        Toast.makeText(this, "lan "+rota.size(), Toast.LENGTH_SHORT).show();
        String url = getMapsApiDirectionsUrl(rota);
        duraklar = rota;
        new FetchURL(Admin_Rota_Goruntuleme.this).execute(url, "driving");


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng kou = new LatLng(40.822213, 29.921675);
        googleMap.addMarker(new MarkerOptions()
                .position(kou)
                .title("Marker"));
        map.moveCamera(CameraUpdateFactory.newLatLng(kou));
        map.moveCamera(CameraUpdateFactory.zoomTo(8.0f));
    }

    public void durakMarkerEkle(ArrayList<Node2> nodes) {
        for (int i = 1; i < nodes.size() - 1; i++) {
            LatLng durak = new LatLng(nodes.get(i).Node_X, nodes.get(i).Node_Y);
            map.addMarker(new MarkerOptions()
                    .position(durak)
                    .title(String.valueOf(nodes.get(i).nodeId)));
        }
    }


    private String getMapsApiDirectionsUrl(ArrayList<Node2> nodes) {
        String origin = "origin=" + nodes.get(0).Node_X + "," + nodes.get(0).Node_Y;
        String waypoints = "waypoints=optimize:true|";
        String destination = "destination=" + nodes.get(0).Node_X + "," + nodes.get(0).Node_Y;
        for (int i = 1; i < nodes.size() - 1; i++) {
            waypoints = waypoints + "" + nodes.get(i).Node_X + "," + nodes.get(i).Node_Y + "|";
        }

        String sensor = "sensor=false";
        String params = origin + "&" + waypoints + "&" + destination + "&" + sensor + "&key=AIzaSyBGQEm8CODkjk6laOBUxMFWVP3zZ0GDBlY";

        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + params;
        return url;
    }


    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();

        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
        map.moveCamera(CameraUpdateFactory.zoomTo(9.0f));
        durakMarkerEkle(duraklar);

    }

}