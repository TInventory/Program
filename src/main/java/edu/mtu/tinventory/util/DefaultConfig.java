package edu.mtu.tinventory.util;

import edu.mtu.tinventory.database.DatabaseConfig;

/**
 * 
 * @author JC Helm
 * @since 11/11/17
 * 
 *        Used in the creations of the default configuration if it does not
 *        exist
 *
 */
public class DefaultConfig {

    /**
     * Saves a new default configuration if one does not exist.
     * 
     * @param sql An instance of DatabaseConfig, is either debug SQL or main SQL
     */
    public static void createDefaultSQL(DatabaseConfig sql) {
	sql.write("user:null");
	sql.write("password:null");
	sql.write("database:null");
	sql.write("host:null");
	sql.write("port:null");
    }
  
}
