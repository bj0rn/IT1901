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
		return String.format("'%s'", value);
	}
	
	private String parseValue(String value) {
		return String.format("'%s'", value);
	}
	
	
	
	
	
	
	//	public void insertOrder(Object order){
//		//We need to retrieve kunde nr from Kunde
//		
//		String query = "SELECT kundenr WHERE fornavn = "";" ;
//		
//		ResultSet rs  = connect.createStatement().executeQuery(query);
//		int kundenr = rs.getInt(1);
//		
//		//Just use the generic insert function
//		insert(new Bestilling(ui, kundenr));
//		
//		}
	
	
//	public void cart() {
//		String query = "SELECT MAX(ordrenr) FROM Bestilling";
//		ResultSet rs = connect.createStatement().executeQuery(query);
//		int menyid = 0;
//		
//		insert(new Bestilling_session(ui, menyid, rs.getInt(0)));
//	}
	

	//TODO: Test
	public LinkedList <String[]> getMenu(){
		LinkedList <String[]> list = new LinkedList <String[]>();
		try{
			
			String query = "SELECT * FROM Meny";
		 	ResultSet rs = connect.createStatement().executeQuery(query);
		 	
			while(!rs.wasNull()) {
				list.add(new String [] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
				rs.next();
			}
		
			return list;
		
		} catch(SQLException sq) {
			System.out.println("MENU: Retrival failed!!");
			
		}
		return list;
	}
	
	

	public String[] Search(String SearchQuery, boolean key) {
		String[] tokens = SearchQuery.split(" ");
		//TODO: Error checking
		
		System.out.println("Search query "+SearchQuery+"");
		
		//TODO: Extend this to number search
		
		//Table lookup based on firstname and lastname 
		String query = "SELECT customerID, first_name, last_name, adress, city, "+
		"zipcode, phone FROM customer WHERE first_name = '" + tokens[0] +"' and last_name = '"+ tokens[1] +"'";
		
		System.out.println(query);
		
		try {
			
			ResultSet rs = connect.createStatement().executeQuery(query);
			//System.out.println("Got data from db "+rs+"");
			
			if(key) {
				rs.first();
				System.out.println("Kommer aldri her ?");
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
			
		} catch(SQLException sq) {
			System.out.println("SEARCH: Query failed");
			sq.printStackTrace();
		}
		
		return null;
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
}
	  
	
	
	
	
	