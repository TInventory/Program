package edu.mtu.tinventory.database.utils;

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
import java.util.regex.Pattern;

import edu.mtu.tinventory.database.query.queries.InfoStreams;

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
     *  
     * @return
     */
    //TODO: docs
    public static InfoStreams newOutputStream() {
        return new InfoStreams(new ByteArrayOutputStream());
    }

    /**
     * 
     * @param data
     * @return
     */
    //TODO: docs
    public static DataInputStream newInputStream(byte[] data) {
        return new DataInputStream(new ByteArrayInputStream(data));
    }

    /**
     * Just here for testing
     * 
     * @param args
     */
    //TODO: docs
    /*
     * public static void main(String[] args) {
     * System.out.println(getTimeString(128060230, true));
     * }
     */

    /**
     * 
     * @param set
     * @return
     */
    //TODO: docs
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
            e.printStackTrace();
        }

        return data;
    }

    /**
     * 
     * @param uuid
     * @return
     */
    //TODO: docs
    public static String strip(UUID uuid) {
        return COMPILE.matcher(uuid.toString()).replaceAll("");
    }

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    //TODO: docs
    public static HashMap<String, Object> getData(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        HashMap<String, Object> row = new HashMap<>();
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
        }
        // System.out.println(row);
        return row;
    }

    /**
     * 
     * @param in
     * @return
     */
    //TODO: docs
    public static long getTime(final String in) {
        long tot = 0;
        final StringBuilder stored = new StringBuilder();
        final char[] chars = in.toCharArray();
        for (final char c : chars) {
            boolean flag = false;
            for (final char ch : DatabaseUtils.allowed_chars) {
                if (ch == c) {
                    flag = true;
                    stored.append(ch);
                    break;
                }
            }
            if (!flag) {
                final long t = DatabaseUtils.getTime(stored.toString(), c);
                if (t == -1) {
                    return -1;
                }
                tot += t;
                stored.setLength(0);
            }

        }

        return tot;
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
    public static String getTimeString(long time, boolean getTruncatedStamps) {
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

        return str.toString();
    }

}
