package me.cncptpr.dbverbindung.core.handler;


import me.cncptpr.dbverbindung.core.dbconnection.DBConnection;

import static me.cncptpr.dbverbindung.core.events.EventHandlers.*;

public class LoginHandler {

    private static boolean loggedIn = false;

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
        boolean b = DBConnection.canConnect();
        if (!b) {
            loggedIn = false;
            LOGOUT_EVENT.call();
        }
        return loggedIn && b;
    }
}
