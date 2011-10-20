package PizzaPck;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.trolltech.qt.core.QDate;
import com.trolltech.qt.core.QTime;
import com.trolltech.qt.gui.QDateEdit;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QRadioButton;
import com.trolltech.qt.gui.QSizePolicy.Policy;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QTimeEdit;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class OrderGUI extends QWidget{

	private DB db;
	private int customerID;

	public Signal1<Boolean> test = new Signal1<Boolean>();
	public Signal1<Integer> signalCustomer = new Signal1<Integer>();
	public Signal1<String[]> signalBridge = new Signal1<String[]>();
	public Signal1<Boolean> signalKitchen = new Signal1<Boolean>();
	//Mirror all the element contained in listProducts
	public List<String[]> listProductsMirror;
	
	//Not used at the moment 
	//public Signal1<String[]> customerInfo = new Signal1<String[]>(); 
	
	protected PizzaList order_list;
	protected QRadioButton delivery;
	protected QRadioButton pickup;
	protected QGridLayout main;

	private QListWidget listProducts;
	private QTextBrowser textCustomer;

	public OrderGUI(DB db){
		this.db = db;

		listProductsMirror = new ArrayList<String[]>();
		
		// alt som har med venstre widget � gj�re
		QGroupBox boxLeft = new QGroupBox();
		boxLeft.setFixedWidth(270);
		QVBoxLayout layoutLeft = new QVBoxLayout();
		boxLeft.setLayout(layoutLeft);

		textCustomer = new QTextBrowser();
		textCustomer.setSizePolicy(Policy.Maximum, Policy.Maximum);
		layoutLeft.addWidget(textCustomer);

		listProducts = new QListWidget();
		listProducts.setSizePolicy(Policy.Maximum, Policy.Maximum);

		layoutLeft.addWidget(listProducts);
		



		//�verste widgeten ikke s� viktig med det f�rste

		delivery = new QRadioButton("Levering");
		delivery.setChecked(true);
		pickup = new QRadioButton("Hente selv");

		//the clock
		QDateEdit changeDate = new QDateEdit();
		changeDate.setDate(QDate.currentDate());

		QTimeEdit changeTime = new QTimeEdit();
		changeTime.setTime(QTime.currentTime());
		
		
		//layouter
		QVBoxLayout layoutRight= new QVBoxLayout();
		QGroupBox boxRight = new QGroupBox();
		boxRight.setFixedWidth(600);
		boxRight.setLayout(layoutRight);

		order_list = new PizzaList(db);
		order_list.setContentsMargins(1, 1, 1, 1);
		//order_list.setFixedWidth(600);
		order_list.scrollarea.setFixedWidth(550);
		layoutRight.addWidget(order_list);
		
		QPushButton btnConfirm = new QPushButton("Bekreft");
		QPushButton btnDelete = new QPushButton("Slett");
		QPushButton update = new QPushButton("Oppdater");
		//setter tingen inn i base widget
		main = new QGridLayout(this);

		QHBoxLayout top = new QHBoxLayout();
		top.addWidget(delivery);
		top.addWidget(pickup);
		top.addWidget(new QLabel("Dato for levering:"));
		top.addWidget(changeDate);
		top.addWidget(new QLabel("Tid for levering:"));
		top.addWidget(changeTime);
		top.addWidget(update);
		
		
		main.addLayout(top, 0, 0, 1, 0);
		main.addWidget(boxLeft, 1, 0);
		main.addWidget(boxRight, 1, 1);
		main.addWidget(btnConfirm, 2, 1);
		main.addWidget(btnDelete, 2, 0);

		//Test function 
		//HelperTest();
		//insertOrder();
		
		
		//Get signal from pizzaList
		order_list.signalBridge.connect(this, "handleListProducts(String[])");
		listProducts.doubleClicked.connect(this, "removeFromLists()");
		btnConfirm.clicked.connect(this, ("confirmOrders()"));

	}
	
	//Not part of the program. Used for testing only
	@SuppressWarnings("deprecation")
	public void HelperTest() {
		System.out.println(delivery.isChecked());
		System.out.println(new Timestamp(new Date().getTime()));
		int val = delivery.isChecked()? 1 : 0;
		System.out.println(val);
}
	public Signal1<Boolean> signalConfirm = new Signal1<Boolean>();
	public void confirmOrder(){
		signalConfirm.emit(true);
	}
	
	
	public void hei() {
		System.out.println("Fill list");
		order_list.fillList();
	}

	public void insertOrder() {
		String currentTime = new java.sql.Timestamp(new java.util.Date().getTime()).toString();
		Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
		int h = time.getHours();
		time.setHours(h +1);
		String del = delivery.isChecked()? "1":"0";
		
		String test = "0";
		String test1 = "0";
		
		System.out.println("CustomerID "+customerID+"");
		
		String [] data = {
				Integer.toString(customerID),
				del,
				"0",
				"0",
				time.toString(),
				currentTime
		};
		try {
			db.insert(new orders(data));
		}catch(RuntimeException err) {
			System.out.println("INSERT: insert order failed ");
			err.printStackTrace();
		}
	}
	
	
	public void test() {
		int index = listProducts.currentRow();
		
	}

	/**
	 *
	 * @param customerID
	 */
	public void displayCustomer(int customerID) {
		try {
			this.customerID = customerID;
			String query = Integer.toString(customerID);
			System.out.println(query);
			String[] data = db.Search(query, false, true);
			StringBuilder build = new StringBuilder();
			String[] fields = {"Fornavn: ","Etternavn: ","Adresse: ","Poststed: ","Postkode: ", "Telefon: "};
			for (int i = 0; i < data.length; i++) {
				build.append(fields[i]+ data[i]+"\n");
			}
			textCustomer.setText(build.toString());
		
			insertOrder();

		}catch(RuntimeException err) {
			System.out.println("SEARCH: displayCustomer() failed");
			err.printStackTrace();
		}
	}
	
	
	public void handleListProducts(String[] data) {
		System.out.println("Insert completed");
		listProductsMirror.add(data);
		//Iterator<String[]> iter = listProductsMirror.iterator();
		String tmp = format(data);
		listProducts.addItem(tmp);
		
		
//		while(iter.hasNext()) {
//			tmp = format(iter.next());
//			listProducts.addItem(tmp);
//		}
		System.out.println("Størrelsen på mirror list"+listProductsMirror.size()+"");
	}
	
	public void removeFromLists() {
		int row = listProducts.currentIndex().row();
		listProducts.takeItem(row);
		listProductsMirror.remove(row);
		System.out.println("Størrelse på listen"+listProductsMirror.size()+"");
	}
	
	//Pizza_name
	//size 
	//Amount
	//Price
	//ingridients
	//comments
	public String format(String[] data) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 4; i++) {
			sb.append(data[i]);
			sb.append("  ");
		}
		
		return sb.toString();
	}
	
	public void confirmOrders() {
		Iterator<String[]> iter = listProductsMirror.iterator();
		while(iter.hasNext()) {
			String[] tmp = iter.next();
			String size;
			if (tmp[1].equals("Stor")){
				size = "1";
			}
			else{
				size = "0";
			}
			String [] data = {
					db.getOrderID(),
					db.getPizzaID(tmp[0]),
					tmp[5],
					size,
					tmp[2]
			};
			
			db.insert(new receipt(data));
			System.out.println(db.getPizzaID(tmp[0]));
			System.out.println(db.getOrderID());
			
			signalKitchen.emit(true);
			
			
		}
	}
	
}


