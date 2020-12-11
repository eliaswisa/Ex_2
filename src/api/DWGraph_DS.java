package api;


import java.util.Collection;
import java.util.HashMap;

/***
 * directed weighed graph who build from nodes and edges that connects between them
 */
public class DWGraph_DS implements directed_weighted_graph ,java.io.Serializable{
    private HashMap<Integer, node_data> nodes;
    private HashMap<Integer, HashMap<Integer, edge_data>> edges;
    private int edge_counter = 0;
    private int nodes_counter = 0;
    private int MC = 0;
//    NodeData Newnode = new NodeData();

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

        if (!nodes.containsKey(n.getKey())) {

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
        if (this.nodes.get(src) == null || this.nodes.get(dest) == null) {
            return;
        }

        if (src != dest && w >= 0) {

            EdgeData newEdge = new EdgeData(src, dest, w);

            if (!edges.get(src).containsKey(dest)) {
//                this.edges.put(src, new HashMap<Integer, edge_data>());
                this.edges.get(src).put(dest, newEdge);
                edge_counter++;
                MC++;
            }
//            else {
//                if (edges.get(src).get(dest) == null) {
////                    this.edges.get(src).put(dest, newEdge);
//                    edge_counter++;
//                    MC++;

//                }
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
    // TODO: 11/12/2020 check if i did it in the right way,it should take all the edge_data elements from all the hash map
    public Collection<edge_data> getE() {
        Collection<Integer> allEdgesSrcKeyList = edges.keySet();
        Collection<edge_data> allGeneralEdgesCollection = null;
        for (int key : allEdgesSrcKeyList) {
            for (int key2 : this.edges.get(key).keySet()) {
                allGeneralEdgesCollection.add(edges.get(key).get(key2));
            }
        }
        return allGeneralEdgesCollection;
    }

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

                edges.get(n.getKey()).remove(key);
                edge_counter--;
                MC++;

            }

//            for (int i = 0; i <= NodeData.id; i++) {
//                if (edges.get(i) != null && edges.get(i).get(key) != null) {
//                    removeEdge(i, key);
//                    edge_counter--;
//                    MC++;
//                }
//            }

            MC++;
            return nd;
        }
        return null;
//            if (nodes.get(key)!=null) {
//
//                for (node_data node : getV()) {
//
//                    removeEdge(key, node.getKey());
//                }
//
//                nodes.remove(key);
//                return nodes.remove(key);
//
//            }
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
}
