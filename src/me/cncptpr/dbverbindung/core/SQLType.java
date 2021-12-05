package me.cncptpr.dbverbindung.core;

public enum SQLType {

    SELECT("SELECT"),
    UPDATE("UPDATE"),
    INSERT("INSERT"),
    DELETE("DELETE");

    private final String command;

    SQLType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static SQLType getType(String sql) {
        String first = sql.split(" ")[0];
        for(SQLType type : SQLType.values())
            if (first.equalsIgnoreCase(type.getCommand()))
                return type;
        throw new Error("Couldn't find type of the SQL Command!");
    }
}
