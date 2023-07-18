package me.cncptpr.dbverbindung.core.events.sqlUpdateEvent;

import me.cncptpr.dbverbindung.core.events.defaults.EventHandler;
import me.cncptpr.dbverbindung.core.events.defaults.Listener;

public class SQLUpdateEventHandler extends EventHandler<Listener<String>, String> {

    public void callAllListeners(String sql) {
        call(sql);
    }
}
