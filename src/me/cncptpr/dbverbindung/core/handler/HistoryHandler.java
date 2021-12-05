package me.cncptpr.dbverbindung.core.handler;

import me.cncptpr.dbverbindung.core.HistoryEntrance;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler {

    private static final List<HistoryEntrance> items = new ArrayList<>();


    private HistoryHandler() {}

    public static List<HistoryEntrance> getItems() {
        return items;
    }

    public static void addNewSQL(String sql) {
        remove(sql);
        items.add(new HistoryEntrance(sql));
    }

    public static void priorities(HistoryEntrance historyEntrance) {
        historyEntrance.prioritise();
    }

    public static void unpriorities(HistoryEntrance historyEntrance) {
        historyEntrance.unprioritise();
    }

    public static boolean remove(HistoryEntrance historyEntrance) {
        return removeFromList(historyEntrance);
    }

    public static boolean removeFromList(HistoryEntrance historyEntrance) {
        return items.remove(historyEntrance);
    }

    public static boolean remove(String sql) {
        boolean removed = false;
        for (int i = 0; i < items.size(); i++)
            if (items.get(i).matchesSql(sql))
                removed = items.remove(items.get(i));
        return removed;
    }

/*
    public static void update() {
        historyPanel.removeAll();
        historyPanel.setLayout(new GridLayout(getGridRowCount(), 2, 20, 20));
        items.forEach(historyEntrance -> historyPanel.add(historyEntrance.getHistoryPanel().getMainPanel()));
        prioritisedItems.forEach(historyEntrance -> historyPanel.add(historyEntrance.getHistoryPanel().getMainPanel()));
        historyPanel.repaint();
    }

    private static int getGridRowCount() {
        int r = items.size() + prioritisedItems.size();
        if (r%2 != 0)
            r++;

        return r/2;
    }


    public static JPanel getHistoryPanel() {
        return historyPanel;
    }

 */
}
