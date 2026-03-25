package ui;

import javax.swing.*;
import java.awt.*;
import dao.UserDAO;

public class LoginForm extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnLogin;

    public LoginForm(){

        setTitle("Login");
        setSize(350,200);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5,1,10,10));
        ((JComponent)getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(15,15,15,15)
        );


        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Login");

        txtUsername.setText("");
        txtPassword.setText("");

        add(new JLabel("Username"));
        add(txtUsername);
        add(new JLabel("Password"));
        add(txtPassword);
        add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    public void login(){

        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if(username.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please fill all fields");
            return;
        }

        UserDAO dao = new UserDAO();

        if(dao.login(username, password)){
            JOptionPane.showMessageDialog(this,"Login successful");

            new MainMenu(); // open main system
            this.dispose(); // close login

        } else {
            JOptionPane.showMessageDialog(this,"Invalid username or password");
        }
    }
}