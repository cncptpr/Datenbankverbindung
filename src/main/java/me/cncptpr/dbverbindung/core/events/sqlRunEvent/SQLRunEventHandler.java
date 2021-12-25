package me.cncptpr.dbverbindung.core.events.sqlRunEvent;

import me.cncptpr.dbverbindung.core.ResultTable;
import me.cncptpr.dbverbindung.core.events.defaults.EventHandler;

public class SQLRunEventHandler extends EventHandler<SQLRunListener, SQLRunEvent> {

    public void callAllListeners(ResultTable resultTable, String source) {
        call(new SQLRunEvent(resultTable, source));
    }

    public void callAllListeners(ResultTable resultTable) {
        callAllListeners(resultTable, null);
    }
}
