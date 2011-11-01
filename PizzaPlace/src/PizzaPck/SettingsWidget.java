package PizzaPck;


import java.sql.Connection;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;



/**
 * 
 * @author Vegard
 *
 */
public class SettingsWidget extends QWidget {
	
	//knapper, tekstfelt og labels
	private QLineEdit txtName, txtContents, txtPrice, txtBorder, txtDelivery;
	private QLabel labName,labContents,labPrice, labBorder, labDelivery;
	private QGridLayout pizzaLayout;
	private QGridLayout settingsLayout, borderLayout;
	private QPushButton btnAddpizza, btnChange, btnChangeDelivey;
	private QGroupBox groupBoxPizza;
	private QGroupBox groupBoxPrice;
	private DB db;
	public static float DELIVERY_LIMIT = 500.0f;
	public static float DELIVERY_PRICE = 50.0f;
	
	
	//Signal handler
	public Signal1 <Boolean> singalInsertProduct = new Signal1<Boolean>();
	
	
	/**
	 * DB objektet ordner slik at man f�r tilgang metoder som jobber mot databasen
	 * @param db
	 */
	public SettingsWidget(DB db){
		setUp();
		this.db = db;
	}
	
	
	
	/**
	 * Insert a new product from textfields into the database and then emits a signal whenever the button is clicked
	 */
	public void insertIntoDB(){

		String[] data ={
				txtName.text(),txtPrice.text(),txtContents.text()," "
		};
		
		try{
			db.insert(new product(data));
		}catch (RuntimeException e) {
			// TODO: handle exception
			System.out.println("insert gikk til dundass");
		}
		//Send signal indicating change in DB 
		System.out.println("Signal emited");
		singalInsertProduct.emit(true);
	}
	
	
	
	public void setDeliveyPrice(){
		String var = txtDelivery.text();
		DELIVERY_PRICE = Float.valueOf(var);
	}
	
	public void setLimit(){
		String var = txtBorder.text();
		DELIVERY_LIMIT = Float.valueOf(var);
	}
	
	
	
	/**
	 * generates a new 
	 */
	private void setUp(){
		
		
		//creates new instances of buttons, labels, lineEdit, etc
		settingsLayout = new QGridLayout();
		pizzaLayout = new QGridLayout();
		borderLayout = new QGridLayout();
		
		
		groupBoxPizza = new QGroupBox("Legg til nye pizzaer");
		groupBoxPizza.setLayout(pizzaLayout);
		this.setLayout(settingsLayout);
		
		groupBoxPrice = new QGroupBox("Endre prisgrense for gratis levering og leveringsgebyr");
		groupBoxPrice.setLayout(borderLayout);
		
		labBorder = new QLabel("Ny grense");
		txtBorder = new QLineEdit();
		labDelivery = new QLabel("Ny Leveringspris");
		txtDelivery = new QLineEdit();
		btnChange = new QPushButton("Endre grensen");
		btnChangeDelivey = new QPushButton("Endre leveringspris");
		
		btnAddpizza = new QPushButton("Legg til pizza i DB");
		
		txtName = new QLineEdit();
		txtContents = new QLineEdit();
		txtPrice = new QLineEdit();
		
		labName = new QLabel("Navn p� pizza:");
		labContents = new QLabel("Ingredienser");
		labPrice = new QLabel("Pris");
		
		
		settingsLayout.addWidget(groupBoxPizza);
		settingsLayout.addWidget(groupBoxPrice);
		
		
		borderLayout.addWidget(labBorder, 0, 0);
		borderLayout.addWidget(txtBorder, 0, 1);
		borderLayout.addWidget(btnChange, 0, 2);
		btnChange.clicked.connect(this, "setLimit()");
		
		borderLayout.addWidget(labDelivery, 1, 0);
		borderLayout.addWidget(txtDelivery, 1, 1);
		borderLayout.addWidget(btnChangeDelivey, 1, 2);
		btnChangeDelivey.clicked.connect(this, "setDeliveyPrice()");
		
		
		
		//add buttons, labels and lineedits to pizzalayout
		pizzaLayout.addWidget(labName, 0, 0);
		pizzaLayout.addWidget(txtName, 0, 1);
		
		pizzaLayout.addWidget(labContents, 1, 0);
		pizzaLayout.addWidget(txtContents, 1, 1);
		
		pizzaLayout.addWidget(labPrice, 2, 0);
		pizzaLayout.addWidget(txtPrice, 2, 1);
		
		pizzaLayout.addWidget(btnAddpizza, 3, 2);
		
		//
		btnAddpizza.clicked.connect(this, "insertIntoDB()");
	}
	
	
	
}
