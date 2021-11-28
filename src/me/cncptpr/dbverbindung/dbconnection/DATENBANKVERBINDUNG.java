package me.cncptpr.dbverbindung.dbconnection;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.mysql.jdbc.*;

/**
 * Stellt die Methoden zum Zugriff auf die Datenbank zur Verfügung.
 * 
 * @author Albert Wiedemann
 * @version 16.06.06
 */
class DATENBANKVERBINDUNG {
    /** Speichert die Verbindung zur Datenbank. */
    private Connection connection;

    /**
     * Constructor for objects of class DATENBANKVERBINDUNG
     */
    DATENBANKVERBINDUNG() {
        try {
            // Lädt den Treiber für die Datenbankverbindung
            // Class.forName("com.mysql.jdbc.Driver").newInstance(); //Nicht mehr nötig bei neuerem Java!
            // Öffent die Verbindung, alle Daten sind über die URL angegeben
            // Protokoll (jdbc:mysql): Der JDBC-Treiber für MySQL wird verwendet
            // Rechner/Pfad (localhost/bankverwaltung): Der Sever läuft auf dem lokalen Rechner, die DB heißt bankverwaltung
            // Parameter: Geben Benutzer und Passwort an.
            //connection = DriverManager.getConnection("jdbc:mysql://192.168.3.3/DatenbankBSP?user=Grueschow.Timo&password=wx7JU2");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root");

            System.out.println("Verbindung aufgebaut.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Beendet die Verbindung, danach ist Schluss.
     */
    void VerbindungBeenden() {
        try {
            connection.close();
            System.out.println("Verbindung beendet.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Holt die Liste der Kunden aus der Datenbank.
     */
    void kundenHolen() {
        try (Statement st = connection.createStatement()){
            
            //Setzt die Anfrage ab, auf die Ergebnistabelle kann mit rs zugegriffen werden
            ResultSet rs = st.executeQuery("SELECT name, pin FROM person WHERE klasse='k'");
            System.out.println("Kundenliste");
            //Geht die Ergebnistabelle Zeile für Zeile durch
            while (rs.next()) {
                //rs. get... holt die Spaltenwerte der aktuellen Zeile
                System.out.println("Name = " + rs.getString("name") + ", PIN = " + rs.getString("pin"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Holt die Liste der Konten aus der Datenbank.
     */
    void sparKontenHolen() {
        //SParkonten holen und ausgeben: (vergleiche mit oben)
        //Anleitung:
        //1. Statement erstellen 
        //2.ResultSet holen
        //3. Solange Result noch einen eintrag hat (next()!!!)
        // Ausgeben:
        //System. out. println ("Nummer = " + rs. getString ("nummer") + ", Kontostand = " + rs. getString ("kontostand") +
        //                      ", Eigentümer = " + rs. getString ("eigentuemer") + ", Zinssatz = " + rs. getString ("zinssatz"));

        //Schleiße rs und Schließe Statement!

        //try catch nicht vergessen!
    }

    /**
     * Speichert den neuen Angestellten in der Datenbank.
     * Die Eindeutigkeit des Namens muss überprüft sein.
     * @param a der zu speichernde Angestellte
     */
    void NeuenAngestelltenEinfuegen(String name, int pin) {
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO person (name, pin, klasse) VALUES ('" + name + "', '" + pin + "', 'a')");
            st.close();

            System.out.println("Neuer Angestellter eingefügt: " + name + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void NeuenKundenEinfuegen(String name, int pin) {
        try {
            System.out.println("Neuer Kunde eingefügt: " + name + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setzt eine neue PIN für den Kunden.
     * @param name Name des Kunden
     * @param nummer die neue PIN
     */
    void PinSetzen(String name, int nummer) {
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE `person` SET `pin`=? WHERE `name`='" + name + "'");
            st.setInt(1, nummer);
            st.executeUpdate();
            st.close();
            System.out.println("PIN gesetzt für Person: " + name + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Speichert das neue Sparkonto in der Datenbank.

     */
    void NeuesSparkontoEinfuegen() {

    }

    /**
     * Speichert das neue Girokonto in der Datenbank.

     */
    void NeuesGirokontoEinfuegen() {

    }

    /**
     * Löscht das angegebene Konto aus der Datenbank.
     * Der Kontostand wird als "0" erwartet.
     * @param konto das zu löschende Konto
     */
    void KontoLoeschen() {}

    /**
     * Löscht die angegebene Person aus der Datenbank.
     * Bei Kunden muss überprüft sein, dass der Kunde keine Konten mehr hat.
     */
    void PersonLoeschen(String name) {

    }

    /**
     * Setzt den Kontostand für das angegebene Konto.
     * Ist bei jeder Kontobewegung aufzurufen.
     */
    void KontostandSetzen(int kontoNummer, double differenz, String beschreibung) {

    }

    /**
     * Ermittelt die maximal bisher vergebene Kontonummer.
     * @return maximal vergebene Kontonummer
     */
    int MaxKontonummerGeben() {
        return 0;
    }

    /**
     * Gibt alle verschuldeten Konten aus.
     */
    void VerschuldeteKontenAngeben() {

    }

}