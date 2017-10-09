package edu.mtu.tinventory.database;

public class Database {

	private Database instance = new Database();
	
	private Database() {
		
	}
	
	public Database getInstance() {
		return instance;
	}
	
	public boolean registerNewItem() {
		return false;
	}
	
	public boolean deleteItem() {
		return false;
	}
	
	public boolean addInventory() {
		return true;
	}
	
	public boolean get() {
		return true;
	}
}
