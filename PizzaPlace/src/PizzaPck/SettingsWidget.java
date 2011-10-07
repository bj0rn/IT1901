package PizzaPck;


import java.sql.Connection;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

public class SettingsWidget extends QWidget {
	
	//knapper, tekstfelt og labels
	QLineEdit txtNavn, txtInnhold, txtPris;
	QLabel labNavn,labInnhold,labPris;
	QGridLayout pizzaLayout = new QGridLayout(this);
	QPushButton btnAddpizza;
	DB db;
	
	
	//Signal handler
	public Signal1 <Boolean> test = new Signal1<Boolean>();
	
	public SettingsWidget(DB db){
		setUp();
		this.db = db;
	}
	
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
	
	public void setUp(){
		btnAddpizza = new QPushButton("Legg til pizza i DB");
		
		txtNavn = new QLineEdit();
		txtInnhold = new QLineEdit();
		txtPris = new QLineEdit();
		
		labNavn = new QLabel("Navn p� pizza:");
		labInnhold = new QLabel("Ingredienser");
		labPris = new QLabel("Pris");
		
		pizzaLayout.addWidget(labNavn, 0, 0);
		pizzaLayout.addWidget(txtNavn, 0, 1);
		
		pizzaLayout.addWidget(labInnhold, 1, 0);
		pizzaLayout.addWidget(txtInnhold, 1, 1);
		
		pizzaLayout.addWidget(labPris, 2, 0);
		pizzaLayout.addWidget(txtPris, 2, 1);
		
		pizzaLayout.addWidget(btnAddpizza, 3, 2);
		
		btnAddpizza.clicked.connect(this, "insertIntoDB()");
	}
	
	
	
}
