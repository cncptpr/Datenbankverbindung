package me.cncptpr.dbverbindung.core.events.sqlRunEvent;

import me.cncptpr.dbverbindung.core.ResultTable;

public record SQLRunEvent(String sql, ResultTable resultTable) {

}
