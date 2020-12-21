package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 */
public class MyFrame extends JFrame {

    private long time=0;
    private int _ind;
    private Arena _ar;
    private gameClient.util.Range2Range _w2f;
    Image agentImage;
    Image pokemonImage;
    //JLabel timeLabel = new JLabel();
     BufferedImage img1 ;



    public MyFrame(String a) {
        super(a);
        int _ind = 0;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void update(Arena ar) {
        this._ar = ar;
        updateFrame();
    }

    private void updateFrame() {
        Range rx = new Range(20, this.getWidth() - 20);
        Range ry = new Range(this.getHeight() - 10, 150);
        Range2D frame = new Range2D(rx, ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g, frame);

    }

    public void paint(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();
        setSize(width, height);
        updateFrame();
        Image buffer_image = createImage(width, height);
        Graphics bufferG;

        bufferG = buffer_image.getGraphics();

        drawGraph(bufferG);
        drawAgants(bufferG);
        drawPokemons(bufferG);

        drawInfo(bufferG);
        bufferG.setColor(Color.BLUE);
        bufferG.setFont(new Font("time",Font.BOLD,20));
        bufferG.drawString("Time "+time,100,50);
        g.drawImage(buffer_image, 0, 0, this);


    }

    private void drawWallPaper(Graphics g) throws IOException {
        img1=ImageIO.read(getClass().getResourceAsStream("wallpaper"));
        g.drawImage(img1,100,100,this);

    }
public void setTime(long time){
       this.time=time;
}
    private void drawInfo(Graphics g) {
        List<String> str = _ar.get_info();
        String dt = "none";
        for (int i = 0; i < str.size(); i++) {
            g.drawString(str.get(i) + " dt: " + dt, 100, 60 + i * 20);
        }

    }

    private void drawGraph(Graphics g) {

        directed_weighted_graph gg = _ar.getGraph();
        Iterator<node_data> iter = gg.getV().iterator();
        while (iter.hasNext()) {
            node_data n = iter.next();
            g.setColor(Color.blue);
            drawNode(n, 5, g);
            Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
            while (itr.hasNext()) {
                edge_data e = itr.next();
                g.setColor(Color.gray);
                drawEdge(e, g);
            }
        }
    }

    private void drawPokemons(Graphics g) {
        ImageIcon im = new ImageIcon("data/images/pokemon256x256.png");
        pokemonImage = im.getImage();
        java.util.List<CL_Pokemon> fs = _ar.getPokemons();
        if (fs != null) {
            Iterator<CL_Pokemon> itr = fs.iterator();
            while (itr.hasNext()) {
                CL_Pokemon f = itr.next();
                Point3D c = f.getLocation();
                int r = 10;
                g.setColor(Color.green);
                if (f.getType() < 0) {
                    g.setColor(Color.orange);
                }
                if (c != null) {
                    geo_location fp = this._w2f.world2frame(c);
                    //g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
                    //StdDraw.picture(x, y, "monye.png",0.0005,0.0004);
                    //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
                    g.drawImage(pokemonImage, (int) fp.x() - 20, (int) fp.y() - 15, 50, 50, null);
                }
            }
        }
    }
    private void drawAgants(Graphics g) {
        ImageIcon im = new ImageIcon("data/images/agent.png");
        agentImage = im.getImage();
        List<CL_Agent> rs = _ar.getAgents();
        //	Iterator<OOP_Point3D> itr = rs.iterator();
        g.setColor(Color.red);
        int i = 0;
        while (rs != null && i < rs.size()) {
            geo_location c = rs.get(i).getLocation();
            String value= rs.get(i).getValue()+"";
            int r = 8;
            i++;
            if (c != null) {

                geo_location fp = this._w2f.world2frame(c);
                g.drawImage(agentImage, (int) fp.x() - 20, (int) fp.y() - 15, 50, 50, null);
                g.setColor(Color.red);
                g.setFont(new Font("time",Font.BOLD,20));
                g.drawString("Score "+value,(int) fp.x(), (int) fp.y()-4*r);
            }
        }
    }

    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
        g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);
    }

    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());

    }
}