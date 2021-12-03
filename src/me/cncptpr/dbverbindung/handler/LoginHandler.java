package me.cncptpr.dbverbindung.handler;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.dbconnection.DBConnection;
import me.cncptpr.dbverbindung.gui.LoginPanel;
import me.cncptpr.dbverbindung.listenersAndEvents.LoginEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginHandler {

    private static LoginPanel loginPanel;

    public static void OnLoginClicked(ActionEvent ignored) {
        Main.settings.set("username", loginPanel.getUsername());
        Main.settings.set("password", loginPanel.getPassword());
        Main.settings.set("database_current", loginPanel.getDatabase());
        Main.settings.set("database_login", loginPanel.getDatabase());
        Main.settings.set("ip", loginPanel.getIP());
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
