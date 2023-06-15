package me.cncptpr.dbverbindung.core.dbconnection;


import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.console.Console;

import javax.swing.Timer;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DBConnection {

    private static String[] allDatabases = null;

    public static Connection getTempConnection() {
        Connection connection = tryConnect();
        new Timer(100, e -> close(connection));
        return connection;
    }

    private static Connection tryConnect() {
        try {
            return connect();
        } catch (NoSuchElementException e) {
            System.out.println(Console.RED + "[Error] Not all information for login provided.");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    private static Connection connect() throws SQLException {
        String ip = Main.SETTINGS.getString("ip");
        String database = Main.SETTINGS.getString("database_current");
        String username = Main.SETTINGS.getString("username");
        String password = Main.SETTINGS.getString("password");
        String url = String.format("jdbc:mysql://%s/%s?user=%s&password=&%s", ip, database, username, password);
        return DriverManager.getConnection(url);
    }

    private static void close(Connection connection) {
        try {
            connection.close();
        } catch (Exception ignore) {

        }
    }

    public static String[] tryGetAllDatabases() {
        try {
            allDatabases = getAllDatabases(getTempConnection());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allDatabases;
    }

    private static String[] getAllDatabases(Connection connection) throws SQLException {
        ResultSet result = connection.getMetaData().getCatalogs();
        ArrayList<String> databases = new ArrayList<>();
        while (result.next()) {
            databases.add(result.getString("TABLE_CAT"));
        }
        result.close();
        return databases.toArray(new String[0]);
    }

    public static boolean canConnect() {
        return getTempConnection() != null;
    }
}

