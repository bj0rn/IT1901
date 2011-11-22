package PizzaPck;

/**
 * This class is similar to the customer table in the database 
 * @author Everyone
 */
public class customer {
	//Table fields 
	private String first_name;
	private String last_name;
	private String adress;
	private String city;
	private String zipcode;
	private String phone;
	
	/**
	 * Takes a String array as input and then stores the indexes according to the customer class' fields
	 * @param data
	 */
	public customer(String [] data) {
		this.first_name = data[0];
		this.last_name = data[1];
		this.adress = data[2];
		this.city = data[3];
		this.zipcode = data[4];
		this.phone = data[5];
	}
	
	
	
}
