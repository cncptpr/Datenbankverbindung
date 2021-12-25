package me.cncptpr.dbverbindung.core.events.defaults;

import java.util.LinkedList;
import java.util.List;

public abstract class EventHandler<L extends Listener<E>, E> {

    private final List<L> listeners = new LinkedList<>();

    public void register(L listener) {
        listeners.add(listener);
    }

    public void remove(L listener) {
        listeners.remove(listener);
    }

    public void call(E event) {
        for (L listener : listeners) {
            new Thread(() -> listener.run(event)).start();
        }
    }

}
