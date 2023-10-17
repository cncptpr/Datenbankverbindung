package me.cncptpr.dbverbindung.swingGUI;

import me.cncptpr.dbverbindung.core.events.loginEvent.LoginEvent;
import me.cncptpr.dbverbindung.core.events.logoutEvent.LogoutEvent;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

import static me.cncptpr.dbverbindung.core.events.EventHandlers.LOGIN_EVENT;
import static me.cncptpr.dbverbindung.core.events.EventHandlers.LOGOUT_EVENT;


/**
 * The window class handles the window and it's content.
 * It changes between login screen and the main menu.
 */
public class Window extends JFrame{

    private final String title;
    private MainMenu mainMenu;
    private LoginPanel loginPanel;

    public Window(String title) {
        super(title);
        this.title = title;
        setLookAndFeel();
        setup();
        LOGIN_EVENT.register(this::onLogin);
        LOGOUT_EVENT.register(this::onLogout);
    }

    private void onLogout(LogoutEvent logoutEvent) {
        showLoginPanel();
        setVisible(true);
    }

    private void onLogin(LoginEvent loginEvent) {
        showMenuPanel();
        setVisible(true);
    }

    private void setup() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(200, 400);
        setLocation(200, 200);
        setDefaultLookAndFeelDecorated(true);
    }

    private void setLookAndFeel() {
        try {

            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMenuPanel() {
        loginPanel = null;
        mainMenu = new MainMenu(this);
        setSize(950, 700 + getInsets().top);
        setResizable(true);
        setContentPane(mainMenu.getMainPanel());
        repaint();
    }

    private void showLoginPanel() {
        mainMenu = null;
        loginPanel = new LoginPanel();
        loginPanel.fill();
        setResizable(false);
        setContentPane(loginPanel.getMainPanel());
        pack();
        repaint();
    }
}
