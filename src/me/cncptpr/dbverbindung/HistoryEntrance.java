package me.cncptpr.dbverbindung;

import me.cncptpr.dbverbindung.gui.HistoryPanel;
import me.cncptpr.dbverbindung.gui.Menu;
import me.cncptpr.dbverbindung.handler.HistoryHandler;
import me.cncptpr.dbverbindung.handler.SQLHandler;

import java.awt.event.ActionEvent;

public class HistoryEntrance {

    private final HistoryPanel historyPanel = new HistoryPanel(this);
    private final String sql;
    private boolean prioritised = false;

    public HistoryEntrance(String sql) {
        this(sql, false);
    }

    public HistoryEntrance(String sql, boolean prioritised) {
        this.sql = sql;
        this.prioritised = prioritised;
        historyPanel.setText(sql);
    }

    public void onExecute(ActionEvent e) {
        Menu.getInstance().SQLResult_Input.setText(sql);
        SQLHandler.tryRunAndShowSQL(sql);
    }

    public void onEdit(ActionEvent e) {
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
}
