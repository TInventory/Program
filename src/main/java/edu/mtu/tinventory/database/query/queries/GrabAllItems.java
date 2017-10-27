package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.database.utils.DatabaseUtils;

public class GrabAllItems implements ExecuteQuery{

	private String table;
	private List<Object> data;
	
	public GrabAllItems(String table) {
		this.table = table;
		data = null;
	}
	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s;", table);
	}
	@Override
	public void execute(ResultSet resultSet) {
		try {
		    System.out.println(resultSet);
			//TODO: Read on parsing data
			List <Object> data = DatabaseUtils.getDataList(resultSet);
			this.data = data;
			
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public List<Object> getData() {
	    return data;
	}
}
