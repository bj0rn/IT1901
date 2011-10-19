package PizzaPck;

public class receipt {
	
	private String orderID;
	private String productID;
	private String comment;
	
	public receipt(String[] data) {
		orderID = data[0];
		productID = data[1];
		comment = data[2];
	}
}
