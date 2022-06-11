package com.example.appson2.algorithm;


import com.example.appson2.DatabaseHelper;

import java.util.ArrayList;

public class VRP {

    double cost;

    Vehicle[] tempVehicle;
    Node[] Nodes;
    ArrayList<Integer> vehCaps = new ArrayList<Integer>();
    double[][] distanceMatrix = {{0.0, 31584.0, 61943.0, 61068.0, 16293.0, 44562.0, 55651.0, 24453.0, 48931.0, 47565.0, 17962.0, 17933.0, 9994.0},
            {34452.0, 0.0, 83722.0, 82847.0, 34211.0, 66341.0, 90527.0, 24103.0, 65817.0, 47216.0, 26957.0, 61001.0, 22670.0},
            {62592.0, 78431.0, 0.0, 8176.0, 47594.0, 21776.0, 28048.0, 67757.0, 103159.0, 43270.0, 69224.0, 51898.0, 57264.0},
            {60990.0, 76829.0, 7946.0, 0.0, 45992.0, 20173.0, 33943.0, 66154.0, 101556.0, 41667.0, 67621.0, 50295.0, 55661.0},
            {16038.0, 31877.0, 47480.0, 46606.0, 0.0, 28319.0, 57018.0, 21202.0, 56406.0, 44315.0, 17247.0, 21648.0, 10710.0},
            {44651.0, 60490.0, 21477.0, 20602.0, 28631.0, 0.0, 29100.0, 49816.0, 85217.0, 32957.0, 51283.0, 39671.0, 39323.0},
            {56441.0, 88510.0, 29258.0, 34457.0, 57673.0, 30162.0, 0.0, 77836.0, 103910.0, 54341.0, 72215.0, 43090.0, 67343.0},
            {25187.0, 22853.0, 74456.0, 73581.0, 24945.0, 57075.0, 81261.0, 0.0, 56552.0, 25952.0, 17691.0, 51735.0, 12325.0},
            {46831.0, 66035.0, 101739.0, 100864.0, 54910.0, 84358.0, 104211.0, 55116.0, 0.0, 78229.0, 48626.0, 53545.0, 45149.0},
            {49304.0, 46970.0, 42108.0, 41233.0, 57291.0, 32728.0, 51808.0, 25739.0, 80669.0, 0.0, 41808.0, 62378.0, 36442.0},
            {18210.0, 23044.0, 63315.0, 62441.0, 17665.0, 45934.0, 73710.0, 18174.0, 49575.0, 41287.0, 0.0, 44184.0, 7904.0},
            {19941.0, 55488.0, 45798.0, 44923.0, 26958.0, 35523.0, 39035.0, 44570.0, 67744.0, 59702.0, 36049.0, 0.0, 34603.0},
            {9054.0, 23585.0, 56992.0, 56118.0, 11342.0, 39611.0, 66530.0, 12910.0, 46251.0, 36023.0, 8321.0, 26429.0, 0.0}};

    public static void x(double[][] distanceMatrix, ArrayList<Node> arr) {
        int toplamYolcu = 0;
        int who = 0;
        for (int i = 0; i < arr.size(); i++) {
            toplamYolcu += arr.get(i).yolcuSayisi;
        }
        if (toplamYolcu > 95 && toplamYolcu < 120) {
            double max = distanceMatrix[0][0];

            for (int counter = 1; counter < distanceMatrix[0].length; counter++) {
                if (distanceMatrix[0][counter] > max) {
                    max = distanceMatrix[0][counter];
                    who = counter;
                }
            }
        }

    }

