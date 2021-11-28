package me.cncptpr.dbverbindung.handler;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.Setting;
import me.cncptpr.dbverbindung.dbconnection.DBConnection;
import me.cncptpr.dbverbindung.gui.LoginPanel;
import me.cncptpr.dbverbindung.gui.Menu;
import me.cncptpr.dbverbindung.listenersAndEvents.LoginEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginHandler {

    private static LoginPanel loginPanel;

    public static void OnLoginClicked(ActionEvent ignored) {
        Setting.setString("username", loginPanel.getUsername());
        Setting.setString("password", loginPanel.getPassword());
        Setting.setString("database_current", loginPanel.getDatabase());
        Setting.setString("database_login", loginPanel.getDatabase());
        Setting.setString("ip", loginPanel.getIP());
        if (DBConnection.canConnect())
            LoginEvent.callLoginListeners(loginPanel.getUsername(), loginPanel.getDatabase(), loginPanel.getIP());
    }

    public static void login() {
        if (DBConnection.canConnect()) {
            LoginEvent.callLoginListeners();
        } else {
            showLoginPanel(Main.getWindow());
        }
    }

    public static void showLoginPanel(JFrame frame) {
        loginPanel = new LoginPanel();
        frame.setResizable(false);
        frame.setContentPane(loginPanel.getMainPanel());
        frame.pack();
        frame.repaint();
        frame.setVisible(true);
    }
}
