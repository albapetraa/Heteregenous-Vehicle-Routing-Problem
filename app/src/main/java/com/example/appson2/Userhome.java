package com.example.appson2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appson2.algorithm.Node;
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


public class Userhome extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    DatabaseHelper db;
    GoogleMap map;
    private int secilenDurak;
    private EditText yolcuText;
    private TextView rotaDurum;
    private Button sec;
    private Polyline currentPolyline;
    private ArrayList<Node> duraklar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        db = new DatabaseHelper(getApplicationContext());

        rotaDurum = findViewById(R.id.rotaDurumText);
        String rotaDurumu = getIntent().getStringExtra("userRotaDurum");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        sec = findViewById(R.id.userDurakButton);
        Spinner spinner = findViewById(R.id.userSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.duraklar_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                secilenDurak = parent.getSelectedItemPosition() + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Userhome.this, "Durak No" + (secilenDurak), Toast.LENGTH_SHORT).show();

                db.kullaniciDuragiEkle(secilenDurak);
                db.kullaniciUserDataEkle(getIntent().getStringExtra("userLoginName"), secilenDurak);
                ArrayList<Node> rotaDuraklar = db.userRotaAl(getIntent().getStringExtra("userLoginName"));
                Log.i("googleurl", "" + getMapsApiDirectionsUrl(rotaDuraklar));
                duraklar = rotaDuraklar;
                rotaDurum.setText("Yeni Rotanız henüz oluşturulmadı.");
                map.clear();


            }
        });
        if (rotaDurumu.equals("false")) {
            rotaDurum.setText("Rotanız henüz oluşturulmadı.");
        } else {
            rotaDurum.setText("Rotanız oluşturuldu.");
            ArrayList<Node> rotaDuraklar = db.userRotaAl(getIntent().getStringExtra("userLoginName"));
            String url = getMapsApiDirectionsUrl(rotaDuraklar);
            duraklar = rotaDuraklar;
            new FetchURL(Userhome.this).execute(url, "driving");
        }


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

    public void durakMarkerEkle(ArrayList<Node> nodes) {
        for (int i = 1; i < nodes.size() - 1; i++) {
            LatLng durak = new LatLng(nodes.get(i).Node_X, nodes.get(i).Node_Y);
            map.addMarker(new MarkerOptions()
                    .position(durak)
                    .title(String.valueOf(nodes.get(i).nodeId)));
        }
    }


    private String getMapsApiDirectionsUrl(ArrayList<Node> nodes) {
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
