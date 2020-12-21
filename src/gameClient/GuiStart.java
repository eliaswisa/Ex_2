package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 *
 */
public class GuiStart extends JFrame {



    public void start_game(){
        //this.setContentPane(new FlowLayout());
        this.setSize(200,300);
        this.setLayout(new FlowLayout());

        JButton start=new JButton("Start");
        JLabel id_label =new JLabel("Enter your id ");
        JLabel level_label=new JLabel("Enter your level 0-23 ");

        JTextField id_text = new JTextField("",15);
        JTextField LEVEL_text = new JTextField("",15);

        this.add(id_label);
        this.add(id_text);
        this.add(level_label);
        this.add(LEVEL_text);
        this.add(start);
        this.setVisible(true);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ex2 ex=new Ex2(Integer.parseInt(id_text.getText()),Integer.parseInt(LEVEL_text.getText()));
                Thread client = new Thread(ex);
                client.start();

            }
        });

    }
}
