package api;

/***
 * edge on the graph who connects between two nodes by source and destination node
 */
public class EdgeData implements edge_data {

    private int src;
    private int dest;
    private String info;
    private double weight;
    private int tag;

    /***
     * constructor getting arguments of src and dest nodes keys and weight
     * @param start
     * @param end
     * @param w
     */

    public EdgeData(int start, int end, double w) {

        this.src = start;
        this.dest = end;
        this.weight = w;
        this.info = "";
        this.tag = 0;
    }
    public EdgeData() {

//        this.src = e.src;
//        this.dest = e.dest;
//        this.weight = e.weight;
//        this.info = "";
//        this.tag = 0;
    }
    /***
     * copy constructor
     * @param e
     */
    public EdgeData(EdgeData e) {

        this.src=e.src;
        this.dest=e.dest;
        this.weight=e.weight;
        this.info="";
        this.tag=0;

    }

    /***
     * EdgeData constructor with arguments
     * @param start
     * @param end
     * @param w
     * @param s
     */
    public EdgeData(int start,int end,double w,String s) {

        this.src=start;
        this.dest=end;
        this.weight=w;
        this.info=s;
        this.tag=0;

    }

    /***
     * source getter
     * @return int node key source
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /***
     * destination getter
     * @return  int node key destination
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /***
     * weight getter
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /***
     * info getter
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
        this.info=s;

    }

    /***
     * tag for marking who used in some algorithms
     * @return int tag
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /***
     *  tag setter
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag=t;

    }
}
