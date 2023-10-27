package me.cncptpr.dbverbindung;

import me.cncptpr.dbverbindung.core.State;
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

    public static void main(String[] args) {
        State.from(new Config());
        Window ignored = new Window("TiTab SQL");
        //LoginHandler.login();
        EventHandlers.LOGOUT_EVENT.call();
    }

}
