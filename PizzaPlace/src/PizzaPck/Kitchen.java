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
 * Creates the kitchen widget for showing active orders
 * @author Vegard
 *
 */
public class Kitchen extends QWidget{

	private DB db;

	private QTextBrowser order;
	private PrintReceipt print;
	private QGridLayout layout;
	private QDateTime dateTime;
	private QPushButton btnFinish;
	private ArrayList<String[]> mirrorOrderList;
	public Signal1<Boolean> signalKitchen = new Signal1<Boolean>();
	public Signal1<Boolean> signalDelivery = new Signal1<Boolean>();
	public Signal1<Boolean> signalPrintReceipt = new Signal1<Boolean>();
	private QListWidget orderList;
	private int row;


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
		
		print = new PrintReceipt();
		
		layout = new QGridLayout(this);

		order = new QTextBrowser();
		orderList = new QListWidget();

		mirrorOrderList = new ArrayList<String[]>();

		//QPushButton reciept = new QPushButton("Kvitering");
		

		//TODO: klokka m� fikses. m� ogs� legge til en i ordergui.
		dateTime = new QDateTime(QDateTime.currentDateTime());
		dateTime.setTime(QTime.currentTime());

		//knappen som fullf�rer en ordre
		btnFinish = new QPushButton("Fullf�r ordre");

		layout.addWidget(new QLabel(dateTime.toString()),0,0);
		layout.addWidget(order, 1, 1);

		layout.addWidget(btnFinish,2,1);
		getOrders();
		btnFinish.clicked.connect(this, "finishOrder()");
		//layout.addWidget(reciept,2,2);
		//reciept.clicked.connect(this,"print()");

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
		ArrayList <String[]> list = db.displayOrders(tmp[0]);
		//System.out.println("Hello");
		for (String[] strings : list) {
			sb.append(strings[4] + " stk. ");
			if (strings[3].equals("1")){
				sb.append("Stor ");
			}
			else{
				sb.append("Liten ");
			}
			sb.append(strings[5]);
			sb.append("\n");
			sb.append(" -");
			sb.append(strings[6]);
			sb.append("\n");
			sb.append(" -");
			sb.append(strings[2]);
			sb.append("\n");
			sb.append("-----------------------------");
			sb.append("\n");

		}
		order.setText(sb.toString());

	}





	public void getOrders() {
		orderList = new QListWidget();
		layout.addWidget(orderList, 1, 0);
		mirrorOrderList = new ArrayList<String[]>();
		ArrayList<String[]> list = db.getAllOrders();

		if(list == null) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		String[] tmp;
		//sortOrders(list);
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
		row = orderList.currentIndex().row();
		QListWidgetItem item = orderList.item(row);
		String delimiters = " ,";
		String tmp = item.toString();
		String[] orderTmp = tmp.split(delimiters);



	}
//	public void print(){
//		PrintReceipt pr = new PrintReceipt();
//		pr.print();
//		signalPrintReceipt.emit(true);
//		System.out.println("PRIIIIIINT!!!");
//	}

	public void finishOrder(){
		String[] tmp = mirrorOrderList.get(row);
		String orderID = tmp[0];
		System.out.println(orderID);
		db.updateFinishStatus(orderID);
		getOrders();
		order.clear();
		signalDelivery.emit(true);
	}


	public void updateKitchen(Boolean bool) {
		System.out.println("Hello world");
		getOrders();
	}

}