package edu.mtu.tinventory.util;

import edu.mtu.tinventory.TInventory;
import edu.mtu.tinventory.database.Configurations;
import edu.mtu.tinventory.database.DatabaseConfig;
import edu.mtu.tinventory.database.MySQL;
import edu.mtu.tinventory.database.query.StatusUpdater;
import edu.mtu.tinventory.database.query.queries.InfoStreams;
import edu.mtu.tinventory.logging.LocalLog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Class full of utilities to make encoding and decoding database stuff
 *        easier
 */
public class DatabaseUtils {
    
    private static final Pattern COMPILE = Pattern.compile("-", Pattern.LITERAL);
    public static char[] allowed_chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * Creates a new blank instance of InfoStream
     * 
     * @return InfoStream : ByteArrayOutputStream : new blank instance
     */
    public static InfoStreams newOutputStream() {
        return new InfoStreams(new ByteArrayOutputStream());
    }

    /**
     * Converts an array of bytes into a DataInputStream
     * 
     * @param data
     *            byte array to be converted
     * @return an instance of DataInputStream made from data
     */
    public static DataInputStream newInputStream(byte[] data) {
        return new DataInputStream(new ByteArrayInputStream(data));
    }

    /**
     * Converts a ResultSet into a mutable List<Object>
     * 
     * @param set
     *            ResultSet to be converted in a List<Object>
     * @return An instance of List<Objects> of the ResultSet
     */
    public static List<Object> getDataList(ResultSet set) {
        List<Object> data = new ArrayList<>();

        try {
            if (set.first()) {
                int count = set.getMetaData().getColumnCount();
                for (int i = 1; i <= count; i++) {
                    data.add(set.getObject(i));
                }
            }
        } catch (Exception e) {
            LocalLog.exception(e);
        }

        return data;
    }

    /**
     * Strips item of the hyphens in its UUID to become more workable, may not
     * be used depending on how we encode unique IDs
     * 
     * @param uuid
     *            Unique ID to be stripped of hyphens
     * @return Returns a String version of the UUID without hyphens
     */
    public String strip(UUID uuid) {
        return COMPILE.matcher(uuid.toString()).replaceAll("");
    }

    /**
     * Takes a resulting set and turns it into a very mutable and efficient
     * HashMap. Can be used with Product, but left open to allow for other data
     * stored.
     * 
     * @param resultingSet
     *            The resulting set to have data extracted from
     * @return Returns an instance of HashMap<String, Object>
     * @throws SQLException
     *             An exception that provides information on a database access
     *             error or other errors.
     */
    public static ArrayList<HashMap<String, Object>> getData(ResultSet resultingSet) throws SQLException {
        // Grab the useful data of the resulting set
        ResultSetMetaData meta = resultingSet.getMetaData();
        int columns = meta.getColumnCount();

        // Iterates through and inserts data into map
        // new map to store each row
        ArrayList<HashMap<String, Object>> mapping = new ArrayList<HashMap<String, Object>>();
        // Actually is rowNumber - 1 because SQL starts at 1.
        while (resultingSet.next()) {
            // Inner
            HashMap<String, Object> row = new HashMap<String, Object>();
            for (int i = 1; i <= columns; i++) {
                // Inserts the data with the String as the column name , and
                // Object as the data under the column
                row.put(meta.getColumnName(i), resultingSet.getObject(i));
            }
            mapping.add(row);
        }
        // Return the hashmap with the data
        return mapping;
    }

    /**
     * Converts timestamp strings to Unix millisecond time
     * 
     * @param in
     *            Timestamp to be converted
     * @return The timestamp in current milliseconds since the Unix Epoch
     */
    public long getTime(final String in) {
        long total = 0;
        final StringBuilder stored = new StringBuilder();
        final char[] chars = in.toCharArray();
        for (final char c : chars) {
            boolean flag = false;
            // Check for false data
            for (final char ch : DatabaseUtils.allowed_chars) {
                if (ch == c) {
                    flag = true;
                    stored.append(ch);
                    break;
                }
            }
            // If data is true, turn it into unix time
            if (!flag) {
                final long time = DatabaseUtils.getTime(stored.toString(), c);
                if (time == -1) {
                    return -1;
                }
                total += time;
                stored.setLength(0);
            }

        }

        return total;
    }

