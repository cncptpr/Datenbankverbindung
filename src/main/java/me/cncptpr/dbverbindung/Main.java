package me.cncptpr.dbverbindung;

import me.cncptpr.dbverbindung.swingGUI.Window;
import me.cncptpr.dbverbindung.core.handler.LoginHandler;
import me.cncptpr.dbverbindung.core.save.Settings;


/**
 * Main Class of the Application.
 * It holds the Settings/Credentials,
 * creates the Window
 * and kicks of initial connection attempt
 */
public class Main {

    public static final String HISTORY_DIR = "history";
    public static final String SETTINGS_DIR = ".";

    private static Window window;

    /**
     * Adapter to the settings file.
     * The settings file currently stores no settings but the credentials for the Program.
     */
    // TODO: 17.07.2023 Maybe a rename?
    public static final Settings SETTINGS = new Settings(SETTINGS_DIR + "/settings.json");

    // TODO: 17.07.2023 Font Size changeable / Bigger

    public static void main(String[] args) {
        window = new Window();
        LoginHandler.login();
    }

    /**
     * Some changes to the ui require the repaint of the window, but don't trigger it.
     * This method does.
     */
    public static void repaintWindow() {
        window.repaint();
    }
}
