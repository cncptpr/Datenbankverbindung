package me.cncptpr.dbverbindung.core.save;

import me.cncptpr.console.Console;

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
//        startUpdateLoop();
    }

    private boolean checkFile() {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String read() {
        try {
            Console.debug("Reading\n");
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String text) {
        try {
            Console.debug("Writing\n");
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

//    private void startUpdateLoop() {
//        // TODO: 17.07.2023 Consider Removing WatchService (not necessary to change the settings while program is running in file)
//        new Thread(() -> {
//            try {
//                WatchService watchService = path.getFileSystem().newWatchService();
//                WatchKey watchKey = getParentFolder(path).register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
//                while (listener != null) {
//                    for (WatchEvent<?> event : watchKey.pollEvents()) {
//                        File file = ((Path) event.context()).toFile();
//                        if (file.equals(new File(pathText))) {
//                            if(ignoreChange > 0) {
//                                ignoreChange--;
//                            } else {
//                                onUpdate();
//                                ignoreChange++;
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

//    private void onUpdate() {
//        listener.run();
//    }

    private Path getParentFolder(Path path) {
        String absolutePath = path.toFile().getAbsolutePath();
        int i = absolutePath.lastIndexOf("\\");
        String substring = absolutePath.substring(0, i);
        return Path.of(substring);
    }


}
