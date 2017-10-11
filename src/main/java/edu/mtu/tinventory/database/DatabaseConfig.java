package edu.mtu.tinventory.database;

public class DatabaseConfig {

	
	private DatabaseConfig instance = new DatabaseConfig();
	
	
	private DatabaseConfig() {
		
	}
	
	public DatabaseConfig getInstance() {
		return instance;
	}
	
	
}
