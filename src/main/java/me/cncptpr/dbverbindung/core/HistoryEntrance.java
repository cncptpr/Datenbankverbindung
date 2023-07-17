package me.cncptpr.dbverbindung.core;

public record HistoryEntrance (String sql, String error){

    private static final String NEW_LINE = "\n";

    public HistoryEntrance(String sql) {
        this(sql, null);
    }

    public String render() {
        StringBuilder builder = new StringBuilder();
        builder.append(sql)
                .append(NEW_LINE)
                .append("------------")
                .append(NEW_LINE);
        if (error == null) {
            builder.append("Erfolgreich ausgeführt!");
        } else {
            builder.append("Fehler: ")
                    .append(error);
        }
        builder.append(NEW_LINE)
                .append("=================")
                .append(NEW_LINE)
                .append(NEW_LINE);
        return builder.toString();
    }
}
