package PizzaPck;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QRadioButton;
import com.trolltech.qt.gui.QScrollArea;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;


/*
 * The OrderGUI class, which connects all the parts of the orderFrame together
 * 
 * @author: Susanne and Linn
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */


public class OrderGUI extends QWidget{

	protected QVBoxLayout bottom_right;
	protected QVBoxLayout bottom_left;
	protected QHBoxLayout top_left;
	protected QHBoxLayout top_right;
	protected QGridLayout bottom_left_stuff;
	
	protected PizzaList order_list;

	protected QLabel costumer;
	protected QLabel costumer_name;
	protected QLabel costumer_adress;
	protected QLabel costumer_zip;
	protected QLabel costumer_phone;
	
	protected QLabel price_mva;
	protected QLabel delivery_price;
	protected QLabel total_price;
	
	
	protected QRadioButton delivery;
	protected QRadioButton pickup;
	
	protected QGridLayout costumer_layout;
	protected QGridLayout price_layout;
	protected QGridLayout top_layout;
	protected QVBoxLayout pizza_layout;
	
	protected QGridLayout main;
	
	DB db;
	
	//Signal handler
	public Signal1<Boolean> test = new Signal1<Boolean> ();
	

	public OrderGUI(DB db){
		this.db = db;
		
		
		this.bottom_left = new QVBoxLayout();
		this.bottom_right = new QVBoxLayout();
		this.top_left = new QHBoxLayout();
		this.top_right = new QHBoxLayout();
		this.bottom_left_stuff = new QGridLayout();
		
		this.costumer_layout = new QGridLayout();
		this.price_layout = new QGridLayout();
		this.top_layout = new QGridLayout();
		this.pizza_layout = new QVBoxLayout();
		
		this.costumer= new QLabel("Kunde : ");
		this.costumer_adress= new QLabel("Adresse: "+"Hekkveien 2");
		this.costumer_name= new QLabel("Navn: "+"Kurt Olsen");
		this.costumer_phone= new QLabel("Telefon: "+"12345678");
		this.costumer_zip= new QLabel("Postnummer: "+"1234");
		
		this.costumer_layout.addWidget(costumer, 1,0);
		this.costumer_layout.addWidget(costumer_name, 2,0);
		this.costumer_layout.addWidget(costumer_adress, 3,0);
		this.costumer_layout.addWidget(costumer_zip, 4,0);
		this.costumer_layout.addWidget(costumer_phone, 5,0);
		
		this.delivery = new QRadioButton("Levering");
		this.delivery.toggled.emit(true);
		this.pickup = new QRadioButton("Hente selv");
		
		this.top_layout.addWidget(delivery, 1, 0);
		this.top_layout.addWidget(pickup, 1, 1);
		
		this.price_mva = new QLabel("Pris :"+"256.00");
		this.delivery_price = new QLabel("Levering: "+"60.00");
		this.total_price = new QLabel("Total: "+"316.00");
		
		this.price_layout.addWidget(price_mva, 1, 0);
		this.price_layout.addWidget(delivery_price, 2, 0);
		this.price_layout.addWidget(total_price, 3, 0);
		
		order_list = new PizzaList(db);
		
		main = new QGridLayout(this);
		
		QWidget price_w = new QWidget();
		price_w.setLayout(pizza_layout);
		
		this.top_left.addLayout(top_layout);
		this.bottom_left_stuff.addLayout(costumer_layout, 1,0);
		this.bottom_left_stuff.addLayout(price_layout, 2, 0);

		this.bottom_left.addLayout(bottom_left_stuff);
		this.bottom_right.addWidget(order_list);
		
		
		
		main.addLayout(top_left, 0, 0);
		main.addLayout(bottom_left, 1, 0);
		main.addLayout(bottom_right, 1, 1);
		this.setFixedSize(800,600);
	}
	
	public void addPizzaList(PizzaList a){
		order_list = a;
	}
	
	public void hei() {
		System.out.println("Fill list");
		order_list.fillList();
	}

}
