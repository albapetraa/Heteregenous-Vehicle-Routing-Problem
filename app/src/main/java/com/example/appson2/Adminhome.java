package com.example.appson2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appson2.algorithm.VRP;
import com.google.android.material.snackbar.Snackbar;

public class Adminhome extends AppCompatActivity {

    DatabaseHelper db;
    private Button yolcuEkle;
    private Button rotaHesapla;
    private int secilenDurak;
    private EditText yolcuText;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);

        yolcuEkle = findViewById(R.id.yolcuEkleButton);
        yolcuText = findViewById(R.id.yolcuText);
        rotaHesapla = findViewById(R.id.rotaHesaplaButton);
        lv = (ListView) findViewById(R.id.liste);
        Spinner spinner = findViewById(R.id.spinner);
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
        yolcuEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(getApplicationContext());
                int yolcuSayisi = Integer.parseInt(yolcuText.getText().toString());
                db.yolcuMiktariDuzenle(secilenDurak, yolcuSayisi);
                Snackbar.make(view, "Düzenlenen Durak: " + secilenDurak + " Yolcu Sayısı: " + yolcuSayisi + " başarıyla düzenlendi.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        rotaHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = new DatabaseHelper(getApplicationContext());
                VRP x = new VRP();
                db.aracBilgileriSifirla();
                x.vrpyicalistir(db);
                db.rotalarOlustu();
                AracAdapter adapter = new AracAdapter(Adminhome.this,R.layout.adapter_view_layout,x.getTempVehicle());
                lv.setAdapter(adapter);
                TextView ekstra = (TextView) findViewById(R.id.ekstraMaliyet);
                ekstra.setText("Kiralama Maliyeti(Her Araç 25 Birim): "+String.valueOf(hesapla(adapter.getCount()))+" birim");
                TextView maliyet = (TextView) findViewById(R.id.maliyet);
                maliyet.setText("Toplam Maliyet: "+String.valueOf(x.getCost())+" birim");
                Snackbar.make(view, "Rota Başarıyla Oluşturuldu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


    }
    public int hesapla(int aracSayisi){
        int maliyet = 0;
        if(aracSayisi>3){
            for (int i = 4; i <= aracSayisi; i++) {
                maliyet +=25;
            }
        }
        return maliyet;
    }


}