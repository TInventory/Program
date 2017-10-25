package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;

import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.database.utils.DatabaseUtils;

public class GrabAllItems implements ExecuteQuery{

	private String table;
	
	public GrabAllItems(String table) {
		this.table = table;
		
	}
	@Override
	public String getQuery() {
		return String.format("SELECT * FROM '%s';", table);
	}
	@Override
	public void execute(ResultSet resultSet) {
		try {
			//TODO: Read on parsing data
			Map<String, Object> data = DatabaseUtils.getDataList(resultSet);
			
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
