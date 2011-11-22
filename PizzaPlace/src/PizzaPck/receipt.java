package PizzaPck;

/**
 * This class is similar to receipt table in the database
 * @author Everyone
 */
public class receipt {
	
	private String orderID;
	private String productID;
	private String comment;
	private String size;
	private String amount;
	

	/**
	 * Data must be a string[] of length 5 and consists of
	 * orderID, productId, comment, size amount
	 * @param data
	 */
	public receipt(String[] data) {
		orderID = data[0];
		productID = data[1];
		comment = data[2];
		size = data[3];
		amount = data[4];
	}
}
