package me.cncptpr.dbverbindung.core;

public enum SQLType {

    SELECT("SELECT"),
    UPDATE("UPDATE"),
    INSERT("INSERT"),
    DELETE("DELETE");

    private final String regex;

    SQLType(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static SQLType getType(String sql) {
        String first = sql.split(" ")[0];
        for(SQLType type : SQLType.values())
            if (first.equalsIgnoreCase(type.getRegex()))
                return type;
        throw new Error("Couldn't find type of the SQL Command!");
    }
}
