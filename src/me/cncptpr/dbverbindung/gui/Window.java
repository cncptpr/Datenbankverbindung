package me.cncptpr.dbverbindung.gui;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.handler.LoginHandler;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class Window extends JFrame{

    public Window() {
        super("SQL Interface");
        setLookAndFeel();
        setup();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        if (isLoggedIn)
            showMenuPanel();
        //else
        //    showLoginPanel();
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
        setSize(950, 700 + getInsets().top);
        setResizable(true);
        setContentPane(Menu.getInstance().getMainPanel());
        repaint();
    }

    private void showLoginPanel() {
        LoginHandler.showLoginPanel(this);
    }
}
