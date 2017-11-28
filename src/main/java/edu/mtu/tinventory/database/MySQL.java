package edu.mtu.tinventory.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.mtu.tinventory.logging.LocalLog;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Handles the connecting to the SQL database
 */
public class MySQL extends Database {

    private String username;
    private String password;
    private String connectionURL;

    private Connection connection;

    /**
     * MySQL Wrapper
     * 
     * @param username
     *            User ID used to access the MySQL Database
     * @param password
     *            User password used to access the MySQL Database
     * @param database
     *            database in the MySQL Database to be accessed
     * @param host
     *            URL of the MySQL Database
     * @param port
     *            Port associated with the URL of the MySQL Database
     */
    public MySQL(String username, String password, String database, String host, int port) {
        // TODO: Temporary hard coded
        /*
         * username = "tinventory";
         * password = "taco";
         * database = "tinventory";
         * host = "kiro47.ddns.net";
         * port = 9998;
         */

        this.username = username;
        this.password = password;
        // Added end string to go over HTTP instead of HTTPS because I was
        // having issues setting up SSL for mySQL (website works fine though)
        // Also auto reconnect flag
        connectionURL = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
    }

    /**
     * Attempts to creates a connection to a MySQL Server
     * 
     * @return true if connected or false if it was unable to establish a
     *         connection
     */
    @Override
    public boolean connect() {

        try {
            // Presume nothing is ever done right and grab the driver class
            // ourselves
            Class.forName("com.mysql.jdbc.Driver");
            // Creates the connection, then returns the instance of it
            this.connection = DriverManager.getConnection(connectionURL, username, password);
            return isConnected();

        } catch (ClassNotFoundException e) {
            // No SQL driver is found, abort the mission
            LocalLog.exception("JDBC Driver not found", e);
        } catch (SQLException e) {
            // Couldn't connect so the server, time to debug
            LocalLog.exception("Could not connect to MySQL!", e);
        }
        // Find why it could not connection
        return false;
    }

    /**
     * Attempts to retrieve an instance of the connection
     * 
     * @return an instance of the connection, returns false if none can be found
     */
    @Override
    public Connection getConnection() {
        // Simple getter
        return connection;
    }

}
