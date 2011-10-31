package PizzaPck;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLayout.SizeConstraint;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QSizePolicy.Policy;
import com.trolltech.qt.gui.QWidget;

/**
 * 
 * @author Vegard
 *
 */
public class KundeWidget extends QWidget {

	private String fornavn, etternavn, adresse, poststed, postkode,telefonNr;

	private QPushButton btnSok, btnBestill, btnOppdater, btnLagre;
	private QLineEdit txtFornavn, txtEtternavn, txtAdresse, txtPoststed, txtPostkode, txtTelefonNr, txtSok;
	private QLabel labFornavn, labEtternavn, labAdresse, labPostSted, labtPostKode, labTelefon, labSok;
	private QGridLayout layout;
	
	//Database instance
	private DB db;
	
	//Current customer
	private String[] tmpCustomer;
	
	//Signal handler
	public Signal1<Integer> signalCustomer = new Signal1<Integer>();
	public Signal1<Boolean> changeTab = new Signal1<Boolean>();
	//Not used at the moment
	//public Signal1<String[]> customerInfo = new Signal1<String[]>();
	

	
	/**
	 * 
	 */
	public KundeWidget(DB db) {
		this.db = db;
		setUp();
	}

	/**
	 * privat metode som setter opp kundewidgeten 
	 */
	private void setUp(){

		//setter opp de akutelle layoutene
		layout = new QGridLayout(this);


		/*
		 * Denne linjen gjør at ting ikke blir strekt ut det som er plassert på layout. 
		 * Da vil alt som er addet til layout også få samme policy. 
		 * Vil du få dette til å gjelde for label eller lineedit, da kan du burke setSizePolicy
		 */
		layout.setSizeConstraint(SizeConstraint.SetFixedSize); // 



		/*
		 * oppretter 
		 */

		//tekstfelt
		txtSok = new QLineEdit();	
		txtFornavn = new QLineEdit();
		txtEtternavn = new QLineEdit();
		txtAdresse = new QLineEdit();
		txtPoststed = new QLineEdit();
		txtPostkode = new QLineEdit();
		txtTelefonNr = new QLineEdit();

		//labels
		labFornavn = new QLabel("Fornavn");
		labEtternavn = new QLabel("Etternavn");
		labAdresse = new QLabel("Adresse");
		labPostSted = new QLabel("Poststed");
		labtPostKode = new QLabel("Postkode");
		labTelefon = new QLabel("Telefon");
		labSok = new QLabel("Søk");



		//knapper
		btnSok = new QPushButton("Søk");
		btnBestill = new QPushButton("Bestill");
		btnLagre = new QPushButton("Lagre kunde");
		btnOppdater = new QPushButton("Oppdater kunde");
		
		//Clicked
		btnSok.clicked.connect(this, "findCustomer()");
		btnLagre.clicked.connect(this, "insertCustomer()");
		btnOppdater.clicked.connect(this, "updateUser()");
		btnBestill.clicked.connect(this, "sendCustomer()");
		//btnBestill.clicked.connect(this, "sendCustomer()");
		
		
		/*
		 * plasserer knapper, labels og tekstfelt slik i layout
		 */
		

		//oppsett av layout
		layout.addWidget(txtSok, 0, 1);
		layout.addWidget(btnSok, 0, 2);

		layout.addWidget(labFornavn, 1, 0);
		layout.addWidget(txtFornavn, 1, 1);

		layout.addWidget(labEtternavn, 2, 0);
		layout.addWidget(txtEtternavn, 2, 1);

		layout.addWidget(labAdresse, 3, 0);
		layout.addWidget(txtAdresse, 3, 1);

		layout.addWidget(labPostSted, 4, 0);
		layout.addWidget(txtPoststed, 4, 1);

		layout.addWidget(labtPostKode, 5, 0);
		layout.addWidget(txtPostkode, 5, 1);

		layout.addWidget(labTelefon, 6, 0);
		layout.addWidget(txtTelefonNr, 6, 1);

		layout.addWidget(btnOppdater, 7, 0);
		layout.addWidget(btnLagre, 7, 1);

		layout.addWidget(btnBestill, 8, 2);

	}
	
	/**
	 * DATA IS PROTECTED!!! Wrapper functions are used to perform tasks on the gui
	 */
	private void insertCustomer() {
		//Get information from gui
		String [] data = getFields();
		//Remember the last customer
		tmpCustomer = data;
		
		customer c = new customer(data);
		
		//Insert throws exception
		try {
			db.insert(c);
		}catch(RuntimeException err) {
			//TODO: Add message box ?
			System.out.println("INSERT: Invalid input");
			
		}
		clearFields();
	}
	
	/**
	 * clear fields
	 */
	private void clearFields(){
		txtSok.clear();
		txtFornavn.clear();
		txtEtternavn.clear();
		txtAdresse.clear();
		txtPoststed.clear();
		txtPostkode.clear();
		txtTelefonNr.clear();
		txtAdresse.clear();
	}
	
	/**
	 * finds the customer in the database
	 */
	private void findCustomer() {
		//Search after the given query and return information from kunde (table)
		try {
			String res[] = db.Search(txtSok.text(), false, false);
			//Save current customer
			tmpCustomer = res;
			txtFornavn.setText(res[0]); 	//Fornavn
			txtEtternavn.setText(res[1]); 	//Etternavn
			txtAdresse.setText(res[2]);   	//Adresse
			txtPoststed.setText(res[3]); 	//Sted
			txtPostkode.setText(res[4]);	//Postkode
			txtTelefonNr.setText(res[5]);	//Telefon 
		
		}catch(RuntimeException err) {
			System.out.println("SEARCH: findCustomer() failed");
		}
		
	}
	
	/**
	 * Update user based on CustomerID
	 */
	private void updateUser(){
		try {
			String[] customer = getFields();
			String SearchQuery = ""+tmpCustomer[0]+" "+tmpCustomer[1]+"";
			db.Update(customer, db.Search(SearchQuery, true, false));
		}catch(RuntimeException err) {
			System.out.println("SEARCH: updateUser() failed");
		}
	}
	
	/**
	 * Return information from txtBoxes
	 * @return
	 */
	private String[] getFields(){
		String[] data =  {
			txtFornavn.text(),
			txtEtternavn.text(),
			txtAdresse.text(),
			txtPoststed.text(),
			txtPostkode.text(),
			txtTelefonNr.text()	
		};
		
		return data;
	}
	
	private void sendCustomer() {
		if(tmpCustomer == null) {
			System.out.println("No customer");
			return;
		}
		
		try {
			String SearchQuery = ""+tmpCustomer[0]+" "+tmpCustomer[1]+"";
			String[]tmp = db.Search(SearchQuery, true, false);
			int customerID = Integer.parseInt(tmp[0]);
			signalCustomer.emit(customerID);
			changeTab.emit(true);
			
		}catch(RuntimeException err) {
			System.out.println("sendCustomer() failed");
		}
		
		clearFields();
	}



}