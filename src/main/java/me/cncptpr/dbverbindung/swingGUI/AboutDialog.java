package me.cncptpr.dbverbindung.swingGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AboutDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonClose;

    public AboutDialog(String title) {
        setTitle("About " + title);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonClose);

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onClose() {
        // add your code here if necessary
        dispose();
    }

    public static void showAboutDialog(JFrame frame) {
        AboutDialog dialog = new AboutDialog(frame.getTitle());
        dialog.pack();
        dialog.setSize(dialog.getSize().width, dialog.getSize().height + 100);
        Point center = new Point(frame.getX() + frame.getWidth()/2, frame.getY() + frame.getHeight()/2);
        dialog.setLocation(center.x - dialog.getWidth()/2, center.y - dialog.getHeight()/2);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}
