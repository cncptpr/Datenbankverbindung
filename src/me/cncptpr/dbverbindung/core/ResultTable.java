package me.cncptpr.dbverbindung.core;

import me.cncptpr.dbverbindung.SQLType;

public record ResultTable(String[] titles, String[][] content, SQLType type, String originalSQL, String selectSQL) {

}
