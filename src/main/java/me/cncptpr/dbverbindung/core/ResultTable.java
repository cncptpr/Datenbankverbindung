package me.cncptpr.dbverbindung.core;

public record ResultTable(String[] titles, String[][] content, SQLType type, String originalSQL, String selectSQL) {

}
