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

    public Config() {
        // I can't figure out how to work with paths ...
        // But this takes the path of the Jar file, moves one directory up,
        // converts it to a String so that the name of the config file can be appended
        // and then makes a Path again.
        Path path = new File(
                new File(
                        // (1) Get path from the Jar file inside the installation directory (as String)
                        //     And yes getPath() returns a String
                        Config.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                // (2) Get the Parent (as Path)
                ).toPath().getParent()
                        // (3) Append the file name (as String)
                        .toString() + "/config.json"
        // (4) Final conversion (to Path)
        ).toPath();
        // This code hurts, but I can't find out how to append to a path object

        try {
            file = Optional.of(new SaveFile(path));
        } catch (IOException e) {
            file = Optional.empty();
            String absolutePath = path.toAbsolutePath().toString();
            Console.error("#red Config File could not be accessed#r, using default config.\n\tFile Path: %s", absolutePath);
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
