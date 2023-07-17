package me.cncptpr.dbverbindung.swingGUI;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.core.dbconnection.DBConnection;
import me.cncptpr.dbverbindung.core.handler.LoginHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static me.cncptpr.dbverbindung.Main.SETTINGS;
import static me.cncptpr.dbverbindung.core.events.EventHandlers.LOGIN_EVENT;

public class LoginPanel {

    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JTextField databaseTextField;
    private JTextField ipTextField;
    private JButton loginButton;
    private JPanel mainPanel;


    public LoginPanel() {
        loginButton.addActionListener(this::OnLoginClicked);
        usernameTextField.addActionListener(e -> passwordTextField.grabFocus());
        usernameTextField.addActionListener(e -> passwordTextField.grabFocus());
        passwordTextField.addActionListener(e -> databaseTextField.grabFocus());
        databaseTextField.addActionListener(e -> ipTextField.grabFocus());
        ipTextField.addActionListener(this::OnLoginClicked);
        usernameTextField.grabFocus();
    }

    public void OnLoginClicked(ActionEvent ignored) {
        Main.SETTINGS.set("username", getUsername());
        Main.SETTINGS.set("password", getPassword());
        Main.SETTINGS.set("database_current", getDatabase());
        Main.SETTINGS.set("database_login", getDatabase());
        Main.SETTINGS.set("ip", getIP());
        if (DBConnection.canConnect())
            LOGIN_EVENT.call(getUsername(), getDatabase(), getIP());
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
        databaseTextField.setText(SETTINGS.getString("database_login"));
        ipTextField.setText(SETTINGS.getString("ip"));
    }
}
