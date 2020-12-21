package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * user gui window
 */
public class GuiStart extends JFrame {


    /**
     * draw window for the user for inserting id and level chooser
     */
    public void start_game() {

        this.setSize(200, 300);
        this.setLayout(new FlowLayout());

        JButton start = new JButton("Start");
        JLabel id_label = new JLabel("please Enter your id ");
        JLabel level_label = new JLabel("please Enter your level 0-23 ");

        JTextField id_text = new JTextField("", 15);
        JTextField LEVEL_text = new JTextField("", 15);
        this.setLocation(700, 300);
        this.add(id_label);
        this.add(id_text);
        this.add(level_label);
        this.add(LEVEL_text);
        this.add(start);
        this.setVisible(true);

        start.addActionListener(new ActionListener() {
            /**
             * action performer who connecting between the content inside the text fields and the code
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Ex2 ex = new Ex2(Integer.parseInt(id_text.getText()), Integer.parseInt(LEVEL_text.getText()));
                Thread client = new Thread(ex);
                client.start();

            }
        });

    }
}
