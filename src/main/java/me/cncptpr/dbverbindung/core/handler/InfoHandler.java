package me.cncptpr.dbverbindung.core.handler;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.core.ColumnInfo;
import me.cncptpr.dbverbindung.core.TableInfo;
import me.cncptpr.dbverbindung.core.dbconnection.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class InfoHandler {

//    private JPanel tabelsInfo = new JPanel();
//    private JPanel columnsInfo = new JPanel();
    private static TableInfo[] lastInfo;

    private static String lastDatabase;


    public InfoHandler() {
//        TabEvent.registerTabListener(this::tabChanged);
//        menu.Info_Tables.setViewportView(tabelsInfo);
//        menu.Info_Columns.setViewportView(columsInfo);

        /*
        tabelsInfo.setLayout(new BoxLayout(tabelsInfo, BoxLayout.PAGE_AXIS));
        tabelsInfo.setBorder(new EmptyBorder(5, 10, 5, 10));
        columnsInfo.setLayout(new BoxLayout(columnsInfo, BoxLayout.PAGE_AXIS));
        columnsInfo.setBorder(new EmptyBorder(5, 10, 5, 10));
        */
    }

    public static TableInfo[] getInfo() {
        if (!isSameDatabaseAsBefore()) {
            try (DBConnection connection = new DBConnection()) {
                List<TableInfo> tableInfos = new LinkedList<>();

                ResultSet tables = connection.getMetaData().getTables(Main.SETTINGS.getString("database_current"), null, "%", null);
                String[] tableNames = getTableNames(tables);

                for (String tableName : tableNames) {
                    System.out.println(tableName);
                    tableInfos.add(new TableInfo(tableName, ColumnInfo.toColumnInfo(getColumnsNames(tableName))));
                }

                lastInfo = tableInfos.toArray(new TableInfo[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lastInfo;
    }

    private static void clearPanels() {
//        tabelsInfo.removeAll();
//        tabelsInfo.removeAll();
    }

    private static void printTableNames(String[] tableNames) {
//        printInfo(tabelsInfo, tableNames, true);
    }

    private static void printColumnsNames(String[] columnNames) {
//        columsInfo.removeAll();
//        printInfo(columsInfo, columnNames, false);
    }

    private static void printInfo(JPanel panel, String[] names, boolean addListener ) {
        for (String name : names) {
            panel.add(generateLabel(name, addListener));
        }
        Main.getWindow().repaint();
    }

    private static String[] getTableNames(ResultSet tables) throws SQLException {
        LinkedList<String> tableNamesList = new LinkedList<>();
        while (tables.next()) {
            tableNamesList.add(tables.getString(3));
        }
        return tableNamesList.toArray(new String[0]);
    }

    private static JLabel generateLabel(String text, boolean addListener) {
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(0, 0, 5, 0));
        label.setFont(new Font(null, Font.BOLD, 15));
        label.setBackground(new Color(165, 167, 172));
        if (addListener)
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1)
//                        selectedTable = text;
                        tryShowColumnsOfSelectedTable();
                }
            });
        return label;
    }

    private static void tryShowColumnsOfSelectedTable() {
        try {
//            printColumnsNames(getColumnsNames(selectedTable));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String[] getColumnsNames(String table) throws SQLException {
        ResultSetMetaData metaData = SQLHandler.getTableMetaData(table);
        LinkedList<String> names = getColumnsNames(metaData);
        return names.toArray(new String[0]);
    }

    private static LinkedList<String> getColumnsNames(ResultSetMetaData metaData) throws SQLException {
        LinkedList<String> names = new LinkedList<>();
        for ( int i = 1; i <= metaData.getColumnCount(); i++) {
            names.add(metaData.getColumnName(i));
        }
        return names;
    }

    private static boolean isSameDatabaseAsBefore() {
        if(Main.SETTINGS.getString("database_current").equals(lastDatabase))
            return true;
        lastDatabase = Main.SETTINGS.getString("database_current");
        return false;
    }

}