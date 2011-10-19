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

	
	//Constructor 
	//Connect
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
	
	
	
	
	
	public void insert(Object obj){
		try{
		
			Class <? extends Object> clazz = obj.getClass();
		
			String classname = clazz.getSimpleName();
		
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO "+ classname +"(");
		
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


	private Object parseValue(Object value) {
		return String.format("'%s'", value).toLowerCase();
	}
	
	private String parseValue(String value) {
		return String.format("'%s'", value);
	}
	
	

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

	
	
	//Update customer based on Customer id
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


	public ArrayList <String[]> getAllOrders() {
		String query = "SELECT * FROM orders";
		ArrayList<String[]> list = new ArrayList<String[]>();
		boolean running = true;
		try {
			ResultSet rs = connect.createStatement().executeQuery(query);
			rs.first();
			while(running) {
				String[] data = {
						rs.getString(1), //OrderID
						rs.getString(2), //customerID
						rs.getString(3), //delivery
						rs.getString(4), //finish
						rs.getString(5), //delivered
						rs.getString(5), //time
						rs.getString(6)  //currentTime
				};
				
				list.add(data);
				running = rs.next();
				}
			return list;
		
		}catch(SQLException sq) {
			System.out.println("getOrders() failed");
		}
		return null;
		
	}
	
	public ArrayList<String[]> displayOrders(String orderNr){
		List <String[]> aList = new ArrayList<String[]>();
		
		//LinkedList<String[]> list = new LinkedList<String[]>();
		java.util.Iterator<String[]> iter = aList.iterator();
		String query = "SELECT * FROM receipt WHERE order = '"+orderNr+"'";
		boolean running = true;
		
		try {
			ResultSet rs = connect.createStatement().executeQuery(query);
			rs.first();
			
			while(running) {
				//adding pizzaID, pizza name and comment
				aList.add(new String[]{rs.getString(2),"", rs.getString(3)});
			}
			int i = 0;
			while(iter.hasNext()) {
				String[] tmp = iter.next();
				String tmpQuery = "SELECT * FROM product WHERE orderID = '"+tmp[0]+"'";
				rs = connect.createStatement().executeQuery(query);
				tmp[2] = rs.getString(1);
				aList.set(i, tmp);
				i++;
				
			}
			
			
			return (ArrayList<String[]>) aList;
			
			
		}catch (SQLException sq) {
			System.out.println("diplayOrder() failed");
		}
		
		return null;
	}
	
	public void delete(Object del) {
		
	}
	
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
}



	  
	
	
	
	
	