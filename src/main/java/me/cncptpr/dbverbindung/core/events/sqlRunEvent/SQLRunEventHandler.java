package me.cncptpr.dbverbindung.core.events.sqlRunEvent;

import me.cncptpr.dbverbindung.core.ResultTable;
import me.cncptpr.dbverbindung.core.events.defaults.EventHandler;
import me.cncptpr.dbverbindung.core.events.defaults.Listener;

public class SQLRunEventHandler extends EventHandler<Listener<SQLRunEvent>, SQLRunEvent> {

    public void callAllListeners(String sql, ResultTable resultTable) {
        call(new SQLRunEvent(sql, resultTable));
    }
}
