package me.cncptpr.dbverbindung.swingGUI;

import me.cncptpr.dbverbindung.console.Console;
import me.cncptpr.dbverbindung.core.ColumnInfo;
import me.cncptpr.dbverbindung.core.HistoryEntrance;
import me.cncptpr.dbverbindung.core.ResultTable;
import me.cncptpr.dbverbindung.core.TableInfo;
import me.cncptpr.dbverbindung.core.dbconnection.DBConnection;
import me.cncptpr.dbverbindung.core.events.sqlRunEvent.SQLRunEvent;
import me.cncptpr.dbverbindung.core.handler.HistoryHandler;
import me.cncptpr.dbverbindung.core.handler.InfoHandler;
import me.cncptpr.dbverbindung.core.handler.SQLHandler;
import org.intellij.lang.annotations.MagicConstant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static me.cncptpr.dbverbindung.core.events.EventHandlers.*;

import static me.cncptpr.dbverbindung.Main.SETTINGS;

public class MainMenu {

    //SQL Result
    public static final int SQLResult_Index = 0;
    public JTextField SQLResult_Input;
    public JTable SQLResult_Table;
    public JButton SQLResult_SendButton;

    //SQL Editor
    public static final int SQLEditor_Index = 1;
    public JPanel SQLEditor_history;
    public JTextArea SQLEditor_Input;
    public JButton SQLEditor_SendButton;

    //Info
    public static final int Info_Index = 3;
    public JScrollPane Info_Tables;
    public JScrollPane Info_Columns;
    private String selectedTable;

    //Database Chooser
    public static final int DBChooser_Index = 4;
    public JComboBox<String> DBChooser_DropDown;
    public JButton DBChooser_ChooseButton;
    public JButton DBChooser_LogoutButton;

    //Menu

    public JTabbedPane Menu_TabbedPane;
    public JPanel MainPanel;

    //History
    public static final int History_Index = 2;
    public JScrollPane History_ScrollPane;
    public JPanel History_Panel = new JPanel();

    public MainMenu() {

        //=========================================== register to listeners ==========================================//
        SQL_EDIT_EVENT.register(this::editSQL);
        SQL_RUN_EVENT.register(this::showSQL);

        //================================================= Run SQL ==================================================//
        SQLResult_SendButton.addActionListener(e -> SQLHandler.tryRunSQL(SQLResult_Input.getText()));
        SQLEditor_SendButton.addActionListener(e -> SQLHandler.tryRunSQL(SQLEditor_Input.getText()));
        SQLResult_Input.addActionListener(e -> SQLHandler.tryRunSQL(SQLResult_Input.getText()));


        //========================================= Fill DBChooser Drop Down =========================================//
        DBChooser_DropDown.removeAllItems();
        for(String database : DBConnection.tryGetAllDatabases()) {
            DBChooser_DropDown.addItem(database);
        }
        DBChooser_DropDown.setSelectedItem(SETTINGS.getString("database_current"));
        DBChooser_DropDown.addActionListener(e -> {
            SETTINGS.set("database_current", DBChooser_DropDown.getSelectedItem());
            changeTab(SQLEditor_Index);
        });

        //============================================== Logout Listener =============================================//
        DBChooser_LogoutButton.addActionListener(e -> LOGOUT_EVENT.call());

        Menu_TabbedPane.addChangeListener(this::tabChanged);

        History_Panel.setLayout(new BoxLayout(History_Panel, BoxLayout.PAGE_AXIS));
        History_ScrollPane.setViewportView(History_Panel);

        changeTab(DBChooser_Index);
    }

    private void tabChanged(ChangeEvent changeEvent) {
        update();
    }

    public void update() {
        int tab = Menu_TabbedPane.getSelectedIndex();
        switch (tab) {
            case SQLResult_Index -> updateSQLResult();
            case SQLEditor_Index -> updateSQLEditor();
            case History_Index -> updateHistory();
            case Info_Index -> updateInfo();
            case DBChooser_Index -> updateDBChooser();
        }
    }


    private void updateSQLResult() {
        // TODO: 05.12.2021 If empty run default select
    }

    private void updateSQLEditor() {

    }

    private void updateHistory() {
        History_Panel.removeAll();
        for (HistoryEntrance item : HistoryHandler.getItems()) {
            if(item.isPrioritised())
                History_Panel.add(new HistoryPanel(item).getMainPanel());
        }
        for (HistoryEntrance item : HistoryHandler.getItems()) {
            if(!item.isPrioritised())
                History_Panel.add(new HistoryPanel(item).getMainPanel());
        }
    }

    private void updateInfo() {
        JPanel tablePanel = new JPanel();
        JPanel columnPanel = new JPanel();

        Info_Tables.setViewportView(tablePanel);
        Info_Columns.setViewportView(columnPanel);

        TableInfo[] info = InfoHandler.getInfo();

        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));
        tablePanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.PAGE_AXIS));
        columnPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        boolean tableSelected = false;
        for (TableInfo table : info){
            tablePanel.add(generateLabel(table.name(), true));
            if (table.name().equalsIgnoreCase(selectedTable)) {
                tableSelected = true;
                for (ColumnInfo columnInfo : table.columns()) {
                    columnPanel.add(generateLabel(columnInfo.name(), false));
                }
            }
        }
        if (!tableSelected) {
            for (ColumnInfo columnInfo : info[0].columns()) {
                columnPanel.add(generateLabel(columnInfo.name(), false));
            }
        }
    }

    private void updateDBChooser() {
    }

    private void editSQL(String sql) {
        SQLEditor_Input.setText(sql);
        changeTab(SQLEditor_Index);
    }

    public void changeTab(int i) {
        Menu_TabbedPane.setSelectedIndex(i);
    }

    public Container getMainPanel() {
        return MainPanel;
    }



    public void runSQL(String sql) {
        SQLHandler.tryRunSQL(sql);
    }

    public void showSQL(SQLRunEvent e) {
        changeTab(0);
        Console.test("Showing SQL");
        ResultTable result = e.resultTable();
        SQLResult_Input.setText(result.originalSQL().replaceAll("\n", " "));
        SQLResult_Table.setModel(new DefaultTableModel(result.content(), result.titles()));
    }

    private JLabel generateLabel(String text, boolean addListener) {
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(0, 0, 5, 0));
        label.setFont(new Font(null, Font.BOLD, 15));
        label.setBackground(new Color(165, 167, 172));
        if (addListener)
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1)
                        selectedTable = text;
                    updateInfo();
                }
            });
        return label;
    }


}
