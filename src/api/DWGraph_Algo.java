package api;

import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph graph;

    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
    }

    /**
     * copy constructor
     */
    public DWGraph_Algo() {
        graph = new DWGraph_DS();
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public directed_weighted_graph copy() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
