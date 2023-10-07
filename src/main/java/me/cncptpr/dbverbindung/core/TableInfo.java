package me.cncptpr.dbverbindung.core;


public record TableInfo(String name, ColumnInfo[] columns ) {

    public String summary() {
        String[] columnNames = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnNames[i] = columns[i].name();
        }
        return name + " (" + String.join(", ", columnNames) + ")";
    }

}
