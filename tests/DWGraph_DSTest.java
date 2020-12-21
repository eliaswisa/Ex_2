

import api.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DWGraph_DSTest {

    @Test
    void addNode() {
        DWGraph_DS g = new DWGraph_DS();

        // g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(2));
        //g.addNode(new NodeData(9));
        boolean a = true;
        boolean b = false;

        Collection<node_data> col = g.getV();
        for (node_data temp : col) {
            b = false;
            if ((temp.getKey() == 0 || temp.getKey() == 1 || temp.getKey() == 2)) {
                b = true;
            } else {
                a = false;
            }
        }
        assertTrue(b && a);
    }

    @Test
    void connect() {
        DWGraph_DS g = new DWGraph_DS();
        edge_data a = new EdgeData();
        edge_data b = new EdgeData();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.connect(0, 1, 2);
        g.connect(0, 0, 2);
        a = g.getEdge(0, 1);
        boolean aa = false;
        b=g.getEdge(0,0);
        aa=(a.getSrc()==0||a.getDest()==1);
        boolean bb=(b==null);
        assertTrue(aa&&bb);


    }


    @Test
    void removeNode() {
        DWGraph_DS g = new DWGraph_DS();

        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.removeNode(0);
        Collection<node_data> col = g.getV();
        boolean a = true;
        boolean b = false;


        for (node_data temp : col) {
            b = false;
            if ((temp.getKey() == 0 || temp.getKey() == 1 || temp.getKey() == 2) && col.size() == 1) {
                b = true;
            } else {
                a = false;
            }
        }
        assertTrue(b && a);
    }

    @Test
    void removeEdge() {
        DWGraph_DS g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(0, 1, 2);
        g.connect(0, 2, 2);
        boolean check = true;
        boolean check2 = false;
        if (g.getEdge(0, 1) == null) {
            check = false;

        }
        if (g.getEdge(0, 3) == null) {
            check2 = true;
        }
        assertTrue(check && check2);
    }

    @Test
    void nodeSize() {
        DWGraph_DS g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(2));
        g.removeNode(0);
        int check = g.nodeSize();
        assertEquals(2, check);
    }

    @Test
    void edgeSize() {
        DWGraph_DS g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(0, 1, 2);
        System.out.println(g.edgeSize());
        g.connect(0, 2, 2);
        System.out.println(g.edgeSize());
        g.connect(0, 2, 3);
        System.out.println(g.edgeSize());
        g.connect(0, 3, 3);
        System.out.println(g.edgeSize());
        g.connect(0, 4, 3);
        System.out.println(g.edgeSize());
        g.removeNode(2);

        //g.removeNode(0);
        System.out.println(g.edgeSize());
        int check = g.edgeSize();
        assertEquals(2, check);
    }

}