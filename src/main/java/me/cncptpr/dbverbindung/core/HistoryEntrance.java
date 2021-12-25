package me.cncptpr.dbverbindung.core;

public class HistoryEntrance {

    private final String sql;
    private boolean prioritised = false;

    public HistoryEntrance(String sql) {
        this(sql, false);
    }

    public HistoryEntrance(String sql, boolean prioritised) {
        this.sql = sql;
        this.prioritised = prioritised;
    }

    public void prioritise() {
        prioritised = true;
    }

    public void prioritise(boolean prioritised) {
        this.prioritised = prioritised;
    }

    public void unprioritise() {
        prioritised = false;
    }

    public String getSql() {
        return sql;
    }

    public boolean isPrioritised() {
        return prioritised;
    }

    public boolean matchesSql(String sql) {
        return sql.equalsIgnoreCase(this.sql);
    }

    /*
    public void onExecute(ActionEvent e) {
        SQLHandler.tryRunSQL(sql);
    }

    public void onEdit(ActionEvent e) {
        EventHandlers.SQL_EDIT.call(sql);
        Menu.getInstance().SQLEditor_Input.setText(sql);
        Menu.getInstance().changeTab(Menu.SQLEditor_Index);
    }

    public void onRemove(ActionEvent e) {
        HistoryHandler.remove(this);
        HistoryHandler.update();
    }

    public void onPrioritise(ActionEvent e) {
        if (prioritised)
            HistoryHandler.unpriorities(this);
        else
            HistoryHandler.priorities(this);
        prioritised = !prioritised;
        HistoryHandler.update();
    }

    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }

    public boolean matchesSql(String sql){
        return this.sql.equalsIgnoreCase(sql);
    }

     */
}
