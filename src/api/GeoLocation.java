package api;

public class GeoLocation implements geo_location {
    private double x;
    private double y;
    private double z;

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(geo_location g) {
        double d;
        return Math.sqrt(Math.pow(g.x() - this.x, 2) + Math.pow(g.y() - this.y, 2) + Math.pow(g.z() - this.z, 2));
    }

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
}
