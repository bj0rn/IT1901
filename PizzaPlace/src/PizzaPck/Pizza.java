package PizzaPck;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

/*
 * 
 * Lager pizza objekter som kan legges i en liste
 * paa OrderGUI
 * 
 * @parameter QPixmap image
 * @parameter QComboBox amount
 * @parameter QComboBox size
 * @parameter QLabel amount_label
 * @parameter QLabel size_label
 * @parameter QLabel price_label
 * @parameter QLabel pizza_name
 * @parameter QLabel ingridients
 * @parameter QGridLayout grid
 * @parameter DecimalFormat price_format
 * @parameter List<String> sizes
 * @parameter QGroupBox box
 * @parameter QLabel image_label
 * 
 * @author Linn, Susanne
 * 
 * 	
 * 
 * */


public class Pizza extends QWidget{
	
	protected QPixmap image;
	protected QComboBox amount;
	protected QComboBox size;
	protected QLabel amount_label;
	protected QLabel size_label;
	protected QLabel price_label;
	protected QLabel pizza_name;
	protected QLabel ingridients;
	protected QGridLayout grid;
	protected DecimalFormat price_format;
	protected double start_price;
	protected List<String> sizes;
	protected QGroupBox box;
	protected QLabel image_label;
	
	public Pizza(String[] liste){
		//image = new QPixmap(null);
		size = new QComboBox();
		amount = new QComboBox();
		start_price = Double.parseDouble(liste[2]);
		amount_label = new QLabel("Antall :");
		size_label = new QLabel("St¿rrelse :");
		pizza_name = new QLabel(liste[1]);
		ingridients = new QLabel(liste[3]);
		grid = new QGridLayout(this);
		
		this.price_format = new DecimalFormat("0.00");
		
		
		sizes = new ArrayList<String>();
		
		setup();
	}
	
	
	public void setup(){
		//antall pizzaer er satt til aa vaere 0-20
		
		List<String> l = new ArrayList<String>();
		for (int i = 0; i <=20; i++) {
			l.add(""+i);
		}
		amount.addItems(l);
		
		
		//Man kan valge mellom liten og stor pizza
		sizes.add("Liten");
		sizes.add("Stor");
		size.addItems(sizes);
		
		
		
		//sender signal om at size er endret
		//size.currentStringChanged.connect(this,"pizzaSizeChanged(String)");
		
		//Sender signal om at antallet pizzaer er endret
		//amount.valueChanged.connect(this,"amountValueChanged(int)");
		price_label = new QLabel("Pris: "+start_price);
		
		
		
		//legge til de forskjellige tingene til griden
		this.grid.addWidget(pizza_name,1,0);
		
		this.grid.addWidget(ingridients,2,0);
		this.grid.addWidget(amount_label,2,2);
		this.grid.addWidget(size_label,3,2);
		this.grid.addWidget(amount,2,3);
		this.grid.addWidget(size,3,3);
		this.grid.addWidget(price_label,3,0);	
		
	}

	//hvis size blir endret sŒ mŒ prisen ogsŒ bli endret
	public void pizzaSizeChanged(String newSize){
		double newPrice = start_price;
		if (newSize.equals("Stor")) {
			newPrice = 250;
		}
		price_label.setText("Pris: "+price_format.format(newPrice));
	}
	
	
	//mŒ endre antallet pizza og endre pris iforhold til det
	public void amountChanged(int newAmount){
		
	}

}

//public Pizza(String pizza,double price,String ingridients, String image){
//
//this.image = new QPixmap(image);
//this.size = new QComboBox();
//this.amount = new QComboBox();
//this.start_price = price;
//this.amount_label = new QLabel("Antall :");
//this.size_label = new QLabel("St¿rrelse :");
//this.pizza_name = new QLabel(pizza);
//this.ingridients = new QLabel(ingridients);
//this.grid = new QGridLayout(this);
//
//this.price_format = new DecimalFormat("0.00");
//
//
//sizes = new ArrayList<String>();
//
//setup();
//
//
//}