package me.cncptpr.dbverbindung.core.events.logoutEvent;

import me.cncptpr.dbverbindung.core.events.defaults.EventHandler;

public class LogoutEventHandler extends EventHandler<LogoutListener, LogoutEvent> {
    public void call() {
        call(new LogoutEvent());
    }
}
