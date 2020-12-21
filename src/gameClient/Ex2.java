package gameClient;
////
import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *class who wraps the hole game and run it for out use
 */
public class Ex2 implements Runnable {
    private static MyFrame _win;
    private static Arena _ar;
    private int id=0;
    private int level =0 ;
    public static void main(String[] a) {


        if(a.length==0) {
            GuiStart stam = new GuiStart();
            stam.start_game();
        }else if(a.length==2){
            Ex2 ex=new Ex2(Integer.parseInt(a[0]),Integer.parseInt(a[1]));
            Thread client = new Thread(ex);
            client.start();
        }
    }

    /**
     * constructor for the class
     * @param _id id argument
     * @param _level level argument
     */
    public Ex2(int _id,int _level){
        this.id=_id;
        this.level=_level;
    }

    /**
     * run the thread that wraps this class
     */
    @Override
    public void run() {
        int scenario_num = this.level;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        game.login(this.id);

        String g = game.getGraph();
        String pks = game.getPokemons();
        System.out.println();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        init(game);
        System.out.println("000000000000000000000000000000000000"+Arena.json2Pokemons(game.getPokemons()));
        game.startGame();
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
        int ind =(int)game.timeToEnd()/100 ;
        long dt = 100;

        while (game.isRunning()) {
            moveAgants(game, gg);
            try {
                if (ind % 1 == 0) {
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(game.timeToEnd());

                    _win.setTime(seconds);
                    _win.repaint();
                }
                Thread.sleep(dt);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }

    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * method who move the agent
     * @param game server game info
     * @param gg DW_Graph for the game
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        _ar.setPokemons(ffs);

        for (int i = 0; i < log.size(); i++) {
            CL_Agent ag = log.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if (dest == -1) {
                edge_data edge = nextNode(gg, src,ffs);
                dw_graph_algorithms algo=new DWGraph_Algo();
                algo.init(gg);
                List<node_data> list =algo.shortestPath(src,edge.getSrc());
                if(list==null){
                    game.chooseNextEdge(ag.getID(), edge.getDest());
                }else{
                   for(node_data n:list){
                       game.chooseNextEdge(ag.getID(), n.getKey());

                   }
                    game.chooseNextEdge(ag.getID(), edge.getDest());
                }


                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
            }
        }
    }

    /**
     *
     * method that choose which node is the next node for the agent in the way to his pokemon
     * @param g
     * @param src
     * @return
     */
    private static edge_data nextNode(directed_weighted_graph g, int src, List<CL_Pokemon> list ) {

        dw_graph_algorithms algo=new DWGraph_Algo();
        algo.init(g);
        double min_dist=Double.POSITIVE_INFINITY;

        edge_data ans=null;

        for(CL_Pokemon i:list){
            edge_data e=i.get_edge();
           double dist= algo.shortestPathDist(src,e.getSrc());
           if(dist< min_dist){
            min_dist=dist;
            ans=e;
           }
        }

        return  ans;
//
    }

    /**
     * initiate the agent for the nearst place to their pokemon targets
     * @param game game server
     */
    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);


        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < cl_fs.size(); a++) {
                Arena.updateEdge(cl_fs.get(a), gg);
            }
            for (int a = 0; a < rs; a++) {
                int ind = a % cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if (c.getType() < 0) {
                    nn = c.get_edge().getSrc();
                }

                game.addAgent(nn);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
