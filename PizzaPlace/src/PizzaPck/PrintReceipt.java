package PizzaPck;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class PrintReceipt extends TheReceipt{
	//private DB db;
	private float delivery_price;
	private float tot;
	private double tot_u_mva;
	private double mva;
	private double final_price;
	private String orderID;
	private String delivery;
	private ArrayList<String[]> products;
	private DecimalFormat format = new DecimalFormat("0.00");
	
	public PrintReceipt(String orderID, String delivery, ArrayList<String[]> products) throws NullPointerException{
		
		super();
		super.setWindowTitle("Kvittering");
		if (products == null) {
			throw new NullPointerException();
		}
		this.products = products;
		this.orderID= orderID;
		this.delivery = delivery;
		System.out.println(delivery);
		print();
	}
	
	public String calculate(String[] product){
		Float price = Float.parseFloat(product[3])* Float.parseFloat(product[5]);
		tot += price;
		return 
		"<tr style=\"font-size:10px\">"+ 
				   "<td width=220>"+ product[3] + " x " +(product[2].equals("1") ? "Stor " : "Liten ") + product[4]+"</td>"+
				   "<td>"+ format.format(price)+"</td>"+
				   "</tr>";
	}
	//må skrive noe HTML-kode eller noe for å formatere utskriften
	public void print(){
		
		this.textbox.append("<b>Ordre nr : "+orderID+"</b>");
		this.textbox.append("Skal være ferdig til : "/*orderID*/+1600);
		this.textbox.append("Skal leveres "+(delivery.equals("1")? "Ja" : "Nei") +"\n");//må hente ut om den skal leveres eller ikke
		
		this.textbox.append("<table>" +
				"<tr>" +
				"<td width=220><strong>Pizza</strong></td>" +
				"<td><strong>Pris</strong></td>" +
				"</tr>" +
				"<tr>" +
				"<td colspan=2><hr align=\"left\" : width =\"305\" /></td>" +
				"</tr>");

		//Her må ordrene legges inn slik at de dukker opp på kviteringen
		
		for (String[] string : products) {
			textbox.append(calculate(string));
		}
		
		
		tot_u_mva = (tot)/(1.25);
		mva = (tot-tot_u_mva);
		
		this.textbox.append("<tr>"+
				"<td colspan=2><hr align=\"left\" : width =\"300\" /></td>" +
				"</tr>" +
				"<p style=\"width: 20px;\">"+
				"<strong>Bestillingsinfo</strong></p>" +
				"<table style=\"font-size: 9px;\">" +
				"<tr>" +
				"<td width=\"220\">" +
				"	Leveringspris:</td>" +
				"<td> " +(tot > 500.0f ? (delivery_price = 0.0f) : (delivery_price = 50.0f)) + "</td>" +
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
				"<td colspan=2><hr align=\"left\" : width =\"300\" />"+
				"</td>" +
				"</tr>" +

				"<tr>" +
				"<td style=\"font-size:11px\":width=\"220\"><b>Total pris:</b></td>" +
				"<td style=\"font-size:11px\"><b>" + format.format(calculateFinalPrize()) + "</b></td>" +
				"</tr>" +
		"</table>");
		
	}
	public float calculateFinalPrize(){
		return tot + delivery_price;
	}
	public void setDelivery_price(float delivery_price) {
		this.delivery_price = delivery_price;
	}

	
}

