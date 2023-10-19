package me.cncptpr.dbverbindung;

import me.cncptpr.dbverbindung.core.events.EventHandlers;
import me.cncptpr.dbverbindung.core.save.Config;
import me.cncptpr.dbverbindung.swingGUI.Window;


/**
 * Main Class of the Application.
 * It holds the {@link Config},
 * creates the {@link Window}
 * and kicks of initial connection attempt
 */
public class Main {

    // TODO: 19.10.2023 CONFIG (mit Credentials) -> CONFIG für Ip, Datenbank und History Path bei der JAR Datei.

    // TODO: 19.10.2023 Automatisch die CONFIG generieren und mit Werten aus UI ausfüllen.

    public static final String HISTORY_DIR = ".titab_sql";
    public static final String CONFIG_DIR = ".titab_sql";

    public static final Config CONFIG = new Config(CONFIG_DIR + "/config.json");

    public static void main(String[] args) {
        Window ignored = new Window("TiTab SQL");
        //LoginHandler.login();
        EventHandlers.LOGOUT_EVENT.call();
    }

}
