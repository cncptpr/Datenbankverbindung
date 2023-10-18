package me.cncptpr.dbverbindung.core.events.loginEvent;

import me.cncptpr.dbverbindung.core.events.defaults.EventHandler;
import me.cncptpr.dbverbindung.core.events.defaults.Listener;

import static me.cncptpr.dbverbindung.Main.CONFIG;

public class LoginEventHandler extends EventHandler<Listener<LoginEvent>, LoginEvent> {

    public void call(String username, String database, String ip) {
        call(new LoginEvent(username, database, ip));
    }

    public void call() {
        String username = CONFIG.getString("username");
        String database = CONFIG.getString("database");
        String ip = CONFIG.getString("ip");
        call(username, database, ip);
    }
}
