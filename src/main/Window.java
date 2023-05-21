package main;

import payload.login.LoginCredential;
import payload.tasks.Task;
import services.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Window extends JFrame {
    private final AuthService authService = new AuthService();
    private final int WINDOW_WIDTH = 1280;
    private final int WINDOW_HEIGHT = 720;
    private final Window M_WINDOW = this;
    private final ArrayList<JLabel> labels = new ArrayList<>();
    private final ArrayList<JPanel> panels = new ArrayList<>();
    private JPanel extendedTaskPanel = null;

    public Window() {   //setting default sizes, colors, locations, and closing operations
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(123, 50, 250));
        loadLoginScreen();
    }

    /*
    * 5/11/2023 - Note: Currently using a null layout manager however I have been informed that
    * it is generally frowned upon to use a null layout manager
    * for now I will stick with it but may come back to redesign this at a later date
    */
    private void loadLoginScreen() { //this method loads resources and sets positions for the login screen
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/files/kaishi_logo.png"));
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        JTextField textField1 = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton button = new JButton();

        label.setText("Welcome to the KaishiCo");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        label.setIcon(icon);
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setBounds(360, -150, 500, 500);  //seems the default is around the (x:0, y:frame's center) will mess with later
        labels.add(label);
        this.add(label);

        panel.setBackground(Color.GRAY);  //this panel will hold the login section
        panel.setBounds(70, 220, 500, 300);
        panel.setLayout(null);
        panels.add(panel);
        this.add(panel);

        label = new JLabel();
        label.setText("Username"); //Username section for the login
        label.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label.setBounds(10, 20, 100, 50);
        labels.add(label);
        panel.add(label);

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
        panel.add(textField1);

        label = new JLabel();
        label.setText("Password"); //Password section for the login
        label.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label.setBounds(10, 120, 100, 50);
        labels.add(label);
        panel.add(label);

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
                if (passField != null) {
                    passField.setBorder(null);
                    passField.setBackground(Color.white);
                }
            }
        });
        passField.setBounds(10, 170, 200, 25);
        panel.add(passField);

        button.setText("Login");    //login button that submits the fields
        button.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        button.setFocusable(false);
        button.setBounds(200, 230, 100, 30);
        button.addActionListener(e -> { //stores the password in a char array then builds a string to
            char[] pwd = passField.getPassword();
            String password = "";
            StringBuilder sb = new StringBuilder();

            for (char c : pwd) {    //builds the string for the password
                sb.append(c);
                password = sb.toString();
            }

            boolean success = authService.requestLogin(new LoginCredential(textField1.getText(), password));
            password = null;    //sets password to null in cases where password might be grabbed before garbage collected
            pwd = null;         //same as above

            textField1.setText("");
            passField.setText("");
            if (success) {  //When successful clears the login screen then loads the main screen
                clearLoginScreen();
                loadMainScreen();
            }
            else {
                JOptionPane.showMessageDialog(this, "Login Unsuccessful");
            }
        });
        panel.add(button);

        label = new JLabel();
        label.setText("<html><a href=''>My GitHub</a></html>");    //this label is a hyperlink to my GitHub set next to the login panel
        label.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label.setBounds(900, 280, 200, 50);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
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
        labels.add(label);
        this.add(label);

        label = new JLabel();
        label.setText("<html><a href=''>KaishiCo GitHub</a></html>");    //this label is a hyperlink to the KaishiCo organization GitHub set next to the login panel
        label.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label.setBounds(875, 360, 200, 50);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
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
        labels.add(label);
        this.add(label);

        this.setVisible(true);  //needs to be called after adding/removing components to window
    }

    private void clearLoginScreen() {   //right now this manually removes everything from the window but will be changed when I change to ArrayList storing
        for(JPanel e : panels) {
            this.remove(e);
        }
        for (JLabel e : labels) {
            this.remove(e);
        }
        panels.clear();
        labels.clear();
        this.revalidate();
        this.repaint();
    }

    private void loadMainScreen() { //loads the main screen with all the current tasks for the week (right now it just shows all tasks)
        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel();

        Task[] tasks = authService.requestTasks();

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));  //panel holding the task panels
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setBounds(0, 0, WINDOW_WIDTH/3, WINDOW_HEIGHT);
        panels.add(panel1);
        this.add(panel1);

        label1.setText("This week's tasks");    //Heading for panel1
        label1.setFont(new Font("Times New Roman", Font.BOLD, 32));
        label1.setAlignmentX(0.5f);
        labels.add(label1);
        panel1.add(label1);

        for (Task t : tasks) {  //this loop adds all current tasks to a panel and adds functionality when hovered and clicked
            JPanel task = new JPanel(); //task panel
            JLabel text = new JLabel(); //task name

            task.setBackground(Color.darkGray);
            task.setBorder(BorderFactory.createLineBorder(Color.black));
            task.setMaximumSize(new Dimension(300, 50));
            task.setAlignmentX(0.5f);
            task.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (extendedTaskPanel == null) {   //this only occurs when the first ever task is opened
                        extendedTaskPanel = new JPanel();
                        JLabel date = new JLabel();
                        JLabel description = new JLabel();

                        extendedTaskPanel.setLayout(new BoxLayout(extendedTaskPanel, BoxLayout.Y_AXIS));
                        extendedTaskPanel.setBackground(Color.darkGray);
                        extendedTaskPanel.setBounds(WINDOW_WIDTH/3, 0, (WINDOW_WIDTH/3)*2, WINDOW_HEIGHT);

                        M_WINDOW.add(extendedTaskPanel);

                        date.setText(t.getDate().toString());
                        date.setFont(new Font("Times New Roman", Font.BOLD, 32));
                        date.setForeground(Color.black);
                        date.setAlignmentX(0.5f);
                        extendedTaskPanel.add(date);
                        extendedTaskPanel.add(Box.createVerticalStrut(30));

                        description.setText(t.getDescription());
                        description.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                        description.setForeground(Color.black);
                        description.setAlignmentX(0.5f);
                        extendedTaskPanel.add(description);

                        M_WINDOW.revalidate();
                        M_WINDOW.repaint();
                    }
                    else {  //this probably adds a couple steps unnecessarily but ensures the extended task panel is created properly
                        M_WINDOW.remove(extendedTaskPanel);

                        extendedTaskPanel = new JPanel();
                        JLabel date = new JLabel();
                        JLabel description = new JLabel();

                        extendedTaskPanel.setLayout(new BoxLayout(extendedTaskPanel, BoxLayout.Y_AXIS));
                        extendedTaskPanel.setBackground(Color.darkGray);
                        extendedTaskPanel.setBounds(WINDOW_WIDTH/3, 0, (WINDOW_WIDTH/3)*2, WINDOW_HEIGHT);

                        M_WINDOW.add(extendedTaskPanel);

                        date.setText(t.getDate().toString());
                        date.setFont(new Font("Times New Roman", Font.BOLD, 32));
                        date.setForeground(Color.black);
                        date.setAlignmentX(0.5f);
                        extendedTaskPanel.add(date);
                        extendedTaskPanel.add(Box.createVerticalStrut(30));

                        description.setText(t.getDescription());
                        description.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                        description.setForeground(Color.black);
                        description.setAlignmentX(0.5f);
                        extendedTaskPanel.add(description);

                        M_WINDOW.revalidate();
                        M_WINDOW.repaint();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {    //cyan border around task when hovered with a mouse
                    task.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                }

                @Override
                public void mouseExited(MouseEvent e) {     //returns to normal when not hovered
                    task.setBorder(BorderFactory.createLineBorder(Color.black));
                }
            });
            panels.add(task);
            panel1.add(task);
            panel1.add(Box.createVerticalStrut(10));    //adds spacing between tasks

            text.setText(t.getName());  //sets the name of the task
            text.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            text.setForeground(Color.black);
            text.setHorizontalAlignment(SwingConstants.CENTER);
            text.setVerticalAlignment(SwingConstants.CENTER);

            task.add(text);
        }

        this.revalidate();  //needs to be called after adding/removing components to window
        this.repaint();
    }
}
