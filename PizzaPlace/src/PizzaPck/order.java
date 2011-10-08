package PizzaPck;

/**
 * 
 * @author bjorn
 *
 */



public class order {
	//fields 
	//private String ordertime;
	private String deliverytime;
	private String homedelivery;
	private String customerID;
	
	//Constructor 
	public order(String [] data) {
		//ordertime = data[0];
		deliverytime = data[0];
		homedelivery = data[1];
		customerID = data[2];
	}
}
