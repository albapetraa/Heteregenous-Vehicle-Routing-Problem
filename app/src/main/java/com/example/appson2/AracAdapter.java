package com.example.appson2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appson2.algorithm.Node;
import com.example.appson2.algorithm.Vehicle;

import java.util.ArrayList;

public class AracAdapter extends ArrayAdapter<Vehicle> {
    private final Context mContext;
    private int mResource;

    private int aracSayisi = 0;


    /***
     *
     * @param context
     * @param resource
     * @param objects
     */
    public AracAdapter(Context context, int resource, Vehicle[] objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int vehId = getItem(position).aracId;
        double carCost = getItem(position).carCost;
        int load = getItem(position).binenYolcuSayisi;
        ArrayList<Node> rota = getItem(position).Rota;
        ArrayList<Node2> rota2 = new ArrayList<Node2>();
        carCost = (int) carCost;
        for (int i = 0; i < rota.size(); i++) {
            Node2 n2 = new Node2(rota.get(i).nodeId,rota.get(i).Node_X,rota.get(i).Node_Y,rota.get(i).yolcuSayisi);
            rota2.add(n2);
        }
        aracSayisi++;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvAracId =(TextView) convertView.findViewById(R.id.aracIdText);
        TextView tvCarCost =(TextView) convertView.findViewById(R.id.aracMaliyet);
        TextView tvCarLoad =(TextView) convertView.findViewById(R.id.aracYolcuSayi);
        Button rotaGoruntuleme = (Button) convertView.findViewById(R.id.rotaButton);
        rotaGoruntuleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Admin_Rota_Goruntuleme.class);
                intent.putParcelableArrayListExtra("aracRotasi",rota2);
                mContext.startActivity(intent);
            }
        });

        tvAracId.setText("Arac Numarası: "+String.valueOf(vehId));
        tvCarCost.setText("Aracın Maliyeti: "+String.valueOf(carCost)+" birim");
        tvCarLoad.setText(String.valueOf("Araçtaki Yolcu Sayısı: "+load));
        return convertView;
    }

}
