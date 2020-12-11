package api;

import gameClient.util.Point3D;
import org.jetbrains.annotations.NotNull;

/***
 * this class is an node who has several fields
 */
public class NodeData implements node_data, Comparable<node_data> ,java.io.Serializable {

    private int key;
    private double weight;
    private int tag;
    private String info;
    //    public static int id = 1;
    private geo_location location;

    /***
     * constructor who gets a key for mapping
     * @param key
     */
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

    /***
     * key getter
     * @return the key of the node
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /***
     * geographic location getter
     * @return geo 3d point
     */
    @Override
    public geo_location getLocation() {
        return this.location;
    }

    /***
     * sets location
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this.location = new Point3D(p.x(), p.y(), p.z());
    }

    /***
     * gets the weight of the node
     * @return double node weight
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /***
     * weight setter
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    /***
     * gets the info from the node
     * @return String info
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /***
     * info setter
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /***
     * tag getter-the tags is an specific mark for some method we using at this project
     * @return int tag
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /***
     * tag setter
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag = t;
    }

    /***
     * compare nodes by their weight-use for the algorithms
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NotNull node_data o) {
        return Double.compare(this.getWeight(), o.getWeight());
    }

    /***
     * toString method for printing String for the user
     * @return
     */
    @Override
    public String toString() {
        return "{k: " + getKey() + ", info: " + getInfo() + ", weight: " + getWeight() + "}";
    }
}
