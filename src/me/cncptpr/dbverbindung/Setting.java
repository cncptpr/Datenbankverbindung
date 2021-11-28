package me.cncptpr.dbverbindung;

import me.cncptpr.dbverbindung.handler.FileHandler;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Setting {

    private static ArrayList<Setting> settings;

    static {
        settings = FileHandler.readSettings();
    }

    public static void set(Setting setting) {
        removeSetting(setting.tag);
        settings.add(setting);
        //FileHandler.saveSettings(settings);
    }

    public static void setString(String tag, String string){
        set(new Setting(tag, string));
    }

    public static boolean removeSetting(String tag) {
        boolean removed = false;
        for (Setting setting : settings.toArray(new Setting[0])) {
            if (tag.equals(setting.getTag())) {
                removeSetting(setting);
                removed = true;
            }
        }
        return removed;
    }

    public static boolean removeSetting(Setting setting) {
        return settings.remove(setting);
    }

    public static Setting get(String tag) {
        for(Setting setting : settings) {
            if(tag.equals(setting.getTag()))
                return setting;
        }
        throw new NoSuchElementException("There was no setting with name: " + tag);
    }

    public static String getString(String tag) {
        return (String) get(tag).value;
    }




    private String tag;
    private Object value;

    public Setting(String tag, Object value) {
        this.tag = tag;
        this.value = value;
    }

    public String getTag(){
        return tag;
    };

    public Object getValue() {
        return value;
    }

}
