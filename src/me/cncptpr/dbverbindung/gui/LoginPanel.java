package me.cncptpr.dbverbindung.gui;

import me.cncptpr.dbverbindung.handler.LoginHandler;

import javax.swing.*;
import java.awt.*;

public class LoginPanel {
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JTextField databaseTextField;
    private JTextField ipTextField;
    private JButton loginButton;
    private JPanel mainPanel;


    public LoginPanel() {
        loginButton.addActionListener(LoginHandler::OnLoginClicked);
        usernameTextField.addActionListener(e -> passwordTextField.grabFocus());
        usernameTextField.addActionListener(e -> passwordTextField.grabFocus());
        passwordTextField.addActionListener(e -> databaseTextField.grabFocus());
        databaseTextField.addActionListener(e -> ipTextField.grabFocus());
        ipTextField.addActionListener(LoginHandler::OnLoginClicked);
        usernameTextField.grabFocus();

    }
    public Container getMainPanel() {
        return mainPanel;
    }

    public String getUsername() {
        return usernameTextField.getText();
    }
    public String getPassword() {
        return passwordTextField.getText();
    }
    public String getDatabase() {
        return databaseTextField.getText();
    }
    public String getIP() {
        return ipTextField.getText();
    }
}
