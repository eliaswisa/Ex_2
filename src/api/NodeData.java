package api;

import gameClient.util.Point3D;

public class NodeData implements node_data {

    private int key;
    private double weight;
    private int tag;
    private String info;
    public static int id = 1;
    private geo_location location;

    public NodeData() {
        this.location = Point3D.ORIGIN;
        this.key = id;
        this.weight = Double.MAX_VALUE;
        this.tag = 0;
        this.info = "";
        this.id++;
    }

    public NodeData(GeoLocation location) {
        this.location = location;
        this.key = id;
        this.id++;
        this.weight = Double.MAX_VALUE;
        this.tag = 0;
        this.info = "";
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(geo_location p) {
        this.location = new Point3D(p.x(), p.y(), p.z());
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        tag = t;
    }
}
