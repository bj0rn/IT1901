package PizzaPck;

import java.net.URLDecoder;
import java.net.URLEncoder;

import com.trolltech.qt.core.QDateTime;
import com.trolltech.qt.core.QTime;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * 
 * @author Linn
 *
 */
public class Delivery extends QWidget{
	
	private DB db;
	private QListWidget orderList;
	private QGridLayout layout;
	private QDateTime dateTime;
	private QPushButton btnFinish;
	private Map map;
	private QVBoxLayout map_lay;
	
	/**
	 * Delivery har kontakt med databasen for å få ut bestillinger. 
	 * Den henter også en map over hvor bestillingen skal
	 * @param db
	 */
	public Delivery(DB db){
		this.db = db;
		map = new Map();
		setup();
	}
	
	/**
	 * Denne metoden setter alt opp slik det skal være på skjermen
	 */
	protected void setup(){
		layout = new QGridLayout(this);
		String[] txtorder = {"Pizzalevering til kunde nr. 1","Pizzalevering til kunde nr.2","Her trenger man egentlig bare å vise ordrenummeret og klokkeslett når leveransen skal levers"};
		orderList = new QListWidget();
		
		
		orderList.addItem(txtorder[0]);
		orderList.addItem(txtorder[1]);
		orderList.addItem(txtorder[2]);
		
		dateTime = new QDateTime(QDateTime.currentDateTime());
		dateTime.setTime(QTime.currentTime());
		
		btnFinish = new QPushButton("Levering");
		map_lay = new QVBoxLayout();
		map_lay.addWidget(map);
		
		layout.addWidget(new QLabel(dateTime.toString()),0,0);
		layout.addWidget(orderList, 1, 0);
		layout.addLayout(map_lay, 1, 1);
		layout.addWidget(btnFinish,2,1);
		
		orderList.clicked.connect(this, "highlight()");
		
	}
	
	/**
	 * Får signal om at en order er klikket på
	 * og derifra kan man klikke på levering...
	 */
	public void highlight() {
		int row = orderList.currentIndex().row();
		QListWidgetItem test = orderList.item(row);
		
		/**
		 * Dette kallet får opp en ny map på den aktuelle bestillingen
		 * som blik klikket på i venstre kolonne. Vi må derfor ta inn 
		 * info om adressen til kunden i denne metoden når vi skal loade nytt map
		 */
		map.loadMap(new QUrl(map.getMap("Kringsjåvegen 51 7032 Trondheim","Prinsens Gate 1 7011 Trondheim", 8)));
		//her må vi få ut adressen dit vi skal... slik at vi kan oppdatere kart
		
		System.out.println(test.text());
	}
}
