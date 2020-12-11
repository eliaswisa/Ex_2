package api;

/***
 * class for finding the edge by object that located on her
 */
public class EdgeLocation implements edge_location
{
    EdgeData d ;
    float ratio = 0  ;
    EdgeLocation(EdgeData c, float ra){
        this.d=c;
        this.ratio = ra;
/***
 * edge getter
 */
    }
    @Override
    public edge_data getEdge() {
        return d;
    }

    /***
     * Returns the relative ration [0,1] of the location between src and dest.
     * @return
     */
    @Override
    public double getRatio() {
        return ratio;
    }
}
