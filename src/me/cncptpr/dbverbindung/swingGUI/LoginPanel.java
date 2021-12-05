package me.cncptpr.dbverbindung.swingGUI;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.core.handler.LoginHandler;

import javax.swing.*;
import java.awt.*;

import static me.cncptpr.dbverbindung.Main.SETTINGS;

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

    public void fill() {
        usernameTextField.setText(SETTINGS.getString("username"));
        passwordTextField.setText(SETTINGS.getString("password"));
        databaseTextField.setText(SETTINGS.getString("database"));
        ipTextField.setText(SETTINGS.getString("ip"));
    }
}
