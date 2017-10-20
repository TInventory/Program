package edu.mtu.tinventory.database;

import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.database.query.queries.createDataTable;

public class DatabaseSetup {
	// TODO: may be stored in config table eventually?
	/**
	 * Name of the database that information is stored in
	 */
	String database = "tinventory";

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
	public Query setupDataTable(String dataTable) {
		return new createDataTable(dataTable);
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
	public String deleteTable(String dataTable) {
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
	private String addColumnToData(String columnName, String dataType, String dataTable) {
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
	private String deleteColum(String columnName,String dataTable) {
		return "ALTER TABLE " + dataTable + " DROP COLUMN" + columnName + ";";
	}

}
