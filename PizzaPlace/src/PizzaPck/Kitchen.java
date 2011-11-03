package PizzaPck;

import java.util.ArrayList;

import com.trolltech.qt.core.QDateTime;
import com.trolltech.qt.core.QTime;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QWidget;

/**
 * Creates the kitchen widget for showing active orders
 * @author
 *
 */
public class Kitchen extends QWidget{

	private DB db;

	private QTextBrowser order;
	private PrintReceipt print;
	private QGridLayout layout;
	private QDateTime dateTime;
	private QPushButton btnFinish,update;
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
	 * creates the kitchen GUI
	 */
	private void setUp() {
		
		layout = new QGridLayout(this);

		order = new QTextBrowser();
		orderList = new QListWidget();

		mirrorOrderList = new ArrayList<String[]>();
		
		//knappen som fullf�rer en ordre
		btnFinish = new QPushButton("Fullf�r ordre");
		update = new QPushButton("Oppdater");
		layout.addWidget(order, 1, 1);

		layout.addWidget(btnFinish,2,1);
		layout.addWidget(update, 2, 0);
		getOrders();
		btnFinish.clicked.connect(this, "finishOrder()");
		update.clicked.connect(this, "getOrders()");
		//layout.addWidget(reciept,2,2);
		//reciept.clicked.connect(this,"print()");

	}
	
	/**
	 * showOrder retrieves all products from the database according to the selected 
	 * element in the over all orders 
	 */
	public void showOrder() {
		row = orderList.currentIndex().row();
		order.clear();
		if(orderList == null) {
			return;
		}

		StringBuilder sb = new StringBuilder();

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


	/**
	 * Retrieves all orders and put them into the list over all active orders
	 */
	public void getOrders() {
		orderList = new QListWidget();
		layout.addWidget(orderList, 1, 0);
		mirrorOrderList = new ArrayList<String[]>();
		ArrayList<String[]> list = db.getAllOrders();

		if(list == null) {
			return;
		}
		//sortOrders(list);
		for(int i = 0; i <= list.size()-1; i++) {
			orderList.addItem(format(list.get(i)));
			mirrorOrderList.add(list.get(i));
		}
		orderList.clicked.connect(this, "showOrder()");
	}
	
	/**
	 * Transform a string array to a string
	 * See {@link StringBuilder}
	 * @param data
	 * @return
	 */
	private String format(String[] data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i]);
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 
	 */
	public void finishOrder(){
		String[] tmp = mirrorOrderList.get(row);
		String orderID = tmp[0];
		System.out.println(orderID);
		db.updateFinishStatus(orderID);
		getOrders();
		order.clear();
		signalDelivery.emit(true);
	}



}