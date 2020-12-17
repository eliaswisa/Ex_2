package api.tests;

import api.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DWGraph_AlgoTest {

    @Test
    void copy() {
        DWGraph_DS g = new DWGraph_DS();

        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.addNode(new NodeData(6));
        g.addNode(new NodeData(7));
        g.connect(0, 1, 1);
        g.connect(1, 2, 4);
        g.connect(2, 3, 4);
        g.connect(3, 4, 2);
        g.connect(4, 5, 2);
        g.connect(4, 6, 2);
        g.connect(5, 5, 2);
        g.connect(5, 10, 2);
        g.connect(4, 7, 2);
        DWGraph_Algo m = new DWGraph_Algo();
        DWGraph_Algo m2 = new DWGraph_Algo();
        m.init(g);
    }

    @Test
    void isConnected() {
        dw_graph_algorithms graph_ = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();
        graph_.init(g);

        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        // g.addNode(new NodeData(2));
        // g.addNode(new NodeData(3));
        g.connect(0, 1, 1);
        g.connect(1, 0, 1);
        // g.connect(1, 2, 1);
//        g.connect(1, 2, 1);
//        g.connect(2,3,1);
//        g.connect(3,1,1);
//        g.connect(1,4,1);
//        g.connect(4,3,1);
        boolean check = graph_.isConnected();
        assertTrue(check);

    }

    // TODO: 18/12/2020 test 
    @Test 
    void shortestPathDist() {
        { dw_graph_algorithms graph_ = new DWGraph_Algo();
            directed_weighted_graph g = new DWGraph_DS();
            graph_.init(g);
            g.addNode(new NodeData(0));
            g.addNode(new NodeData(1));
            g.addNode(new NodeData(2));
            g.addNode(new NodeData(3));
            g.addNode(new NodeData(4));
            g.addNode(new NodeData(5));
            g.addNode(new NodeData(6));
            g.addNode(new NodeData(7));
            g.addNode(new NodeData(8));
            g.connect(0, 1, 1);
            g.connect(1, 2, 1);
            g.connect(2, 8, 1);
            g.connect(0, 8, 8);

            //double a = graph_.shortestPathDist(0, 8);
            //double b = graph_.shortestPathDist(0, 9);

            Collection<node_data> cl = graph_.shortestPath(0, 8);
            //  assertEquals(cl);
        }
    }


//    @Test
//    void shortestPath() {
//        dw_graph_algorithms graph_ = new DWGraph_Algo();
//        directed_weighted_graph g = new DWGraph_DS();
//        graph_.init(g);
//
//
//        g.addNode(new NodeData(0));
//        g.addNode(new NodeData(1));
//        g.addNode(new NodeData(2));
//        g.addNode(new NodeData(3));
//        g.addNode(new NodeData(4));
//        g.addNode(new NodeData(5));
//        g.addNode(new NodeData(6));
//        g.addNode(new NodeData(7));
//        g.addNode(new NodeData(8));
//        g.connect(0, 1, 1);
//        g.connect(1, 2, 1);
//        g.connect(2, 8, 1);
//        g.connect(0, 8, 8);
//        graph_.shortestPath(0, 7);
//        //2 ליסטים שאמורים להיות זהים לגמריי
//    }

    @Test
    void save() {

    }

    @Test
    void load() {

    }


    @Test
    void shortestPath() {
        dw_graph_algorithms graph_ = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();
        graph_.init(g);
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.addNode(new NodeData(6));
        g.addNode(new NodeData(7));
        g.addNode(new NodeData(8));
        g.connect(0, 1, 1);
        g.connect(1, 2, 1);
        g.connect(2, 8, 1);
        g.connect(0, 8, 8);

        //double a = graph_.shortestPathDist(0, 8);
        //double b = graph_.shortestPathDist(0, 9);

        Collection<node_data> cl = graph_.shortestPath(0, 8);
        //assertEquals(cl, manual);
    }
}