    /**
     * Retrieves the Unix time
     * 
     * @param in
     *            String time to be parsed
     * @param timescale
     *            time scale to be used
     * @return The long number of exact Unix time
     */
    private static long getTime(final String in, final char timescale) {
        long tot = 0;
        final int num = Integer.parseInt(in);

        switch (timescale) {
        case 's':
            tot += num * 1000l;
            break;
        case 'm':
            tot += num * 60000l;
            break;
        case 'h':
            tot += num * 3600000l;
            break;
        case 'd':
            tot += num * 86400000l;
            break;
        case 'w':
            tot += num * 604800000l;
            break;
        default:
            return -1;
        }
        return tot;
    }

    /**
     * Creates and returns a simple time stamp
     * 
     * @param time
     *            Unix time or from getTime
     * @param getTruncatedStamps
     *            if True uses shorthand terms for time stamp, if false uses
     *            human recognizable terms
     * @return A string time stamp in format of ("[days] Days [hours] Hours
     *         [minutes] Minutes [seconds] Seconds") or their truncated form
     */
    public String getTimeString(long time, boolean getTruncatedStamps) {
        final StringBuilder str = new StringBuilder();

        final long days = time / (1000 * 60 * 60 * 24);
        time -= days * 1000 * 60 * 60 * 24;
        final long hours = time / (1000 * 60 * 60);
        time -= hours * 1000 * 60 * 60;
        final long minutes = time / (1000 * 60);
        time -= minutes * 1000 * 60;
        final long seconds = time / 1000;
        time -= seconds * 1000;

        if (days > 0) {
            // Uses "Days" if getTruncatedStamps is false, else use d
            str.append(days).append(getTruncatedStamps ? " d " : "Days ");
        }
        if (hours > 0) {
            // Uses "Hours" if getTruncatedStamps is false, else use h
            str.append(hours).append(getTruncatedStamps ? " h " : "Hours ");
        }
        if (minutes > 0) {
            // Uses "Minutes" if getTruncatedStamps is false, else use m
            str.append(minutes).append(getTruncatedStamps ? " m " : " Minutes ");
        }
        if (seconds > 0) {
            // Uses "Seconds" if getTruncatedStamps is false, else use s
            str.append(seconds).append(getTruncatedStamps ? " s " : " Seconds ");
        }

        return str.toString().trim();
    }
    
    /**
     * Sets the frozen status of the database
     * @param isFrozen true to set database as frozen, false otherwise.
     */
    public static void setDatabaseFrozen(boolean isFrozen) {
        TInventory.databaseFrozen = isFrozen;
    }
    
    /**
     * Checks if database is frozen
     * @return Returns true if the database if frozen, false otherwise
     */
    public static boolean isDatabaseFrozen() {
        return TInventory.databaseFrozen;
    }

    
    /**
     * Creates new thread to run status checker
     */
    // Maybe not hard code? could go either way
    private static ScheduledExecutorService executors = Executors.newScheduledThreadPool(1);
    
    /**
     * Only to ever be called once
     * Starts the active checker for frozen status
     */
    public static void checkStatus() {
        executors.scheduleAtFixedRate(new StatusUpdater(), 1, 5, TimeUnit.SECONDS);
    }

    public static void shutdownStatusThread() {
        executors.shutdown();
    }

    public static Object parseTags(List<String> tags) {
            String tagString  = "";
            for (String tag  : tags) {
                tagString.concat(":" + tag);
            }
            return "'" + tagString.substring(1) + "'";
    }

    public static ArrayList<String> createTagsFromString(String tags) {
        ArrayList<String> array = new ArrayList<>();
        for (String string : tags.split(":")) {
            array.add(string);
        }
        return array;
    }
    
    /**
     * Retrieves the data for the MySQL login and applies it
     *
     * @return MySQL instance
     */
    public static MySQL getMySQL() {
        Configurations config;
        // If in debug mode
        if (TInventory.getDebugFlag()) {
            config = DatabaseConfig.getDebugSQLConfig().getConfigurations();
        }
        else {
            config = DatabaseConfig.getSQLConfig().getConfigurations();
        }

        return new MySQL(config.getUsername(), config.getPassword(), config.getDatabase(), config.getHost(),
                config.getPort());
    }

}
