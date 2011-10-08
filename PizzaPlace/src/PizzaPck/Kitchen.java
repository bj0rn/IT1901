package PizzaPck;

import com.trolltech.qt.core.QDateTime;
import com.trolltech.qt.core.QTime;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QWidget;

/**
 *	Creates the kitchen widget for showing active orders  
 * @author Vegard
 *
 */
public class Kitchen extends QWidget{
	
	private DB db;
	
	private QTextBrowser order;
	private QListWidget orderList;
	private QGridLayout layout;
	private QDateTime dateTime;
	private QPushButton btnFinish;
	
	/**
	 * takes at DB as input and stores the reference to the databasehandler
	 * @param db
	 */
	public Kitchen(DB db){
		this.db = db;
		setUp();
	}

	/**
	 * creates the kitchen gui
	 */
	private void setUp() {
		
		layout = new QGridLayout(this);
		
		
		String tekst = "Her har jeg planer om at det skal vises liste over alt som skal lages med kommentarer. <br><br> Knappen nederst fullf�rer orderen for den markerte orderen i listen til venstre <br><br> dette er bare en id� p� hvordan det kan l�ses. er bare � skrike ut om det er noe som ikke stemmer";
		order = new QTextBrowser();
		order.setText(tekst);
		
		String[] txtorder = {"Her b�r man kunne trykke p� en ordre,", "deretter f� opp hva som skal lages i textEditen til h�yre.", "Her trenger man egentlig bare � vise ordrenummeret og klokkeslett n�r den skal v�re ferdig"};
		orderList = new QListWidget();
		
		orderList.addItem(txtorder[0]);
		orderList.addItem(txtorder[1]);
		orderList.addItem(txtorder[2]);
		
		//TODO: klokka m� fikses. m� ogs� legge til en i ordergui.
		dateTime = new QDateTime(QDateTime.currentDateTime());
		dateTime.setTime(QTime.currentTime());
		
		//knappen som fullf�rer en ordre
		btnFinish = new QPushButton("Fullf�r ordre");
		
		layout.addWidget(new QLabel(dateTime.toString()),0,0);
		layout.addWidget(order, 1, 1);
		layout.addWidget(orderList, 1, 0);
		layout.addWidget(btnFinish,2,1);
	
		
	}
}
