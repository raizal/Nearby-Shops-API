package org.nearbyshops.DAOsPreparedRoles;


import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.DeliveryGuySelf;
import org.nearbyshops.ModelRoles.Staff;
import org.nearbyshops.ModelRoles.Usernames;

import java.sql.*;
import java.util.ArrayList;


public class DeliveryGuySelfDAO {

	private HikariDataSource dataSource = Globals.getDataSource();

	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}


	public int saveForShopAdmin(DeliveryGuySelf deliveryGuySelf)
	{

		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statementUsername = null;
//		int rowIdOfInsertedRow = -1;
		int rowIdUserID = -1;


		String insertUsername = "INSERT INTO "
				+ Usernames.TABLE_NAME
				+ "("
				+ Usernames.USERNAME + ""
				+ ") VALUES(?)";




		String insertItemCategory = "INSERT INTO "
				+ DeliveryGuySelf.TABLE_NAME
				+ "("

				+ DeliveryGuySelf.DELIVERY_GUY_SELF_ID + ","
				+ DeliveryGuySelf.NAME + ","
				+ DeliveryGuySelf.SHOP_ID + ","
//				+ DeliveryGuySelf.USERNAME + ","

				+ DeliveryGuySelf.PASSWORD + ","
				+ DeliveryGuySelf.ABOUT + ","
				+ DeliveryGuySelf.PROFILE_IMAGE_URL + ","

				+ DeliveryGuySelf.PHONE_NUMBER + ","
				+ DeliveryGuySelf.DESIGNATION + ","

//				+ DeliveryGuySelf.CURRENT_LATITUDE + ","
//				+ DeliveryGuySelf.CURRENT_LONGITUDE + ","

				+ DeliveryGuySelf.IS_ENABLED + ","
				+ DeliveryGuySelf.IS_WAITLISTED + ","

				+ DeliveryGuySelf.ACCOUNT_PRIVATE + ","

				+ DeliveryGuySelf.GOVERNMENT_ID_NAME + ","
				+ DeliveryGuySelf.GOVERNMENT_ID_NUMBER + ""


				+ " ) VALUES (?,?,?, ?,?,? ,?,? ,?,? ,?,?,?)";
		
		try {

			
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);


			statementUsername = connection.prepareStatement(insertUsername,PreparedStatement.RETURN_GENERATED_KEYS);
			statementUsername.setString(1,deliveryGuySelf.getUsername());
			statementUsername.executeUpdate();

			ResultSet rs2 = statementUsername.getGeneratedKeys();

			if (rs2.next()) {
				rowIdUserID = rs2.getInt(1);
			}



			statement = connection.prepareStatement(insertItemCategory,Statement.RETURN_GENERATED_KEYS);

			int i = 0;
			statement.setInt(++i,rowIdUserID);
			statement.setString(++i,deliveryGuySelf.getName());
			statement.setObject(++i,deliveryGuySelf.getShopID());

//			statement.setString(++i,deliveryGuySelf.getUsername());
			statement.setString(++i,deliveryGuySelf.getPassword());
			statement.setString(++i,deliveryGuySelf.getAbout());
			statement.setString(++i,deliveryGuySelf.getProfileImageURL());

			statement.setString(++i,deliveryGuySelf.getPhoneNumber());
			statement.setString(++i,deliveryGuySelf.getDesignation());

			statement.setObject(++i,deliveryGuySelf.getEnabled());
			statement.setObject(++i,deliveryGuySelf.getWaitlisted());

			statement.setBoolean(++i,deliveryGuySelf.isAccountPrivate());
			statement.setString(++i,deliveryGuySelf.getGovtIDName());
			statement.setString(++i,deliveryGuySelf.getGovtIDNumber());


			statement.executeUpdate();
//			ResultSet rs = statement.getGeneratedKeys();
//
//			if(rs.next())
//			{
//				rowIdOfInsertedRow = rs.getInt(1);
//			}
			
			
			
			System.out.println("Key autogenerated SaveDistributor: " + rowIdUserID);

			connection.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			rowIdUserID=-1;
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		}
		finally
		{

			if (statementUsername != null) {
				try {
					statementUsername.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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

		
		return rowIdUserID;
	}
	


	public int updateForShopAdmin(DeliveryGuySelf deliveryGuySelf)
	{

		String updateUsername = "UPDATE " + Usernames.TABLE_NAME
				+ " SET " + Usernames.USERNAME + " = ?"
				+ " WHERE " + Usernames.USER_ID + " = ?";



		String updateStatement = "UPDATE " + DeliveryGuySelf.TABLE_NAME
				+ " SET "
				+ DeliveryGuySelf.NAME + " = ?,"
				+ DeliveryGuySelf.SHOP_ID + " = ?,"
//				+ DeliveryGuySelf.USERNAME + " = ?,"
				+ DeliveryGuySelf.PASSWORD + " = ?,"

				+ DeliveryGuySelf.ABOUT + " = ?,"
				+ DeliveryGuySelf.PROFILE_IMAGE_URL + " = ?,"
				+ DeliveryGuySelf.PHONE_NUMBER + " = ?,"
				+ DeliveryGuySelf.DESIGNATION + " = ?,"

				+ DeliveryGuySelf.IS_ENABLED + " = ?,"
				+ DeliveryGuySelf.IS_WAITLISTED + " = ?,"

				+ DeliveryGuySelf.ACCOUNT_PRIVATE + " = ?,"

				+ DeliveryGuySelf.GOVERNMENT_ID_NAME + " = ?,"
				+ DeliveryGuySelf.GOVERNMENT_ID_NUMBER + " = ?"

				+ " WHERE " + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statementUsername = null;
		int updatedRows = -1;
		
		try {
			
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			statementUsername = connection.prepareStatement(updateUsername, PreparedStatement.RETURN_GENERATED_KEYS);
			statementUsername.setString(1,deliveryGuySelf.getUsername());
			statementUsername.setInt(2,deliveryGuySelf.getDeliveryGuyID());
			statementUsername.executeUpdate();


			statement = connection.prepareStatement(updateStatement);

			int i = 0;

			statement.setString(++i,deliveryGuySelf.getName());
			statement.setObject(++i,deliveryGuySelf.getShopID());
//			statement.setString(1,deliveryGuySelf.getUsername());
			statement.setString(++i,deliveryGuySelf.getPassword());

			statement.setString(++i,deliveryGuySelf.getAbout());
			statement.setString(++i,deliveryGuySelf.getProfileImageURL());
			statement.setString(++i,deliveryGuySelf.getPhoneNumber());
			statement.setString(++i,deliveryGuySelf.getDesignation());

			statement.setObject(++i,deliveryGuySelf.getEnabled());
			statement.setObject(++i,deliveryGuySelf.getWaitlisted());

			statement.setBoolean(++i,deliveryGuySelf.isAccountPrivate());
			statement.setString(++i,deliveryGuySelf.getGovtIDName());
			statement.setString(++i,deliveryGuySelf.getGovtIDNumber());


			statement.setObject(++i,deliveryGuySelf.getDeliveryGuyID());
			
			updatedRows = statement.executeUpdate();
			
			
			System.out.println("Total rows updated: " + updatedRows);	
			
			//conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			updatedRows=-1;
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		finally
		
		{

			if (statementUsername != null) {
				try {
					statementUsername.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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

		return updatedRows;
		
	}



	public int updateBySelf(DeliveryGuySelf deliveryGuySelf)
	{

		String updateUsername = "UPDATE " + Usernames.TABLE_NAME
				+ " SET " + Usernames.USERNAME + " = ?"
				+ " WHERE " + Usernames.USER_ID + " = ?";



		String updateStatement = "UPDATE " + DeliveryGuySelf.TABLE_NAME
				+ " SET "
				+ DeliveryGuySelf.NAME + " = ?,"
				+ DeliveryGuySelf.SHOP_ID + " = ?"
//				+ DeliveryGuySelf.USERNAME + " = ?,"
				+ DeliveryGuySelf.PASSWORD + " = ?,"

				+ DeliveryGuySelf.ABOUT + " = ?,"
				+ DeliveryGuySelf.PROFILE_IMAGE_URL + " = ?,"
				+ DeliveryGuySelf.PHONE_NUMBER + " = ?,"

				+ DeliveryGuySelf.CURRENT_LATITUDE + " = ?,"
				+ DeliveryGuySelf.CURRENT_LATITUDE + " = ?,"

//				+ DeliveryGuySelf.IS_ENABLED + " = ?,"
//				+ DeliveryGuySelf.IS_WAITLISTED + " = ?,"


				+ " WHERE " + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statementUsername = null;
		int updatedRows = -1;

		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			statementUsername = connection.prepareStatement(updateUsername, PreparedStatement.RETURN_GENERATED_KEYS);
			statementUsername.setString(1,deliveryGuySelf.getUsername());
			statementUsername.setInt(2,deliveryGuySelf.getDeliveryGuyID());
			statementUsername.executeUpdate();


			statement = connection.prepareStatement(updateStatement);

			int i = 0;
			statement.setString(++i,deliveryGuySelf.getName());
			statement.setObject(++i,deliveryGuySelf.getShopID());
//			statement.setString(1,deliveryGuySelf.getUsername());
			statement.setString(++i,deliveryGuySelf.getPassword());

			statement.setString(++i,deliveryGuySelf.getAbout());
			statement.setString(++i,deliveryGuySelf.getProfileImageURL());
			statement.setString(++i,deliveryGuySelf.getPhoneNumber());

			statement.setObject(++i,deliveryGuySelf.getCurrentLatitude());
			statement.setObject(++i,deliveryGuySelf.getCurrentLongitude());

//			statement.setObject(++i,deliveryGuySelf.getEnabled());
//			statement.setObject(++i,deliveryGuySelf.getWaitlisted());


			statement.setObject(++i,deliveryGuySelf.getDeliveryGuyID());

			updatedRows = statement.executeUpdate();


			System.out.println("Total rows updated: " + updatedRows);

			//conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			updatedRows=-1;
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		finally

		{

			if (statementUsername != null) {
				try {
					statementUsername.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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

		return updatedRows;

	}




//	public int updateBySelf(DeliveryGuySelf deliveryGuySelf)
//	{
//		String updateStatement = "UPDATE " + DeliveryGuySelf.TABLE_NAME
//				+ " SET "
//				+ DeliveryGuySelf.USERNAME + " = ?,"
//				+ DeliveryGuySelf.PASSWORD + " = ?,"
//				+ DeliveryGuySelf.ABOUT + " = ?,"
//				+ DeliveryGuySelf.PROFILE_IMAGE_URL + " = ?,"
//				+ DeliveryGuySelf.PHONE_NUMBER + " = ?,"
//
//				+ DeliveryGuySelf.NAME + " = ?,"
//				+ DeliveryGuySelf.SHOP_ID + " = ?"
//
//				+ " WHERE " + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = ?";
//
//		Connection connection = null;
//		PreparedStatement statement = null;
//		int updatedRows = -1;
//
//		try {
//
//			connection = dataSource.getConnection();
//			statement = connection.prepareStatement(updateStatement);
//
//			statement.setString(1,deliveryGuySelf.getUsername());
//			statement.setString(2,deliveryGuySelf.getPassword());
//			statement.setString(3,deliveryGuySelf.getAbout());
//			statement.setString(4,deliveryGuySelf.getProfileImageURL());
//			statement.setString(5,deliveryGuySelf.getPhoneNumber());
//
//			statement.setObject(6,deliveryGuySelf.getEnabled());
//			statement.setObject(7,deliveryGuySelf.getWaitlisted());
//
//			statement.setString(6,deliveryGuySelf.getName());
//			statement.setObject(7,deliveryGuySelf.getShopID());
//
//			statement.setObject(8,deliveryGuySelf.getDeliveryGuyID());
//
//			updatedRows = statement.executeUpdate();
//
//
//			System.out.println("Total rows updated: " + updatedRows);
//
//			conn.close();
//
//		} catch (SQLException e) {
//			 TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally
//
//		{
//
//			try {
//
//				if(statement!=null)
//				{statement.close();}
//			} catch (SQLException e) {
//				 TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			try {
//
//				if(connection!=null)
//				{connection.close();}
//			} catch (SQLException e) {
//				 TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		return updatedRows;
//
//	}



	public int deleteDeliveryVehicleSelf(int vehicleID)
	{

		String deleteUsername = " DELETE FROM " + Usernames.TABLE_NAME +
				" WHERE " + Usernames.USER_ID + " = ?";


		String deleteStatement = "DELETE FROM " + DeliveryGuySelf.TABLE_NAME
				+ " WHERE " + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = ?";
		
		
		Connection connection= null;
		PreparedStatement statement = null;
		PreparedStatement statementUsername = null;

		int rowsCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			statement = connection.prepareStatement(deleteStatement);
			statement.setObject(1,vehicleID);
			rowsCountDeleted = statement.executeUpdate();
			System.out.println(" Deleted Count: " + rowsCountDeleted);


			statementUsername = connection.prepareStatement(deleteUsername);
			statementUsername.setInt(1,vehicleID);
			statementUsername.executeUpdate();


			connection.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			rowsCountDeleted=0;

			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		finally
		{


			if (statementUsername != null) {
				try {
					statementUsername.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
	
		
		return rowsCountDeleted;
	}
	
	



	
	
	public ArrayList<DeliveryGuySelf> readForShopAdmin(Integer shopID, Boolean isEnabled)
	{
		String query = "SELECT "
				+ DeliveryGuySelf.DELIVERY_GUY_SELF_ID + ","
				+ DeliveryGuySelf.NAME + ","
				+ DeliveryGuySelf.SHOP_ID + ","
				+ Usernames.USERNAME + ","
				+ DeliveryGuySelf.PASSWORD + ","

				+ DeliveryGuySelf.ABOUT + ","
				+ DeliveryGuySelf.PROFILE_IMAGE_URL + ","
				+ DeliveryGuySelf.PHONE_NUMBER + ","

				+ DeliveryGuySelf.DESIGNATION + ","
				+ DeliveryGuySelf.CURRENT_LATITUDE + ","
				+ DeliveryGuySelf.CURRENT_LONGITUDE + ","

				+ DeliveryGuySelf.IS_ENABLED + ","
				+ DeliveryGuySelf.IS_WAITLISTED + ","

				+ DeliveryGuySelf.ACCOUNT_PRIVATE + ","

				+ DeliveryGuySelf.GOVERNMENT_ID_NAME + ","
				+ DeliveryGuySelf.GOVERNMENT_ID_NUMBER + ","
				+ DeliveryGuySelf.TIMESTAMP_CREATED + ""

				+ " FROM " + DeliveryGuySelf.TABLE_NAME
				+ " INNER JOIN " + Usernames.TABLE_NAME + " ON (" + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = " + Usernames.USER_ID + ")";

		boolean isFirst = true;

		if(shopID != null)
		{
			query = query + " WHERE " + DeliveryGuySelf.SHOP_ID + " = " + shopID;

				isFirst = false;
		}

		if(isEnabled !=null)
		{
			if(isFirst)
			{
				query = query + " WHERE " + DeliveryGuySelf.IS_ENABLED + " = "  + isEnabled;

				isFirst = false;
			}
			else
			{
				query = query + " AND " + DeliveryGuySelf.IS_ENABLED + " = "  + isEnabled;
			}
		}



		ArrayList<DeliveryGuySelf> vehiclesList = new ArrayList<DeliveryGuySelf>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{

				DeliveryGuySelf deliveryGuySelf = new DeliveryGuySelf();

				deliveryGuySelf.setDeliveryGuyID(rs.getInt(DeliveryGuySelf.DELIVERY_GUY_SELF_ID));
				deliveryGuySelf.setName(rs.getString(DeliveryGuySelf.NAME));
				deliveryGuySelf.setShopID(rs.getInt(DeliveryGuySelf.SHOP_ID));

				deliveryGuySelf.setUsername(rs.getString(Usernames.USERNAME));
				deliveryGuySelf.setPassword(rs.getString(DeliveryGuySelf.PASSWORD));

				deliveryGuySelf.setAbout(rs.getString(DeliveryGuySelf.ABOUT));
				deliveryGuySelf.setProfileImageURL(rs.getString(DeliveryGuySelf.PROFILE_IMAGE_URL));
				deliveryGuySelf.setPhoneNumber(rs.getString(DeliveryGuySelf.PHONE_NUMBER));

				deliveryGuySelf.setDesignation(rs.getString(DeliveryGuySelf.DESIGNATION));

				deliveryGuySelf.setCurrentLatitude((Double) rs.getObject(DeliveryGuySelf.CURRENT_LATITUDE));
				deliveryGuySelf.setCurrentLongitude((Double) rs.getObject(DeliveryGuySelf.CURRENT_LONGITUDE));

				deliveryGuySelf.setEnabled(rs.getBoolean(DeliveryGuySelf.IS_ENABLED));
				deliveryGuySelf.setWaitlisted(rs.getBoolean(DeliveryGuySelf.IS_WAITLISTED));

				deliveryGuySelf.setAccountPrivate(rs.getBoolean(DeliveryGuySelf.ACCOUNT_PRIVATE));
				deliveryGuySelf.setGovtIDName(rs.getString(DeliveryGuySelf.GOVERNMENT_ID_NAME));
				deliveryGuySelf.setGovtIDNumber(rs.getString(DeliveryGuySelf.GOVERNMENT_ID_NUMBER));
				deliveryGuySelf.setTimestampCreated(rs.getTimestamp(DeliveryGuySelf.TIMESTAMP_CREATED));

				vehiclesList.add(deliveryGuySelf);
				
			}
			

			
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


		return vehiclesList;
	}




	
	public DeliveryGuySelf readForLogin(int deliveryGuyID)
	{
		
		String query = "SELECT "
				+ DeliveryGuySelf.DELIVERY_GUY_SELF_ID + ","
				+ DeliveryGuySelf.NAME + ","
				+ DeliveryGuySelf.SHOP_ID + ","
				+ Usernames.USERNAME + ","
				+ DeliveryGuySelf.PASSWORD + ","

				+ DeliveryGuySelf.ABOUT + ","
				+ DeliveryGuySelf.PROFILE_IMAGE_URL + ","
				+ DeliveryGuySelf.PHONE_NUMBER + ","

				+ DeliveryGuySelf.DESIGNATION + ","
				+ DeliveryGuySelf.CURRENT_LATITUDE + ","
				+ DeliveryGuySelf.CURRENT_LONGITUDE + ","

				+ DeliveryGuySelf.IS_ENABLED + ","
				+ DeliveryGuySelf.IS_WAITLISTED + ","

//				+ DeliveryGuySelf.ACCOUNT_PRIVATE + ","
				+ DeliveryGuySelf.GOVERNMENT_ID_NAME + ","
				+ DeliveryGuySelf.GOVERNMENT_ID_NUMBER + ","
				+ DeliveryGuySelf.TIMESTAMP_CREATED + ""

				+ " FROM " + DeliveryGuySelf.TABLE_NAME
				+ " INNER JOIN " + Usernames.TABLE_NAME + " ON (" + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = " + Usernames.USER_ID + ")"
				+ " WHERE " + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = " + deliveryGuyID;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;


		DeliveryGuySelf deliveryGuySelf = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				deliveryGuySelf = new DeliveryGuySelf();


				deliveryGuySelf.setDeliveryGuyID(rs.getInt(DeliveryGuySelf.DELIVERY_GUY_SELF_ID));
				deliveryGuySelf.setName(rs.getString(DeliveryGuySelf.NAME));
				deliveryGuySelf.setShopID(rs.getInt(DeliveryGuySelf.SHOP_ID));

				deliveryGuySelf.setUsername(rs.getString(Usernames.USERNAME));
				deliveryGuySelf.setPassword(rs.getString(DeliveryGuySelf.PASSWORD));

				deliveryGuySelf.setAbout(rs.getString(DeliveryGuySelf.ABOUT));
				deliveryGuySelf.setProfileImageURL(rs.getString(DeliveryGuySelf.PROFILE_IMAGE_URL));
				deliveryGuySelf.setPhoneNumber(rs.getString(DeliveryGuySelf.PHONE_NUMBER));

				deliveryGuySelf.setEnabled((Boolean) rs.getObject(DeliveryGuySelf.IS_ENABLED));
				deliveryGuySelf.setWaitlisted((Boolean) rs.getObject(DeliveryGuySelf.IS_WAITLISTED));

				deliveryGuySelf.setGovtIDName(rs.getString(DeliveryGuySelf.GOVERNMENT_ID_NAME));
				deliveryGuySelf.setGovtIDNumber(rs.getString(DeliveryGuySelf.GOVERNMENT_ID_NUMBER));
				deliveryGuySelf.setTimestampCreated(rs.getTimestamp(DeliveryGuySelf.TIMESTAMP_CREATED));

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
	
		return deliveryGuySelf;
	}


	public DeliveryGuySelf getShopIDForDeliveryGuy(int deliveryGuyID)
	{

		String query = "SELECT "
				+ DeliveryGuySelf.DELIVERY_GUY_SELF_ID + ","
				+ DeliveryGuySelf.SHOP_ID
				+ " FROM " + DeliveryGuySelf.TABLE_NAME
				+ " WHERE " + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = " + deliveryGuyID;

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;


		DeliveryGuySelf deliveryGuySelf = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);

			while(rs.next())
			{
				deliveryGuySelf = new DeliveryGuySelf();


				deliveryGuySelf.setDeliveryGuyID(rs.getInt(DeliveryGuySelf.DELIVERY_GUY_SELF_ID));
//				deliveryGuySelf.setName(rs.getString(DeliveryGuySelf.NAME));
				deliveryGuySelf.setShopID(rs.getInt(DeliveryGuySelf.SHOP_ID));

//				deliveryGuySelf.setUsername(rs.getString(DeliveryGuySelf.USERNAME));
//				deliveryGuySelf.setPassword(rs.getString(DeliveryGuySelf.PASSWORD));
//
//				deliveryGuySelf.setAbout(rs.getString(DeliveryGuySelf.ABOUT));
//				deliveryGuySelf.setProfileImageURL(rs.getString(DeliveryGuySelf.PROFILE_IMAGE_URL));
//				deliveryGuySelf.setPhoneNumber(rs.getString(DeliveryGuySelf.PHONE_NUMBER));
//
//				deliveryGuySelf.setEnabled((Boolean) rs.getObject(DeliveryGuySelf.IS_ENABLED));
//				deliveryGuySelf.setWaitlisted((Boolean) rs.getObject(DeliveryGuySelf.IS_WAITLISTED));

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

		return deliveryGuySelf;
	}




	public boolean checkUsernameExists(String username)
	{

		String query = "SELECT " + Usernames.USERNAME
					+ " FROM " + Usernames.TABLE_NAME
					+ " WHERE " + Usernames.USERNAME + " = '" + username + "'";

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

//		System.out.println(query);

		DeliveryGuySelf deliveryGuySelf = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);

//			if(rs.getFetchSize()==0)
//			{
//				return false;
//			}
//			else
//			{
//				return true;
//			}


			while(rs.next())
			{

				return true;
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

		return false;
	}




	public void logMessage(String message)
	{
		System.out.println(message);
	}




	public DeliveryGuySelf checkDeliveryGuy(String username, String password)
	{


		logMessage("Checking DeliveryGuySelf");


		boolean isFirst = true;

		String query = "SELECT "
						+ DeliveryGuySelf.DELIVERY_GUY_SELF_ID + ","
						+ Usernames.USERNAME + ","
						+ DeliveryGuySelf.PASSWORD + ","
						+ DeliveryGuySelf.IS_ENABLED + ","
						+ DeliveryGuySelf.SHOP_ID
						+ " FROM " + DeliveryGuySelf.TABLE_NAME
						+ " INNER JOIN " + Usernames.TABLE_NAME + " ON (" + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = " + Usernames.USER_ID + ")";



		/*if(deliveryGuyID!=null)
		{
			query = query + " WHERE " + DeliveryGuySelf.DELIVERY_GUY_SELF_ID + " = " + deliveryGuyID;

			isFirst = false;
		}*/

		if(username!=null)
		{
			String queryPartUsername = Usernames.USERNAME + " = '" + username + "'";

			query = query + " WHERE " + queryPartUsername;

			isFirst = false;
		}


		if(password!=null)
		{
			String queryPartPassword = DeliveryGuySelf.PASSWORD + " = '" + password + "'";

			if(isFirst)
			{
				query = query + " WHERE " + queryPartPassword;
			}
			else
			{
				query = query + " AND " + queryPartPassword;
			}
		}


//		logMessage(query);


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;


		//Distributor distributor = null;
		//Admin admin = null;

		DeliveryGuySelf deliveryGuySelf = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			while(rs.next())
			{

				logMessage("Inside While check deliveryGUySelf");

				deliveryGuySelf = new DeliveryGuySelf();

				deliveryGuySelf.setDeliveryGuyID(rs.getInt(DeliveryGuySelf.DELIVERY_GUY_SELF_ID));
				deliveryGuySelf.setShopID(rs.getInt(DeliveryGuySelf.SHOP_ID));
				deliveryGuySelf.setUsername(rs.getString(Usernames.USERNAME));
				deliveryGuySelf.setPassword(rs.getString(DeliveryGuySelf.PASSWORD));
				deliveryGuySelf.setEnabled(rs.getBoolean(DeliveryGuySelf.IS_ENABLED));


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

		return deliveryGuySelf;
	}


}
