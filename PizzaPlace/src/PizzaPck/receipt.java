package PizzaPck;

public class receipt {
	
	private String orderID;
	private String productID;
	private String comment;
	private String size;
	private String amount;
	
	public receipt(String[] data) {
		orderID = data[0];
		productID = data[1];
		comment = data[2];
		size = data[3];
		amount = data[4];
	}
}
