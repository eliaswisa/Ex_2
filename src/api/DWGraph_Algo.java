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

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph graph;
    private HashMap<Integer, Double> dist;
    private HashMap<Integer, Integer> prev;


    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
    }

    /**
     * copy constructor
     */
    public DWGraph_Algo() {
        graph = new DWGraph_DS();

        dist = new HashMap<>();
        prev = new HashMap<>();


    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public directed_weighted_graph copy() {
        if (graph == null)
            return null;
        directed_weighted_graph d_w_g = new DWGraph_DS();
        return d_w_g;
    }

    @Override
    public boolean isConnected() {


        if (this.graph.nodeSize() == 0 || this.graph.nodeSize() == 1)
            return true;

        List<node_data> list = new ArrayList<node_data>(this.graph.getV());
        djikstra(list.get(0).getKey());

        return !this.dist.containsValue(Double.MAX_VALUE);
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        djikstra(src);
        return dist.get(dest);
    }

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
     * saves and write our WDGraph to json structured file
     * @param file - the file name (may include a relative path).
     * @return
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
