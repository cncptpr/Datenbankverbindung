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

/**
 * {@link HistoryFile} handles the reading, writing and organisation of the sql history.
 * For each day (in the planned use case the is only one session per day) a new file containing the history is created.
 * Multiple creations per day will append to the same file.
 */
public class HistoryFile {

    PrintWriter writer;

    /**
     * Construct a {@link HistoryFile} object that is operating in a given directory.
     * Multiple file will be created over time, so this directory should not be a user directory, but it can be the same one used for the settings.
     * All required folders will be created by this object.
     * @param path The path to the history directory
     * @throws IOException when unable to create or access the required folder or files
     */
    public HistoryFile(String path) throws IOException {
        boolean ignored = new File(path).mkdirs();
        Date date = new Date(Calendar.getInstance().getTime().getTime());

        String formatPath = formatPath(path, "history", date);
        writer = new PrintWriter(new FileOutputStream(formatPath, true));
        registerFile(formatPath);
    }

    /**
     * Appends the rendered {@link HistoryEntrance} to the end of the current file history file.
     * @param history The {@link HistoryEntrance} to be rendered and appended.
     */
    public void append(HistoryEntrance history) {
        append(history.render());
    }

    /**
     * Appends a {@link String} to the end of the current file history file.
     * This {@link String} should be the output of {@link HistoryEntrance#render()}.
     * @param history The {@link String} to be appended.
     */
    public void append(String history) {
        writer.println(history);
        writer.flush();
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

    /**
     * Assembles the file path from following arguments
     * @param path to the folder of the file location
     * @param name used for the file
     * @return Path for the file as a String
     */
    private static String formatPath(String path, String name) {
        boolean doNotAddSlash = path.endsWith("/") || path.endsWith("\\") || path.isEmpty();
        return path + (doNotAddSlash?"":"/") + name;
    }

    /**
     * Registers a new file in order to keep better track of all the history file.
     * @param filePath the path to the file itself (not the directory)
     * @throws IOException when unable to access or create the required files
     */
    private static void registerFile(String filePath) throws IOException {
        String parent = new File(filePath).getParent();
        parent = parent==null ? "" : (parent + "/");
        File registryPath = new File(formatPath(parent, ".history"));
        boolean ignored = registryPath.createNewFile();

        List<String> registeredFiles = Files.readAllLines(registryPath.toPath());
        registeredFiles.remove(filePath);
        registeredFiles.add(filePath);
        Files.write(registryPath.toPath(), registeredFiles, StandardOpenOption.WRITE);
    }

    /**
     * Reads the contents of the last History file in order to be displayed again.
     * The last History file is the one of the day before until there are entrances to the current file,
     * then the last file is the current one.
     * @param folderPath Path to the folder containing all the history files
     * @return The contest of the file in one String
     * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read
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

    /**
     * A helper method to wrap a {@link HistoryFile} into an {@link Optional}
     * and set it to empty when an {@link IOException} occurs.
     * This is done because the History File is not essential to the Programm
     * and to force checks whether it failed to construct.
     * @param path The path to the history directory
     * @return {@link Optional} of a {@link HistoryFile} or a empty one when there is an error
     */
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
