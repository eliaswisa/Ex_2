package api;

import gameClient.util.Point3D;
import org.jetbrains.annotations.NotNull;

public class NodeData implements node_data, Comparable<node_data> {

    private int key;
    private double weight;
    private int tag;
    private String info;
    //    public static int id = 1;
    private geo_location location;

    public NodeData(int key) {
        this.location = new Point3D(0, 0, 0);
        this.key = key;
        this.weight = Double.MAX_VALUE;
        this.tag = 0;
        this.info = "";

    }

//    public NodeData(GeoLocation location) {
//        this.location = location;
//        this.key = id;
//        this.id++;
//        this.weight = Double.MAX_VALUE;
//        this.tag = 0;
//        this.info = "";
//    }

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

    @Override
    public int compareTo(@NotNull node_data o) {
        return Double.compare(this.getWeight(), o.getWeight());
    }

    @Override
    public String toString() {
        return "{k: " + getKey() + ", info: " + getInfo() + ", weight: " + getWeight() + "}";
    }
}
