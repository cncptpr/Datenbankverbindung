package me.cncptpr.dbverbindung.core.save;

import me.cncptpr.console.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;

public class SaveFile {

    private final Path path;
    private final File file;

    public SaveFile(Path path) throws IOException {
        this.path = path;
        file = path.toFile();
        boolean ignored = file.createNewFile();
    }

    public String read() {
        try {
            Console.debug("Reading\n");
            return Files.readString(path);
        } catch (IOException e) {
            Console.error(e);
        }
        return null;
    }

    public void write(String text) {
        try {
            Console.debug("Writing\n");
            PrintWriter writer = new PrintWriter(file);
            writer.print(text);
            writer.close();
        } catch (FileNotFoundException e) {
            Console.error(e);
        }
    }


}
