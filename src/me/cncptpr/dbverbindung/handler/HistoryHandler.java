package me.cncptpr.dbverbindung.handler;

import me.cncptpr.dbverbindung.HistoryEntrance;
import me.cncptpr.dbverbindung.gui.Menu;
import me.cncptpr.dbverbindung.listenersAndEvents.TabEvent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistoryHandler {

    private static final ArrayList<HistoryEntrance> prioritisedItems = new ArrayList<>();
    private static final ArrayList<HistoryEntrance> normalItems = new ArrayList<>();
    private static final JPanel historyPanel = new JPanel();


    private HistoryHandler() {}

    static {
        Menu.getInstance().History_ScrollPane.setViewportView(historyPanel);
        TabEvent.registerTabListener(HistoryHandler::tabChanged);
    }

    private static void tabChanged(TabEvent tabEvent) {
        if (tabEvent.tabIndex == Menu.History_Index)
            update();
    }

    /**
     * Adds the given SQL command. Panel will be updated automatically. If SQL Command has already been added, it will just update its position.
     * @param sql to be displayed
     */
    public static void addNewSQL(String sql) {
        if (removeFromList(sql, prioritisedItems)) {
            prioritisedItems.add(new HistoryEntrance(sql, true));
            return;
        }
        removeFromList(sql, normalItems);
        normalItems.add(new HistoryEntrance(sql));
    }

    /**
     * Prioritised / Pins given {@code HistoryEntrance}.
     * @param historyEntrance to be prioritised
     * @return {@code true} if {@code HistoryEntrance} could be prioritised
     */
    public static boolean priorities(HistoryEntrance historyEntrance) {
        if (normalItems.remove(historyEntrance)) {
            prioritisedItems.add(historyEntrance);
            return true;
        }
        return false;
    }

    /**
     * Unprioritised / Unpins given {@code HistoryEntrance}.
     * @param historyEntrance to be unprioritised
     * @return {@code true} if {@code HistoryEntrance} could be unprioritised
     */
    public static boolean unpriorities(HistoryEntrance historyEntrance) {
        if (prioritisedItems.remove(historyEntrance)) {
            normalItems.add(historyEntrance);
            return true;
        }
        return false;
    }

    /**
     * Removes given {@code HistoryEntrance}
     * @param historyEntrance to be removed
     * @return {@code true} if {@code HistoryEntrance} could be removed
     */
    public static boolean remove(HistoryEntrance historyEntrance) {
        return removeFromList(historyEntrance, normalItems) || removeFromList(historyEntrance, prioritisedItems);
    }

    public static boolean removeFromList(HistoryEntrance historyEntrance, ArrayList<HistoryEntrance> list) {
        if (list.remove(historyEntrance)){
            return true;
        }
        return false;
    }

    /**
     * Removes {@code HistoryEntrance} given with given SQL Command
     * @param sql to be removed
     * @return {@code true} if {@code HistoryEntrance} could be removed
     */
    public static boolean remove(String sql) {
        return removeFromList(sql, normalItems) || removeFromList(sql, prioritisedItems);
    }

    public static boolean removeFromList(String sql, ArrayList<HistoryEntrance> list) {
        boolean removed = false;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).matchesSql(sql))
                removed = list.remove(list.get(i));
        return removed;
    }


    /**
     * Displays all added {@code HistoryEntrance}.
     */
    public static void update() {
        historyPanel.removeAll();
        historyPanel.setLayout(new GridLayout(getGridRowCount(), 2, 20, 20));
        normalItems.forEach(historyEntrance -> historyPanel.add(historyEntrance.getHistoryPanel().getMainPanel()));
        prioritisedItems.forEach(historyEntrance -> historyPanel.add(historyEntrance.getHistoryPanel().getMainPanel()));
        historyPanel.repaint();
        int max = Menu.getInstance().History_ScrollPane.getHorizontalScrollBar().getMaximum();
        Menu.getInstance().History_ScrollPane.getHorizontalScrollBar().setValue(max);
    }

    private static int getGridRowCount() {
        int r = normalItems.size() + prioritisedItems.size();
        if (r%2 != 0)
            r++;

        return r/2;
    }



}
