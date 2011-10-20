package PizzaPck;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * 
 * Lager pizza objekter som kan legges i en liste
 * paa OrderGUI
 * 
 * 
 * @author Linn, Susanne
 * 
 * 	
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
	protected List<String> amountList;
	protected QGroupBox box;
	protected QLabel image_label;
	protected QLineEdit comments;
	protected QLabel labComments;
	public QPushButton btnAdd;
	
	protected QHBoxLayout size_layout;
	protected QHBoxLayout amount_layout;
	
	//Send pizza information to pizzalist
	public Signal1<String[]> signalPizza = new Signal1<String[]>();
	
	public Pizza(String[] liste){
		//image = new QPixmap(null);
		start_price = Double.parseDouble(liste[2]);
		pizza_name = new QLabel(liste[1]);
		ingridients = new QLabel(liste[3]);
		grid = new QGridLayout(this);
		
		setup();
	}
	public void setup(){
		
		comments = new QLineEdit();
		comments.setFixedWidth(150);
		labComments = new QLabel("Kommentar:");
		labComments.setFixedWidth(100);
		
		price_format = new DecimalFormat("0.00");
		price_format.format(start_price);
		price_label = new QLabel("Pris: "+start_price);
		
		btnAdd = new QPushButton("Legg til");
		btnAdd.clicked.connect(this, "signalBtnAdd()");

		//Man kan valge mellom liten og stor pizza
		size = new QComboBox();
		sizes = new ArrayList<String>();
		size_label = new QLabel("Størrelse :");
		sizes.add("Liten");
		sizes.add("Stor");
		size.addItems(sizes);
		
		//
		amount = new QComboBox();
		amount_label = new QLabel("Antall :");
		amountList = new ArrayList<String>();

		for (int i = 1; i < 21; i++) {
			amountList.add(""+i);
		}
		amount.addItems(amountList);
		
		ingridients.setFont(new QFont("Veranda",11));
		ingridients.setWordWrap(true);
		labComments.setWordWrap(true);
		
		pizza_name.setFont(new QFont("Arial",14,QFont.Weight.Bold.value()));
		pizza_name.setWordWrap(true);
		price_label.setWordWrap(true);
		
		amount_layout = new QHBoxLayout();
		amount_layout.addWidget(amount_label);
		amount_layout.addWidget(amount);
		
		size_layout = new QHBoxLayout();
		size_layout.addWidget(size_label);
		size_layout.addWidget(size);
		this.setStyleSheet("QLineEdit { font-size: 11px; }");
		//sender signal om at size er endret
		//size.currentStringChanged.connect(this,"pizzaSizeChanged(String)");
		
		//Sender signal om at antallet pizzaer er endret
		//amount.valueChanged.connect(this,"amountValueChanged(int)");
		
		//legge til de forskjellige tingene til griden
		this.grid.addWidget(pizza_name,1,0);
		
		this.grid.addWidget(ingridients,2,0);
		this.grid.addLayout(amount_layout, 2, 5);
		this.grid.addLayout(size_layout, 3, 5);
		this.grid.addWidget(price_label,3,0);
		this.grid.addWidget(labComments, 4, 0);
		this.grid.addWidget(comments, 5, 0);
		this.grid.addWidget(btnAdd, 5, 5);
		this.grid.setWidgetSpacing(10);
		
	}

	//hvis size blir endret s� m� prisen ogs� bli endret
	
	public void pizzaSizeChanged(String newSize){
		double newPrice = start_price;
		if (newSize.equals("Stor")) {
			newPrice = 250;
		}
		price_label.setText("Pris: "+price_format.format(newPrice));
	}
	
	public void signalBtnAdd() {
		String [] data = {
			pizza_name.text(),       //Pizza name
			size.currentText(),		//Size
			amount.currentText(),	//Amount
			Double.toString(start_price),//start_prize
			ingridients.text(), //ingridients
			comments.text() // comments
		};
		//Send data
		System.out.println("Signal sendt from pizza ");
		signalPizza.emit(data);
		
		
	}
}
