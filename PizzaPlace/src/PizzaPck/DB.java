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
			
//			//TODO:Finne en bedre løsning på dette
//			try {
//				//Create customer table
//				connect.createStatement().execute("CREATE TABLE `Kunde` ("+
//				"kundenr INT NOT NULL AUTO_INCREMENT, "+
//						"fornavn varchar(255) NOT NULL, "+
//				"etternavn varchar(255) NOT NULL, "+
//						"addresse varchar(255) NOT NULL, "+
//				"sted varchar(255) NOT NULL, "+
//						"postkode varchar(255) NOT NULL, "+
//				"telefonnr varchar(255) NOT NULL, "+
//						"UNIQUE(kundenr), "+
//				"FULLTEXT(fornavn, etternavn, sted, postkode, telefonnr))");
//				
//			}catch(SQLException sq) {
//				System.out.println("Kunde pizza finnes allerede");
//			}
//			
//			try {
//				connect.createStatement().execute("CREATE TABLE bestiling ("+
//			"ordrenr INT NOT NULL AUTO_INCREMENT, "+
//						"bestillingstid varchar(255) NOT NULL, "+
//			"leveringstid varchar(255), "+
//						"levering BOOL, "+
//			"`kundenr` INT NOT NULL, " +
//			"UNIQUE(ordrenr))");
//			
//			} catch(SQLException sq) {
//				System.out.println("Bestilling finnes allerede");
//			}
//			
//			
//			try {
//				connect.createStatement().execute("CREATE TABLE meny ("+
//			"menyid INT NOT NULL AUTO_INCREMENT, "+
//						"navn varchar(255) NOT NULL, "+
//			"innhold varchar(255) NOT NULL, "+
//						"pris DOUBLE NOT NULL, "+
//			"UNIQUE(menyid))");
//			}catch(SQLException sq) {
//				System.out.println("Meny finnes allerede");
//			}
//		
//		
		
		}catch(SQLException sq) {
			System.out.println("Failed to connect");
			sq.printStackTrace();
		
		}catch(ClassNotFoundException err){
			err.printStackTrace();
		}
	}
	
	
	//Customer table and menu table
	//NOTE: REFLECTION!!!
	
	
//	public String [] helper(LinkedList <Object> list) {
//		Iterator iter = (Iterator) list.iterator();
//		while(iter.hasNext()) {
//			Object obj = iter.next();
//			Class <? extends Object> clazz = obj.getClass();
//			Field[] field = clazz.getDeclaredFields();
//			for (Field field : field) {
//				field.setAccessible(true);
//				Object value = field.get(obj);
//				field.setAccessible(false);
//			}
//		}
//		
//	}
	
	
	
	
	
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
	public LinkedList <Object> get_menu() {
		String query = "SELECT * FROM Meny";
		ResultSet rs = connect.createStatement().executeQuery(query);
		LinkedList <Object> list = new LinkedList <Object>();
		while(!rs.wasNull()) {
			list.add(new Meny(rs.getString(1), rs.getString(2), rs.getDouble(3)));
			rs.next();
		}
		
		return list;
	}
	
	

	public String[] SearchForUser(String SearchQuery) {
		String[] tokens = SearchQuery.split(" ");
		String fornavn;
		String etternavn;
		
		//Table lookup based on firstname and lastname 
		String query = "SELECT fornavn, etternavn, addresse, sted, "+
		"postkode, telefon FROM Kunde WHERE fornavn = '" + tokens[0] +"' and etternavn = '"+ tokens[1] +"'";
		
		
		ResultSet rs = connect.createStatement().executeQuery(query);
		
		//Skip id field or return if no information is found
		if (!rs.next()){
			return; 
		}
		
		String [] data = {
				rs.getString(1), //fornavn
				rs.getString(2), //etternavn
				rs.getString(3), //addresse
				rs.getString(4), //sted
				rs.getString(5), // postkode
				rs.getString(6) //telefon
		};
		
		//Display 
		Kunde tmp = new Kunde(data);
		tmp.displayInfo(ui);
		
	}
	
	//Combine Bestilling, bestilling overview and (meny is displayed ?)  
	
	
	
	
	