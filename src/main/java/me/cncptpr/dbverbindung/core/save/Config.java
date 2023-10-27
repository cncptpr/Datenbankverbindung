package me.cncptpr.dbverbindung.core.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.cncptpr.console.Console;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class Config {
    private Optional<SaveFile> file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    HashMap<String, Object> map;

    private static final String DATABASE = "database";
    private static final String IP = "ip";
    private static final String HISTORY_DIR = "history_dir";

    public Config() {
        try {
            file = Optional.of(new SaveFile("config.json"));
        } catch (IOException e) {
            file = Optional.empty();
        }
        map = defaultSettings();
        load();
    }

    public String getIP() {
        return getString(IP);
    }

    public String getHistoryDir() {
        return getString(HISTORY_DIR);
    }

    public String getDatabase() {
        return getString(DATABASE);
    }

    private Object get(String key) {
        if (map == null) {
            Console.info("Map empty\n");
            load();
        }
        return map.get(key);
    }

    private String getString(String key) {
        return (String) get(key);
    }

    private void save() {
        file.ifPresent(file -> file.write(gson.toJson(map)));
    }

    private void load() {
        file.ifPresent(file -> map = gson.fromJson(file.read(), HashMap.class));
        if (map == null) {
            map = defaultSettings();
            save();
        }
    }

    private HashMap<String, Object> defaultSettings() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(DATABASE, "");
        map.put(IP, "");
        map.put(HISTORY_DIR, "");
        return map;
    }
}
