package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Window extends JFrame {
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JTextField textField1;
    private JPasswordField passField;
    private JButton button;
    //v placeholder variables for future implementations
//    private JTextField textField2;
//    private JMenuBar menuBar;
//    private JTextField textField3;
    //^ placeholder variables for future implementations
    private JPanel panel1;
    private ImageIcon icon;

    public Window() {   //setting default sizes, colors, locations, and closing operations
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(123, 50, 250));
    }

    /*
    * 5/11/2023 - Note: Currently using a null layout manager however I have been informed that
    * it is generally frowned upon to use a null layout manager
    * for now I will stick with it but may come back to redesign this at a later date
    */
    public void loadLoginScreen() { //this method loads resources and sets positions for the login screen
        icon = new ImageIcon(this.getClass().getResource("/files/kaishi_logo.png"));
        panel1 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        textField1 = new JTextField();
        passField = new JPasswordField();
        button = new JButton();

        label1.setText("Welcome to the KaishiCo");
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        label1.setIcon(icon);
        label1.setHorizontalTextPosition(JLabel.RIGHT);
        label1.setVerticalTextPosition(JLabel.CENTER);
        label1.setBounds(360, -150, 500, 500);  //seems the default is around the (x:0, y:frame's center) will mess with later

        panel1.setBackground(Color.GRAY);  //this panel will hold the login section
        panel1.setBounds(70, 220, 500, 300);
        panel1.setLayout(null);

        label2.setText("Username"); //Username section for the login
        label2.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label2.setBounds(10, 20, 100, 50);

        textField1.setColumns(30);   //text field for Username
        textField1.setEditable(true);
        textField1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textField1.setSelectionColor(new Color(51, 153, 255));
        textField1.addFocusListener(new FocusListener() {   //when text field is focused changes background color to light gray and gives it a blue border
            @Override
            public void focusGained(FocusEvent e) {
                textField1.setBorder(BorderFactory.createLineBorder(Color.blue));
                textField1.setBackground(Color.lightGray);
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField1.setBorder(null);
                textField1.setBackground(Color.white);
            }
        });
        textField1.setBounds(10, 70, 200, 25);

        label3.setText("Password"); //Password section for the login
        label3.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label3.setBounds(10, 120, 100, 50);

        passField.setColumns(30);   //password field for Password
        passField.setEditable(true);
        passField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        passField.setSelectionColor(new Color(51, 153, 255));
        passField.addFocusListener(new FocusListener() {    //when text field is focused changes background color to light gray and gives it a blue border
            @Override
            public void focusGained(FocusEvent e) {
                passField.setBorder(BorderFactory.createLineBorder(Color.blue));
                passField.setBackground(Color.lightGray);
            }

            @Override
            public void focusLost(FocusEvent e) {
                passField.setBorder(null);
                passField.setBackground(Color.white);
            }
        });
        passField.setBounds(10, 170, 200, 25);

        button.setText("Login");    //login button that submits the fields (right now it just clears the text fields)
        button.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        button.setFocusable(false);
        button.setBounds(200, 230, 100, 30);
        button.addActionListener(e -> {
            textField1.setText("");
            passField.setText("");
        });

        label4.setText("<html><a href=''>My GitHub</a></html>");    //this label is a hyperlink to my GitHub set next to the login panel
        label4.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label4.setBounds(900, 280, 200, 50);
        label4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/ArduousKokuhaku52"));
                }
                catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        label5.setText("<html><a href=''>KaishiCo GitHub</a></html>");    //this label is a hyperlink to the KaishiCo organization GitHub set next to the login panel
        label5.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label5.setBounds(875, 360, 200, 50);
        label5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/KaishiCo"));
                }
                catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.add(label1);
        this.add(label4);
        this.add(label5);
        this.add(panel1);
        panel1.add(label2);
        panel1.add(textField1);
        panel1.add(label3);
        panel1.add(passField);
        panel1.add(button);

        this.setVisible(true);  //needs to be called after adding/removing components to window
    }
}
