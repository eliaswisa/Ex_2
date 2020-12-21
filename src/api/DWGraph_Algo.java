package api;

import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * class of group of algorithms that used on this graph
 */
public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph graph;
    private HashMap<Integer, Double> dist;
    private HashMap<Integer, Integer> prev;

    /**
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

    /**
     *Return the underlying graph of which this class works.
     * @return directed_weighted_graph
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    /**
     *deep copy of this weighted graph.
     * @return directed_weighted_graph
     */

    @Override
    public directed_weighted_graph copy() {
        DWGraph_DS d_w_g = new DWGraph_DS((DWGraph_DS) this.graph) ;
        return d_w_g;
    }

    /**
     *Returns true if and only if (iff) there is a valid path from each node to each
     *      * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * @return true if the graph is connected by definition or false if not connectes
     */

    @Override
    public boolean isConnected() {


        boolean a = false;
        boolean b = false;

        if (this.graph.nodeSize() == 0 || this.graph.nodeSize() == 1)
            return true;

        List<node_data> list = new ArrayList<node_data>(this.graph.getV());
        djikstra(list.get(0).getKey());
        a = !this.dist.containsValue(Double.MAX_VALUE);


        return a;


    }

    /**
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

    /**
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
     *
     * @param file - the file name (may include a relative path).
     * @return boolean value if the save is done properly
     */
    @Override
    public boolean save(String file) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonEdgeArray = new JSONArray();
            JSONArray jsonNodeArray = new JSONArray();
            Map<String, Object> jsonNodes = new HashMap<String, Object>();
            Map<String, Object> jsonEdges = new HashMap<String, Object>();
            Collection<node_data> nodes1 = this.graph.getV();
            for(node_data node : nodes1)
            {
                String geoLocationSt = node.getLocation().x() + "," + node.getLocation().y() + "," + node.getLocation().z();
                jsonNodes.put("pos", geoLocationSt);
                jsonNodes.put("id", node.getKey());
                jsonNodeArray.put(jsonNodes);
            }
            for(node_data node : nodes1) {
                for (edge_data edge : ((DWGraph_DS) graph).getE(node.getKey())) {
                    jsonEdges.put("src", edge.getSrc());
                    jsonEdges.put("w", edge.getWeight());
                    jsonEdges.put("dest", edge.getDest());
                    jsonEdgeArray.put(jsonEdges);
                }
            }
            jsonObject.put("Edges", jsonEdgeArray);
            jsonObject.put("Nodes", jsonNodeArray);
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(jsonObject.toString());
            printWriter.flush();
            printWriter.close();
        }
        catch(FileNotFoundException | JSONException e)
        {
            return false;
        }
        return true;
    }




    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */


    @Override
    public boolean load(String file) {
        try {
            Gson gson = new Gson();
            JsonObject jsonOb = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
            JsonArray nodesArray = jsonOb.getAsJsonArray("Nodes");
            directed_weighted_graph g = new DWGraph_DS();
            for (JsonElement node : nodesArray) {
                String[] nodePosition = ((JsonObject) node).get("pos").getAsString().split(",");
                geo_location location3d = new GeoLocation(Double.parseDouble(nodePosition[0]), Double.parseDouble(nodePosition[1]), Double.parseDouble(nodePosition[2]));
                NodeData nd=new NodeData(((JsonObject)node).get("id").getAsInt());
                nd.setLocation(location3d);
                g.addNode(nd);
            }

            JsonArray edgesArray = jsonOb.getAsJsonArray("Edges");
            for (JsonElement edge : edgesArray){
                JsonObject e = (JsonObject)edge;
                g.connect(e.get("src").getAsInt(),e.get("dest").getAsInt(),e.get("w").getAsDouble());
            }
            this.graph = g;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Dijkstra's algorithm is a step-by-step process we can use to find the shortest path between two vertices in a weighted graph. This algorithm enables us to find shortest distances and minimum costs
     * @param source -source node
     */
    public void djikstra(int source) {
        PriorityQueue<node_data> q = new PriorityQueue<node_data>(new Comparator<node_data>() {
            @Override
            public int compare(node_data o1, node_data o2) {
                return -Double.compare(dist.get(o2.getKey()),dist.get(o1.getKey()));
            }
        });
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


}