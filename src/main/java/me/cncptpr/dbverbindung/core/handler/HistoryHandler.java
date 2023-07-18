package me.cncptpr.dbverbindung.core.handler;

import me.cncptpr.dbverbindung.core.HistoryEntrance;

import java.util.ArrayList;
import java.util.List;

import static me.cncptpr.dbverbindung.core.events.EventHandlers.*;

public class HistoryHandler {

    private static final ArrayList<HistoryEntrance> items = new ArrayList<>();


    private HistoryHandler() {}

    public static void add(HistoryEntrance entrance) {
        items.add(0, entrance);
    }

    public static void add(String sql) {
        add(new HistoryEntrance(sql));
    }
    private static void add(String sql, String error) {
        add(new HistoryEntrance(sql, error));
    }

    public static String render() {
        StringBuilder builder = new StringBuilder();
        for (HistoryEntrance entrance : items) {
            builder.append(entrance.render());
        }
        return builder.toString();
    }

    public static void init() {
        SQL_RUN_EVENT.register(event -> add(event.sql()));
        SQL_UPDATE_EVENT.register(HistoryHandler::add);
        SQL_ERROR_EVENT.register(event -> add(event.sql(), event.error()));
    }
}
