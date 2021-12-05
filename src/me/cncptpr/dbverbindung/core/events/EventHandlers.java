package me.cncptpr.dbverbindung.core.events;

import me.cncptpr.dbverbindung.core.events.editSQLEvent.SQLEditEventHandler;
import me.cncptpr.dbverbindung.core.events.loginEvent.LoginEventHandler;
import me.cncptpr.dbverbindung.core.events.logoutEvent.LogoutEventHandler;
import me.cncptpr.dbverbindung.core.events.sqlRunEvent.SQLRunEventHandler;

public class EventHandlers {

    public static final LoginEventHandler LOGIN_EVENT = new LoginEventHandler();
    public static final LogoutEventHandler LOGOUT_EVENT = new LogoutEventHandler();

    public static final SQLRunEventHandler SQL_RUN_EVENT = new SQLRunEventHandler();
    public static final SQLEditEventHandler SQL_EDIT_EVENT = new SQLEditEventHandler();

}
