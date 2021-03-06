package edu.mtu.tinventory.database;

import java.util.HashMap;
import edu.mtu.tinventory.logging.LocalLog;

/**
 * 
 * @author JC Helm <jchelm@mtu.edu>
 * @since 11/11/17
 *
 */
public class Configurations {

    /** Username for SQL Login*/
    private String username;
    /** Password for SQL Login*/
    private String password;
    /** Name of the database to connect to*/
    private String database;
    /** URL of host to connect to*/
    private String host;
    /** Port to connect to for SQL */
    private int port;

    /**
     * Configuration of data
     * 
     * @param map HashMap of configuration data
     */
    public Configurations(HashMap<String, String> map) {
	Object port = map.get("port");
	Object host = map.get("host");
	Object database = map.get("database");
	Object password = map.get("password");
	Object user = map.get("user");
	
	if (port == null || host == null || database == null || password == null || user == null) {
	    LocalLog.error("PART OF THE CONFIGURATION WAS NULL, CONNECTION WILL NOT ESTABLISH!");
	    //TODO: maybe disconnect here? or bring up dialog to log in correctly
	    return;
	}
	this.username = user.toString();
	this.password = password.toString();
	this.database = database.toString();
	this.host = host.toString();
	this.port = Integer.parseInt(port.toString());
	
    }

    public String getUsername() {
	return username;
    }

    public String getPassword() {
	return password;
    }

    public String getDatabase() {
	return database;
    }

    public String getHost() {
	return host;
    }

    public int getPort() {
	return port;
    }
}
