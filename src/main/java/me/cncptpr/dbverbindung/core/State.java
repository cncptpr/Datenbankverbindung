package me.cncptpr.dbverbindung.core;

import me.cncptpr.dbverbindung.core.save.Config;

// Stores the State of the current Project
public class State {
    
    private static State state;

    public static State state() {
        return state;
    }

    public static void setState(State state) {
        State.state = state;
    }

    public static void from(Config config) {
        state = new State(config.getIP(), config.getDatabase(), config.getHistoryDir());
    }
    private String ip;
    private String password;
    private String username;
    private String databaseConfig;
    private String databaseCurrent;
    private String historyDir;

    public State(String ip, String databaseConfig, String historyDir) {
        this.ip = ip;
        this.databaseConfig = databaseConfig;
        this.historyDir = historyDir;
    }

    public String ip() {
        return ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public String password() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String username() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String databaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(String databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public String databaseCurrent() {
        return databaseCurrent;
    }

    public void setDatabaseCurrent(String databaseCurrent) {
        this.databaseCurrent = databaseCurrent;
    }

    public String historyDir() {
        return historyDir;
    }

    public void setHistoryDir(String historyDir) {
        this.historyDir = historyDir;
    }
}
