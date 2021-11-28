package me.cncptpr.dbverbindung.handler;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.Setting;
import me.cncptpr.dbverbindung.dbconnection.DBConnection;
import me.cncptpr.dbverbindung.gui.Menu;
import me.cncptpr.dbverbindung.listenersAndEvents.TabEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class InfoHandler {

    private JPanel tabelsInfo = new JPanel();
    private JPanel columsInfo = new JPanel();

    private String selectedTable;
    private String lastDatabase;


    public InfoHandler(Menu menu) {
        TabEvent.registerTabListener(this::tabChanged);
        menu.Info_Tables.setViewportView(tabelsInfo);
        menu.Info_Columns.setViewportView(columsInfo);

        tabelsInfo.setLayout(new BoxLayout(tabelsInfo, BoxLayout.PAGE_AXIS));
        tabelsInfo.setBorder(new EmptyBorder(5, 10, 5, 10));
        columsInfo.setLayout(new BoxLayout(columsInfo, BoxLayout.PAGE_AXIS));
        columsInfo.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    private void tabChanged(TabEvent e) {
        if(e.tabIndex == Menu.Info_Index) {
            tryUpdateInfo();
        }
    }

    private void tryUpdateInfo() {
        try {
            updateInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateInfo() throws SQLException {
        int scollDistanceTables = Menu.getInstance().Info_Tables.getVerticalScrollBar().getValue();
        int scollDistanceColums = Menu.getInstance().Info_Columns.getVerticalScrollBar().getValue();
        clearPanels();
        ResultSet tables = DBConnection.getTempConnection().getMetaData().getTables(null, null, "%", null);
        String[] tableNames = getTableNames(tables);
        if (!isSameDatabaseAsBefore())
            selectedTable = tableNames[0];
        printTableNames(tableNames);
        tryShowColumnsOfSelectedTable();
        Menu.getInstance().Info_Tables.getVerticalScrollBar().setValue(scollDistanceTables);
        Menu.getInstance().Info_Columns.getVerticalScrollBar().setValue(scollDistanceColums);
    }

    private void clearPanels() {
        tabelsInfo.removeAll();
        tabelsInfo.removeAll();
    }

    private void printTableNames(String[] tableNames) {
        printInfo(tabelsInfo, tableNames, true);
    }

    private void printColumnsNames(String[] columnNames) {
        columsInfo.removeAll();
        printInfo(columsInfo, columnNames, false);
    }

    private void printInfo(JPanel panel, String[] names, boolean addListener ) {
        for (String name : names) {
            panel.add(generateLabel(name, addListener));
        }
        Main.getWindow().repaint();
    }

    private String[] getTableNames(ResultSet tables) throws SQLException {
        ArrayList<String> tableNamesList = new ArrayList<>();
        while (tables.next()) {
            tableNamesList.add(tables.getString(3));
        }
        return tableNamesList.toArray(new String[0]);
    }

    private JLabel generateLabel(String text, boolean addListener) {
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(0, 0, 5, 0));
        label.setFont(new Font(null, Font.BOLD, 15));
        label.setBackground(new Color(165, 167, 172));
        if (addListener)
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1)
                        selectedTable = text;
                        tryShowColumnsOfSelectedTable();
                }
            });
        return label;
    }

    private void tryShowColumnsOfSelectedTable() {
        try {
            printColumnsNames(getColumnsNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] getColumnsNames() throws Exception {
        ResultSetMetaData metaData = getTableMetaData();
        ArrayList<String> names = getColumnsNames(metaData);
        return names.toArray(new String[0]);
    }

    private ArrayList<String> getColumnsNames(ResultSetMetaData metaData) throws SQLException {
        ArrayList<String> names = new ArrayList<>();
        for ( int i = 1; i <= metaData.getColumnCount(); i++) {
            names.add(metaData.getColumnName(i));
        }
        return names;
    }

    private ResultSetMetaData getTableMetaData() throws Exception {

        return SQLHandler.executeSQLSelect("SELECT * FROM " + selectedTable).getMetaData();
    }

    private boolean isSameDatabaseAsBefore() {
        if(Setting.getString("database_current").equals(lastDatabase))
            return true;
        lastDatabase = Setting.getString("database_current");
        return false;
    }

}
