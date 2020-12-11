package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/***
 * class of group of algorithms that used on this graph
 */
public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph graph;
    private HashMap<Integer, Double> dist;
    private HashMap<Integer, Integer> prev;

    /***
     * Init the graph on which this set of algorithms operates on.
     * @param g graph to initiate
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
    }

    /**
     * default constructor
     */
    public DWGraph_Algo() {
        graph = new DWGraph_DS();

        dist = new HashMap<>();
        prev = new HashMap<>();


    }

    /***
     *Return the underlying graph of which this class works.
     * @return directed_weighted_graph
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    /***
     *deep copy of this weighted graph.
     * @return directed_weighted_graph
     */
    @Override
    public directed_weighted_graph copy() {
        if (graph == null)
            return null;
        directed_weighted_graph d_w_g = new DWGraph_DS();
        return d_w_g;
    }

    /***
     *Returns true if and only if (iff) there is a valid path from each node to each
     *      * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * @return true if the graph is connected by definition or false if not connectes
     */
    @Override
    public boolean isConnected() {


        if (this.graph.nodeSize() == 0 || this.graph.nodeSize() == 1)
            return true;

        List<node_data> list = new ArrayList<node_data>(this.graph.getV());
        djikstra(list.get(0).getKey());

        return !this.dist.containsValue(Double.MAX_VALUE);
    }

    /***
     * finds the shortest path to the destination using djikstra algorithm compatible to our data structure
     * @param src - start node
     * @param dest - end (target) node
     * @return double summed weight from src to dest
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        djikstra(src);
        return dist.get(dest);
    }

    /***
     *returns the the shortest path between src to dest - as an ordered List of nodes using djikstra algorithm
     * @param src - start node
     * @param dest - end (target) node
     * @return nodes key collection
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        djikstra(src);

        List<node_data> result = new ArrayList<>();

        int father = dest;

        result.add(this.graph.getNode(dest));

        while (true) {

            if (father == src) break;

            result.add(this.graph.getNode(this.prev.get(father)));
            father = this.prev.get(father);

        }

        List<node_data> back_result = new ArrayList<>();

        for (int i = result.size() - 1; i >= 0; i--) {
            back_result.add(result.get(i));
        }

        return back_result;
    }

    /**
     * saves and write our WDGraph to json structured file format in text (saving all nodes and their edges)
     * @param file - the file name (may include a relative path).
     * @return boolean value if the save is done properly
     */
    @Override
    public boolean save(String file) {
        JsonObject DWGraph= new JsonObject();
        JsonArray edges= new JsonArray();
        for(edge_data e: ((DWGraph_DS)graph).getE()){
            JsonObject edge= new JsonObject();
            edge.addProperty("src",e.getSrc());
            edge.addProperty("w",e.getWeight());
            edge.addProperty("dest",e.getDest());
            edges.add(edge);
        }

        JsonArray nodes= new JsonArray();
        for (node_data n: graph.getV()){
            JsonObject node= new JsonObject();
            String pos= n.getLocation().x()+","+n.getLocation().y()+","+n.getLocation().z();
            node.addProperty("pos", pos);
            node.addProperty("id", n.getKey());
            nodes.add(node);
        }
        DWGraph.add("Edges",edges);
        DWGraph.add("Nodes",nodes);
        try
        {
            Gson gson = new Gson();
            PrintWriter printWriter = new PrintWriter(new File(file));
            printWriter.write(gson.toJson(graph));
            printWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /***
     * can load a graph from a file with json format using Gson library
     * @param file - file name of JSON file
     * @return boolean if manage to load properly and init the DWGraph with the loaded data(nodes and edges)
     */
    @Override
    public boolean load(String file) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(DWGraph_DS.class, new DW_JsonGraphDeserial());
            Gson gsonCreated = gsonBuilder.create();

            FileReader reader = new FileReader(file);
            this.graph = gsonCreated.fromJson(reader, DWGraph_DS.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /***
     * Dijkstra's algorithm is a step-by-step process we can use to find the shortest path between two vertices in a weighted graph. This algorithm enables us to find shortest distances and minimum costs
     * @param source -source node
     */
    public void djikstra(int source) {

        PriorityQueue<node_data> q = new PriorityQueue<>();

        for (node_data vertex : this.graph.getV()) {

            if (vertex.getKey() != source) {

                vertex.setWeight(Double.MAX_VALUE);
                this.dist.put(vertex.getKey(), Double.MAX_VALUE);
                this.prev.put(vertex.getKey(), null);
            } else {
                this.dist.put(vertex.getKey(), 0.0);
                vertex.setWeight(0.0);
            }

            q.add(vertex);

        }

        while (!q.isEmpty()) {

            node_data u = q.poll();

//            System.out.println(u);

            for (edge_data e : this.graph.getE(u.getKey())) {

                double alt = this.dist.get(u.getKey()) + e.getWeight();

                if (alt < this.dist.get(e.getDest())) {

                    this.dist.put(e.getDest(), alt);
                    this.prev.put(e.getDest(), u.getKey());

                    this.graph.getNode(e.getDest()).setWeight(alt);

                    q.remove(this.graph.getNode(e.getDest()));
                    q.add(this.graph.getNode(e.getDest()));

                }

            }

        }


    }

    public static void main(String[] args) {
        DWGraph_DS g = new DWGraph_DS();
        DWGraph_Algo m = new DWGraph_Algo();
        m.init(g);

        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));

        g.connect(0, 1, 1);
        g.connect(1, 2, 4);
        g.connect(2, 3, 4);
        g.connect(3, 4, 2);

//        g.connect(4,5,6);

//        g.connect(5,1,1);

        System.out.println(m.isConnected());

    }
}
