package me.cncptpr.dbverbindung.save;

import me.cncptpr.dbverbindung.consol.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;

public class SaveFile {

    private final String pathText;
    private final Path path;
    private final File file;
    private Runnable listener;
    private int ignoreChange = 0;

    public SaveFile(String pathText, Runnable listener) {
        this.pathText = pathText;
        path = Path.of(pathText);
        file = new File(pathText);
        checkFile();
        this.listener = listener;
        startUpdateLoop();
    }

    private void checkFile() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        try {
            System.out.println(ConsoleColors.ANSI_GREEN + "Reading");
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String text) {
        try {
            System.out.println(ConsoleColors.ANSI_GREEN + "Writing");
            PrintWriter writer = new PrintWriter(file);
            writer.print(text);
            ignoreChange += 2;
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setListener(Runnable listener) {
        this.listener = listener;

    }

    private void startUpdateLoop() {
        new Thread(() -> {
            try {
                WatchService watchService = path.getFileSystem().newWatchService();
                WatchKey watchKey = getParentFolder(path).register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
                while (listener != null) {
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        File file = ((Path) event.context()).toFile();
                        if (file.equals(new File(pathText))) {
                            if(ignoreChange > 0) {
                                ignoreChange--;
                            } else {
                                onUpdate();
                                ignoreChange++;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void onUpdate() {
        listener.run();
    }

    private Path getParentFolder(Path path) {
        String absolutePath = path.toFile().getAbsolutePath();
        int i = absolutePath.lastIndexOf("\\");
        String substring = absolutePath.substring(0, i);
        return Path.of(substring);
    }


}
