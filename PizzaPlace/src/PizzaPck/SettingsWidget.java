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
	private QLineEdit txtName, txtContents, txtPrice;
	private QLabel labName,labContents,labPrice;
	private QGridLayout pizzaLayout;
	private QPushButton btnAddpizza;
	private QGroupBox groupBoxPizza;
	private DB db;
	
	
	//Signal handler
	public Signal1 <Boolean> test = new Signal1<Boolean>();
	
	
	/**
	 * DB objektet ordner slik at man får tilgang metoder som jobber mot databasen
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
		test.emit(true);
	}
	
	
	
	/**
	 * generates a new 
	 */
	private void setUp(){
		
		
		//creates new instances of buttons, labels, lineEdit, etc
		pizzaLayout = new QGridLayout();
		
		groupBoxPizza = new QGroupBox("Legg til nye pizzaer");
		groupBoxPizza.setLayout(pizzaLayout);
		groupBoxPizza.setParent(this);
		
		btnAddpizza = new QPushButton("Legg til pizza i DB");
		
		txtName = new QLineEdit();
		txtContents = new QLineEdit();
		txtPrice = new QLineEdit();
		
		labName = new QLabel("Navn pï¿½ pizza:");
		labContents = new QLabel("Ingredienser");
		labPrice = new QLabel("Pris");
		
		
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
