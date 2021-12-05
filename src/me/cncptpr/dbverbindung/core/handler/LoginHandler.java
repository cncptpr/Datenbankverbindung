package me.cncptpr.dbverbindung.core.handler;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.core.dbconnection.DBConnection;
import me.cncptpr.dbverbindung.swingGUI.LoginPanel;

import java.awt.event.ActionEvent;

import static me.cncptpr.dbverbindung.core.events.EventHandlers.*;

public class LoginHandler {

    private static boolean loggedIn = false;
    private static LoginPanel loginPanel;

    public static void OnLoginClicked(ActionEvent ignored) {
        Main.SETTINGS.set("username", loginPanel.getUsername());
        Main.SETTINGS.set("password", loginPanel.getPassword());
        Main.SETTINGS.set("database_current", loginPanel.getDatabase());
        Main.SETTINGS.set("database_login", loginPanel.getDatabase());
        Main.SETTINGS.set("ip", loginPanel.getIP());
        if (DBConnection.canConnect())
            LOGIN_EVENT.call(loginPanel.getUsername(), loginPanel.getDatabase(), loginPanel.getIP());
    }

    public static void login() {
        if (DBConnection.canConnect()) {
            loggedIn = true;
            LOGIN_EVENT.call();
        } else {
            loggedIn = false;
            LOGOUT_EVENT.call();
        }
    }

    public static void logout() {
        loggedIn = false;
        LOGOUT_EVENT.call();
    }

    public static boolean isLoggedIn() {
        if (!DBConnection.canConnect()) {
            loggedIn = false;
            // TODO: 03.12.2021 Logout Listener
        }
        return loggedIn && DBConnection.canConnect();
    }
}
