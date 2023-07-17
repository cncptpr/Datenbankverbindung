package me.cncptpr.dbverbindung.core;

import java.util.LinkedList;
import java.util.List;

public record ColumnInfo (String name) {

    public static ColumnInfo[] toColumnInfo(String[] names) {
        List<ColumnInfo> columnInfos = new LinkedList<>();
        for(String name : names) {
            columnInfos.add(new ColumnInfo(name));
        }
        return columnInfos.toArray(new ColumnInfo[0]);
    }

}
