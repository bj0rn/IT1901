package PizzaPck;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QRadioButton;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QSizePolicy.Policy;

public class OrderGUI extends QWidget{

	private DB db;
	private int customerID;
		
	public Signal1<Boolean> test = new Signal1<Boolean>();
	public Signal1<Integer> signalCustomer = new Signal1<Integer>();
	
	
	protected PizzaList order_list;
	protected QRadioButton delivery;
	protected QRadioButton pickup;
	protected QGridLayout main;
	
	private QListWidget listProducts;
	private QTextBrowser textCustomer;
	
	public OrderGUI(DB db){
		this.db = db;
		
		// alt som har med venstre widget å gjøre
		QGroupBox boxLeft = new QGroupBox();
		boxLeft.setFixedWidth(300);
		QVBoxLayout layoutLeft = new QVBoxLayout();
		boxLeft.setLayout(layoutLeft);
		
		textCustomer = new QTextBrowser();
		textCustomer.setSizePolicy(Policy.Maximum, Policy.Maximum);
		layoutLeft.addWidget(textCustomer);
		
		listProducts = new QListWidget();
		listProducts.setSizePolicy(Policy.Maximum, Policy.Maximum);

		
		layoutLeft.addWidget(listProducts);
		


		//øverste widgeten ikke så viktig med det første

		delivery = new QRadioButton("Levering");
		delivery.toggled.emit(true);
		pickup = new QRadioButton("Hente selv");

		QVBoxLayout layoutRight= new QVBoxLayout();
		QGroupBox boxRight = new QGroupBox();
		boxRight.setFixedWidth(600);
		boxRight.setLayout(layoutRight);
		
		order_list = new PizzaList(db);
		layoutRight.addWidget(order_list);

		QPushButton btnConfirm = new QPushButton("Bekreft");
		QPushButton btnDelete = new QPushButton("Slett");
		
		//setter tingen inn i base widget
		main = new QGridLayout(this);

		main.addWidget(boxLeft, 0, 0);
		main.addWidget(boxRight, 0, 1);
		main.addWidget(btnConfirm, 2, 1);
		main.addWidget(btnDelete, 2, 0);

	}

	public void hei() {
		System.out.println("Fill list");
		order_list.fillList();
	}
	
	//Click sensitive
	public void insertOrder() {
		//Get information from gui 
		String [] data = {
				
		};
		
		try {
			db.insert(new order(data));
		}catch(RuntimeException err) {
			System.out.println("INSERT: insert order failed ");
		}
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
			for (int i = 0; i < data.length; i++) {
				build.append(data[i]+"\n");
			}
			textCustomer.setText(build.toString());

		}catch(RuntimeException err) {
			System.out.println("SEARCH: displayCustomer() failed");
			err.printStackTrace();
		}
	}
}

