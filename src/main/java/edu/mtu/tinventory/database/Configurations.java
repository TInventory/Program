package edu.mtu.tinventory.database;

import java.util.HashMap;

import edu.mtu.tinventory.logging.LocalLog;

public class Configurations {

    private String username;
    private String password;
    private String database;
    private String host;
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
