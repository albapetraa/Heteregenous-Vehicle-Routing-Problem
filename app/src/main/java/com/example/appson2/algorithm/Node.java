package com.example.appson2.algorithm;

public class Node {

    public int nodeId;
    public double Node_X, Node_Y;
    public int yolcuSayisi;
    public boolean rotalandiMi;
    private boolean varisNoktasiMi;

    public Node(double varisNoktasi_x, double varisNoktasi_y)
    {
        this.nodeId = 0;
        this.Node_X = varisNoktasi_x;
        this.Node_Y = varisNoktasi_y;
        this.varisNoktasiMi = true;
    }

    public Node(int id, double x, double y, int yolcuSayisi)
    {
        this.nodeId = id;
        this.Node_X = x;
        this.Node_Y = y;
        this.yolcuSayisi = yolcuSayisi;
        this.rotalandiMi = false;
        this.varisNoktasiMi = false;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public double getNode_X() {
        return Node_X;
    }

    public void setNode_X(double node_X) {
        Node_X = node_X;
    }

    public double getNode_Y() {
        return Node_Y;
    }

    public void setNode_Y(double node_Y) {
        Node_Y = node_Y;
    }

    public int getYolcuSayisi() {
        return yolcuSayisi;
    }

    public void setYolcuSayisi(int yolcuSayisi) {
        this.yolcuSayisi = yolcuSayisi;
    }

    public boolean isRotalandiMi() {
        return rotalandiMi;
    }

    public void setRotalandiMi(boolean rotalandiMi) {
        this.rotalandiMi = rotalandiMi;
    }

    public boolean isVarisNoktasiMi() {
        return varisNoktasiMi;
    }

    public void setVarisNoktasiMi(boolean varisNoktasiMi) {
        this.varisNoktasiMi = varisNoktasiMi;
    }
}
