package PizzaPck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.trolltech.qt.core.QDateTime;
import com.trolltech.qt.core.QTime;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
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

	private QGridLayout layout;
	private QDateTime dateTime;
	private QPushButton btnFinish;
	private ArrayList<String[]> mirrorOrderList; 
	public Signal1<Boolean> signalKitchen = new Signal1<Boolean>();
	private QListWidget orderList;
	
	
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
		
		
	
		order = new QTextBrowser();
		orderList = new QListWidget();
		
		mirrorOrderList = new ArrayList<String[]>();
		
		
		
		//TODO: klokka m� fikses. m� ogs� legge til en i ordergui.
		dateTime = new QDateTime(QDateTime.currentDateTime());
		dateTime.setTime(QTime.currentTime());
		
		//knappen som fullf�rer en ordre
		btnFinish = new QPushButton("Fullf�r ordre");
		
		layout.addWidget(new QLabel(dateTime.toString()),0,0);
		layout.addWidget(order, 1, 1);
		
		layout.addWidget(btnFinish,2,1);
		getOrders();
		
		
		
	}
	
	public void hei() {
		int row = orderList.currentIndex().row();
		QListWidgetItem test = orderList.item(row);
		
		System.out.println(test.text());
	}
	
	public void showOrder() {
		order.clear();
		if(orderList == null) {
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		int row = orderList.currentIndex().row();
		
		String[] tmp = mirrorOrderList.get(row);
		ArrayList  <String[]> list = db.displayOrders(tmp[0]);
		System.out.println("Hello");
		for (String[] strings : list) {
			sb.append(strings[2]);
			sb.append(" ");
			sb.append(strings[3]);
			sb.append(" ");
			sb.append(strings[4]);
			sb.append(" ");
			sb.append("\n");
			
		}
		order.setText(sb.toString());
		
	}
	
	
	
	public void getOrders() {
		orderList = new QListWidget();
		layout.addWidget(orderList, 1, 0);
		ArrayList<String[]> list = db.getAllOrders();
		if(list == null) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		String[] tmp;
		for(int i = 0; i <= list.size()-1; i++) {
			//tmp = list.get(i);
			orderList.addItem(format(list.get(i)));
			mirrorOrderList.add(list.get(i));
		}
		orderList.clicked.connect(this, "showOrder()");
	}
	
	private String format(String[] data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i]);
			sb.append(" ");
		}
		return sb.toString();
		
	}
	
	public void displayOrders() {
		int row = orderList.currentIndex().row();
		QListWidgetItem item = orderList.item(row);
		String delimiters = " ,";
		String tmp = item.toString();
		String[] orderTmp = tmp.split(delimiters);
		
		
		
	}
	
	public void updateKitchen(Boolean bool) {
			System.out.println("Hello world");
			getOrders();
	}
	
}
