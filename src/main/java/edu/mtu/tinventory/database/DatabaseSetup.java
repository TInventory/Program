package edu.mtu.tinventory.database;

public class DatabaseSetup {

	public String setupDataTable() {
		return "CREATE TABLE data (id VARCHAR(120) , name VARCHAR(120), price VARCHAR(120), currency VARCHAR(5), quantity VARCHAR(128)  )";
	}

	public void setupConfigTable() {

	}

	private void deleteTable() {

	}

}
