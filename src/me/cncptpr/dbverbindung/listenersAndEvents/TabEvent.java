package me.cncptpr.dbverbindung.listenersAndEvents;

import java.util.ArrayList;

public class TabEvent {

    private static ArrayList<TabListener> tabListeners = new ArrayList<>();

    public static void registerTabListener(TabListener l) {
        removeTabListener(l);
        tabListeners.add(l);
    }

    public static void removeTabListener(TabListener l) {
        tabListeners.remove(l);
    }

    public static void callTabListeners(int tab) {
        for (TabListener l : tabListeners)
            callTabListenerInNewThread(tab, l);
    }

    private static void callTabListenerInNewThread(int tab, TabListener l) {
        new Thread(() ->
                l.run(new TabEvent(tab))
        ).start();
    }

    public final int tabIndex;

    public TabEvent(int tabIndex) {
        this.tabIndex = tabIndex;
    }
}
