package me.cncptpr.dbverbindung.handler;

import me.cncptpr.dbverbindung.dbconnection.DBConnection;
import me.cncptpr.dbverbindung.gui.Menu;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLHandler {

    private SQLHandler() {

    }

    public static boolean tryRunAndShowSQL(String sql) {
        try {
            runAndShowSQL(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void runAndShowSQL(String sql) throws SQLException {
        ResultSet resultSet;
        if (sql.startsWith("SELECT"))
            resultSet = executeSQLSelect(sql);
        else
            resultSet = executeSQLUpdate(sql);
        showSQLResult(resultSet);
        HistoryHandler.addNewSQL(sql);
    }

    public static ResultSet executeSQLSelect(String sql) throws SQLException {
        return DBConnection.getTempConnection().prepareStatement(sql).executeQuery();
    }

    private static ResultSet executeSQLUpdate(String sql) throws SQLException {
        DBConnection.getTempConnection().prepareStatement(sql).executeUpdate();
        return DBConnection.getTempConnection().prepareStatement(getSelectSQL(sql)).executeQuery();
    }

    private static String getSelectSQL(String sql) {
        if(sql.startsWith("UPDATE"))
            return getSelectSQLFromUpdate(sql);
        else if (sql.startsWith("INSERT"))
            return getSelectSQLFromInsert(sql);
        return null;
    }

    private static String getSelectSQLFromUpdate(String sql) {
        String table = sql.split(" ")[1];
        return "SELECT * FROM "+ table;
    }

    private static String getSelectSQLFromInsert(String sql) {
        String table = sql.split(" ")[2];
        return "SELECT * FROM "+ table;
    }

    private static void showSQLResult(ResultSet resultSet) {
        tryListSQLResult(resultSet);
        Menu.getInstance().changeTab(Menu.SQLResult_Index);
    }

    private static void tryListSQLResult(ResultSet resultSet) {
        try {
            listSQLResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listSQLResult(ResultSet resultSet) throws SQLException {
        String[] titles = getTitles(resultSet.getMetaData());
        String[][] content = getContent(resultSet, titles);
        writeTable(titles, content);
    }


    private static String[] getTitles(ResultSetMetaData meta) throws SQLException {
        String[] titles = new String[meta.getColumnCount()];
        for (int i = 0; i < titles.length; i++)
            titles[i] = meta.getColumnName(i + 1);
        return titles;
    }

    private static void writeTable(String[] titles, String[][] content) {
        Menu.getInstance().SQLResult_Table.setModel(new DefaultTableModel(content, titles));
    }

    private static String[][] getContent(ResultSet result, String[] titles) throws SQLException {
        ArrayList<String[]> content = new ArrayList<>();
        while (result.next()) {
            String[] rowContent = new String[titles.length];
            for (int i = 0; i < titles.length; i++)
                rowContent[i] = result.getString(titles[i]);
            content.add(rowContent);
        }

        return content.toArray(new String[0][0]);
    }

    private static int getSize(ResultSet result) throws SQLException {

        int rowcount = 0;
        if (result.last()) {
            rowcount = result.getRow();
            result.beforeFirst();
        }
        return rowcount;
    }
}
