package api;

public class EdgeLocation implements edge_location
{
    EdgeData d ;
    float ratio = 0  ;

    EdgeLocation(EdgeData c, float ra){
        this.d=c;
        this.ratio = ra;

    }
    @Override
    public edge_data getEdge() {
        return d;
    }

    @Override
    public double getRatio() {
        return ratio;
    }
}
