package edu.mtu.tinventory.database.query;

import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.util.DatabaseUtils;

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
