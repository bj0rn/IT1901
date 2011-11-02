package PizzaPck;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

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
	private QListWidget deliveryList;
	private QGridLayout layout;
	private QDateTime dateTime;
	private QPushButton btnDelivered;
	private QPushButton receipt;
	private Map map;
	private QVBoxLayout map_lay;
	private ArrayList<String[]> mirrorDeliveryList; 
	private int row;
	private PrintReceipt print;
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
		deliveryList = new QListWidget();
		
		mirrorDeliveryList = new ArrayList<String[]>();
		layout = new QGridLayout(this);
		
		receipt = new QPushButton("Skriv ut");
		btnDelivered = new QPushButton("Levering");
		map_lay = new QVBoxLayout();
		map_lay.addWidget(map);
		

		layout.addLayout(map_lay, 1, 1);
		layout.addWidget(btnDelivered,2,1);
		layout.addWidget(receipt,2,2);
		
		getDeliveries();
		
		btnDelivered.clicked.connect(this, "setOrderDelivered()");
		receipt.clicked.connect(this,"print()");
	}
	
	public void setOrderDelivered(){
		String[] tmp = mirrorDeliveryList.get(row);
		String orderID = tmp[0];
		
		db.updateDeliveredStatus(orderID);
		getDeliveries();
		map.loadMap(new QUrl(map.getMap("Bispegata 5  7032 Trondheim","")));
	}
	
	public void print(){
		String[] tmp = mirrorDeliveryList.get(row);
		
		ArrayList<String[]> receiptProducts = db.getReceipt(tmp[0]);
		
		try {
			print = new PrintReceipt(tmp[0],tmp[2], receiptProducts);
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.println("Det er en tom liste");
		}		
		print.show();
	}
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
	
	
	public void showDeliveries(){
		row = deliveryList.currentIndex().row();
		
		StringBuilder toAdress = new StringBuilder();
		String[] tmp = mirrorDeliveryList.get(row);
		
		toAdress.append(tmp[10]+" ");
		toAdress.append(tmp[11]+" ");
		toAdress.append(tmp[12]+" ");
		map.loadMap(new QUrl(map.getMap("Bispegata 5  7032 Trondheim",toAdress.toString())));
	}
	
	
	private String format(String[] data) {
		StringBuilder sb = new StringBuilder();
			sb.append(data[0]+":  "); //Ordre nummer
			sb.append((data[2].equals("1") ? "Skal leveres " : "Skal IKKE leveres "));
			sb.append(data[8]+" ");//Fornavn
			sb.append(data[9]+" ");//Etternavn
			sb.append(data[5]+"\n");//leverings dato+ time
			sb.append(data[10]+", ");//adresse
			sb.append(data[11]+" ");//postnummer 
			sb.append(data[12]+", ");//zipcode
			sb.append(data[13]+"\n");//telefonnummer
		return sb.toString();
		
	}
	
	/**
	 * Får signal om at en order er klikket på
	 * og derifra kan man klikke på levering...
	 */
	public void highlight() {
		int row = deliveryList.currentIndex().row();
		QListWidgetItem test = deliveryList.item(row);
		
		/**
		 * Dette kallet får opp en ny map på den aktuelle bestillingen
		 * som blik klikket på i venstre kolonne. Vi må derfor ta inn 
		 * info om adressen til kunden i denne metoden når vi skal loade nytt map
		 */
		map.loadMap(new QUrl(map.getMap("Kringsjåvegen 51 7032 Trondheim","Prinsens Gate 1 7011 Trondheim")));
		//her må vi få ut adressen dit vi skal... slik at vi kan oppdatere kart
		
		System.out.println(test.text());
	}
}
