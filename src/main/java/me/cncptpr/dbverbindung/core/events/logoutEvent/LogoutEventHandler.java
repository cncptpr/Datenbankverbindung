package me.cncptpr.dbverbindung.core.events.logoutEvent;

import me.cncptpr.dbverbindung.core.events.defaults.EventHandler;
import me.cncptpr.dbverbindung.core.events.defaults.Listener;

public class LogoutEventHandler extends EventHandler<Listener<LogoutEvent>, LogoutEvent> {
    public void call() {
        call(new LogoutEvent());
    }
}
