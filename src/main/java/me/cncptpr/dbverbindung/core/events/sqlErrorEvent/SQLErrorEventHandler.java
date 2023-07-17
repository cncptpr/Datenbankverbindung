package me.cncptpr.dbverbindung.core.events.sqlErrorEvent;

import me.cncptpr.dbverbindung.core.events.defaults.EventHandler;

public class SQLErrorEventHandler extends EventHandler<SQLErrorListener, SQLErrorEvent> {

    public void callAllListeners(String sql, String error) {
        call(new SQLErrorEvent(sql, error));
    }
}
