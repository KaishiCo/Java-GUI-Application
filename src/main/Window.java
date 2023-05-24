package main;

import payload.login.LoginCredential;
import payload.tasks.Task;
import payload.tasks.TaskRequest;
import security.AuthToken;
import services.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

public class Window extends JFrame {
    private final AuthService authService = new AuthService();
    private final int WINDOW_WIDTH = 1280;
    private final int WINDOW_HEIGHT = 720;
    private final Window M_WINDOW = this;
    private final ArrayList<JLabel> labels = new ArrayList<>();
    private final ArrayList<JPanel> panels = new ArrayList<>();
    private JScrollPane scrollFrame;
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
                clearScreen();
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

    private void clearScreen() {   //right now this manually removes everything from the window but will be changed when I change to ArrayList storing
        for(JPanel e : panels) {
            this.remove(e);
        }
        for (JLabel e : labels) {
            this.remove(e);
        }
        if (scrollFrame != null) {
            this.remove(scrollFrame);
        }
        panels.clear();
        labels.clear();
        this.revalidate();
        this.repaint();
    }

    private void loadMainScreen() { //loads the main screen with all the current tasks for the week (right now it just shows all tasks)
        JPanel createTaskPanel = new JPanel();
        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel();

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));  //panel holding the task panels
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setBounds(0, 0, WINDOW_WIDTH/3, WINDOW_HEIGHT);
        scrollFrame = new JScrollPane(panel1);  //makes the panel with all the tasks scrollable ? will test
        scrollFrame.setBounds(0, 0, WINDOW_WIDTH/3, WINDOW_HEIGHT - 38);
        panels.add(panel1);
        this.add(scrollFrame);

        label1.setText("This week's tasks");    //Heading for panel1
        label1.setFont(new Font("Times New Roman", Font.BOLD, 32));
        label1.setAlignmentX(0.5f);
        labels.add(label1);
        panel1.add(label1);

        createAndAddTaskPanels(authService.requestTasks(), panel1);    //adds all task panels in the database

        createTaskPanel.setBackground(Color.darkGray);
        createTaskPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        createTaskPanel.setMaximumSize(new Dimension(300, 50));
        createTaskPanel.setAlignmentX(0.5f);
        createTaskPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openAddWindow();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                createTaskPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                createTaskPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        });
        panel1.add(createTaskPanel);

        label1 = new JLabel();
        label1.setText("Create New Task");
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label1.setForeground(Color.black);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setVerticalAlignment(SwingConstants.CENTER);
        createTaskPanel.add(label1);

        this.revalidate();  //needs to be called after adding/removing components to window
        this.repaint();
    }

    public void createAndAddTaskPanels(Task[] tasks, JPanel taskPanel) {
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
                    if (extendedTaskPanel != null) {   //removes old panel if one was opened before
                        M_WINDOW.remove(extendedTaskPanel);
                    }
                    extendedTaskPanel = new JPanel();
                    JLabel date = new JLabel();
                    JLabel description = new JLabel();
                    JPanel taskOptions = new JPanel();
                    JButton editTask = new JButton();
                    JButton deleteTask = new JButton();

                    extendedTaskPanel.setLayout(new BoxLayout(extendedTaskPanel, BoxLayout.Y_AXIS));    //this panel holds all task information
                    extendedTaskPanel.setBackground(Color.darkGray);
                    extendedTaskPanel.setBounds(WINDOW_WIDTH/3, 0, (WINDOW_WIDTH/3)*2, WINDOW_HEIGHT);

                    M_WINDOW.add(extendedTaskPanel);

                    date.setText(t.getDate().toString());   //date text
                    date.setFont(new Font("Times New Roman", Font.BOLD, 32));
                    date.setForeground(Color.black);
                    date.setAlignmentX(0.5f);
                    extendedTaskPanel.add(date);
                    extendedTaskPanel.add(Box.createVerticalStrut(30));

                    description.setText(t.getDescription());    //description text
                    description.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                    description.setForeground(Color.black);
                    description.setAlignmentX(0.5f);
                    extendedTaskPanel.add(description);
                    extendedTaskPanel.add(Box.createVerticalGlue());

                    taskOptions.setLayout(new BoxLayout(taskOptions, BoxLayout.X_AXIS));    //panel that holds the edit and delete buttons
                    taskOptions.setBackground(Color.darkGray);
                    taskOptions.setAlignmentX(0.5f);
                    extendedTaskPanel.add(taskOptions);

                    editTask.setText("Edit");   //this button calls the openEditWindow function
                    editTask.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                    editTask.setFocusable(false);
                    editTask.setMaximumSize(new Dimension(100, 30));
                    editTask.setAlignmentY(0.5f);
                    editTask.addActionListener(b -> openEditWindow(t.getName(), t.getDate().toString(), t.getDescription(), t.getId()));
                    editTask.setEnabled(AuthToken.getUserId().equals(t.getUserId()));
                    taskOptions.add(editTask);
                    taskOptions.add(Box.createHorizontalStrut(20));

                    deleteTask.setText("Delete");   //this button calls the API to delete the current task
                    deleteTask.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                    deleteTask.setFocusable(false);
                    deleteTask.setMaximumSize(new Dimension(100, 30));
                    deleteTask.setAlignmentY(0.5f);
                    deleteTask.addActionListener(b -> {
                        if (JOptionPane.showConfirmDialog(M_WINDOW, "Are you sure you wish to delete?") == JOptionPane.OK_OPTION) {
                            if (authService.deleteTask(t.getId())) {
                                JOptionPane.showMessageDialog(M_WINDOW, "Task deleted successfully");
                                M_WINDOW.clearScreen();
                                M_WINDOW.loadMainScreen();
                            }
                            else {
                                JOptionPane.showMessageDialog(M_WINDOW, "Couldn't delete task");
                            }
                        }
                    });
                    deleteTask.setEnabled(AuthToken.getUserId().equals(t.getUserId()));
                    taskOptions.add(deleteTask);

                    M_WINDOW.revalidate();
                    M_WINDOW.repaint();
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
            taskPanel.add(task);
            taskPanel.add(Box.createVerticalStrut(10));    //adds spacing between tasks

            text.setText(t.getName());  //sets the name of the task
            text.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            text.setForeground(Color.black);
            text.setHorizontalAlignment(SwingConstants.CENTER);
            text.setVerticalAlignment(SwingConstants.CENTER);

            task.add(text);
        }
    }

    public void openEditWindow(String nameText, String dateText, String descriptionText, UUID taskId) {
        this.remove(extendedTaskPanel);

        extendedTaskPanel = new JPanel();   //panel for editing task
        extendedTaskPanel.setBounds(WINDOW_WIDTH/3, 0, (WINDOW_WIDTH/3)*2, WINDOW_HEIGHT);
        extendedTaskPanel.setBackground(Color.darkGray);
        extendedTaskPanel.setLayout(new BoxLayout(extendedTaskPanel, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("Name");
        JTextField name = new JTextField(nameText);
        JLabel label2 = new JLabel("Date");
        JTextField date = new JTextField(dateText);
        JLabel label3 = new JLabel("Description");
        JTextArea description = new JTextArea(descriptionText);
        JPanel taskOptions = new JPanel();
        JButton apply = new JButton();
        JButton cancel = new JButton();


        label1.setAlignmentX(0.5f); //Name text
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        label1.setForeground(Color.black);
        extendedTaskPanel.add(label1);
        extendedTaskPanel.add(Box.createVerticalStrut(5));

        name.setFont(new Font("Times New Roman", Font.PLAIN, 24));  //Text field for the name
        name.setAlignmentX(0.5f);
        name.setEnabled(true);
        name.setMaximumSize(new Dimension(500, 50));
        extendedTaskPanel.add(name);
        extendedTaskPanel.add(Box.createVerticalStrut(10));

        label2.setAlignmentX(0.5f); //Date text
        label2.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        label2.setForeground(Color.black);
        extendedTaskPanel.add(label2);
        extendedTaskPanel.add(Box.createVerticalStrut(5));

        date.setFont(new Font("Times New Roman", Font.PLAIN, 24));  //Text field for the date
        date.setAlignmentX(0.5f);
        date.setEnabled(true);
        date.setMaximumSize(new Dimension(500, 50));
        extendedTaskPanel.add(date);
        extendedTaskPanel.add(Box.createVerticalStrut(10));

        label3.setAlignmentX(0.5f); //Description text
        label3.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        label3.setForeground(Color.black);
        extendedTaskPanel.add(label3);
        extendedTaskPanel.add(Box.createVerticalStrut(5));

        description.setFont(new Font("Times New Roman", Font.PLAIN, 24));   //Text area for description
        description.setAlignmentX(0.5f);
        description.setLineWrap(true);
        description.setEnabled(true);
        description.setMaximumSize(new Dimension(500, 300));
        extendedTaskPanel.add(description);
        extendedTaskPanel.add(Box.createVerticalStrut(50));

        taskOptions.setLayout(new BoxLayout(taskOptions, BoxLayout.X_AXIS));    //panel that holds the apply and cancel buttons
        taskOptions.setMaximumSize(new Dimension(250, 50));
        taskOptions.setBackground(Color.darkGray);
        taskOptions.setAlignmentX(0.5f);
        extendedTaskPanel.add(taskOptions);

        apply.setText("Apply"); //apply button that calls AuthServices editTask function
        apply.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        apply.setFocusable(false);
        apply.setMaximumSize(new Dimension(110, 30));
        apply.setAlignmentY(0.5f);
        apply.addActionListener(e -> {
            TaskRequest task = new TaskRequest(name.getText(), "2023-05-18", description.getText());
            if (authService.editTask(task, taskId)) {
                JOptionPane.showMessageDialog(this, "Task edited successfully");
                this.remove(extendedTaskPanel);
                clearScreen();
                loadMainScreen();
            }
            else {
                JOptionPane.showMessageDialog(this, "Couldn't edit task");
            }
        });
        taskOptions.add(apply);
        taskOptions.add(Box.createHorizontalStrut(20));

        cancel.setText("Cancel");   //cancel button that returns to main screen
        cancel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        cancel.setFocusable(false);
        cancel.setMaximumSize(new Dimension(110, 30));
        cancel.addActionListener(e -> {
            this.remove(extendedTaskPanel);
            loadMainScreen();
        });
        cancel.setAlignmentY(0.5f);
        taskOptions.add(cancel);

        this.add(extendedTaskPanel);
        this.revalidate();
        this.repaint();
    }

    public void openAddWindow() {
        if (extendedTaskPanel != null) {
            this.remove(extendedTaskPanel);
        }

        extendedTaskPanel = new JPanel();
        extendedTaskPanel.setBounds(WINDOW_WIDTH/3, 0, (WINDOW_WIDTH/3)*2, WINDOW_HEIGHT);
        extendedTaskPanel.setBackground(Color.darkGray);
        extendedTaskPanel.setLayout(new BoxLayout(extendedTaskPanel, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("Name");
        JTextField name = new JTextField();
        JLabel label2 = new JLabel("Date");
        JTextField date = new JTextField();
        JLabel label3 = new JLabel("Description");
        JTextArea description = new JTextArea();
        JPanel taskOptions = new JPanel();
        JButton add = new JButton();
        JButton cancel = new JButton();


        label1.setAlignmentX(0.5f);
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        label1.setForeground(Color.black);
        extendedTaskPanel.add(label1);
        extendedTaskPanel.add(Box.createVerticalStrut(5));

        name.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        name.setAlignmentX(0.5f);
        name.setEnabled(true);
        name.setMaximumSize(new Dimension(500, 50));
        extendedTaskPanel.add(name);
        extendedTaskPanel.add(Box.createVerticalStrut(10));

        label2.setAlignmentX(0.5f);
        label2.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        label2.setForeground(Color.black);
        extendedTaskPanel.add(label2);
        extendedTaskPanel.add(Box.createVerticalStrut(5));

        date.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        date.setAlignmentX(0.5f);
        date.setEnabled(true);
        date.setMaximumSize(new Dimension(500, 50));
        extendedTaskPanel.add(date);
        extendedTaskPanel.add(Box.createVerticalStrut(10));

        label3.setAlignmentX(0.5f);
        label3.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        label3.setForeground(Color.black);
        extendedTaskPanel.add(label3);
        extendedTaskPanel.add(Box.createVerticalStrut(5));

        description.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        description.setAlignmentX(0.5f);
        description.setLineWrap(true);
        description.setEnabled(true);
        description.setMaximumSize(new Dimension(500, 300));
        extendedTaskPanel.add(description);
        extendedTaskPanel.add(Box.createVerticalStrut(50));

        taskOptions.setLayout(new BoxLayout(taskOptions, BoxLayout.X_AXIS));
        taskOptions.setMaximumSize(new Dimension(250, 50));
        taskOptions.setBackground(Color.darkGray);
        taskOptions.setAlignmentX(0.5f);
        extendedTaskPanel.add(taskOptions);

        add.setText("Add"); //add button that calls AuthServices addTask function
        add.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        add.setFocusable(false);
        add.setMaximumSize(new Dimension(110, 30));
        add.setAlignmentY(0.5f);
        add.addActionListener(e -> {
            TaskRequest task = new TaskRequest(name.getText(), "2023-05-18", description.getText());
            if (authService.addTask(task)) {
                JOptionPane.showMessageDialog(this, "Task added successfully");
                this.remove(extendedTaskPanel);
                clearScreen();
                loadMainScreen();
            }
            else {
                JOptionPane.showMessageDialog(this, "Couldn't add task");
            }
        });
        taskOptions.add(add);
        taskOptions.add(Box.createHorizontalStrut(20));

        cancel.setText("Cancel");   //cancel button that loads the main screen
        cancel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        cancel.setFocusable(false);
        cancel.setMaximumSize(new Dimension(110, 30));
        cancel.addActionListener(e -> {
            this.remove(extendedTaskPanel);
            loadMainScreen();
        });
        cancel.setAlignmentY(0.5f);
        taskOptions.add(cancel);

        this.add(extendedTaskPanel);
        this.revalidate();
        this.repaint();
    }
}
