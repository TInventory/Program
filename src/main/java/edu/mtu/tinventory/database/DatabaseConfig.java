package edu.mtu.tinventory.database;

import edu.mtu.tinventory.logging.LocalLog;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Used to access local file I/O stuff to store local database configuration data
 */

public class DatabaseConfig {

	private File file;

	// Suppression again because the yellow tick annoys me
	@SuppressWarnings("unused")
    private static final DatabaseConfig 
    // These are equivalent to seperate variables
	        sqlConfig = new DatabaseConfig("sqlConfig"), 
	        test = new DatabaseConfig("test");
	
	/**
	 * Constructor for config file class/classes
	 * 
	 * @param filename
	 *            Filename of the class to be created
	 */
	private DatabaseConfig(String filename) {

		// New instance of the filename
		file = new File(filename);

		// Try to create the new file
		try {
			file.createNewFile();
		} 
		// Catch exceptions
		catch (IOException exception) {
			LocalLog.exception("Failed to create new file " + filename, exception);
		}
	}

	/**
	 * Saves the document 
	 * 
	 * @param file : file to be saved to the local datastore
	 */
	public boolean save(File file) {
		try {

		} catch (Exception exception) {
			LocalLog.exception("File " + file.getName() + " could not be saved.", exception);
			return false;
		}
		return true;
	}

}
