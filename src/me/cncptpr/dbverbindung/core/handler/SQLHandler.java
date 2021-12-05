package me.cncptpr.dbverbindung.core.handler;

import me.cncptpr.dbverbindung.core.SQLType;
import me.cncptpr.dbverbindung.core.ResultTable;
import me.cncptpr.dbverbindung.core.dbconnection.DBConnection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import static me.cncptpr.dbverbindung.core.events.EventHandlers.SQL_RUN_EVENT;

public class SQLHandler {

    private SQLHandler() {

    }

    public static void tryRunSQL(String sql) {
        try {
            runSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void runSQL(String sql) throws SQLException {
        ResultSet resultSet = executeSQL(sql);
        HistoryHandler.addNewSQL(sql);
        SQL_RUN_EVENT.callAllListeners(convertToResultTable(resultSet, sql));
    }

    private static ResultSet executeSQL(String sql) throws SQLException {
        SQLType type = SQLType.getType(sql);
        if (type.equals(SQLType.SELECT))
            return executeSQLSelect(sql);
        else
            return executeSQLUpdate(sql);
    }

    private static ResultTable convertToResultTable(ResultSet resultSet, String sql) throws SQLException {
        String[] titles = getTitles(resultSet);
        String[][] content = getContent(resultSet, titles);
        return new ResultTable(titles, content, SQLType.getType(sql), sql, getSelectSQL(sql));
    }

    public static ResultSet executeSQLSelect(String sql) throws SQLException {
        return DBConnection.getTempConnection().prepareStatement(sql).executeQuery();
    }

    private static ResultSet executeSQLUpdate(String sql) throws SQLException {
        if(!isBadSQL(sql)) {
            DBConnection.getTempConnection().prepareStatement(sql).executeUpdate();
        }
        return executeSQLSelect(sql);
    }

    private static boolean isBadSQL(String sql) {
        return sql.contains("DELETE") && !sql.contains("WHERE");
    }

    private static String getSelectSQL(String sql) {
        SQLType type = SQLType.getType(sql);
        return switch (type) {
            case SELECT -> sql;
            case UPDATE -> getSelectSQLFromUpdate(sql);
            case INSERT -> getSelectSQLFromInsert(sql);
            case DELETE -> getSelectSQLFromDelete(sql);
        };
    }

    private static String getSelectSQLFromUpdate(String sql) {
        String table = sql.split(" ")[1];
        return "SELECT * FROM "+ table;
    }

    private static String getSelectSQLFromInsert(String sql) {
        String table = sql.split(" ")[2];
        return "SELECT * FROM "+ table;
    }

    private static String getSelectSQLFromDelete(String sql) {
        //is the same position im the syntax
        return getSelectSQLFromInsert(sql);
    }


    private static String[] getTitles(ResultSet result) throws SQLException {
        ResultSetMetaData meta = result.getMetaData();
        String[] titles = new String[meta.getColumnCount()];
        for (int i = 0; i < titles.length; i++)
            titles[i] = meta.getColumnName(i + 1);
        return titles;
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
}
