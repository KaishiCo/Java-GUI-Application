package main;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private JLabel label1;
    //v placeholder variables for future implementations
//    private JLabel label2;
//    private JLabel label3;
//    private JMenuBar menuBar;
//    private JTextArea textArea1;
//    private JTextArea textArea2;
//    private JTextArea textArea3;
    //^ placehold variables for future implementations
    private ImageIcon icon;

    public Window() {   //setting default sizes, colors, locations, and closing operations
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(123, 50, 250));
    }

    public void loadLoginScreen() { //this method loads resources and sets positions for the login screen
        icon = new ImageIcon(this.getClass().getResource("/files/kaishi_logo.png"));
        label1 = new JLabel();

        label1.setText("Welcome to the KaishiCo");
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        label1.setIcon(icon);
        label1.setHorizontalTextPosition(JLabel.RIGHT);
        label1.setVerticalTextPosition(JLabel.CENTER);
        label1.setBounds(410, -150, 500, 500);  //seems the default is around the (x:0, y:frame's center) will mess with later

        this.add(label1);
        this.setVisible(true);  //needs to be called after adding/removing components to window
    }
}
