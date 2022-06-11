package com.example.appson2.algorithm;


import java.util.ArrayList;


public class Vehicle {

    public int aracId;
    public ArrayList<Node> Rota = new ArrayList<>();
    public int kapasite;
    public int binenYolcuSayisi;
    public int MevcutLokasyon;
    public boolean Kapali;
    public int carCost;

    public Vehicle(int id, int cap) {
        this.aracId = id;
        this.kapasite = cap;
        this.binenYolcuSayisi = 0;
        this.MevcutLokasyon = 0;
        this.Kapali = false;
        this.Rota.clear();
    }

    public void AddNode(Node Durak)
    {
        Rota.add(Durak);
        this.MevcutLokasyon = Durak.nodeId;
    }

    public boolean Kontrol(int yolcuSayi)
    {
        return ((binenYolcuSayisi + yolcuSayi <= kapasite));
    }

}
