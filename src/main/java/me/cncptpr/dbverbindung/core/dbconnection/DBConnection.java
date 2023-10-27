package me.cncptpr.dbverbindung.core.dbconnection;


import me.cncptpr.console.Console;
import me.cncptpr.dbverbindung.core.State;

import java.sql.*;
import java.util.ArrayList;

import static me.cncptpr.dbverbindung.core.State.state;

public class DBConnection implements AutoCloseable {

    public static boolean canConnect() {
        try (DBConnection ignored = new DBConnection()){
            return true;
        } catch (SQLException er) {
            if (er.getMessage().contains("No suitable driver found")) {
                Console.debug("Der scheiß #r #red JDBC Driver #r geht schon wieder nicht!!!!!!!!!!!!\n");
            }
            return false;
        }
    }

    public static boolean isJDBCDriverThere() {
        try (DBConnection ignored = new DBConnection()){
            return true;
        } catch (SQLException er) {
            if (er.getMessage().contains("No suitable driver found")) {
                Console.info("Der scheiß #r #red JDBC Driver #r geht schon wieder nicht!!!!!!!!!!!!\n");
                return false;
            }
            return true;
        }
    }

    private final Connection connection;

    public DBConnection() throws SQLException {
        State state = state();
        String url = String.format("jdbc:mysql://%s/%s", state.ip(), state.databaseCurrent());
        connection = DriverManager.getConnection(url, state.username(), state.password());
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ignored) {}
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return connection.getMetaData();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public String[] getAllDatabases() throws SQLException {
        ResultSet result = connection.getMetaData().getCatalogs();
        ArrayList<String> databases = new ArrayList<>();
        while (result.next()) {
            databases.add(result.getString("TABLE_CAT"));
        }
        result.close();
        return databases.toArray(new String[0]);
    }
}

