package PizzaPck;

public class orders {
	private String customerID;
	private String delivery;
	private String finish;
	private String delivered;
	private String time;
	private String currentTime;
	
	
	public orders(String [] data) {
		customerID = data[0];
		delivery = data[1];
		finish = data[2];
		delivered = data[3];
		time = data[4];
		currentTime = data[5];
	}
}
