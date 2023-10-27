package me.cncptpr.dbverbindung.core.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.cncptpr.console.Console;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;


/**
 * A wrapper for the config file allowing to read from it and generate a default config file if empty or not created.
 */
public class Config {
    private Optional<SaveFile> file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    HashMap<String, Object> map;

    private static final String DATABASE = "database";
    private static final String IP = "ip";
    private static final String HISTORY_DIR = "history_dir";

    private static final String PATH = "config.json";

    public Config() {
        try {
            file = Optional.of(new SaveFile(PATH));
        } catch (IOException e) {
            file = Optional.empty();
            Path p = new File(PATH).toPath();
            Console.error("#red Config File could not be accessed#r, using default config.\n\tFile Path: %s", p.toAbsolutePath().toString());
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
