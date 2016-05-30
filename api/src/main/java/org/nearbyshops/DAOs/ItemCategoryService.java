package org.nearbyshops.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.nearbyshops.ContractClasses.ItemCategoryContract;
import org.nearbyshops.ContractClasses.ItemContract;
import org.nearbyshops.ContractClasses.JDBCContract;
import org.nearbyshops.ContractClasses.ShopContract;
import org.nearbyshops.ContractClasses.ShopItemContract;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.Utility.GeoLocation;


public class ItemCategoryService {


	GeoLocation center;

	GeoLocation[] minMaxArray;
	GeoLocation pointOne;
	GeoLocation pointTwo;


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}
	
	
	public int saveItemCategory(ItemCategory itemCategory)
	{
		
		int rowCount = 0;	
		Connection conn = null;
		Statement stmt = null;
		
		String insertItemCategory = "";
		
		System.out.println("isLeaf : " + itemCategory.getIsLeafNode());

		
		if(itemCategory.getParentCategoryID()>0)
		{
		
			insertItemCategory = "INSERT INTO "
					+ ItemCategoryContract.TABLE_NAME				
					+ "("  
					+ ItemCategoryContract.ITEM_CATEGORY_NAME + ","
					+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","
					+ ItemCategoryContract.PARENT_CATEGORY_ID + ","
					+ ItemCategoryContract.IMAGE_PATH + ","
					+ ItemCategoryContract.IS_LEAF_NODE + ") VALUES("
					+ "'" + itemCategory.getCategoryName()	+ "'," 
					+ "'" + itemCategory.getCategoryDescription() + "',"
					+ "" + itemCategory.getParentCategoryID() + ","
					+ "'" + itemCategory.getImagePath() + "',"
					+ "'" + itemCategory.getIsLeafNode() + "'"
					+ ")";
		}
		
		
		if(itemCategory.getParentCategoryID() == 0)
		{
			
			insertItemCategory = "INSERT INTO "
					+ ItemCategoryContract.TABLE_NAME				
					+ "("  
					+ ItemCategoryContract.ITEM_CATEGORY_NAME + ","
					+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","
					+ ItemCategoryContract.PARENT_CATEGORY_ID + ","
					+ ItemCategoryContract.IMAGE_PATH + ","
					+ ItemCategoryContract.IS_LEAF_NODE + ") VALUES("
					+ "'" + itemCategory.getCategoryName()	+ "'," 
					+ "'" + itemCategory.getCategoryDescription() + "',"
					+ "" + "NULL" + ","
					+ "'" + itemCategory.getImagePath() + "',"
					+ "'" + itemCategory.getIsLeafNode() + "'"
					+ ")";
		
		}
		
		
		int idOfInsertedRow = 0;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
		
			
			
			rowCount = stmt.executeUpdate(insertItemCategory,Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if(rs.next())
			{
				idOfInsertedRow = rs.getInt(1);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return idOfInsertedRow;
	}

	

	public int updateItemCategory(ItemCategory itemCategory)
	{
		int rowCount = 0;
		
		
		System.out.println("isLeaf : " + itemCategory.getIsLeafNode());
		
		String updateStatement = "";
		
		
		if(itemCategory.getParentCategoryID()!=0)
		{
		
		updateStatement = "UPDATE " 
				
				+ ItemCategoryContract.TABLE_NAME 
				
				+ " SET " + ItemCategoryContract.ITEM_CATEGORY_NAME + " = "
				+ "'" + itemCategory.getCategoryName() + "',"
				
				+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + " = "
				+ "'" + itemCategory.getCategoryDescription() + "',"
				
				+ " " + ItemCategoryContract.IMAGE_PATH + " = "
				+ "'" + itemCategory.getImagePath() + "',"
				
				+ " " + ItemCategoryContract.PARENT_CATEGORY_ID + " = "
				+ "" + itemCategory.getParentCategoryID() + ","
				
				+ " " + ItemCategoryContract.IS_LEAF_NODE + " = "
				+ "'" + itemCategory.getIsLeafNode() + "'"
				
				+ " WHERE " +  ItemCategoryContract.ITEM_CATEGORY_ID + "="
				+ itemCategory.getItemCategoryID();
		
		}
		
		
		if(itemCategory.getParentCategoryID()==0)
		{
			
			updateStatement = "UPDATE " 
					
				+ ItemCategoryContract.TABLE_NAME 
				
				+ " SET " + ItemCategoryContract.ITEM_CATEGORY_NAME + " = "
				+ "'" + itemCategory.getCategoryName() + "',"
				
				+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + " = "
				+ "'" + itemCategory.getCategoryDescription() + "',"
				
				+ " " + ItemCategoryContract.IMAGE_PATH + " = "
				+ "'" + itemCategory.getImagePath() + "',"
				
				+ " " + ItemCategoryContract.PARENT_CATEGORY_ID + " = "
				+ "" + "NULL" + ","
				
				+ " " + ItemCategoryContract.IS_LEAF_NODE + " = "
				+ "'" + itemCategory.getIsLeafNode() + "'"
				
				+ " WHERE " +  ItemCategoryContract.ITEM_CATEGORY_ID + "="
				+ itemCategory.getItemCategoryID();
			
		}
		
		
		
		
		
		Connection conn = null;
		Statement stmt = null; 
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
					,JDBCContract.CURRENT_USERNAME
					,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCount = stmt.executeUpdate(updateStatement);
			
			
			System.out.println("Total rows updated: " + rowCount);	
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		
		{
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rowCount;
	}


	

	public int deleteItemCategory(int itemCategoryID)
	{
		
		String deleteStatement = "DELETE FROM ITEM_CATEGORY WHERE ID = " 
				+ itemCategoryID;
		
		
		Connection conn= null;
		Statement stmt = null;
		int rowCountDeleted = 0;
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
					,JDBCContract.CURRENT_USERNAME
					,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCountDeleted = stmt.executeUpdate(deleteStatement);
			
			System.out.println("row Count Deleted: " + rowCountDeleted);	
			
			conn.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		
		{
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rowCountDeleted;
	}


	
	
	public ArrayList<ItemCategory> getItemCategories(
			int shopID, int parentID,
			double latCenter, double lonCenter,
			double deliveryRangeMin, double deliveryRangeMax,
			double proximity)
	{

		String query = "";
		
		String queryNormal = "SELECT * FROM " + ItemCategoryContract.TABLE_NAME;


		if(parentID>0)
		{
			queryNormal = queryNormal + " WHERE " 
					+ ItemCategoryContract.PARENT_CATEGORY_ID 
					+ "=" + parentID ; 
		}
		
		
		
		// a recursive CTE (Common table Expression) query. This query is used for retrieving hierarchical / tree set data. 
		
		
		String withRecursiveStart = "WITH RECURSIVE category_tree(" 
					+ ItemCategoryContract.ITEM_CATEGORY_ID + ","
					+ ItemCategoryContract.PARENT_CATEGORY_ID + "," 
					+ ItemCategoryContract.IMAGE_PATH + ","
					+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","
					+ ItemCategoryContract.IS_LEAF_NODE + "," 
					+ ItemCategoryContract.ITEM_CATEGORY_NAME + ") AS (";
		
		
		String queryJoin = "SELECT DISTINCT " 
		
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.PARENT_CATEGORY_ID + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.IMAGE_PATH + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.IS_LEAF_NODE + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_NAME
				
				
				+ " FROM " 
				+ ShopContract.TABLE_NAME  + "," + ShopItemContract.TABLE_NAME + "," 
				+ ItemContract.TABLE_NAME + "," + ItemCategoryContract.TABLE_NAME
				+ " WHERE " 
				+ ShopContract.TABLE_NAME + "." + ShopContract.SHOP_ID 
				+ "="
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.SHOP_ID
				+ " AND "
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.ITEM_ID
				+ "="
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_ID
				+ " AND "
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_CATEGORY_ID
				+ "="
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_ID;
		
		

		if(shopID > 0)
		{
				queryJoin = queryJoin + " AND "
						+ ShopContract.TABLE_NAME 
						+ "."
						+ ShopContract.SHOP_ID + " = " + shopID; 	
			
		}
		
		if(latCenter>0 && latCenter>0)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.

			queryJoin = queryJoin
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LAT_MAX
					+ " >= " + latCenter
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LAT_MIN
					+ " <= " + latCenter
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LON_MAX
					+ " >= " + lonCenter
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LON_MIN
					+ " <= " + lonCenter;

			//+ " BETWEEN " + latmax + " AND " + latmin;
		}



		if(deliveryRangeMax > 0 || deliveryRangeMin > 0)
		{

			// apply delivery range filter
			queryJoin = queryJoin
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
		}


		// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
		// not required.
		if((proximity > 0) &&
				(deliveryRangeMax ==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax)))
		{
			// generate bounding coordinates for the shop based on the required location and its

			center = GeoLocation.fromDegrees(latCenter,lonCenter);
			minMaxArray = center.boundingCoordinates(proximity,6371.01);

			pointOne = minMaxArray[0];
			pointTwo = minMaxArray[1];

			double latMin = pointOne.getLatitudeInDegrees();
			double lonMin = pointOne.getLongitudeInDegrees();
			double latMax = pointTwo.getLatitudeInDegrees();
			double lonMax = pointTwo.getLongitudeInDegrees();


			// Make sure that shop center lies between the bounding coordinates generated by proximity bounding box

			queryJoin = queryJoin

					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LAT_CENTER
					+ " < " + latMax

					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LAT_CENTER
					+ " > " + latMin

					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LON_CENTER
					+ " < " + lonMax

					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LON_CENTER
					+ " > " + lonMin;
		}

		
		String union = " UNION ";
		
		String querySelect = " SELECT "
				
				+ "cat." + ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ "cat." + ItemCategoryContract.PARENT_CATEGORY_ID + ","
				+ "cat." + ItemCategoryContract.IMAGE_PATH + ","
				+ "cat." + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","
				+ "cat." + ItemCategoryContract.IS_LEAF_NODE + ","
				+ "cat." + ItemCategoryContract.ITEM_CATEGORY_NAME
				+ " FROM category_tree tempCat," + 	ItemCategoryContract.TABLE_NAME + " cat"
				+ " WHERE cat." + ItemCategoryContract.ITEM_CATEGORY_ID
				+ " = tempcat." + ItemCategoryContract.PARENT_CATEGORY_ID
				+ " )";
		
		
		String queryLast = " SELECT "
				+ ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ ItemCategoryContract.PARENT_CATEGORY_ID + ","
				+ ItemCategoryContract.IMAGE_PATH + ","
				+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","
				+ ItemCategoryContract.IS_LEAF_NODE + ","
				+ ItemCategoryContract.ITEM_CATEGORY_NAME
				+ " FROM category_tree";

		
		if(parentID>0)
		{
			queryLast = queryLast + " WHERE " 
					+ ItemCategoryContract.PARENT_CATEGORY_ID 
					+ "=" + parentID ; 
		}

		
		String queryRecursive = withRecursiveStart + queryJoin + union + querySelect +  queryLast;
				
		
		
		if(shopID==0 && latCenter == 0 && lonCenter == 0)
		{
			query = queryNormal;
			
		}else
		{
			query = queryRecursive;
		}

		System.out.println(query);
		
		
		ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				ItemCategory itemCategory = new ItemCategory();
				
				itemCategory.setItemCategoryID(rs.getInt(ItemCategoryContract.ITEM_CATEGORY_ID));
				itemCategory.setParentCategoryID(rs.getInt(ItemCategoryContract.PARENT_CATEGORY_ID));
				itemCategory.setIsLeafNode(rs.getBoolean(ItemCategoryContract.IS_LEAF_NODE));
				itemCategory.setImagePath(rs.getString(ItemCategoryContract.IMAGE_PATH));
				itemCategory.setCategoryName(rs.getString(ItemCategoryContract.ITEM_CATEGORY_NAME));
				itemCategory.setCategoryDescription(rs.getString(ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION));
				itemCategoryList.add(itemCategory);		
			}
			
			
			conn.close();
			System.out.println("Total itemCategories queried " + itemCategoryList.size());	
			
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
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return itemCategoryList;
	}
	
	
	
	
	public ItemCategory getItemCategory(int itemCategoryID)
	{
		
		String query = "SELECT * FROM " 
				+ ItemCategoryContract.TABLE_NAME 
				+ " WHERE " +  ItemCategoryContract.ITEM_CATEGORY_ID +  "= " + itemCategoryID;
		
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		ItemCategory itemCategory = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				itemCategory = new ItemCategory();
				itemCategory.setItemCategoryID(rs.getInt(ItemCategoryContract.ITEM_CATEGORY_ID));
				itemCategory.setCategoryName(rs.getString(ItemCategoryContract.ITEM_CATEGORY_NAME));
				itemCategory.setCategoryDescription(rs.getString(ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION));
				itemCategory.setParentCategoryID(rs.getInt(ItemCategoryContract.PARENT_CATEGORY_ID));
				itemCategory.setIsLeafNode(rs.getBoolean(ItemCategoryContract.IS_LEAF_NODE));
				itemCategory.setImagePath(rs.getString(ItemCategoryContract.IMAGE_PATH));
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
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return itemCategory;
	}
	
}