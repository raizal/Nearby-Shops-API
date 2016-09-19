package org.nearbyshops.DAOsPreparedRoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.Distributor;

import java.sql.*;
import java.util.ArrayList;


public class DistributorDAOPrepared {


	private HikariDataSource dataSource = Globals.getDataSource();

	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}
	
	
	
	public int saveDistributor(Distributor distributor)
	{	
		
		Connection connection = null;
		PreparedStatement statement = null;
		int rowIdOfInsertedRow = -1;

		String insert = "INSERT INTO "
				+ Distributor.TABLE_NAME
				+ "("  
				+ Distributor.DISTRIBUTOR_NAME + ","
				+ Distributor.USERNAME + ","
				+ Distributor.PASSWORD + ","
				+ Distributor.IS_ENABLED + ","
				+ Distributor.IS_WAITLISTED + ","
				+ ") VALUES(?,?,?,?)";
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);

			statement.setString(1,distributor.getDistributorName());
			statement.setString(2,distributor.getUsername());
			statement.setString(3,distributor.getPassword());
			statement.setBoolean(4,distributor.getEnabled());
			statement.setBoolean(5,distributor.getWaitlisted());

			rowIdOfInsertedRow = statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();

			if(rs.next())
			{
				rowIdOfInsertedRow = rs.getInt(1);
			}
			
			
			
			System.out.println("Key autogenerated SaveDistributor: " + rowIdOfInsertedRow);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		return rowIdOfInsertedRow;
	}
	

	public int updateDistributor(Distributor distributor)
	{	
		String updateStatement = "UPDATE " + Distributor.TABLE_NAME
				+ " SET "
				+ Distributor.DISTRIBUTOR_NAME + " = ?,"
				+ Distributor.USERNAME + " = ?,"
				+ Distributor.PASSWORD + " = ?,"
				+ Distributor.IS_ENABLED + " = ?,"
				+ Distributor.IS_WAITLISTED + " = ?"
				+ " WHERE " + Distributor.DISTRIBUTOR_ID + " = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = -1;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			statement.setString(1,distributor.getDistributorName());
			statement.setString(2,distributor.getUsername());
			statement.setString(3,distributor.getPassword());
			statement.setBoolean(4,distributor.getEnabled());
			statement.setBoolean(5,distributor.getWaitlisted());
			statement.setInt(6,distributor.getDistributorID());

			updatedRows = statement.executeUpdate();
			
			
			System.out.println("Total rows updated: " + updatedRows);	
			
			//conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		
		{
			
			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return updatedRows;
		
	}
	

	public int deleteDistributor(int distributorID)
	{
		
		String deleteStatement = "DELETE FROM " + Distributor.TABLE_NAME
				+ " WHERE " + Distributor.DISTRIBUTOR_ID  + " = ?";
		
		
		Connection connection= null;
		PreparedStatement statement = null;
		int rowsCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);

			statement.setInt(1,distributorID);

			rowsCountDeleted = statement.executeUpdate();
			
			System.out.println(" Deleted Count: " + rowsCountDeleted);	
			
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		
		{
			
			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		return rowsCountDeleted;
	}
	
	
	
	
	
	public ArrayList<Distributor> getDistributors()
	{
		String query = "SELECT * FROM " + Distributor.TABLE_NAME;

		ArrayList<Distributor> distributorList = new ArrayList<Distributor>();
		
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				
				Distributor distributor = new Distributor();

				distributor.setDistributorID(rs.getInt(Distributor.DISTRIBUTOR_ID));
				distributor.setDistributorName(rs.getString(Distributor.DISTRIBUTOR_NAME));
				distributor.setUsername(rs.getString(Distributor.USERNAME));
				distributor.setPassword(rs.getString(Distributor.PASSWORD));
				distributor.setEnabled(rs.getBoolean(Distributor.IS_ENABLED));
				distributor.setWaitlisted(rs.getBoolean(Distributor.IS_WAITLISTED));

				distributorList.add(distributor);
			}
			
			
			
			
			System.out.println("Total Distributor queried " + distributorList.size());	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		finally
		
		{
			
			try {
					if(rs!=null)
					{rs.close();}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
								
		return distributorList;
	}

	
	public Distributor getDistributor(int distributorID)
	{
		
		String query = "SELECT * FROM " + Distributor.TABLE_NAME
						+ " WHERE ID = " + distributorID;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
	
		Distributor distributor = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				distributor = new Distributor();

				distributor.setDistributorID(rs.getInt(Distributor.DISTRIBUTOR_ID));
				distributor.setDistributorName(rs.getString(Distributor.DISTRIBUTOR_NAME));
				distributor.setUsername(rs.getString(Distributor.USERNAME));
				distributor.setPassword(rs.getString(Distributor.PASSWORD));
				distributor.setEnabled(rs.getBoolean(Distributor.IS_ENABLED));
				distributor.setWaitlisted(rs.getBoolean(Distributor.IS_WAITLISTED));
			}
			
			
			//System.out.println("Total itemCategories queried " + itemCategoryList.size());	
	
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		
		{
			
			try {
					if(rs!=null)
					{rs.close();}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return distributor;
	}

	public Distributor getDistributorPassword(Integer distributorID, String username)
	{


		String query = "";


		if(distributorID!=null)
		{
			query = "SELECT * FROM " + Distributor.TABLE_NAME
					+ " WHERE ID = " + distributorID;

		}

		else if(username!=null)
		{
			query = "SELECT * FROM " + Distributor.TABLE_NAME
					+ " WHERE " +  Distributor.DISTRIBUTOR_NAME + " = " + "'" + username + "'";

		}



		if(query.equals(""))
		{
			return null;
		}



		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;


		Distributor distributor = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			while(rs.next())
			{
				distributor = new Distributor();

				distributor.setDistributorID(rs.getInt(Distributor.DISTRIBUTOR_ID));
				distributor.setDistributorName(rs.getString(Distributor.DISTRIBUTOR_NAME));
				distributor.setUsername(rs.getString(Distributor.USERNAME));
				distributor.setPassword(rs.getString(Distributor.PASSWORD));
				distributor.setEnabled(rs.getBoolean(Distributor.IS_ENABLED));
				distributor.setWaitlisted(rs.getBoolean(Distributor.IS_WAITLISTED));
			}


			//System.out.println("Total itemCategories queried " + itemCategoryList.size());



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally

		{

			try {
				if(rs!=null)
				{rs.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return distributor;
	}




	public void logMessage(String message)
	{
		System.out.println(message);
	}



	// method to authenticate Distributor
	public Distributor checkDistributor(Integer distributorID, String username, String password)
	{


		logMessage("Checking Distributor");


		boolean isFirst = true;

		String query = "SELECT * FROM " + Distributor.TABLE_NAME;

		if(distributorID!=null)
		{
			query = query + " WHERE " + Distributor.DISTRIBUTOR_ID + " = " + distributorID;

			isFirst = false;
		}

		if(username!=null)
		{
			String queryPartUsername = Distributor.USERNAME + " = '" + username + "'";

			if(isFirst)
			{
				query = query + " WHERE " + queryPartUsername;

				isFirst = false;
			}
			else
			{
				query = query + " AND " + queryPartUsername;
			}
		}


		if(password!=null)
		{
			String queryPartPassword = Distributor.PASSWORD + " = '" + password + "'";

			if(isFirst)
			{
				query = query + " WHERE " + queryPartPassword;
			}
			else
			{
				query = query + " AND " + queryPartPassword;
			}
		}


		logMessage(query);


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;


		//Distributor distributor = null;
		Distributor distributor = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			while(rs.next())
			{
				distributor = new Distributor();

				logMessage("Inside WHile check admin");

				distributor.setDistributorID(rs.getInt(Distributor.DISTRIBUTOR_ID));
				distributor.setDistributorName(rs.getString(Distributor.DISTRIBUTOR_NAME));
				distributor.setUsername(rs.getString(Distributor.USERNAME));
				distributor.setPassword(rs.getString(Distributor.PASSWORD));
				distributor.setWaitlisted(rs.getBoolean(Distributor.IS_WAITLISTED));
				distributor.setEnabled(rs.getBoolean(Distributor.IS_ENABLED));
			}


			//System.out.println("Total itemCategories queried " + itemCategoryList.size());



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally

		{

			try {
				if(rs!=null)
				{rs.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return distributor;
	}
}