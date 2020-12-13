package api;

/***
 * class that used to implementing 3d point and describes geographic location of nodes in 3d space with x y z coordinates
 */
public class GeoLocation implements geo_location {
    private double x;
    private double y;
    private double z;

    /***
     *
     * @return x coordinate
     */
    @Override
    public double x() {
        return this.x;
    }

    /***
     *
     * @return y coordinate
     */
    @Override
    public double y() {
        return this.y;
    }

    /***
     *
     * @return z coordinate
     */
    @Override
    public double z() {
        return this.z;
    }


    /***
     * constructor with x y z arguments
     * @param x
     * @param y
     * @param z
     */
    public GeoLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * copy constructor
     *
     * @param geo
     */
    public GeoLocation(geo_location geo) {
        this.x = geo.x();
        this.y = geo.y();
        this.z = geo.z();
    }

    /***
     * method that calculate distance between two points
     * @param g geo location object
     * @return double distance
     */
    @Override
    public double distance(geo_location g) {
        double d;
        return Math.sqrt(Math.pow(g.x() - this.x, 2) + Math.pow(g.y() - this.y, 2) + Math.pow(g.z() - this.z, 2));
    }

}
