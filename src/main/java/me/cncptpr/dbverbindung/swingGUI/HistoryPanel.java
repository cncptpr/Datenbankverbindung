package me.cncptpr.dbverbindung.swingGUI;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.core.HistoryEntrance;
import me.cncptpr.dbverbindung.core.events.EventHandlers;
import me.cncptpr.dbverbindung.core.handler.HistoryHandler;
import me.cncptpr.dbverbindung.core.handler.SQLHandler;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel {
    private JButton executeButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton priorityButton;
    private JScrollPane scrollPanel;
    private JPanel mainPanel;

    private final JPanel contentPanel = new JPanel();

    public HistoryPanel(HistoryEntrance entrance) {
        scrollPanel.setViewportView(contentPanel);
        setText(entrance.getSql());
        priorityButton.setText(entrance.isPrioritised()?"Lösen":"Anpinnen");

        // TODO: 05.12.2021 update History Panel
        executeButton.addActionListener(e -> SQLHandler.tryRunSQL(entrance.getSql()));
        removeButton.addActionListener(e -> {
            HistoryHandler.remove(entrance);
            Main.getWindow().update();
        });
        editButton.addActionListener(e -> EventHandlers.SQL_EDIT_EVENT.call(entrance.getSql()));
        priorityButton.addActionListener(e -> {
            entrance.prioritise(!entrance.isPrioritised());
            priorityButton.setText(entrance.isPrioritised()?"Lösen":"Anpinnen");
            Main.getWindow().update();
        });
    }

    public void setText(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            JLabel label = new JLabel(line);
            label.setBackground(Color.RED);
            contentPanel.add(label);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
