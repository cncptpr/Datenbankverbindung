package me.cncptpr.dbverbindung.gui;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.handler.SQLHandler;
import me.cncptpr.dbverbindung.dbconnection.DBConnection;
import me.cncptpr.dbverbindung.listenersAndEvents.TabEvent;
import me.cncptpr.dbverbindung.listenersAndEvents.TabListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.util.ArrayList;

public class Menu {

    private static Menu instance = new Menu();

    //Listeners
    private ArrayList<TabListener> tabListeners = new ArrayList();

    //SQL Result
    public static final int SQLResult_Index = 0;
    public JTextField SQLResult_Input;
    public JTable SQLResult_Table;
    public JButton SQLResult_SendButton;

    //SQL Editor
    public static final int SQLEditor_Index = 1;
    public JPanel SQLEditor_history;
    public JTextArea SQLEditor_Input;
    public JButton SQLEditor_SendButton;

    //Info
    public static final int Info_Index = 3;
    public JScrollPane Info_Tables;
    public JScrollPane Info_Columns;

    //Database Chooser
    public static final int DBChooser_Index = 4;
    public JComboBox<String> DBChooser_DropDown;
    public JButton DBChooser_ChooseButton;
    public JButton DBChooser_LogoutButton;

    //Menu

    public JTabbedPane Menu_TabbedPane;
    public JPanel MainPanel;

    //History
    public static final int History_Index = 2;
    public JScrollPane History_ScrollPane;

    public static Menu getInstance() {
        return instance;
    }

    public static void reset() {
        instance = new Menu();
    }

    public static void show() {
        Main.getWindow().setContentPane(getInstance().MainPanel);
    }

    private Menu() {
        SQLResult_SendButton.addActionListener(e -> SQLHandler.tryRunAndShowSQL(SQLResult_Input.getText()));
        SQLEditor_SendButton.addActionListener(e -> SQLHandler.tryRunAndShowSQL(SQLEditor_Input.getText()));
        SQLResult_Input.addActionListener(e -> SQLHandler.tryRunAndShowSQL(SQLResult_Input.getText()));

        DBChooser_DropDown.removeAllItems();
        for(String database : DBConnection.tryGetAllDatabases()) {
            DBChooser_DropDown.addItem(database);
        }
        DBChooser_DropDown.setSelectedItem(Main.settings.getString("database_current"));
        DBChooser_DropDown.addActionListener(e -> Main.onChangeDatabase());
        DBChooser_LogoutButton.addActionListener(e -> Main.onLogout());

        Menu_TabbedPane.addChangeListener(this::tabChanged);

        changeTab(DBChooser_Index);
    }

    private void tabChanged(ChangeEvent changeEvent) {
        TabEvent.callTabListeners(Menu_TabbedPane.getSelectedIndex());
    }

    public void changeTab(int i) {
        TabEvent.callTabListeners(i);
        Menu_TabbedPane.setSelectedIndex(i);
    }

    public Container getMainPanel() {
        return MainPanel;
    }


}
