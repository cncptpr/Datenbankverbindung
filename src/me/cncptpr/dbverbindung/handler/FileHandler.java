package me.cncptpr.dbverbindung.handler;

import com.google.gson.Gson;
import me.cncptpr.dbverbindung.Setting;

import java.awt.datatransfer.FlavorEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

    private static final Gson gson = new Gson();
    private static final String SETTINGS_PATH = "settings.json";

    private FileHandler() {};

    public static boolean saveSettings(ArrayList<Setting> settings) {
        File file = new File(SETTINGS_PATH);
        String json = gson.toJson(settings);
        if (tryCreateFileIfNotExist(file))
            return tryPrint(file, json);
        return false;
    }

    private static boolean tryPrint(File file, String content) {
        try {
            print(file, content);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void print(File file, String content) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.print(content);
        writer.close();
    }

    private static boolean tryCreateFileIfNotExist(File file) {
        if (file.exists())
            return true;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Setting> readSettings() {
        File file = new File(SETTINGS_PATH);
        if (file.exists()) {
            String json = tryRead(file);
            return (ArrayList<Setting>) gson.fromJson(json, ArrayList.class);
        }
        return new ArrayList<>();
    }

    private static String tryRead(File file) {
        try {
            read(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String read(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder json = new StringBuilder();
        while (scanner.hasNext())
            json.append(scanner.next()).append("/n");
        if (json.toString().equals(""))
            return null;
        return json.toString();
    }



}
