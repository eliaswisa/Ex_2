package api;

import javax.print.attribute.HashAttributeSet;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer, node_data> nodes;
    private HashMap<Integer, HashMap<Integer, edge_data>> edges;
    private int edge_counter = 0;
    private int nodes_counter = 0;
    private int MC = 0;


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
        if (this.edges.get(src)!=null) {
            if (this.edges.get(src).get(dest) != null) {
                return this.edges.get(src).get(dest);
            }
        }
        return null;
    }

    @Override
    public void addNode(node_data n) {
        nodes.put(n.getKey(), (NodeData) n);
        MC++;
        nodes_counter++;
    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Collection<node_data> getV() {
        return null;
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
