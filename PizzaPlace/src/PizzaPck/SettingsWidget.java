package PizzaPck;


import java.sql.Connection;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSizePolicy.Policy;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;



/**
 * 
 * @author Vegard
 *
 */
public class SettingsWidget extends QWidget {
	
	//knapper, tekstfelt og labels
	private QLineEdit txtName, txtContents, txtPrice, txtBorder, txtDelivery;
	private QLabel labName,labContents,labPrice, labBorder, labDelivery, txtLimit;
	private QGridLayout pizzaLayout;
	private QGridLayout borderLayout;
	QVBoxLayout box;
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
		if (Float.valueOf(var) < 0){
			txtLimit.setStyleSheet("QLabel { color : red; }");
			txtLimit.setText(" Kan ikke være negativ, pris er "+ DELIVERY_PRICE + " og nåværende grense er " + DELIVERY_LIMIT);
		}
		else{
		DELIVERY_PRICE = Float.valueOf(var);
		txtLimit.setStyleSheet("QLabel { color : black; }");
		txtLimit.setText("Nåværende grense er " + DELIVERY_LIMIT+" og nåværende pris er " +DELIVERY_PRICE);
		}
	}
	
	public void setLimit(){
		String var = txtBorder.text();
		if (Float.valueOf(var) < 0){
			txtLimit.setStyleSheet("QLabel { color : red; }");
			txtLimit.setText("Kan ikke være negativ, grense er " + DELIVERY_LIMIT + " og nåværende pris er " +DELIVERY_PRICE);
		}
		else{
		DELIVERY_LIMIT = Float.valueOf(var);
		txtLimit.setStyleSheet("QLabel { color : black; }");
		txtLimit.setText("Nåværende grense er " + DELIVERY_LIMIT+" og nåværende pris er " +DELIVERY_PRICE);
		}
	}
	
	
	
	/**
	 * generates a new 
	 */
	private void setUp(){
		
		
		//creates new instances of buttons, labels, lineEdit, etc
		box = new QVBoxLayout();
		pizzaLayout = new QGridLayout();
		borderLayout = new QGridLayout();
		
		
		groupBoxPizza = new QGroupBox("Legg til nye pizzaer");
		groupBoxPizza.setLayout(pizzaLayout);
		this.setLayout(box);
		
		groupBoxPrice = new QGroupBox("Endre prisgrense for gratis levering og leveringsgebyr");
		groupBoxPrice.setLayout(borderLayout);
		
		labBorder = new QLabel("Ny grense");
		txtBorder = new QLineEdit();
		txtBorder.setFixedWidth(200);
		labDelivery = new QLabel("Ny Leveringspris");
		txtDelivery = new QLineEdit();
		txtDelivery.setFixedWidth(200);
		btnChange = new QPushButton("Endre grensen");
		btnChangeDelivey = new QPushButton("Endre leveringspris");
		
		
		labName = new QLabel("Navn p� pizza:");
		txtName = new QLineEdit();
		txtName.setFixedWidth(200);
		labContents = new QLabel("Ingredienser");
		txtContents = new QLineEdit();
		txtContents.setFixedWidth(200);
		labPrice = new QLabel("Pris");
		txtPrice = new QLineEdit();
		txtPrice.setFixedWidth(200);
		btnAddpizza = new QPushButton("Legg til pizza i DB");
		txtLimit = new QLabel("Nåværende grense er " + DELIVERY_LIMIT + " og nåværende pris er " +DELIVERY_PRICE);
		
		
		box.addWidget(groupBoxPizza);
		box.addWidget(groupBoxPrice);
		
		//add buttons, labels and lineedits to borderlayout
		borderLayout.addWidget(txtLimit, 0, 0, 1, 0);
		borderLayout.addWidget(labBorder, 1, 0);
		borderLayout.addWidget(txtBorder, 1, 1);
		borderLayout.addWidget(btnChange, 1, 2);
		txtLimit.setSizePolicy(Policy.Fixed, Policy.Fixed);
		btnChange.clicked.connect(this, "setLimit()");
		
		borderLayout.addWidget(labDelivery, 2, 0);
		borderLayout.addWidget(txtDelivery, 2, 1);
		borderLayout.addWidget(btnChangeDelivey, 2, 2);		
		btnChangeDelivey.clicked.connect(this, "setDeliveyPrice()");
		
		//add buttons, labels and lineedits to pizzalayout
		pizzaLayout.addWidget(labName, 0, 0);
		pizzaLayout.addWidget(txtName, 0, 1);		
		pizzaLayout.addWidget(labContents, 1, 0);
		pizzaLayout.addWidget(txtContents, 1, 1);		
		pizzaLayout.addWidget(labPrice, 2, 0);
		pizzaLayout.addWidget(txtPrice, 2, 1);		
		pizzaLayout.addWidget(btnAddpizza, 3, 2);
		btnAddpizza.clicked.connect(this, "insertIntoDB()");
	}
}
