package PizzaPck;

/**
 * This class is similar to orders table in the database
 * 
 * @author Vegard
 *
 */
public class orders {
	private String customerID;
	private String delivery;
	private String finish;
	private String delivered;
	private String time;
	private String currentTime;
	
	/**
	 * this constructers must recieve values according to 
	 * customerId, delivery, finish, delivered, time and currentTime
	 * 
	 * @param data
	 */
	public orders(String [] data) {
		customerID = data[0];
		delivery = data[1];
		finish = data[2];
		delivered = data[3];
		time = data[4];
		currentTime = data[5];
	}
}
