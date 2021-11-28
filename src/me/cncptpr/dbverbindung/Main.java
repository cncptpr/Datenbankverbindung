package me.cncptpr.dbverbindung;

import me.cncptpr.dbverbindung.gui.Menu;
import me.cncptpr.dbverbindung.gui.Window;
import me.cncptpr.dbverbindung.handler.InfoHandler;
import me.cncptpr.dbverbindung.handler.LoginHandler;
import me.cncptpr.dbverbindung.listenersAndEvents.LoginEvent;

import java.sql.ResultSet;

public class Main {

    private static Window window;


    public static void main(String[] args) {
        window = new Window();
        LoginEvent.registerLoginListener(Main::onLoggedIn);
        LoginHandler.login();
    }

    public static void onLoggedIn(LoginEvent e) {
        Menu.reset();
        Menu.show();
        new InfoHandler(Menu.getInstance());
        window.setLoggedIn(true);
    }

    public static void onChangeDatabase() {
        Setting.setString("database_current", (String) Menu.getInstance().DBChooser_DropDown.getSelectedItem());
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
