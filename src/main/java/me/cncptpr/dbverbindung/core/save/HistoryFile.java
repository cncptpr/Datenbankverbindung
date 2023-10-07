package me.cncptpr.dbverbindung.core.save;


import me.cncptpr.console.Console;
import me.cncptpr.dbverbindung.core.HistoryEntrance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;


// TODO: 07.10.2023 Documentation
public class HistoryFile {

    PrintWriter writer;

    public HistoryFile() throws IOException {
        this("");
    }

    public HistoryFile(String path) throws IOException {
        new File(path).mkdirs();
        Date date = new Date(Calendar.getInstance().getTime().getTime());

        String formatPath = formatPath(path, "history", date);
        writer = new PrintWriter(new FileOutputStream(formatPath, true));
        registerFile(formatPath);
    }

    public void append(HistoryEntrance history) {
        append(history.render());
    }

    public void append(String history) {
        writer.println(history);
        writer.flush();
    }

    /**
     * Test for the History File Class
     */
    public static void main(String[] agrs) throws IOException {
        HistoryFile file = new HistoryFile();
        file.append(new HistoryEntrance("test1"));
        file.append(new HistoryEntrance("test2"));
        file.append(new HistoryEntrance("test3"));
        file.append(new HistoryEntrance("test4"));
        HistoryFile file2 = new HistoryFile();
        file2.append(new HistoryEntrance("test5"));
        file2.append(new HistoryEntrance("test6"));
        file2.append(new HistoryEntrance("test7"));
        file2.append(new HistoryEntrance("test8"));
    }

    /**
     * Assembles the file path from following arguments
     * @param path to the folder of the file location
     * @param name used for the file
     * @param date on which the file was used (current date)
     * @return Path for the file as a String
     */
    private static String formatPath(String path, String name, Date date) {
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd").format(date);
        return formatPath(path, name) + "_" + timeStamp + ".txt";
    }

    private static String formatPath(String path, String name) {
        boolean doNotAddSlash = path.endsWith("/") || path.endsWith("\\") || path.isEmpty();
        return path + (doNotAddSlash?"":"/") + name;
    }

    private static void registerFile(String filePath) throws IOException {
        String parent = new File(filePath).getParent();
        parent = parent==null ? "" : (parent + "/");
        File registryPath = new File(formatPath(parent, ".history"));
        registryPath.createNewFile();

        List<String> registeredFiles = Files.readAllLines(registryPath.toPath());
        registeredFiles.remove(filePath);
        registeredFiles.add(filePath);
        Files.write(registryPath.toPath(), registeredFiles, StandardOpenOption.WRITE);
    }

    /**
     * Reads the contents of the last History file in order to be displayed again.
     * @param folderPath Path to the folder containing all the history files
     * @return The contest of the file in one String
     * @throws IOException
     */
    public static String readLastHistoryFile(String folderPath) throws IOException {
        File registryPath = new File(formatPath(folderPath, ".history"));
        List<String> files = Files.readAllLines(registryPath.toPath());

        String lastFile = read(files.getLast());
        if (lastFile.isEmpty() && files.removeLast() != null && !files.isEmpty())
            return read(files.getLast());
        return lastFile;
    }

    private static String read(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
    }

    public static Optional<HistoryFile> newAsOptional(String path) {
        try {
            return Optional.of(new HistoryFile(path));
        } catch (IOException e) {
            Console.debug("Can't create new #red History File!\n");
            Console.error(e);
            return Optional.empty();
        }
    }
}
