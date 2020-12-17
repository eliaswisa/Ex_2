package gameClient.myGameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LaunchPage implements ActionListener {
    private JFrame mainWindowFrame;
    private JButton startButton;
    private JPanel jp;
    private JLabel topLabel;
    private JTextField id = new JTextField(20);
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    LaunchPage() {
        mainWindowFrame = new JFrame("Pokemon Game");
        // mainWindowFrame.setLayout(new GridLayout(1,1));
        mainWindowFrame.setVisible(true);
        mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindowFrame.setSize(1000, 600);
        mainWindowFrame.setResizable(true);
        mainWindowFrame.setLocation(dim.width / 2 - 1000 / 2, dim.height / 2 - 600 / 2);
        jp = new JPanel();
        jp.setBackground(Color.GRAY);
        topLabel = new JLabel("hello,please enter user id and choose your scenario number");
//      topLabel.setAlignmentX(500);
//      topLabel.setAlignmentY(900);
        topLabel.setFont(topLabel.getFont().deriveFont(20.0f));
        jp.add(id);
        startButton = new JButton("start Game");
        //startButton.setLayout(new FlowLayout(FlowLayout.TRAILING));
        startButton.setBounds(100, 160, 200, 70);
        //startButton.setFocusable(true);
        startButton.addActionListener(this);
        jp.add(topLabel);
        jp.add(startButton);
        mainWindowFrame.add(jp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            mainWindowFrame.dispose();
            Thread client = new Thread(new Ex2());
            client.start();

        }
    }
}