    public ArrayList<Node> initNode(DatabaseHelper dbhelp) {

        ArrayList<Node> nd = dbhelp.nodeal();
        Node node1 = new Node(nd.get(0).nodeId,nd.get(0).Node_X,nd.get(0).Node_Y,nd.get(0).yolcuSayisi);
        Node node2 = new Node(nd.get(1).nodeId,nd.get(1).Node_X,nd.get(1).Node_Y,nd.get(1).yolcuSayisi);
        Node node3 = new Node(nd.get(2).nodeId,nd.get(2).Node_X,nd.get(2).Node_Y,nd.get(2).yolcuSayisi);
        Node node4 = new Node(nd.get(3).nodeId,nd.get(3).Node_X,nd.get(3).Node_Y,nd.get(3).yolcuSayisi);
        Node node5 = new Node(nd.get(4).nodeId,nd.get(4).Node_X,nd.get(4).Node_Y,nd.get(4).yolcuSayisi);
        Node node6 = new Node(nd.get(5).nodeId,nd.get(5).Node_X,nd.get(5).Node_Y,nd.get(5).yolcuSayisi);
        Node node7 = new Node(nd.get(6).nodeId,nd.get(6).Node_X,nd.get(6).Node_Y,nd.get(6).yolcuSayisi);
        Node node8 = new Node(nd.get(7).nodeId,nd.get(8).Node_X,nd.get(7).Node_Y,nd.get(7).yolcuSayisi);
        Node node9 = new Node(nd.get(8).nodeId,nd.get(8).Node_X,nd.get(8).Node_Y,nd.get(8).yolcuSayisi);
        Node node10 = new Node(nd.get(9).nodeId,nd.get(9).Node_X,nd.get(9).Node_Y,nd.get(9).yolcuSayisi);
        Node node11 = new Node(nd.get(10).nodeId,nd.get(10).Node_X,nd.get(10).Node_Y,nd.get(10).yolcuSayisi);
        Node node12 = new Node(nd.get(11).nodeId,nd.get(11).Node_X,nd.get(11).Node_Y,nd.get(11).yolcuSayisi);
        Node node0 = new Node(40.822213, 29.921675);


        Node[] Nodes = new Node[13];
        Nodes[0] = node0;
        Nodes[1] = node1;
        Nodes[2] = node2;
        Nodes[3] = node3;
        Nodes[4] = node4;
        Nodes[5] = node5;
        Nodes[6] = node6;
        Nodes[7] = node7;
        Nodes[8] = node8;
        Nodes[9] = node9;
        Nodes[10] = node10;
        Nodes[11] = node11;
        Nodes[12] = node12;

        System.out.println("NODELAR-------------");
        for (int i = 0; i < Nodes.length; i++) {
            Node nod = Nodes[i];
            System.out.println(nod.Node_X + " " + nod.Node_Y + " " + nod.yolcuSayisi);
        }

        ArrayList<Node> arrNodes = new ArrayList<Node>();
        arrNodes.add(node0);
        for (int i = 0; i < Nodes.length; i++) {
            if (Nodes[i].yolcuSayisi != 0) {
                arrNodes.add(Nodes[i]);
            }

        }

        int toplamYolcu = 0;
        for (int i = 0; i < Nodes.length; i++) {
            Node n = Nodes[i];
            toplamYolcu += n.yolcuSayisi;
        }
        if (toplamYolcu > 95 && toplamYolcu < 120) {
            double max = distanceMatrix[0][0];

            for (int counter = 1; counter < distanceMatrix[0].length; counter++) {
                if (distanceMatrix[0][counter] > max && (toplamYolcu - 95 >= arrNodes.get(counter).yolcuSayisi)) {
                    max = distanceMatrix[0][counter];
                }
            }
            for (int counter = 1; counter < arrNodes.size(); counter++) {
                if (max == distanceMatrix[0][counter]) {
                    arrNodes.get(counter).yolcuSayisi = arrNodes.get(counter).yolcuSayisi - (toplamYolcu - 95);
                }
            }
            arrNodes.clear();
            arrNodes.add(node0);
            for (int i = 0; i < Nodes.length; i++) {
                if (Nodes[i].yolcuSayisi != 0) {
                    arrNodes.add(Nodes[i]);
                }
            }

        } else {
            int x = toplamYolcu - 95;
            int q = 0;
            for (int i = 0; i < 10; i++) {
                if (q > x) {
                    break;
                } else if (x == 0) {
                    break;
                } else {
                    q += 25;
                }
            }
            int yeniAracSayisi = q / 25;
            for (int i = 0; i < yeniAracSayisi; i++) {
                vehCaps.add(25);
            }
        }
        toplamYolcu = 0;
        for (int i = 0; i < Nodes.length; i++) {
            Node n = Nodes[i];
            toplamYolcu += n.yolcuSayisi;
        }

        return arrNodes;
    }

