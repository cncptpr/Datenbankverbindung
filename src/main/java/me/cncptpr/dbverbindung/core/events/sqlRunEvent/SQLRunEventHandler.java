package me.cncptpr.dbverbindung.core.events.sqlRunEvent;

import me.cncptpr.dbverbindung.core.ResultTable;
import me.cncptpr.dbverbindung.core.events.defaults.EventHandler;

public class SQLRunEventHandler extends EventHandler<SQLRunListener, SQLRunEvent> {

    public void callAllListeners(String sql, ResultTable resultTable) {
        call(new SQLRunEvent(sql, resultTable));
    }
}
