package me.cncptpr.dbverbindung.core.handler;

import me.cncptpr.console.Console;
import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.core.HistoryEntrance;
import me.cncptpr.dbverbindung.core.save.HistoryFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static me.cncptpr.dbverbindung.core.events.EventHandlers.*;

public class HistoryHandler {

    private static final ArrayList<HistoryEntrance> items = new ArrayList<>();

    private static String previousHistory;
    private static final Optional<HistoryFile> file = HistoryFile.newAsOptional(Main.HISTORY_DIR);

    static {
        try {
            previousHistory = HistoryFile.readLastHistoryFile(Main.HISTORY_DIR);
        } catch (IOException e) {
            Console.debug("Can't read last History File!");
            previousHistory = "";
        }
    }

    private HistoryHandler() {}

    public static void add(HistoryEntrance entrance) {
        items.add(entrance);
        file.ifPresent(x -> x.append(entrance));
    }

    public static void add(String sql) {
        add(new HistoryEntrance(sql));
    }
    private static void add(String sql, String error) {
        add(new HistoryEntrance(sql, error));
    }

    public static String render() {
        StringBuilder builder = new StringBuilder();
        if (!previousHistory.isEmpty())
            builder.append("================ Previous History ================")
                    .append("\n\n")
                    .append(previousHistory)
                    .append("\n\n")
                    .append("================ Current History ================")
                    .append("\n\n");
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
