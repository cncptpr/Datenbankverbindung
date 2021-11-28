package me.cncptpr.dbverbindung.gui;

import me.cncptpr.dbverbindung.HistoryEntrance;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel {
    private JPanel buttonPanel;
    private JButton executeButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton priorityButton;
    private JScrollPane scrollPanel;
    private final JPanel contentPanel = new JPanel();
    private JPanel mainPanel;

    public HistoryPanel(HistoryEntrance entrance) {
        executeButton.addActionListener(entrance::onExecute);
        removeButton.addActionListener(entrance::onRemove);
        editButton.addActionListener(entrance::onEdit);
        priorityButton.addActionListener(entrance::onPrioritise);
        scrollPanel.setViewportView(contentPanel);
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

    public void setColor(Color color) {
        mainPanel.setBackground(color);
    }
}
