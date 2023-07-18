package me.cncptpr.dbverbindung.core.events.sqlErrorEvent;

import me.cncptpr.dbverbindung.core.events.defaults.EventHandler;
import me.cncptpr.dbverbindung.core.events.defaults.Listener;

public class SQLErrorEventHandler extends EventHandler<Listener<SQLErrorEvent>, SQLErrorEvent> {

    public void callAllListeners(String sql, String error) {
        call(new SQLErrorEvent(sql, error));
    }
}
