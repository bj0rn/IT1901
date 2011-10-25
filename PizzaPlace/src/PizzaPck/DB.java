package PizzaPck;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

import javax.swing.JTextArea;
import javax.swing.text.html.HTMLDocument.Iterator;

public class DB {
	//Fields 
	String driver = "org.gjt.mm.mysql.Driver";
	String host = "jdbc:mysql://mysql.stud.ntnu.no/batunges_data";
	String user = "batunges_uber13";
	String pass = "uber13";
	Connection connect;


	/**
	 * Create new connection
	 * @throws SQLException
	 * @Throws {@link ClassNotFoundException}
	 */
	public DB() {
		try {
			//Load driver and connect
			Class.forName(driver);
			connect = DriverManager.getConnection(host, user, pass);

			System.out.println("Sucessfully connected to db");

		} catch(ClassNotFoundException err){
			System.out.println("Failed to load driver");
			err.printStackTrace();
		} catch(SQLException sq) {
			System.out.println("Failed to connect");
			sq.printStackTrace();
		}
	}




	/**
	 * Generic insert function for the database. Uses instances of objects
	 * to build insert queries. The fields and data present in the object is
	 * used as a template. 
	 * @throws RuntimeException
	 * @param obj
	 */
	public void insert(Object obj){
		try{

			Class <? extends Object> clazz = obj.getClass();

			String classname = clazz.getSimpleName();

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO "+ classname +"(");

			//Get field names
			Field[] fields = clazz.getDeclaredFields();
			boolean commaAdded = false;
			for (Field field : fields) {
				if(!commaAdded) {
					commaAdded = true;
				}
				else {
					sb.append(", "); 
				}
				sb.append(field.getName());
			}
			sb.append(") VALUES(");

			commaAdded = false;
			//Get field data
			for(Field field : fields) {
				String fieldName = field.getName();
				field.setAccessible(true);
				Object value = field.get(obj);
				field.setAccessible(false);

				if(!commaAdded){
					commaAdded=true;
				}else{
					sb.append(", ");
				}
				sb.append(parseValue(value));
			}
			sb.append(")");

			String query1 = sb.toString();
			System.out.println(query1);


			connect.createStatement().execute(query1);	

		}catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Create a String object of a inserted Object. Used by the generic
	 * insert function to manipulate data.
	 * @param value
	 * @return
	 */
	private Object parseValue(Object value) {
		return String.format("'%s'", value).toLowerCase();
	}
	/**
	 * C
	 * @param value
	 * @return
	 */
	private String parseValue(String value) {
		return String.format("'%s'", value);
	}


	/**
	 * Retrieve all rows from table products. The data retrieved are packed inside a list of
	 * string arrays. Each string array contain productID, name, price and 
	 * Ingredients, in the given order. 
	 * @throws SQLException
	 *
	 * @return {@link LinkedList}
	 */
	public LinkedList <String[]> getMenu(){
		LinkedList <String[]> list = new LinkedList <String[]>();
		try{

			String query = "SELECT * FROM product";
			ResultSet rs = connect.createStatement().executeQuery(query);
			rs.first();
			boolean running = true;
			while(running){
				list.add(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
				running =rs.next();
			}		
			return list;

		} catch(SQLException sq) {
			System.out.println("MENU: Retrival failed!!");

		}
		return list;
	}

	/**
	 * Multi purpose search function for the customer table. 
	 * @param SearchQuery
	 * @param key Boolean
	 * @param customer Boolean
	 * @return String[]
	 * @Throws {@link SQLException}
	 */
	public String[] Search(String SearchQuery, boolean key, boolean customer) {

		String query = "";

		if (customer) {
			query = "SELECT customerID, first_name, last_name, adress, city, "+
					"zipcode, phone FROM customer "+
					"WHERE customerID = '"+SearchQuery+"'";
			System.out.println(query);
		}
		else {
			String[] tokens = SearchQuery.split(" ");
			query = "SELECT customerID, first_name, last_name, adress, city, "+
					"zipcode, phone FROM customer WHERE first_name = '" + tokens[0].toLowerCase() +"' and last_name = '"+ tokens[1].toLowerCase() +"'";
		}


		try {

			ResultSet rs = connect.createStatement().executeQuery(query);
			//System.out.println("Got data from db "+rs+"");


			//Return customer key 
			if(key) {
				rs.first();
				String[] CustomerID = {rs.getString(1)};
				System.out.println(CustomerID[0]);
				return CustomerID;
			}


			//Skip id field or return if no information is found
			if (!rs.next()){
				return null; 
			}

			String[] data = {
					rs.getString(2), //fornavn
					rs.getString(3), //etternavn
					rs.getString(4), //addresse
					rs.getString(5), //sted
					rs.getString(6), // postkode
					rs.getString(7) //telefon
			};

			return data;

		} catch(Exception e) {
			throw new RuntimeException(e);
		}

	}


	/**
	 * Updates a customer in the customer table by using the customerID.
	 * 
	 * @param customer the new customer
	 * @param CustomerID the old customer(customer id is located at first position)
	 * @throws SQLException
	 */
	public void Update(String[] customer, String [] CustomerID){
		String query = "UPDATE customer SET first_name = '"+customer[0]+"'"+
				", last_name = '"+customer[1]+"'"+
				", adress = '"+customer[2]+"'"+
				", city = '"+customer[3]+"'"+
				" , zipcode = '"+customer[4]+"'"+
				", phone = '"+customer[5]+"' WHERE customerID = '"+CustomerID[0]+"'";

		System.out.println(query);
		try {
			connect.createStatement().execute(query);
		}catch(SQLException sq) {
			System.out.println("UPDATE: Failed!!");
			sq.printStackTrace();
		}
	}

	public ArrayList <String[]> getAllDeliveries(){
		String query = "SELECT * FROM orders WHERE (finish ='1' AND delivery = '1') AND delivered = '0'";
		ArrayList<String[]> list = new ArrayList<String[]>();
		boolean running = true;
		try{
			ResultSet rs = connect.createStatement().executeQuery(query);

			if(!rs.first()) {
				return null;
			}

			while(running) {
				//Encapsulate retrieved data 
				String[] data = {
						//from order
						rs.getString(1), //OrderID
						rs.getString(2), //customerID
						rs.getString(3), //delivery
						rs.getString(4), //finish
						rs.getString(5), //delivered
						rs.getTimestamp(6).toString(),//time
						rs.getTimestamp(7).toString(),  //currentTime

						//from customer
						"", //customerId
						"", //first_name
						"", //last_name
						"", //adress
						"", //zipcode
						"", //city 
						"" //phone

				};

				list.add(data);
				//Handle next row
				running = rs.next();
			}


			for (String[] strings : list) {
				try {
					query = "SELECT * FROM customer WHERE customerID = '"+strings[1]+"' ";
					ResultSet fu = connect.createStatement().executeQuery(query);
					if(fu.first() == false) {
						System.out.println("FU!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}
					strings[7] = fu.getString(1);	//customerID
					strings[8] = fu.getString(2);	//first_name
					strings[9] = fu.getString(3);	//last_name
					strings[10] = fu.getString(4);	//adress
					strings[11] = fu.getString(5);	//zipcode
					strings[12] = fu.getString(6);	//city
					strings[13] = fu.getString(7);	//phone

				}catch(SQLException sq) {
					System.out.println("FU!!!");
					sq.printStackTrace();
				}
			}
			return list;

		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}

		return null;
	}
	/**
	 * Retrive all orders from the orders table. The data retrieved are packed
	 * inside string arrays. Each string array contains orderID, customerID,
	 * delivery, finish, delivered, time and currentTime, in the given order. 
	 * 
	 * @return ArrayList<String[]>
	 * @throws SQLException
	 */
	public ArrayList <String[]> getAllOrders() {
		String query = "SELECT * FROM orders WHERE finish = '0' ORDER BY time DESC";
		ArrayList<String[]> list = new ArrayList<String[]>();
		boolean running = true;
		try {
			ResultSet rs = connect.createStatement().executeQuery(query);


			if(!rs.first()) {
				return null;
			}

			while(running) {
				//Encapsulate retrieved data 
				String[] data = {
						rs.getString(1), //OrderID
						rs.getString(2), //customerID
						rs.getString(3), //delivery
						rs.getString(4), //finish
						rs.getString(5), //delivered
						rs.getTimestamp(6).toString(),//time
						rs.getTimestamp(7).toString()  //currentTime
				};
				list.add(data);
				//Handle next row
				running = rs.next();
			}
			return list;

		}catch(SQLException sq) {
			System.out.println("getAllOrders() failed");
			sq.printStackTrace();
		}
		return null;

	}
	//TODO: Skrive om denne kommentaren 
	/**
	 * Retrieves all rows in table receipt where orderID == orderNr. The data
	 * Retrieved are packed inside string arrays. Each string array contain orderID, 
	 * productID, comment, size,amount, pizza name, and ingredients. Pizza name and ingridents are
	 * Retrieved by performing an additional query, and retrieving the name and ingridents from
	 * products where productID == productID
	 * @param orderNr
	 * @return ArrayList<String[]>
	 */

	public ArrayList<String[]> displayOrders(String orderNr){
		ArrayList <String[]> aList = new ArrayList<String[]>();
		String query = "SELECT * FROM receipt WHERE orderID = '"+orderNr+"'";
		boolean running = true;

		try {
			ResultSet rs = connect.createStatement().executeQuery(query);
			rs.first();
			while(running) {
				String[] data = {

						rs.getString(1), //OrderID
						rs.getString(2), //ProductID
						rs.getString(3), //Comment 
						rs.getString(4), //size
						rs.getString(5), //amount
						"",				 //Pizza navn
						""				//ingridienser
				};
				aList.add(data);
				running = rs.next();
			}



			for (String[] strings : aList) {
				try {
					query = "SELECT * FROM product WHERE productID = '"+strings[1]+"' ";
					ResultSet fu = connect.createStatement().executeQuery(query);
					if(fu.first() == false) {
						System.out.println("FU!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}
					strings[5] = fu.getString(2);	//name
					strings[6] = fu.getString(4);	//ingridents
				}catch(SQLException sq) {
					System.out.println("FU!!!");
					sq.printStackTrace();
				}
			}
			return aList;
		}catch (SQLException sq) {
			System.out.println("diplayOrder() failed");
			sq.printStackTrace();
		}

		return null;
	}

	public void delete(Object del) {

	}

	/**
	 * Retrieve productID from product where PizzaName == name. 
	 * 
	 * @param string PizzaName
	 * @return String
	 */
	public String getPizzaID(String PizzaName) {
		String  query = "SELECT productID FROM product WHERE name = '"+PizzaName+"'";
		try {
			ResultSet rs = connect.createStatement().executeQuery(query);
			rs.first();
			return rs.getString(1);
		}catch(SQLException sq) {
			System.out.println("ERROR: getPizzaID()");
			sq.printStackTrace();
		}
		return "";
	}

	public void updateFinishStatus(String orderID){
		String query = "UPDATE orders SET finish = '1' WHERE orderID = '"+orderID+"'";
		try{
			connect.createStatement().execute(query);
		}
		catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	public void updateDeliveredStatus(String orderID){
		String query = "UPDATE orders SET delivered = '1' WHERE orderID = '"+orderID+"'";
		try{
			connect.createStatement().execute(query);
		}
		catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}


	/**
	 * Retrieve the last inserted orderID from table orders
	 * @return String
	 */
	public String getOrderID() {
		String query = "SELECT MAX(orderid) FROM orders";
		try {
			ResultSet rs = connect.createStatement().executeQuery(query);
			rs.first();
			return rs.getString(1);
		}catch(SQLException sq) {
			System.out.println("ERROR: getOrderID()");
			sq.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 
	 * @param time
	 * @param delivery
	 * @param orderID
	 */
	public void updateTime(Timestamp time, int delivery, String orderID) {
		String query = "UPDATE orders SET time = '"time+"'"+
				", delivery = '"+delivery+"'  WHERE orderID = '"+orderID+"'";
		
		try {
			connect.createStatement().execute(query);
			
		}catch(SQLException sq) {
			System.out.println("updateTime() failed!!");
			sq.printStackTrace();
		}
	}
}








