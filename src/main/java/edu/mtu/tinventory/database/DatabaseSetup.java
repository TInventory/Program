package edu.mtu.tinventory.database;

import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.database.query.queries.CreateDataTable;
import edu.mtu.tinventory.database.query.queries.DropTable;

public class DatabaseSetup {
    // TODO: may be stored in config table eventually?
    /**
     * Name of the database that information is stored in
     */
    String database = "tinventory";

    //TODO: Most of these need to return Query 's not strings
    /**
     * Returns MySQL STATEMENT to create the database
     * 
     * @return The Query that executes the instruction
     */
    public String createDatabase(String database) {
        return "CREATE DATABASE " + database + ";";
    }

    /**
     * Lists all databases in the SQL server
     * @return A list<String> of SQL Databases
     */
    public String listDatabases() {
        //TODO: Actually list them
        return "SHOW DATABASES;";
    }
    
    /**
     * Returns MySQL STATEMENT to delete the database
     * 
     * @return The Query that executes the instruction
     */
    public String deleteDatabase(String database) {
        return "DROP DATABASE " + database + ";";
    }

    /**
     * Returns MySQL STATEMENT to delete the dataTable
     * 
     * @return The Statement that executes the instruction
     */
    public String deleteDataTable(String dataTable) {
        return "DROP DATABASE " + dataTable + ";";
    }

    /**
     * Returns MySQL STATEMENT to setup the basic inventory table
     * 
     * @return The Statement that executes the instruction
     */
    public Query setupDataTable(String dataTable) {
        return new CreateDataTable(dataTable);
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
    public Query deleteTable(String dataTable) {
        return new DropTable(dataTable);
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
    private String deleteColumn(String columnName, String dataTable) {
        return "ALTER TABLE " + dataTable + " DROP COLUMN" + columnName + ";";
    }

}
