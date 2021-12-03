package me.cncptpr.dbverbindung;

import me.cncptpr.dbverbindung.gui.Menu;
import me.cncptpr.dbverbindung.gui.Window;
import me.cncptpr.dbverbindung.handler.InfoHandler;
import me.cncptpr.dbverbindung.handler.LoginHandler;
import me.cncptpr.dbverbindung.listenersAndEvents.LoginEvent;
import me.cncptpr.dbverbindung.save.SaveManager;

import java.sql.ResultSet;

public class Main {

    private static Window window;
    public static SaveManager settings;


    public static void main(String[] args) {
        settings = new SaveManager("settings.json");
        window = new Window();
        LoginEvent.registerLoginListener(Main::onLoggedIn);
        LoginHandler.login();
    }

    public static void onLoggedIn(LoginEvent e) {
        window.setVisible(true);
        Menu.reset();
        Menu.show();
        new InfoHandler(Menu.getInstance());
        window.setLoggedIn(true);
    }

    public static void onChangeDatabase() {
        settings.set("database_current", Menu.getInstance().DBChooser_DropDown.getSelectedItem());
        Menu.getInstance().changeTab(Menu.SQLResult_Index);
    }

    public static Window getWindow() {
        return window;
    }

    public static void onLogout() {

    }

    public static void toArray(ResultSet tables) {
    }
}
