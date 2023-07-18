package me.cncptpr.dbverbindung.swingGUI;

import me.cncptpr.dbverbindung.Main;
import me.cncptpr.dbverbindung.core.dbconnection.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static me.cncptpr.dbverbindung.Main.SETTINGS;
import static me.cncptpr.dbverbindung.core.events.EventHandlers.LOGIN_EVENT;

/**
 * This Class contains the logic for the Login Screen and reads and writes the credentials to the Settings file.
 */
public class LoginPanel {
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JTextField databaseTextField;
    private JTextField ipTextField;
    private JButton loginButton;
    private JPanel mainPanel;


    public LoginPanel() {
        loginButton.addActionListener(this::OnLoginClicked);
        usernameTextField.addActionListener(e -> passwordTextField.grabFocus());
        usernameTextField.addActionListener(e -> passwordTextField.grabFocus());
        passwordTextField.addActionListener(e -> databaseTextField.grabFocus());
        databaseTextField.addActionListener(e -> ipTextField.grabFocus());
        ipTextField.addActionListener(this::OnLoginClicked);
        usernameTextField.grabFocus();
    }

    public void OnLoginClicked(ActionEvent ignored) {
        Main.SETTINGS.set("username", getUsername());
        Main.SETTINGS.set("password", getPassword());
        Main.SETTINGS.set("database_current", getDatabase());
        Main.SETTINGS.set("database_login", getDatabase());
        Main.SETTINGS.set("ip", getIP());
        resetLoginButton();
        if (DBConnection.canConnect())
            LOGIN_EVENT.call(getUsername(), getDatabase(), getIP());
        else
            setLoginButtonToError();
    }

    public Container getMainPanel() {
        return mainPanel;
    }

    private String getUsername() {
        return usernameTextField.getText();
    }
    private String getPassword() {
        return passwordTextField.getText();
    }
    private String getDatabase() {
        return databaseTextField.getText();
    }
    private String getIP() {
        return ipTextField.getText();
    }

    /**
     * Fills the forum with the information in the settings file
     */
    public void fill() {
        usernameTextField.setText(SETTINGS.getString("username"));
        passwordTextField.setText(SETTINGS.getString("password"));
        databaseTextField.setText(SETTINGS.getString("database_login"));
        ipTextField.setText(SETTINGS.getString("ip"));
    }

    /**
     * When a login error occurs the look and text of the login button is changed for visual feedback.
     * The button is automatically reset after three seconds via the {@link #resetLoginButton()} method.
     */
    private void setLoginButtonToError() {
        loginButton.setBackground(new Color(94, 7, 16));
        loginButton.setForeground(Color.WHITE);
        loginButton.setText("Fehler beim Anmelden");
        new Thread(() -> {
            try {
                Thread.sleep(3*1000);
                resetLoginButton();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * Resets the button (from e.g. it's error state) to the normal button.
     * See also {@link #setLoginButtonToError()}
     */
    private void resetLoginButton() {
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        loginButton.setText("Login");
    }
}
