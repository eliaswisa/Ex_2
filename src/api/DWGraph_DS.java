package api;


import java.util.Collection;
import java.util.HashMap;

/***
 * directed weighed graph who build from nodes and edges that connects between them
 */
public class DWGraph_DS implements directed_weighted_graph, java.io.Serializable {
    private HashMap<Integer, node_data> nodes = new HashMap<Integer, node_data>();
    private HashMap<Integer, HashMap<Integer, edge_data>> edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
    private int edge_counter = 0;
    private int nodes_counter = 0;
    private int MC = 0;


    /***
     * default constructor
     */
    public DWGraph_DS() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
        MC = 0;
        edge_counter = 0;
        nodes_counter = 0;
    }

    // TODO: 13/12/2020 copy constructor check if good
    public DWGraph_DS(DWGraph_DS c) {
        this.setEdgeCounter(this.edgeSize());
        for (node_data node : c.getV()) {
            NodeData tempi = new NodeData((NodeData) node);
            this.addNode(tempi);
        }
        //////////////////////////////////////////////////////////////////////
        if (c.edges.keySet() != null) {
            for (int key : c.edges.keySet()) {
                HashMap<Integer, edge_data> edgesTempHash = new HashMap<Integer, edge_data>();
                this.edges.put(key, edgesTempHash);
                for (edge_data tempEdge : c.edges.get(key).values()) {
                    EdgeData tempE = new EdgeData((EdgeData) tempEdge);
                    this.edges.get(key).put(tempEdge.getDest(), tempE);
                }
            }
        }
    }

    /***
     * node getter
     * @param key - the node_id
     * @return node_data by his key
     */
    @Override
    public node_data getNode(int key) {
        if (this.nodes.get(key) == null) {
            return null;
        }
        return this.nodes.get(key);
    }

    /***
     * edge getter
     * @param src node src
     * @param dest destination node
     * @return edge between two nodes
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (this.nodes.get(src) == null || this.nodes.get(dest) == null) {
            return null;
        }
        if (this.edges.get(src) != null) {
            if (this.edges.get(src).get(dest) != null) {
                return this.edges.get(src).get(dest);
            }
        }
        return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n node to be added
     */
    @Override
    public void addNode(node_data n) {
        if (nodes.isEmpty()) {
            nodes.put(n.getKey(), n);
            MC++;
            nodes_counter++;
            return;
        } else if (nodes.get(n.getKey()) == null) {

            nodes.put(n.getKey(), n);
            edges.put(n.getKey(), new HashMap<>());

            MC++;
            nodes_counter++;
        }
    }

    /***
     * connects between two nodes
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if ((src == dest) || (w == 0)) {
            return;
        }

        if (this.nodes.get(src) == null || this.nodes.get(dest) == null) {
            return;
        } else {
            EdgeData tempEd = new EdgeData(src, dest, w);
            if (this.edges.get(src) == null) {
                this.edges.put(src, new HashMap<Integer, edge_data>());
                this.edges.get(src).put(dest, tempEd);
                edge_counter++;
                MC++;
            } else {

                if ((edges.get(src).get(dest)) == null) {
                    this.edges.get(src).put(dest, tempEd);
                    edge_counter++;
                    MC++;
                }
            }
        }


    }

    /***
     * nodes collection of this graph
     * @return Collection of the nodes in this graph
     */
    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }

    /***
     * edges collection of edges by specific node
     * @param node_id
     * @return Collection of edges
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        if (edges.containsKey(node_id))
            return edges.get(node_id).values();

        return null;
    }

    /***
     * all graph edges in collection
     * @return all graph collections
     */
    // TODO: 13/12/2020 fix it in general becaise the null pointer exception
    // TODO: 11/12/2020 check if i did it in the right way,it should take all the edge_data elements from all the hash map
//    public Collection<edge_data> getE() {
//        Collection<Integer> allEdgesSrcKeyList = edges.keySet();
//        Collection<edge_data> allGeneralEdgesCollection = new ConcurrentLinkedDeque<>();
//        allGeneralEdgesCollection.add(new EdgeData(3, 5, 1));
//
//        for (int key : allEdgesSrcKeyList) {
//            for (int key2 : this.edges.get(key).keySet()) {
//                allGeneralEdgesCollection.add(edges.get(key).get(key2));
//            }
//        }
//        return allGeneralEdgesCollection;
//    }

    /***
     * node remover by specific key if exists in the graph
     * @param key
     * @return the removed node
     */
    @Override
    public node_data removeNode(int key) {
        if (nodes.get(key) != null) {
            node_data nd = this.nodes.remove(key);
            if (edges.get(key) != null) {
                Collection<edge_data> edgeColl = this.edges.remove(key).values();
                edge_counter = edge_counter - edgeColl.size();
            }
            for (node_data n : getV()) {
                if (edges.get(n.getKey()) != null) {
                    if (edges.get(n.getKey()).get(key) != null) {
                        edges.get(n.getKey()).remove(key);
                        edge_counter--;
                        MC++;
                    }
                }
            }
            MC++;
            return nd;
        }
        return null;
    }

    /***
     * edge remover
     * @param src
     * @param dest
     * @return the removed edge
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if (this.edges.get(src) != null) {
            this.edges.get(src).remove(dest);
        }
        return null;
    }

    /***
     * the number of the nodes in the graph
     * @return
     */
    @Override
    public int nodeSize() {
        //return nodes_counter;
        return this.nodes.size();
    }

    /***
     * return the number of the edges in this graph in o(1)
     * @return int edges size
     */
    @Override
    public int edgeSize() {
        return edge_counter;
    }

    /***
     * mode counter that sums the number of actions that change the inner structure of the graph
     * @return int mode counter
     */
    @Override
    public int getMC() {
        return MC;
    }

    public int edgesCounter() {
        return this.edge_counter;
    }

    public void setEdgeCounter(int c) {
        this.edge_counter = c;
    }

    public void edgesMapCopier() {
        HashMap<Integer, HashMap<Integer, edge_data>> edges2 = new HashMap<Integer, HashMap<Integer, edge_data>>();


        this.edges = edges2;
    }

    @Override
    public boolean equals(Object object) {

        if (object == null || getClass() != object.getClass()) return false;
        DWGraph_DS WDSGraph = (DWGraph_DS) object;
        boolean a = (edgesCounter() == WDSGraph.edgesCounter());
        boolean b = getV().size()==(WDSGraph.getV().size());

        return (a && b);
    }
}
