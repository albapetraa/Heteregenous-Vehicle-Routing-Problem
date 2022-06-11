package com.example.appson2.algorithm;

import java.util.ArrayList;

public class Solution {

    int AracSayisi;
    int DurakSayisi;
    Vehicle[] Araclar;
    double Maliyet;


    Solution(int DurakSayisi, int AracSayisi, ArrayList<Integer> vehCaps) {
        this.AracSayisi = AracSayisi;
        this.DurakSayisi = DurakSayisi;
        this.Maliyet = 0;
        Araclar = new Vehicle[this.AracSayisi];

        for (int i = 0; i < this.AracSayisi; i++) {
            int VechCap = vehCaps.get(i);
            Araclar[i] = new Vehicle(i + 1, VechCap);
        }
    }

    public boolean DuraktaYolcuVarMi(ArrayList<Node> arrNodes) {
        for (int i = 1; i < arrNodes.size(); i++) {
            if (!arrNodes.get(i).rotalandiMi) {
                return true;
            }
        }
        return false;
    }

    public void GreedySolution(ArrayList<Node> arrNodes, double[][] MaliyetMatrisi) {

        double AdayMaliyet, SonMaliyet;
        int AracIndex = 0;

        while (DuraktaYolcuVarMi(arrNodes)) {

            int DurakIndex = 0;
            Node Aday = null;
            double minimumMaliyet = (float) Double.MAX_VALUE;

            if (Araclar[AracIndex].Rota.isEmpty()) {
                Araclar[AracIndex].AddNode(arrNodes.get(0));
            }

            for (int i = 1; i < DurakSayisi; i++) {
                if (arrNodes.get(i).rotalandiMi == false) {

                    if (Araclar[AracIndex].Kontrol(arrNodes.get(i).yolcuSayisi)) {
                        AdayMaliyet = MaliyetMatrisi[Araclar[AracIndex].MevcutLokasyon][i];
                        if (minimumMaliyet > AdayMaliyet) {
                            minimumMaliyet = AdayMaliyet;
                            DurakIndex = i;
                            Aday = arrNodes.get(i);
                            Araclar[AracIndex].binenYolcuSayisi += arrNodes.get(i).yolcuSayisi;
                            arrNodes.get(i).yolcuSayisi = 0;
                        }
                    } else {
                        if ((arrNodes.get(i).yolcuSayisi > Araclar[AracIndex].kapasite) && (Araclar[AracIndex].binenYolcuSayisi < Araclar[AracIndex].kapasite)) {
                            AdayMaliyet = MaliyetMatrisi[Araclar[AracIndex].MevcutLokasyon][i];
                            if (minimumMaliyet > AdayMaliyet) {
                                int demandSayisi = arrNodes.get(i).yolcuSayisi;
                                int vehCapacity = Araclar[AracIndex].kapasite;
                                int vehLoad = Araclar[AracIndex].binenYolcuSayisi;
                                int x = vehCapacity - vehLoad;
                                Araclar[AracIndex].binenYolcuSayisi = vehLoad + x;
                                arrNodes.get(i).yolcuSayisi = demandSayisi - x;
                                Aday = arrNodes.get(i);
                                minimumMaliyet = AdayMaliyet;
                            }

                        }

                    }
                }
            }

            if (Aday == null) {
                if (AracIndex + 1 < Araclar.length) {
                    if (Araclar[AracIndex].MevcutLokasyon != 0) {
                        SonMaliyet = MaliyetMatrisi[Araclar[AracIndex].MevcutLokasyon][0];
                        Araclar[AracIndex].AddNode(arrNodes.get(0));
                        Araclar[AracIndex].carCost += SonMaliyet;
                        this.Maliyet += SonMaliyet;
                    }
                    AracIndex = AracIndex + 1;
                } else {
                    System.out.println("Problemin çözülemiyor. Çözülmesi için düzgün yolcu sayıları girin.");
                    System.exit(0);
                }
            } else {
                Araclar[AracIndex].AddNode(Aday);
                arrNodes.get(DurakIndex).rotalandiMi = true;
                Araclar[AracIndex].carCost += minimumMaliyet;

                this.Maliyet += minimumMaliyet;

            }
        }
        SonMaliyet = MaliyetMatrisi[Araclar[AracIndex].MevcutLokasyon][0];
        Araclar[AracIndex].AddNode(arrNodes.get(0));
        this.Maliyet += SonMaliyet;

    }

    public void CozumuYazdir(String CozumAlgoritmasi) {
        System.out.println("=========================================================");
        System.out.println(CozumAlgoritmasi + "\n");

        for (int j = 0; j < AracSayisi; j++) {
            if (!Araclar[j].Rota.isEmpty()) {
                System.out.print("Araç " + j + ":");
                int RotaUzunlugu = Araclar[j].Rota.size();
                for (int k = 0; k < RotaUzunlugu; k++) {
                    if (k == RotaUzunlugu - 1) {
                        System.out.print(Araclar[j].Rota.get(k).nodeId + "[" + Araclar[j].binenYolcuSayisi + "]");
                    } else {
                        System.out.print(Araclar[j].Rota.get(k).nodeId + "->");
                    }
                }
                System.out.println();
            }
        }
        System.out.println("\nÇözümün maliyeti:  " + this.Maliyet + "\n");
    }

    public Vehicle[] getAraclar() {
        return Araclar;
    }

    public double getMaliyet() {
        return Maliyet;
    }
}


