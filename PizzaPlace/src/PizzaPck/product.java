package PizzaPck;

public class product {
	//fields
	private String name;
	private String price;
	private String ingredients;
	private String pict;
	
	
	public product(String[] data ){
		name = data[0];
		price = data[1];
		ingredients = data[2];
		pict = data[3];
		}
}
