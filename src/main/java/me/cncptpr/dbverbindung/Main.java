package me.cncptpr.dbverbindung;

import me.cncptpr.dbverbindung.swingGUI.Window;
import me.cncptpr.dbverbindung.core.handler.LoginHandler;
import me.cncptpr.dbverbindung.core.save.SaveManager;

public class Main {

    private static Window window;

    // TODO: 17.07.2023 Simplify SaveManager (it is only used for these settings)
    public static final SaveManager SETTINGS = new SaveManager("settings.json");


    public static void main(String[] args) {
        window = new Window();

        LoginHandler.login();
    }

    public static Window getWindow() {
        return window;
    }
}
