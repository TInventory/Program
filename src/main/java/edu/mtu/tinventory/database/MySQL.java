package edu.mtu.tinventory.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
     * @param username User ID used to access the MySQL Database
     * @param password User password used to access the MySQL Database
     * @param database database in the MySQL Database to be accessed
     * @param host URL of the MySQL Database
     * @param port Port associated with the URL of the MySQL Database
     */
    public MySQL(String username, String password, String database, String host, int port) {
    	//TODO: Temporary hard coded
    	username = "tinventory";
    	password = "taco";
    	database = "tinventory";
    	host = "kiro47.ddns.net";
    	port = 9998;
    	
        this.username = username;
        this.password = password;
        connectionURL = "jdbc:mysql://" + host + ":" + port + "/" + database;
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
            // Presume nothing is ever done right and grab the driver class ourselves
            Class.forName("com.mysql.jdbc.Driver");
            // Creates the connection, then returns the instance of it
            this.connection = DriverManager.getConnection(connectionURL, username, password);
            return isConnected();
            
        } 
        catch (ClassNotFoundException e) {
            // No SQL driver is found, abort the mission
            System.out.println("JDBC Driver not found!");
        } 
        catch (SQLException e) {
            // Couldn't connect so the server, time to debug
            System.out.println("Could not connect to MySQL! " + e.getMessage());
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
