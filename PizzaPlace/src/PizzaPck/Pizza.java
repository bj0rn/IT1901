package PizzaPck;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

/**
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
 * 	Må renskrives!!!!
 * 
 * */

 class Pizza extends QWidget{
	
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
	protected QLineEdit comments;
	protected QLabel labComments;
	public QPushButton btnAdd;
	
	
	public Pizza(String[] liste){
		//image = new QPixmap(null);
		String storrelse = "Størrelse";
		try {
			URLEncoder.encode(storrelse,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		size = new QComboBox();
		amount = new QComboBox();
		start_price = Double.parseDouble(liste[2]);
		amount_label = new QLabel("Antall :");
		size_label = new QLabel(storrelse+" :");
		pizza_name = new QLabel(liste[1]);
		ingridients = new QLabel(liste[3]);
		grid = new QGridLayout(this);
		comments = new QLineEdit();
		labComments = new QLabel("Kommentar: ");
		btnAdd = new QPushButton("Legg til");
		this.price_format = new DecimalFormat("0.00");
		
		sizes = new ArrayList<String>();
		
		
		setup();
	}
	
	
	public void setup(){
		
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
		this.grid.addWidget(labComments, 4, 0);
		this.grid.addWidget(comments, 4, 1);
		this.grid.addWidget(btnAdd, 4, 3);
		
	}

	//hvis size blir endret s� m� prisen ogs� bli endret
	
	public void pizzaSizeChanged(String newSize){
		double newPrice = start_price;
		if (newSize.equals("Stor")) {
			newPrice = 250;
		}
		price_label.setText("Pris: "+price_format.format(newPrice));
	}
	
	
	
	//m� endre antallet pizza og endre pris iforhold til det


}

//public Pizza(String pizza,double price,String ingridients, String image){
//
//this.image = new QPixmap(image);
//this.size = new QComboBox();
//this.amount = new QComboBox();
//this.start_price = price;
//this.amount_label = new QLabel("Antall :");
//this.size_label = new QLabel("St�rrelse :");
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