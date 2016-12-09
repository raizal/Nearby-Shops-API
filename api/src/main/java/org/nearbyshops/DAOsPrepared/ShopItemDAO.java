package org.nearbyshops.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.JDBCContract;
import org.nearbyshops.Model.*;
import org.nearbyshops.ModelEndPoints.ShopItemEndPoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopItemDAO {


	private HikariDataSource dataSource = Globals.getDataSource();


	public int insertShopItem(ShopItem shopItem)
	{
		
		Connection connection = null;
		PreparedStatement statement = null;
		int rowCount = 0;

		//+ "" + shopItem.getQuantityUnit() + ","
		//+ "" + shopItem.getQuantityMultiple() + ","

		String insertShop = "INSERT INTO "
				+ ShopItem.TABLE_NAME
				+ "("  
				+ ShopItem.SHOP_ID + ","
				+ ShopItem.ITEM_PRICE + ","
				+ ShopItem.ITEM_ID + ","

				+ ShopItem.AVAILABLE_ITEM_QUANTITY + ","
				+ ShopItem.EXTRA_DELIVERY_CHARGE + ","
				+ ShopItem.LAST_UPDATE_DATE_TIME
				+ " ) VALUES (?,?,? ,?,?,?)";

		/*+ "" + shopItem.getItemID() + ","
				+ "" + shopItem.getItemPrice() + ","
				+ "" + shopItem.getItemID() + ","*/


		/* + "" + shopItem.getAvailableItemQuantity() + ","
				+ "" + shopItem.getExtraDeliveryCharge() + ","
				+ "" + "now()" + ""
				*/

		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertShop,Statement.RETURN_GENERATED_KEYS);

			statement.setObject(1,shopItem.getShopID());
			statement.setObject(2,shopItem.getItemPrice());
			statement.setObject(3,shopItem.getItemID());

			statement.setObject(4,shopItem.getAvailableItemQuantity());
			statement.setObject(5,shopItem.getExtraDeliveryCharge());
			statement.setTimestamp(6,new Timestamp(System.currentTimeMillis()));

			rowCount = statement.executeUpdate();
			System.out.println("Key autogenerated SaveShop: Rows Inserted = " + rowCount);
			
			
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

		return rowCount;
	}



	public int insertShopItemBulk(List<ShopItem> shopItemList)
	{

		Connection connection = null;
		PreparedStatement statement = null;
		int rowCount = 0;

		//+ "" + shopItem.getQuantityUnit() + ","
		//+ "" + shopItem.getQuantityMultiple() + ","

		String insertShop = "INSERT INTO "
				+ ShopItem.TABLE_NAME
				+ "("
				+ ShopItem.SHOP_ID + ","
				+ ShopItem.ITEM_PRICE + ","
				+ ShopItem.ITEM_ID + ","

				+ ShopItem.AVAILABLE_ITEM_QUANTITY + ","
				+ ShopItem.EXTRA_DELIVERY_CHARGE + ","
				+ ShopItem.LAST_UPDATE_DATE_TIME
				+ " ) VALUES (?,?,? ,?,?,?)";

		/*+ "" + shopItem.getItemID() + ","
				+ "" + shopItem.getItemPrice() + ","
				+ "" + shopItem.getItemID() + ","*/


		/* + "" + shopItem.getAvailableItemQuantity() + ","
				+ "" + shopItem.getExtraDeliveryCharge() + ","
				+ "" + "now()" + ""
				*/

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertShop,Statement.RETURN_GENERATED_KEYS);


			for(ShopItem shopItem: shopItemList)
			{
				statement.setObject(1,shopItem.getShopID());
				statement.setObject(2,shopItem.getItemPrice());
				statement.setObject(3,shopItem.getItemID());

				statement.setObject(4,shopItem.getAvailableItemQuantity());
				statement.setObject(5,shopItem.getExtraDeliveryCharge());
				statement.setTimestamp(6,new Timestamp(System.currentTimeMillis()));

				statement.addBatch();
			}


			int[] rowCountArray = statement.executeBatch();

			for(int rows: rowCountArray)
			{
				rowCount = rowCount + rows;
			}

			System.out.println("Rows Inserted : INSERT BULK = " + rowCount);


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

		return rowCount;
	}




	public int updateAvailableItemQuantity(int orderID)
	{


		System.out.println(" inside update available item quantity ");

		String updateQuantity =

			" Update shop_item SET available_item_quantity = available_item_quantity - item_quantity from order_item,customer_order " +
			" where order_item.item_id = shop_item.item_id " +
			" and customer_order.order_id = order_item.order_id " +
			" and shop_item.shop_id = customer_order.shop_id " +
			" and customer_order.order_id = " + orderID;



		Connection conn = null;
		Statement stmt = null;
		int updatedRows = -1;

		try {

			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);


			stmt = conn.createStatement();

			updatedRows = stmt.executeUpdate(updateQuantity);


			System.out.println("Total rows updated: Update Available Item Quantity " + updatedRows);

			//conn.close();

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

		return updatedRows;
	}
	
	
	public int updateShopItem(ShopItem shopItem)
	{
		/*

				+ ShopItemContract.QUANTITY_MULTIPLE + " ="
				+ "" + shopItem.getQuantityMultiple() + ","
				+ ShopItemContract.QUANTITY_UNIT + " ="
				+ "'" + shopItem.getQuantityUnit() + "',"
		 */

		String updateStatement = "UPDATE " + ShopItem.TABLE_NAME
				+ " SET "

				+ ShopItem.AVAILABLE_ITEM_QUANTITY + " = ?,"
				+ ShopItem.ITEM_ID + " = ?,"
				+ ShopItem.ITEM_PRICE + " = ?,"

				+ ShopItem.SHOP_ID + " = ?,"
				+ ShopItem.EXTRA_DELIVERY_CHARGE + " = ?,"
				+ ShopItem.LAST_UPDATE_DATE_TIME + " = ?"

				+ " WHERE "
				+ ShopItem.SHOP_ID + " = ?"
				+ " AND "
				+ ShopItem.ITEM_ID + " = ?";
		
		System.out.println("Query:" + updateStatement);
		
		
		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = 0;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			statement.setObject(1,shopItem.getAvailableItemQuantity());
			statement.setObject(2,shopItem.getItemID());
			statement.setObject(3,shopItem.getItemPrice());

			statement.setObject(4,shopItem.getShopID());
			statement.setObject(5,shopItem.getExtraDeliveryCharge());
			statement.setTimestamp(6,new Timestamp(System.currentTimeMillis()));


			statement.setObject(7,shopItem.getShopID());
			statement.setObject(8,shopItem.getItemID());


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
	
	
	
	
	public int deleteShopItem(int shopID,int itemID)
	{
		String deleteStatement = "DELETE FROM " + ShopItem.TABLE_NAME
				+ " WHERE " + ShopItem.SHOP_ID + " = ?"
				+ " AND " + ShopItem.ITEM_ID + " = ?";
		
		
		Connection connection= null;
		PreparedStatement statement = null;
		int rowsCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);

			statement.setObject(1,shopID);
			statement.setObject(2,itemID);

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



//	GeoLocation center;
//
//	GeoLocation[] minMaxArray;
//	GeoLocation pointOne;
//	GeoLocation pointTwo;



public ArrayList<ShopItem> getShopItems(
											Integer itemCategoryID,
											Integer shopID, Integer itemID,
											Double latCenter, Double lonCenter,
											Double deliveryRangeMin, Double deliveryRangeMax,
											Double proximity,
											Integer endUserID, Boolean isFilledCart,
											Boolean isOutOfStock, Boolean priceEqualsZero,
											String searchString,
											String sortBy,
											Integer limit, Integer offset

)
{

		String query = "";

		// set this flag to false after setting the first query
		boolean isFirst = true;
		
		
		
//		String queryNormal = "SELECT * FROM " + ShopItem.TABLE_NAME;


			/*+ "6371 * acos(cos( radians("
			+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
			+ lonCenter + "))"
			+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) as distance" + ","*/
	
	
		String queryJoin = "SELECT DISTINCT "

				+ ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + ","
				+ ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + ","
				+ ShopItem.TABLE_NAME + "." + ShopItem.ITEM_PRICE + ","
				+ ShopItem.TABLE_NAME + "." + ShopItem.AVAILABLE_ITEM_QUANTITY + ","
				+ ShopItem.TABLE_NAME + "." + ShopItem.EXTRA_DELIVERY_CHARGE + ","
				+ ShopItem.TABLE_NAME + "." + ShopItem.DATE_TIME_ADDED + ","
				+ ShopItem.TABLE_NAME + "." + ShopItem.LAST_UPDATE_DATE_TIME + ""

				+ " FROM "
				+ Shop.TABLE_NAME  + "," + ShopItem.TABLE_NAME + ","
				+ Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME
				+ " WHERE "
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID
				+ "="
				+ ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND "
				+ ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID
				+ "="
				+ Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND "
				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID
				+ "="
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID;



	if(endUserID!=null)
	{

		if(isFilledCart!=null)
		{
			if(isFilledCart)
			{
				queryJoin = queryJoin + " AND "
						+ ShopItem.TABLE_NAME
						+ "."
						+ ShopItem.SHOP_ID + " IN "
						+ " (SELECT " + Cart.SHOP_ID + " FROM " + Cart.TABLE_NAME + " WHERE "
						+ Cart.END_USER_ID + " = " + endUserID + ")";
			}else
			{
				queryJoin = queryJoin + " AND "
						+ ShopItem.TABLE_NAME
						+ "."
						+ ShopItem.SHOP_ID + " NOT IN "
						+ " (SELECT " + Cart.SHOP_ID + " FROM " + Cart.TABLE_NAME + " WHERE "
						+ Cart.END_USER_ID + " = " + endUserID + ")";

			}

		}
	}



	if(shopID !=null)
	{
			queryJoin = queryJoin + " AND "
					+ ShopItem.TABLE_NAME
					+ "."
					+ ShopItem.SHOP_ID + " = " + shopID;


//			queryNormal = queryNormal + " WHERE "
//						+ ShopItem.SHOP_ID + " = " + shopID;

			isFirst = false;
		
	}



	if(searchString !=null)
	{
		String queryPartSearch = " ( " +Item.TABLE_NAME + "." + Item.ITEM_DESC +" ilike '%" + searchString + "%'"
				+ " or " + Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + " ilike '%" + searchString + "%'"
				+ " or " + Item.TABLE_NAME + "." + Item.ITEM_NAME + " ilike '%" + searchString + "%'" + ") ";

		queryJoin = queryJoin + " AND " + queryPartSearch;
	}



	if(itemID !=null)
	{	
	
		queryJoin = queryJoin + " AND "
					+ ShopItem.TABLE_NAME
					+ "."
					+ ShopItem.ITEM_ID + " = " + itemID;


//		if(isFirst)
//		{
//			queryNormal = queryNormal + " WHERE "
//					+ ShopItem.ITEM_ID + " = " + itemID;
//
//			isFirst = false;
//
//		}else
//		{
//			queryNormal = queryNormal + " AND "
//					+ ShopItem.ITEM_ID + " = " + itemID;
//		}

	}


	if(itemCategoryID !=null)
	{

		queryJoin = queryJoin + " AND "
				+ ItemCategory.TABLE_NAME
				+ "."
				+ ItemCategory.ITEM_CATEGORY_ID + " = " + itemCategoryID;


//		if(isFirst)
//		{
//			queryNormal = queryNormal + " WHERE "
//					+ ItemCategory.ITEM_CATEGORY_ID + " = " + itemCategoryID;
//
//			isFirst = false;
//
//		}else
//		{
//			queryNormal = queryNormal + " AND "
//					+ ItemCategory.ITEM_CATEGORY_ID + " = " + itemCategoryID;
//		}

	}


	if(priceEqualsZero!=null)
	{
		if(priceEqualsZero)
		{
			queryJoin = queryJoin + " AND "
					+ ShopItem.TABLE_NAME  + "." + ShopItem.ITEM_PRICE + " = " + 0;

//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE "
//						+ ShopItem.ITEM_PRICE + " = " + 0;
//
//				isFirst = false;
//
//			}else
//			{
//				queryNormal = queryNormal + " AND "
//						+ ShopItem.ITEM_PRICE + " = " + 0;
//
//			}

		}

	}


	if(isOutOfStock!=null)
	{
		if(isOutOfStock)
		{
			queryJoin = queryJoin + " AND "
					+ ShopItem.TABLE_NAME  + "." + ShopItem.AVAILABLE_ITEM_QUANTITY + " = " + 0;

//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE "
//						+ ShopItem.AVAILABLE_ITEM_QUANTITY + " = " + 0;
//
//				isFirst = false;
//
//			}else
//			{
//				queryNormal = queryNormal + " AND "
//						+ ShopItem.AVAILABLE_ITEM_QUANTITY + " = " + 0;
//
//			}
//
		}

	}


	/*

	if(itemCategoryID > 0)
	{

			queryJoin = queryJoin + " AND "
					+ ItemCategoryContract.TABLE_NAME
					+ "."
					+ ItemCategoryContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

	}

	*/



	/*
			Applying Filters
	 */



	if(latCenter != null && lonCenter != null)
	{
		// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
		// latCenter and lonCenter. For more information see the API documentation.


		/*String queryPartLatLonCenterTwo = "";

		queryPartLatLonCenterTwo = queryPartLatLonCenterTwo
				+ " AND "
				+ Shop.TABLE_NAME
				+ "."
				+ Shop.LAT_MAX
				+ " >= " + latCenter
				+ " AND "
				+ Shop.TABLE_NAME
				+ "."
				+ Shop.LAT_MIN
				+ " <= " + latCenter
				+ " AND "
				+ Shop.TABLE_NAME
				+ "."
				+ Shop.LON_MAX
				+ " >= " + lonCenter
				+ " AND "
				+ Shop.TABLE_NAME
				+ "."
				+ Shop.LON_MIN
				+ " <= " + lonCenter;*/

		//+ " BETWEEN " + latmax + " AND " + latmin;

		String queryPartlatLonCenter = "";

		queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
				+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
				+ lonCenter + "))"
				+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";



//		if(isFirst)
//		{
//			queryNormal = queryNormal + " WHERE ";
//
//			 reset the flag
//			isFirst = false;
//
//		}else
//		{
//			queryNormal = queryNormal + " AND ";
//		}


//		queryNormal = queryNormal + queryPartlatLonCenter;

		queryJoin = queryJoin + " AND " + queryPartlatLonCenter;
	}



	if(deliveryRangeMin !=null && deliveryRangeMax!=null){

		// apply delivery range filter

		// apply delivery range filter
		String queryPartDeliveryRange = "";

		queryPartDeliveryRange = queryPartDeliveryRange
				+ Shop.TABLE_NAME
				+ "."
				+ Shop.DELIVERY_RANGE
				+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
				//+ " <= " + deliveryRange;



//		if(isFirst)
//		{
//			queryNormal = queryNormal + " WHERE ";
//
//			 reset the flag
//			isFirst = false;
//
//		}else
//		{
//			queryNormal = queryNormal + " AND ";
//		}


//		queryNormal = queryNormal + queryPartDeliveryRange;

		System.out.println("Delivery Range Min : "  + deliveryRangeMin + " Max : " + deliveryRangeMax);

		queryJoin = queryJoin + " AND " + queryPartDeliveryRange;

	}


	// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
	// not required.
	if(proximity !=null)
	{
		// generate bounding coordinates for the shop based on the required location and its
			/*
		center = GeoLocation.fromDegrees(latCenter,lonCenter);
		minMaxArray = center.boundingCoordinates(proximity,6371.01);

		pointOne = minMaxArray[0];
		pointTwo = minMaxArray[1];

		double latMin = pointOne.getLatitudeInDegrees();
		double lonMin = pointOne.getLongitudeInDegrees();
		double latMax = pointTwo.getLatitudeInDegrees();
		double lonMax = pointTwo.getLongitudeInDegrees();


		// Make sure that shop center lies between the bounding coordinates generated by proximity bounding box


		String queryPartProximityTwo = "";


		queryPartProximityTwo = queryPartProximityTwo

				+ " AND "
				+ Shop.TABLE_NAME
				+ "."
				+ Shop.LAT_CENTER
				+ " < " + latMax

				+ " AND "
				+ Shop.TABLE_NAME
				+ "."
				+ Shop.LAT_CENTER
				+ " > " + latMin

				+ " AND "
				+ Shop.TABLE_NAME
				+ "."
				+ Shop.LON_CENTER
				+ " < " + lonMax

				+ " AND "
				+ Shop.TABLE_NAME
				+ "."
				+ Shop.LON_CENTER
				+ " > " + lonMin;*/


		String queryPartProximity = "";
		// filter using Haversine formula using SQL math functions
		queryPartProximity = queryPartProximity
				+ " (6371.01 * acos(cos( radians("
				+ latCenter
				+ ")) * cos( radians("
				+ Shop.LAT_CENTER
				+ " )) * cos(radians( "
				+ Shop.LON_CENTER
				+ ") - radians("
				+ lonCenter
				+ "))"
				+ " + sin( radians("
				+ latCenter
				+ ")) * sin(radians("
				+ Shop.LAT_CENTER
				+ ")))) <= "
				+ proximity ;


//		if(isFirst)
//		{
//			queryNormal = queryNormal + " WHERE ";

			// reset the flag
//			isFirst = false;
//
//		}else
//		{
//			queryNormal = queryNormal + " AND ";
//		}



//		queryNormal = queryNormal + queryPartProximity;

		queryJoin = queryJoin + " AND " + queryPartProximity;

	}




	if(sortBy!=null)
	{
		if(!sortBy.equals(""))
		{
			String queryPartSortBy = " ORDER BY " + sortBy;

//			queryNormal = queryNormal + queryPartSortBy;
			queryJoin = queryJoin + queryPartSortBy;
		}
	}



	if(limit !=null)
	{

		String queryPartLimitOffset = "";

		if(offset !=null)
		{
			queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

		}else
		{
			queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
		}

//		queryNormal = queryNormal + queryPartLimitOffset;
		queryJoin = queryJoin + queryPartLimitOffset;
	}



			/*
					Applying Filters Ends
			 */


//			if(itemCategoryID!=null || (latCenter!=null && lonCenter!=null))
//			{
//
//				query = queryJoin;
//
//				System.out.println("Query Join : "  + queryJoin);
//
//			} else
//			{
//
//				query = queryNormal;
//			}


		query = queryJoin;

		
		
		
		ArrayList<ShopItem> shopItemList = new ArrayList<ShopItem>();
		
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {


			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				
				ShopItem shopItem = new ShopItem();
				shopItem.setShopID(rs.getInt(ShopItem.SHOP_ID));
				shopItem.setItemID(rs.getInt(ShopItem.ITEM_ID));
				shopItem.setAvailableItemQuantity(rs.getInt(ShopItem.AVAILABLE_ITEM_QUANTITY));
				shopItem.setItemPrice(rs.getDouble(ShopItem.ITEM_PRICE));

				shopItem.setDateTimeAdded(rs.getTimestamp(ShopItem.DATE_TIME_ADDED));
				shopItem.setLastUpdateDateTime(rs.getTimestamp(ShopItem.LAST_UPDATE_DATE_TIME));
				shopItem.setExtraDeliveryCharge(rs.getInt(ShopItem.EXTRA_DELIVERY_CHARGE));
				
				shopItemList.add(shopItem);
				
			}
			
			System.out.println("Total ShopItems queried = " + shopItemList.size());	
			
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
		
								
		return shopItemList;

	}




	public ShopItemEndPoint getEndpointMetadata(
			Integer itemCategoryID,
			Integer shopID, Integer itemID,
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin, Double deliveryRangeMax,
			Double proximity,
			Integer endUserID, Boolean isFilledCart,
			Boolean isOutOfStock, Boolean priceEqualsZero,
			String searchString

	)
	{


		String query = "";

		// set this flag to false after setting the first query
		boolean isFirst = true;



//		String queryNormal = "SELECT " +
//								"count(*) as item_count" +
//								" FROM " + ShopItem.TABLE_NAME;
//



		String queryJoin = "SELECT DISTINCT "

				+ "count(*) as item_count"

				+ " FROM "
				+ Shop.TABLE_NAME  + "," + ShopItem.TABLE_NAME + ","
				+ Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME

				+ " WHERE "
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID
				+ "="
				+ ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND "
				+ ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID
				+ "="
				+ Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND "
				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID
				+ "="
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID;



		if(endUserID!=null)
		{

			if(isFilledCart!=null)
			{
				if(isFilledCart)
				{
					queryJoin = queryJoin + " AND "
							+ ShopItem.TABLE_NAME
							+ "."
							+ ShopItem.SHOP_ID + " IN "
							+ " (SELECT " + Cart.SHOP_ID + " FROM " + Cart.TABLE_NAME + " WHERE "
							+ Cart.END_USER_ID + " = " + endUserID + ")";
				}else
				{
					queryJoin = queryJoin + " AND "
							+ ShopItem.TABLE_NAME
							+ "."
							+ ShopItem.SHOP_ID + " NOT IN "
							+ " (SELECT " + Cart.SHOP_ID + " FROM " + Cart.TABLE_NAME + " WHERE "
							+ Cart.END_USER_ID + " = " + endUserID + ")";

				}

			}
		}



		if(shopID !=null)
		{
			queryJoin = queryJoin + " AND "
					+ ShopItem.TABLE_NAME
					+ "."
					+ ShopItem.SHOP_ID + " = " + shopID;


//			queryNormal = queryNormal + " WHERE "
//					+ ShopItem.SHOP_ID + " = " + shopID;

			isFirst = false;

		}



		if(searchString !=null)
		{
			String queryPartSearch = " ( " +Item.TABLE_NAME + "." + Item.ITEM_DESC +" ilike '%" + searchString + "%'"
					+ " or " + Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + " ilike '%" + searchString + "%'"
					+ " or " + Item.TABLE_NAME + "." + Item.ITEM_NAME + " ilike '%" + searchString + "%'" + ") ";


			queryJoin = queryJoin + " AND " + queryPartSearch;
		}



		if(itemID !=null)
		{

			queryJoin = queryJoin + " AND "
					+ ShopItem.TABLE_NAME
					+ "."
					+ ShopItem.ITEM_ID + " = " + itemID;


//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE "
//						+ ShopItem.ITEM_ID + " = " + itemID;
//
//				isFirst = false;
//
//			}else
//			{
//				queryNormal = queryNormal + " AND "
//						+ ShopItem.ITEM_ID + " = " + itemID;
//			}

		}


		if(itemCategoryID !=null)
		{

			queryJoin = queryJoin + " AND "
					+ ItemCategory.TABLE_NAME
					+ "."
					+ ItemCategory.ITEM_CATEGORY_ID + " = " + itemCategoryID;


//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE "
//						+ ItemCategory.ITEM_CATEGORY_ID + " = " + itemCategoryID;
//
//				isFirst = false;
//
//			}else
//			{
//				queryNormal = queryNormal + " AND "
//						+ ItemCategory.ITEM_CATEGORY_ID + " = " + itemCategoryID;
//			}
//
		}


		if(priceEqualsZero!=null)
		{
			if(priceEqualsZero)
			{
				queryJoin = queryJoin + " AND "
						+ ShopItem.TABLE_NAME  + "." + ShopItem.ITEM_PRICE + " = " + 0;

//				if(isFirst)
//				{
//					queryNormal = queryNormal + " WHERE "
//							+ ShopItem.ITEM_PRICE + " = " + 0;
//
//					isFirst = false;
//
//				}else
//				{
//					queryNormal = queryNormal + " AND "
//							+ ShopItem.ITEM_PRICE + " = " + 0;
//
//				}
//
			}

		}


		if(isOutOfStock!=null)
		{
			if(isOutOfStock)
			{
				queryJoin = queryJoin + " AND "
						+ ShopItem.TABLE_NAME  + "." + ShopItem.AVAILABLE_ITEM_QUANTITY + " = " + 0;

//				if(isFirst)
//				{
//					queryNormal = queryNormal + " WHERE "
//							+ ShopItem.AVAILABLE_ITEM_QUANTITY + " = " + 0;
//
//					isFirst = false;
//
//				}else
//				{
//					queryNormal = queryNormal + " AND "
//							+ ShopItem.AVAILABLE_ITEM_QUANTITY + " = " + 0;
//
//				}

			}

		}


	/*

	if(itemCategoryID > 0)
	{

			queryJoin = queryJoin + " AND "
					+ ItemCategoryContract.TABLE_NAME
					+ "."
					+ ItemCategoryContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

	}

	*/



	/*
			Applying Filters
	 */



		// apply visibility filter
		if(latCenter != null && lonCenter != null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.

			String queryPartlatLonCenter = "";

			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";



//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE ";
//
//				 reset the flag
//				isFirst = false;
//
//			}else
//			{
//				queryNormal = queryNormal + " AND ";
//			}


//		queryNormal = queryNormal + queryPartlatLonCenter;

			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;
		}



		// apply delivery range filter
		if(deliveryRangeMin !=null && deliveryRangeMax!=null){

			// apply delivery range filter

			// apply delivery range filter
			String queryPartDeliveryRange = "";

			queryPartDeliveryRange = queryPartDeliveryRange
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
			//+ " <= " + deliveryRange;



//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE ";

				// reset the flag
//				isFirst = false;

//			}else
//			{
//				queryNormal = queryNormal + " AND ";
//			}


//		queryNormal = queryNormal + queryPartDeliveryRange;

			System.out.println("Delivery Range Min : "  + deliveryRangeMin + " Max : " + deliveryRangeMax);

			queryJoin = queryJoin + " AND " + queryPartDeliveryRange;

		}


		// apply proximity filter
		if(proximity !=null)
		{

			// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
			// not required.

			// generate bounding coordinates for the shop based on the required location and its

			String queryPartProximity = "";

			// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians("
					+ latCenter
					+ ")) * cos( radians("
					+ Shop.LAT_CENTER
					+ " )) * cos(radians( "
					+ Shop.LON_CENTER
					+ ") - radians("
					+ lonCenter
					+ "))"
					+ " + sin( radians("
					+ latCenter
					+ ")) * sin(radians("
					+ Shop.LAT_CENTER
					+ ")))) <= "
					+ proximity ;


//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE ";

				// reset the flag
//				isFirst = false;

//			}else
//			{
//				queryNormal = queryNormal + " AND ";
//			}



//		queryNormal = queryNormal + queryPartProximity;

			queryJoin = queryJoin + " AND " + queryPartProximity;

		}



	/*
			Applying Filters Ends
	 */

/*

		if(latCenter==null || lonCenter ==null)
		{
			query = queryNormal;

		} else
		{

			query = queryJoin;
		}
*/



//		if(itemCategoryID!=null || (latCenter!=null && lonCenter!=null))
//		{
//
//			query = queryJoin;

//			System.out.println("Query Join : "  + queryJoin);


//		} else
//		{
//
//			query = queryNormal;
//		}


		query = queryJoin;



		ShopItemEndPoint endPoint = new ShopItemEndPoint();


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {



			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			while(rs.next())
			{
				endPoint.setItemCount(rs.getInt("item_count"));
			}

			System.out.println("Total ShopItem Count = " + endPoint.getItemCount());

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


		return endPoint;
	}





//	Double latCenter, Double lonCenter,
//	Double deliveryRangeMin, Double deliveryRangeMax,
//	Double proximity,
//  Integer endUserID, Boolean isFilledCart,
//  Boolean isOutOfStock, Boolean priceEqualsZero,



	public ShopItemEndPoint getShopItemsForShop(
			Integer itemCategoryID,
			Integer shopID, Integer itemID,
			String searchString,
			String sortBy,
			Integer limit, Integer offset

	)
	{

		String query = "";

		String queryJoin = "SELECT "
				+ "count(*) over() AS full_count " + ","
				+ ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + ","
				+ ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + ""

				+ " FROM " + ShopItem.TABLE_NAME + "," + Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME
				+ " WHERE " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + "=" + Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "=" + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID;



		if(shopID !=null)
		{
			queryJoin = queryJoin
					+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + " = " + shopID;
		}



		if(searchString !=null)
		{
			String queryPartSearch = " ( " +Item.TABLE_NAME + "." + Item.ITEM_DESC +" ilike '%" + searchString + "%'"
					+ " or " + Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + " ilike '%" + searchString + "%'"
					+ " or " + Item.TABLE_NAME + "." + Item.ITEM_NAME + " ilike '%" + searchString + "%'" + ") ";

			queryJoin = queryJoin + " AND " + queryPartSearch;
		}



		if(itemID !=null) {

			queryJoin = queryJoin + " AND "
					+ ShopItem.TABLE_NAME
					+ "."
					+ ShopItem.ITEM_ID + " = " + itemID;

		}


		if(itemCategoryID !=null)
		{

			queryJoin = queryJoin + " AND "
					+ ItemCategory.TABLE_NAME
					+ "."
					+ ItemCategory.ITEM_CATEGORY_ID + " = " + itemCategoryID;

		}



	/*
			Applying Filters
	 */



		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

//			queryNormal = queryNormal + queryPartSortBy;
				queryJoin = queryJoin + queryPartSortBy;
			}
		}



		if(limit !=null)
		{

			String queryPartLimitOffset = "";

			if(offset !=null)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}

//		queryNormal = queryNormal + queryPartLimitOffset;
			queryJoin = queryJoin + queryPartLimitOffset;
		}



			/*
					Applying Filters Ends
			 */


		query = queryJoin;




		ShopItemEndPoint endPoint = new ShopItemEndPoint();

		ArrayList<ShopItem> shopItemList = new ArrayList<ShopItem>();


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {


			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			while(rs.next())
			{

				ShopItem shopItem = new ShopItem();
				shopItem.setShopID(rs.getInt(ShopItem.SHOP_ID));
				shopItem.setItemID(rs.getInt(ShopItem.ITEM_ID));

				endPoint.setItemCount(rs.getInt("full_count"));

				shopItemList.add(shopItem);

			}

			endPoint.setResults(shopItemList);

			System.out.println("Total ShopItems queried = " + shopItemList.size());

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


		return endPoint;
	}

}
