package me.cncptpr.dbverbindung.core.handler;

import me.cncptpr.console.Console;
import me.cncptpr.dbverbindung.core.ColumnInfo;
import me.cncptpr.dbverbindung.core.State;
import me.cncptpr.dbverbindung.core.TableInfo;
import me.cncptpr.dbverbindung.core.dbconnection.DBConnection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class InfoHandler {

    private static TableInfo[] lastInfo;

    private static String lastDatabase;

    public static TableInfo[] getInfo() {
        if (!isSameDatabaseAsBefore()) {
            try (DBConnection connection = new DBConnection()) {
                List<TableInfo> tableInfos = new LinkedList<>();

                String[] tableNames = getTableNames(connection.getMetaData());

                for (String tableName : tableNames) {
                    tableInfos.add(new TableInfo(tableName, getColumnInfos(tableName)));
                }

                lastInfo = tableInfos.toArray(new TableInfo[0]);
            } catch (SQLException e) {
                Console.error(e);
            }
        }
        return lastInfo;
    }


    public static String[] getTableNames(DatabaseMetaData metaData) throws SQLException {
        ResultSet tables = metaData.getTables(State.state().databaseCurrent(), null, "%", null);
        LinkedList<String> tableNamesList = new LinkedList<>();
        while (tables.next()) {
            tableNamesList.add(tables.getString(3));
        }
        return tableNamesList.toArray(new String[0]);
    }

    /**
     * Retrieves the metadata and gets information about the columns of the table.
     * See also {@link #getColumnsInfos(ResultSetMetaData)}
     * @param table The name of the table to get the Information from
     * @return An array with all the Information for each column
     * @throws SQLException if a database access error occurs or this method is called on a closed connection
     */
    private static ColumnInfo[] getColumnInfos(String table) throws SQLException {
        ResultSetMetaData metaData = SQLHandler.getTableMetaData(table);
        return getColumnsInfos(metaData);
    }

    /**
     * Reads the information (column name and column type) out of the Table Metadata
     * @param metaData The metadata of the table.
     *                 Get it with the {@link SQLHandler#getTableMetaData(String)} method
     *                 or use the {@link #getColumnInfos(String)} helper method.
     * @return An array with all the Information for each column
     * @throws SQLException if a database access error occurs
     */
    private static ColumnInfo[] getColumnsInfos(ResultSetMetaData metaData) throws SQLException {
        LinkedList<ColumnInfo> names = new LinkedList<>();
        for ( int i = 1; i <= metaData.getColumnCount(); i++) {
            names.add(new ColumnInfo(metaData.getColumnName(i), metaData.getColumnType(i)));
        }
        return names.toArray(new ColumnInfo[0]);
    }

    private static boolean isSameDatabaseAsBefore() {
        if(State.state().databaseCurrent().equals(lastDatabase))
            return true;
        lastDatabase = State.state().databaseCurrent();
        return false;
    }

}
