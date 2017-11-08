package edu.mtu.tinventory.database.utils;

import edu.mtu.tinventory.database.DatabaseInterface;

public class StatusUpdater implements Runnable{

    DatabaseInterface dInterface = DatabaseInterface.getInstance();
    @Override
    public void run() {
    	//TODO: My debug message
    	//System.out.println(DatabaseUtils.isDatabaseFrozen());
        if (DatabaseUtils.isDatabaseFrozen()) {
            dInterface.accessible = false;
        }
        else {
            dInterface.accessible = true;
        }
        
    }

}
