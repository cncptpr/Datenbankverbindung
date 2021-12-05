package me.cncptpr.dbverbindung.core.save;

import com.google.gson.Gson;

import java.util.HashMap;

public class SaveManager {
    // TODO: 28.11.2021 Rename class

    private final SaveFile file;
    private final Gson gson = new Gson();
    HashMap<String, Object> map = new HashMap<>();

    public SaveManager (String path) {
        file = new SaveFile(path, this::load);
        load();
    }

    public void set(String key, Object value) {
        map.put(key, value);
        save();
    }

    public Object get(String key) {
        if (map == null) {
            System.out.println("Map empty");
            load();
        }
        return map.get(key);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    private void save() {
        file.write(gson.toJson(map));
    }

    private void load() {
         map = gson.fromJson(file.read(), HashMap.class);
      }

}
