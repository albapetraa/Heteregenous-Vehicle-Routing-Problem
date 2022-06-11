package com.example.appson2;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class Node2 implements Parcelable {

    public int nodeId;
    public double Node_X, Node_Y;
    public int yolcuSayisi;
    public boolean rotalandiMi;
    private boolean varisNoktasiMi;

    public Node2(double varisNoktasi_x, double varisNoktasi_y)
    {
        this.nodeId = 0;
        this.Node_X = varisNoktasi_x;
        this.Node_Y = varisNoktasi_y;
        this.varisNoktasiMi = true;
    }

    public Node2(int id, double x, double y, int yolcuSayisi)
    {
        this.nodeId = id;
        this.Node_X = x;
        this.Node_Y = y;
        this.yolcuSayisi = yolcuSayisi;
        this.rotalandiMi = false;
        this.varisNoktasiMi = false;
    }

    protected Node2(Parcel in) {
        nodeId = in.readInt();
        Node_X = in.readDouble();
        Node_Y = in.readDouble();
        yolcuSayisi = in.readInt();
        rotalandiMi = in.readByte() != 0;
        varisNoktasiMi = in.readByte() != 0;
    }

    public static final Creator<Node2> CREATOR = new Creator<Node2>() {
        @Override
        public Node2 createFromParcel(Parcel in) {
            return new Node2(in);
        }

        @Override
        public Node2[] newArray(int size) {
            return new Node2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeInt(nodeId);
            parcel.writeDouble(Node_X);
            parcel.writeDouble(Node_Y);
            parcel.writeInt(yolcuSayisi);
            parcel.writeBoolean(rotalandiMi);
            parcel.writeBoolean(varisNoktasiMi);
        }


    }
}