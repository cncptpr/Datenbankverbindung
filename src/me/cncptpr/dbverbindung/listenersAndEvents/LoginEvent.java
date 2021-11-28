package me.cncptpr.dbverbindung.listenersAndEvents;

import me.cncptpr.dbverbindung.Setting;

import java.util.ArrayList;

public class LoginEvent {

    private static final ArrayList<LoginListener> loginListeners = new ArrayList<>();

    public static void registerLoginListener(LoginListener l) {
        removeLoginListener(l);
        loginListeners.add(l);
    }

    public static void removeLoginListener(LoginListener l) {
        loginListeners.remove(l);
    }

    public static void callLoginListeners() {
        for (LoginListener l : loginListeners)
            callLoginListenerInNewThread(new LoginEvent(Setting.getString("username"), Setting.getString("database_login"), Setting.getString("id")), l);
    }

    public static void callLoginListeners(String username, String database, String id) {
        for (LoginListener l : loginListeners)
            callLoginListenerInNewThread(new LoginEvent(username, database, id), l);
    }

    public static void callLoginListeners(LoginEvent event) {
        for (LoginListener l : loginListeners)
            callLoginListenerInNewThread(event, l);
    }

    private static void callLoginListenerInNewThread(LoginEvent event, LoginListener l) {
        new Thread(() ->
                l.run(event)
        ).start();
    }

    private final String username;
    private final String database;
    private final String id;

    public LoginEvent(String username, String database, String id) {
        this.username = username;
        this.database = database;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getDatabase() {
        return database;
    }

    public String getId() {
        return id;
    }
}
