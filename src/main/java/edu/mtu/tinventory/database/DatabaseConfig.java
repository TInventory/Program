package edu.mtu.tinventory.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.LocalUtils;

/**
 * 
 * @author JC Helm
 * @since 10/12/2017
 * 
 *        Used to access local file I/O stuff to store local database
 *        configuration data and logs
 */

public class DatabaseConfig {

    LocalUtils utils = new LocalUtils();
    private File file;
    private File directory;
    private String executableDirectory;
    private String jvmVersion;
    private String user;
    private String directoryName;

    /**
     * Inner Enum Class
     * 
     * Convince holder for file names of each used file.
     */
    private enum FileName {

        SQL_CONNECTION_FILE("SQLConnections"),
        LOCAL_LOG("LocalLog"),
        TESTING_LOCAL_LOG("TESTING_LocalLog"),
        TESTING_SQL_CONNECTION_FILE("TESTING_SQLConnections");

        private String fileName;

        FileName(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String toString() {
            return fileName;
        }

    }

    /**
     * Retrieves the File and its options
     * 
     * @return instance of this.class for this file
     */
    public static DatabaseConfig getSQLConfig() {
        return SQL_Config;
    }

    /**
     * Retrieves the File and its options
     * 
     * @return instance of this.class for this file
     */
    public static DatabaseConfig getLocalLogs() {
        return Local_Logs;
    }

    /**
     * Retrieves the File and its options
     * 
     * @return instance of this.class for this file
     */
    public static DatabaseConfig getDebugSQLConfig() {
        return TESTING_SQL_CONFIG;
    }

    /**
     * Retrieves the File and its options
     * 
     * @return instance of this.class for this file
     */
    public static DatabaseConfig getDebugLocalLog() {
        return TESTING_LOCAL_LOG;
    }

    /**
     * Seperate instances of DatabaseConfig for each file
     */
    private static final DatabaseConfig
    // These are equivalent to separate variables
    SQL_Config = new DatabaseConfig(FileName.SQL_CONNECTION_FILE), 
    Local_Logs = new DatabaseConfig(FileName.LOCAL_LOG),
    TESTING_SQL_CONFIG = new DatabaseConfig(FileName.TESTING_SQL_CONNECTION_FILE),
    TESTING_LOCAL_LOG = new DatabaseConfig(FileName.TESTING_LOCAL_LOG);

    /**
     * Constructor for config file class/classes
     * 
     * @param filename
     *            Filename of the class to be created
     */
    private DatabaseConfig(FileName filename) {
        // Assign global variables
        this.executableDirectory = System.getProperty("user.dir");
        this.jvmVersion = System.getProperty("java.version");
        this.user = System.getProperty("user.name");
        this.directoryName = "TInventory";

        // If prereqs exist
        if (makeDirectory()) {
            // New instance of the filename
            file = new File(getDirectory().getPath() + filename + ".data");

            // Try to create the new file
            try {
                // Creates the file if it does not exist otherwise just passes
                // on as true
                file.createNewFile();
            }
            // Catch exceptions
            catch (IOException exception) {
                LocalLog.exception("Failed to create new file " + filename + ".data", exception);
            }

        }
    }

    /**
     * Returns version of Java the system is running
     * 
     * @return The version of Java the system is running
     */
    public String getJVMVersion() {
        return jvmVersion;
    }

    /**
     * Retrieves all lines of the current file
     * 
     * @return List<String> Of individual lines of the file
     */
    public List<String> readFile() {
        // List to hold things
        ArrayList<String> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();
            // While there is more to read
            while (line != null) {
                // TODO: Here will be decryption
                list.add(line);
                line = reader.readLine();
            }
            // Close file
            reader.close();
        } catch (Exception exception) {

            LocalLog.exception(exception);
        }

        return list;

    }

    /**
     * Retrieves the configurations from the file
     * Only intended to be used with the SQL_CONNECTION_FILE
     * 
     * @return A HashMap of keys with their values of configuration options
     */
    public HashMap<String, String> getConfigurations() {
        // Map holder
        HashMap<String, String> map = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                // TODO: decrpytion goes here
                // Might use another character
                String[] options = line.split(":");
                map.put(options[0], options[1]);
                // To next line
                line = reader.readLine();
            }
            // Close file
            reader.close();
        } catch (Exception exception) {
            LocalLog.exception(exception);
        }
        return map;
    }

    /**
     * Inserts
     * 
     * @param text
     *            String: text to be inserted into the file
     * @return Returns true if successful, false otherwise
     */
    public boolean write(String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(text);
            // Maybe there is a more efficient way to do this for log, maybe
            // only write every so often?
            writer.close();
        } catch (Exception exception) {
            LocalLog.exception("File " + file.getName() + " could not be saved.", exception);
            return false;
        }
        return true;
    }

    /**
     * Retrieves the current TInventory directory folder
     * 
     * @return
     */
    private File getDirectory() {
        return directory;
    }

    /**
     * makeDirectory()
     * 
     * @param filePath
     * @return Returns true if the directory now exists or already exists, false
     *         if it does not exist
     */
    private boolean makeDirectory() {
        // Retrieves URI of where we plan to place the files based on the OS
        URI uri = getDirectoryPath();
        // Retrieve the original file/directory that was found, and make sure it
        // exists
        File priorDirectory = new File(uri);
        // Name of the directory we are trying to make / ensure exists
        // global variable initialized here
        this.directory = new File(uri + directoryName);

        // Ensure the File (Really FilePath) is a directory , should always be.
        if (priorDirectory.isDirectory()) {
            if (!directory.exists()) {
                // If directory is made successfully
                // method handles both creation and check
                if (directory.mkdir()) {
                    LocalLog.info("Local storage directory successfully created at \"" + directory.toURI() + "\"!");
                    // Directory now exists
                    return true;
                } else {
                    LocalLog.error("Local storage directory failed to be created at \"" + directory.toURI() + "\"!");
                    // Directory still does not exist
                    return false;
                }
            } else {
                // The Directory already existed
                return true;
            }
        }
        // Should not actually happen, if it does we need to do more debugging
        return false;
    }

    /**
     * Retrieves the URI of where to create the directory of files for
     * TInventory
     * 
     * @return The URI parent of the TInventory folder
     * 
     */
    private URI getDirectoryPath() {
        String uri;
        switch (utils.getOperatingSystem()) {
        case WINDOWS:
            uri = String.format(System.getenv("ProgramFiles"), user);
            break;
        case MAC_OS:
            uri = String.format("/Users/%s/Library", user);
            break;
        case LINUX:
            uri = String.format("/usr/share/", user);
            break;
        case SOLARIS:
            uri = String.format("/usr/share/", user);
            break;
        default:
            uri = executableDirectory;
            break;
        }
        // Convert string to URI
        try {
            return new URI(uri);
        } catch (URISyntaxException exception) {
            LocalLog.exception(exception);
            return null;
        }
    }
}
