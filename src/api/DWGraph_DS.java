package api;


import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer, node_data> nodes;
    private HashMap<Integer, HashMap<Integer, edge_data>> edges;
    private int edge_counter = 0;
    private int nodes_counter = 0;
    private int MC = 0;
//    NodeData Newnode = new NodeData();

    public DWGraph_DS() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
        MC = 0;
        edge_counter = 0;
        nodes_counter = 0;
    }

    @Override
    public node_data getNode(int key) {
        if (this.nodes.get(key) == null) {
            return null;
        }
        return this.nodes.get(key);
    }

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
     * @param n
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

    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if (edges.containsKey(node_id))
            return edges.get(node_id).values();

        return null;
    }

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

    @Override
    public edge_data removeEdge(int src, int dest) {
        if (this.edges.get(src) != null) {
            this.edges.get(src).remove(dest);
        }
        return null;
    }

    @Override
    public int nodeSize() {
        //return nodes_counter;
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return edge_counter;
    }

    @Override
    public int getMC() {
        return MC;
    }
}