    public double[][] initDistanceMatrix(ArrayList<Node> arr) {
        double[][] distance = {{0.0, 31584.0, 61943.0, 61068.0, 16293.0, 44562.0, 55651.0, 24453.0, 48931.0, 47565.0, 17962.0, 17933.0, 9994.0},
                {34452.0, 0.0, 83722.0, 82847.0, 34211.0, 66341.0, 90527.0, 24103.0, 65817.0, 47216.0, 26957.0, 61001.0, 22670.0},
                {62592.0, 78431.0, 0.0, 8176.0, 47594.0, 21776.0, 28048.0, 67757.0, 103159.0, 43270.0, 69224.0, 51898.0, 57264.0},
                {60990.0, 76829.0, 7946.0, 0.0, 45992.0, 20173.0, 33943.0, 66154.0, 101556.0, 41667.0, 67621.0, 50295.0, 55661.0},
                {16038.0, 31877.0, 47480.0, 46606.0, 0.0, 28319.0, 57018.0, 21202.0, 56406.0, 44315.0, 17247.0, 21648.0, 10710.0},
                {44651.0, 60490.0, 21477.0, 20602.0, 28631.0, 0.0, 29100.0, 49816.0, 85217.0, 32957.0, 51283.0, 39671.0, 39323.0},
                {56441.0, 88510.0, 29258.0, 34457.0, 57673.0, 30162.0, 0.0, 77836.0, 103910.0, 54341.0, 72215.0, 43090.0, 67343.0},
                {25187.0, 22853.0, 74456.0, 73581.0, 24945.0, 57075.0, 81261.0, 0.0, 56552.0, 25952.0, 17691.0, 51735.0, 12325.0},
                {46831.0, 66035.0, 101739.0, 100864.0, 54910.0, 84358.0, 104211.0, 55116.0, 0.0, 78229.0, 48626.0, 53545.0, 45149.0},
                {49304.0, 46970.0, 42108.0, 41233.0, 57291.0, 32728.0, 51808.0, 25739.0, 80669.0, 0.0, 41808.0, 62378.0, 36442.0},
                {18210.0, 23044.0, 63315.0, 62441.0, 17665.0, 45934.0, 73710.0, 18174.0, 49575.0, 41287.0, 0.0, 44184.0, 7904.0},
                {19941.0, 55488.0, 45798.0, 44923.0, 26958.0, 35523.0, 39035.0, 44570.0, 67744.0, 59702.0, 36049.0, 0.0, 34603.0},
                {9054.0, 23585.0, 56992.0, 56118.0, 11342.0, 39611.0, 66530.0, 12910.0, 46251.0, 36023.0, 8321.0, 26429.0, 0.0}};

        return distance;
    }

    public void vrpyicalistir(DatabaseHelper dbhelper) {
        vehCaps.add(40);
        vehCaps.add(30);
        vehCaps.add(25);
        ArrayList<Node> arrNodes = initNode(dbhelper);

        double[][] distanceMatrix = initDistanceMatrix(arrNodes);
        x(distanceMatrix, arrNodes);

        int yazdir = 0;

        if (yazdir == 1) {
            for (int i = 0; i < arrNodes.size(); i++) {
                for (int j = 0; j < arrNodes.size(); j++) {
                    System.out.print(distanceMatrix[arrNodes.get(i).nodeId][arrNodes.get(j).nodeId] + "  ");
                }
                System.out.println();
            }
        }

        System.out.println("Açgözlü algoritma ile çözüm bulunmaya çalışılıyor.");

        Solution s = new Solution(arrNodes.size(), vehCaps.size(), vehCaps);

        s.GreedySolution(arrNodes, distanceMatrix);

        s.CozumuYazdir("Açgözlü Algoritma");
        Vehicle[] solutionVehicles = s.getAraclar();

        tempVehicle = s.getAraclar();

        for (int i = 0; i < solutionVehicles.length; i++) {
            String path = "";
            for (int j = 0; j < solutionVehicles[i].Rota.size(); j++) {
                int rotaId =solutionVehicles[i].Rota.get(j).nodeId;
                path = path +Integer.toString(rotaId)+" ";
            }
            dbhelper.aracBilgileriEkle(solutionVehicles[i].aracId,path,solutionVehicles[i].carCost,solutionVehicles[i].binenYolcuSayisi);
        }
        cost = s.getMaliyet();



    }

    public Vehicle[] getTempVehicle() {
        return tempVehicle;
    }

    public double getCost() {
        return cost;
    }
}
