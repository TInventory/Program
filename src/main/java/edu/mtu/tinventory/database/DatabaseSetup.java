package edu.mtu.tinventory.database;

public class DatabaseSetup {
	// TODO: may be stored in config table eventually?
	/**
	 * Name of the database that information is stored in
	 */
	String database = "tinventory";
	/**
	 * Name of the table that the main data is stored in
	 */
	String dataTable = "inventory";

	/**
	 * Returns MySQL STATEMENT to create the database
	 * 
	 * @return The Statement that executes the instruction
	 */
	public String createDatabase() {
		return "CREATE DATABASE " + database + ";";
	}

	/**
	 * Returns MySQL STATEMENT to delete the database
	 * 
	 * @return The Statement that executes the instruction
	 */
	public String deleteDatabase() {
		return "DROP DATABASE " + database + ";";
	}

	/**
	 * Returns MySQL STATEMENT to setup the basic inventory table
	 * 
	 * @return The Statement that executes the instruction
	 */
	public String setupDataTable() {
		return "CREATE TABLE " + dataTable
				+ " (id VARCHAR(120) , name VARCHAR(120), price VARCHAR(120), currency VARCHAR(5), quantity VARCHAR(128)  );";
	}

	/**
	 * Returns MySQL STATEMENT to setup the basic configuration table
	 * 
	 * @return The Statement that executes the instruction
	 */
	public void setupConfigTable() {

	}

	/**
	 * Returns MySQL STATEMENT to delete the inventory table
	 * 
	 * @return The Statement that executes the instruction
	 */
	private String deleteTable() {
		return "DROP TABLE IF EXISTS" + dataTable + ";";
	}

	/**
	 * Adds a column to the inventory table
	 * 
	 * 
	 * @param columnName
	 *            Name of the column to be created
	 * @param dataType
	 *            Datatype to be stored in column TODO: make enum
	 * @return The Statement that executes the instruction
	 */
	// TODO: Change data type to an enum of acceptable values
	private String addColumnToData(String columnName, String dataType) {
		return "ALTER TABLE " + dataTable + " ADD" + columnName + " " + dataType + ";";
	}

	/**
	 * Adds a column to the inventory table
	 * 
	 * 
	 * @param columnName
	 *            Name of the column to be deleted
	 * @return The Statement that executes the instruction
	 */
	private String deleteColum(String columnName) {
		return "ALTER TABLE " + dataTable + " DROP COLUMN" + columnName + ";";
	}

}
