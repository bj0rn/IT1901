package PizzaPck;

/**
 * This class is similar to the product table in the database
 * @author Everyone
 */
public class product {
	//fields
	private String name;
	private String price;
	private String ingredients;

	
	/**
	 * Data must be a String[] of length 4 and consist of
	 * name. price, ingredients, picture
	 * @param data
	 */
	public product(String[] data ){
		name = data[0];
		price = data[1];
		ingredients = data[2];
		}
}

