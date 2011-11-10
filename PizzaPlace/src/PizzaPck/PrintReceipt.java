package PizzaPck;


import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class extends the TheReceipt class,
 * @see: TheReceipt
 * and formats the receipt depending on the
 * order that is marked when you click "Skriv ut".
 * @author Everyone
 */
public class PrintReceipt extends TheReceipt{
	//private DB db;
	private float delivery_price;
	private float tot;
	private double tot_u_mva;
	private double mva;
	private String orderID;
	private String delivery;
	private ArrayList<String[]> products;
	private DecimalFormat format = new DecimalFormat("0.00");
	private String deliveryTime;
	private String[] temp;
	
	/**
	 * The Constructor takes a set of parameters
	 * to get the information it needs to build 
	 * the receipt. 
	 * 
	 * @param orderID
	 * @param delivery
	 * @param products
	 * @throws NullPointerException
	 */
	public PrintReceipt(ArrayList<String[]> products, String[] tmp) throws NullPointerException{
		
		super();
		super.setWindowTitle("Kvittering");
		if (products == null) {
			throw new NullPointerException();
		}
		this.products = products;
		this.orderID= tmp[0];
		this.delivery = tmp[2];
		this.deliveryTime = tmp[6].substring(0, tmp[6].length()-5);
		temp = tmp;
		System.out.println(delivery);
		print();
	}
	
	/**
	 * This method fetches the orders, amount,
	 * size and price for each product,
	 * and insert each product to the receipt.
	 * The method returns a String with HTML formated
	 * text, wich is used to build the receipt in the
	 * print method. 
	 * @see:print
	 * @param product
	 * @return
	 */
	public String calculate(String[] product){
		Float price = Float.parseFloat(product[3])
					*(product[2].equals("1") ? 1.25f : 1.00f)* 
					Float.parseFloat(product[5]);
		tot += price;
		return 
		"<tr style=\"font-size:10px\">"+ 
				   "<td width=220>"+ product[3] + " x " 
				   +(product[2].equals("1") ? "Stor " : "Liten ") 
				   + product[4]+"</td>"+
				   "<td>"+ format.format(price)+"</td>"+
				   "</tr>";
	}
	
	/**
	 * This method formates the whole receipt with;
	 * order id,
	 * time set to deliver,
	 * if it should be delivered or not,
	 * a list of the products with amount,size,pizza name and price,
	 * delivery price,
	 * price without mva,
	 * mva,
	 * total price
	 */
	public void print(){
		
		this.textbox.append("<b style=\"font-size:12px\">Ordre nr : "+orderID+"</b>"+
		"<br style=\"font-size:10px\"><b>Navn : </b>"+temp[8]+ " "+ temp[9]+"</br>"+
		"<br style=\"font-size:10px\"><b>Adresse : </b>"+temp[10]+"</br>"	+
		"<br style=\"font-size:10px\"><b>Skal være ferdig til : </b>"+deliveryTime+"</br>"+
		"<br style=\"font-size:10px\"><b>Skal leveres : </b>"+(delivery.equals("1")? "Ja" : "Nei")
		+"</br>");
		
		this.textbox.append("\n");
		
		this.textbox.append("<table>" +
				"<tr>" +
				"<td width=220><strong>Pizza</strong></td>" +
				"<td><strong>Pris</strong></td>" +
				"</tr>" +
				"<tr>" +
				"<td colspan=2><strong><hr align=\"left\" "+
				": width =\"320\" /></strong></td>" +
				"</tr>");

		//Her må ordrene legges inn slik at de dukker opp på kviteringen
		
		for (String[] string : products) {
			textbox.append(calculate(string));
		}
		
		
		tot_u_mva = (tot)/(1.25);
		mva = (tot-tot_u_mva);
		
		this.textbox.append("<tr>"+
				"<td colspan=2><b><hr align=\"left\" "+
				": width =\"300\" /></b></td>" +
				"</tr>" +
				"<p style=\"width: 20px;\">"+
				"<strong>Bestillingsinfo</strong></p>" +
				"<table style=\"font-size: 9px;\">" +
				"<tr>" +
				"<td width=\"220\">" +
				"	Leveringspris:</td>" +
				"<td> " +(tot > SettingsWidget.DELIVERY_LIMIT ?
						format.format((delivery_price = 0.0f)) : 
						format.format((delivery_price = SettingsWidget.DELIVERY_PRICE))) 
						+ "</td>" +
				"</tr>" +

				"<tr>" +
				"<td width=\"220\">Totalpris med mva:</td>" + 
				"<td>" + format.format(tot) + "</td>" +
				"</tr>"+

				"<tr>" +
				"<td width=\"220\">Totalpris uten mva:</td>" +
				"<td>" + format.format(tot_u_mva) + "</td>" +
				"</tr>" +

				"<tr>" +
				"<td width=\"220\">Merverdiavgift:</td>" +
				"<td>" + format.format(mva) + "</td>" +
				"</tr>" +
				
				"<tr>"+
				"<td colspan=2><b><hr align=\"left\" : width =\"300\" /></b>"+
				"</td>" +
				"</tr>" +

				"<tr>" +
				"<td style=\"font-size:11px\""+
				":width=\"220\"><b>Total pris:</b></td>" +
				"<td style=\"font-size:11px\"><b>" + 
				format.format(calculateFinalPrize()) + 
				"</b></td>" +
				"</tr>" +
		"</table>");
		
	}
	
	/**
	 * This methode calculates the final total price
	 * of the order
	 * @return
	 */
	public float calculateFinalPrize(){
		return tot + delivery_price;
	}	
}

