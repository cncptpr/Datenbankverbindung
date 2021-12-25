package me.cncptpr.dbverbindung;

import me.cncptpr.dbverbindung.swingGUI.Window;
import me.cncptpr.dbverbindung.core.handler.LoginHandler;
import me.cncptpr.dbverbindung.core.save.SaveManager;

import java.sql.ResultSet;

public class Main {

    private static Window window;
    public static final SaveManager SETTINGS = new SaveManager("settings.json");


    public static void main(String[] args) {
        window = new Window();

        LoginHandler.login();
    }

    public static Window getWindow() {
        return window;
    }

    public static void onLogout() {
        // TODO: 03.12.2021 Add logout to window
    }

    public static void toArray(ResultSet tables) {
    }
}
