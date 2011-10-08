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
	private QLineEdit txtNavn, txtInnhold, txtPris;
	private QLabel labNavn,labInnhold,labPris;
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
				txtNavn.text(),txtPris.text(),txtInnhold.text()," "
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
		
		txtNavn = new QLineEdit();
		txtInnhold = new QLineEdit();
		txtPris = new QLineEdit();
		
		labNavn = new QLabel("Navn pï¿½ pizza:");
		labInnhold = new QLabel("Ingredienser");
		labPris = new QLabel("Pris");
		
		
		//add buttons, labels and lineedits to pizzalayout
		pizzaLayout.addWidget(labNavn, 0, 0);
		pizzaLayout.addWidget(txtNavn, 0, 1);
		
		pizzaLayout.addWidget(labInnhold, 1, 0);
		pizzaLayout.addWidget(txtInnhold, 1, 1);
		
		pizzaLayout.addWidget(labPris, 2, 0);
		pizzaLayout.addWidget(txtPris, 2, 1);
		
		pizzaLayout.addWidget(btnAddpizza, 3, 2);
		
		//
		btnAddpizza.clicked.connect(this, "insertIntoDB()");
	}
	
	
	
}
