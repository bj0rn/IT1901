package PizzaPck;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.Timer;

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
 * this class creates the gui for displaying the 
 * chef's user interface 
 *
 */
public class Delivery extends QWidget{
	
	private DB db;
	private QListWidget deliveryList;
	private QGridLayout layout;
	private QPushButton btnDelivered, update;
	private QPushButton receipt;
	private Map map;
	private QVBoxLayout map_lay;
	private ArrayList<String[]> mirrorDeliveryList; 
	private int row;
	private PrintReceipt print;

	
	/**
	 * The constructor receives a reference to a
	 * db class for accessing the database methods
	 * @param db
	 */
	public Delivery(DB db){
		this.db = db;
		map = new Map();
		setup();
	}
	
	
	/**
	 * this method setup the interface for the delivery
	 * 
	 */
	protected void setup(){
		
		deliveryList = new QListWidget();
		
		
		mirrorDeliveryList = new ArrayList<String[]>();
		layout = new QGridLayout(this);
		
		receipt = new QPushButton("Skriv ut");
		btnDelivered = new QPushButton("Levering");
		update = new QPushButton("Oppdater");
		map_lay = new QVBoxLayout();
		map_lay.addWidget(map);
		

		layout.addLayout(map_lay, 1, 1);
		layout.addWidget(update, 2, 0);
		layout.addWidget(btnDelivered,2,1);
		layout.addWidget(receipt,2,2);
		
		getDeliveries();
		update.clicked.connect(this,"getDeliveries()");
		btnDelivered.clicked.connect(this, "setOrderDelivered()");
		receipt.clicked.connect(this,"print()");
	}
	
	/**
	 * Takes the selected element in deliveryList and sets the order status to delivered
	 */
	public void setOrderDelivered(){
		String[] tmp = mirrorDeliveryList.get(row);
		String orderID = tmp[0];
		
		db.updateDeliveredStatus(orderID);
		getDeliveries();
		map.loadMap(new QUrl(map.getMap("Bispegata 5  7032 Trondheim","")));
	}
	
	
	/**
	 * creates a print receipt and displays it
	 *  
	 */
	public void print(){
		String[] tmp = mirrorDeliveryList.get(row);
		
		ArrayList<String[]> receiptProducts = db.getReceipt(tmp[0]);
		
		try {
			print = new PrintReceipt(tmp[0],tmp[2], receiptProducts, tmp[6]);
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.println("Det er en tom liste");
		}		
		print.show();
	}
	
	/**
	 * get all orders from the database where the finish status is set to true
	 */
	public void getDeliveries() {
		
		deliveryList = new QListWidget();
		layout.addWidget(deliveryList, 1, 0);
		mirrorDeliveryList = new ArrayList<String[]>();
		ArrayList<String[]> list = db.getAllDeliveries();
		
		if(list == null) {
			return;
		}
		
		for(int i = 0; i <= list.size()-1; i++) {
			
			deliveryList.addItem(format(list.get(i)));
			mirrorDeliveryList.add(list.get(i));
			
		}
		deliveryList.clicked.connect(this, "showDeliveries()");
	}
	
	/**
	 * displays the map for delivery 
	 */
	public void showDeliveries(){
		row = deliveryList.currentIndex().row();
		
		StringBuilder toAdress = new StringBuilder();
		String[] tmp = mirrorDeliveryList.get(row);
		
		toAdress.append(tmp[10]+" ");
		toAdress.append(tmp[11]+" ");
		toAdress.append(tmp[12]+" ");
		map.loadMap(new QUrl(map.getMap("Bispegata 5  7032 Trondheim",toAdress.toString())));
	}
	
	/**
	 * formats the string displayed in deliverylist 
	 * @param data
	 * @return
	 */
	private String format(String[] data) {
		StringBuilder sb = new StringBuilder();
			sb.append(data[0]+":  "); //Ordre nummer
			sb.append((data[2].equals("1") ? "Skal leveres " : "Skal IKKE leveres "));
			sb.append(data[8]+" ");//Fornavn
			sb.append(data[9]+" ");//Etternavn
			sb.append(data[6]+"\n");//leverings dato+ time
			sb.append(data[10]+", ");//adresse
			sb.append(data[11]+" ");//postnummer 
			sb.append(data[12]+", ");//zipcode
			sb.append(data[13]+"\n");//telefonnummer
		return sb.toString();
		
	}
}
