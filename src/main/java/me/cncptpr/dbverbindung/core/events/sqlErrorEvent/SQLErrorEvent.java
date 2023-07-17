package me.cncptpr.dbverbindung.core.events.sqlErrorEvent;

public record SQLErrorEvent (String sql, String error ){}
