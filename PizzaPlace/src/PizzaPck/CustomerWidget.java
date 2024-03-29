package PizzaPck;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QCompleter;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLayout.SizeConstraint;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QStringListModel;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * This class handle all customer registration
 * @author Everyone
 *
 */
public class CustomerWidget extends QWidget {


	private QPushButton btnOrder, btnUpdate, btnSave;
	private QLineEdit txtFirstName, txtLastName, txtAdress, 
	txtCity, txtZipCode, txtPhone, txtSearch;

	private QLabel labFirstName, labLastName, 
	labAdress, labCity, labZipCode, labPhone, search;

	private QGridLayout layoutGrid;
	private QGroupBox groupbox;
	private QHBoxLayout layoutSearch;
	private QVBoxLayout layoutMain;
	private QListWidget list;

	//Database instance
	private DB db;
	private QStringListModel words;

	//Current customer
	private String[] tmpCustomer;
	private String customerid;

	//Signal handler
	public Signal1<Integer> signalCustomer = new Signal1<Integer>();
	public Signal1<Boolean> changeTab = new Signal1<Boolean>();

	/**
	 * Creates a new instance of "KundeWidget" and 
	 * @param db
	 */
	public CustomerWidget(DB db) {
		this.db = db;
		setUp();
	}

	/**
	 * This mehod creates the user interface 
	 * for the customer registration. 
	 */
	private void setUp(){

		//setter opp de akutelle layoutene

		groupbox = new QGroupBox();
		layoutGrid = new QGridLayout();
		layoutSearch = new QHBoxLayout();
		layoutMain = new QVBoxLayout(groupbox);
		layoutMain.addLayout(layoutSearch);
		layoutMain.addLayout(layoutGrid);
		groupbox.setParent(this);

		/*
		 * Denne linjen gjør at ting ikke blir strekt ut det som er plassert på layout. 
		 * Da vil alt som er addet til layout også få samme policy. 
		 * Vil du få dette til å gjelde for label eller lineedit, 
		 * da kan du burke setSizePolicy
		 */
		layoutGrid.setSizeConstraint(SizeConstraint.SetFixedSize); // 



		/*
		 * Oppretter de forskjellige info feltene 
		 */

		//tekstfelt
		txtSearch = new QLineEdit();	
		txtFirstName = new QLineEdit();
		txtLastName = new QLineEdit();
		txtAdress = new QLineEdit();
		txtCity = new QLineEdit();
		txtZipCode = new QLineEdit();
		txtPhone = new QLineEdit();

		//labels
		labFirstName = new QLabel("Fornavn");
		labLastName = new QLabel("Etternavn");
		labAdress = new QLabel("Adresse");
		labCity = new QLabel("Poststed");
		labZipCode = new QLabel("Postkode");
		labPhone = new QLabel("Telefon");
		search = new QLabel("Søk");


		words = new QStringListModel();
		QCompleter completer = new QCompleter(words);
		completer.setCaseSensitivity(Qt.CaseSensitivity.CaseInsensitive);
		txtSearch.setCompleter(completer);


		//knapper
		btnOrder = new QPushButton("Bestill");
		btnSave = new QPushButton("Lagre kunde");
		btnUpdate = new QPushButton("Oppdater kunde");

		//Clicked
		btnSave.clicked.connect(this, "insertCustomer()");
		btnUpdate.clicked.connect(this, "updateUser()");
		btnOrder.clicked.connect(this, "sendCustomer()");
		//btnBestill.clicked.connect(this, "sendCustomer()");

		txtSearch.textEdited.connect(this,"findCustomer()");
		completer.activated.connect(this,"insertInfo(String)");

		/*
		 * plasserer knapper, labels og tekstfelt slik i layout
		 */


		//oppsett av layout
		txtSearch.setFixedWidth(200);
		layoutSearch.addStretch(1);
		layoutSearch.addWidget(search);
		layoutSearch.addStretch(3);
		layoutSearch.addWidget(txtSearch);
		layoutSearch.addStretch(1);

		layoutGrid.addWidget(labFirstName, 1, 0);
		layoutGrid.addWidget(txtFirstName, 1, 1);

		layoutGrid.addWidget(labLastName, 2, 0);
		layoutGrid.addWidget(txtLastName, 2, 1);

		layoutGrid.addWidget(labAdress, 3, 0);
		layoutGrid.addWidget(txtAdress, 3, 1);

		layoutGrid.addWidget(labCity, 4, 0);
		layoutGrid.addWidget(txtCity, 4, 1);

		layoutGrid.addWidget(labZipCode, 5, 0);
		layoutGrid.addWidget(txtZipCode, 5, 1);

		layoutGrid.addWidget(labPhone, 6, 0);
		layoutGrid.addWidget(txtPhone, 6, 1);

		layoutGrid.addWidget(btnUpdate, 7, 0);
		layoutGrid.addWidget(btnSave, 7, 1);

		layoutGrid.addWidget(btnOrder, 8, 2);

	}

	/**
	 * DATA IS PROTECTED!!! 
	 * Wrapper functions are used to perform 
	 * tasks on the GUI.
	 */
	private void insertCustomer() {
		//Get information from gui
		try {
			String [] data = getFields();
			//Remember the last customer
			tmpCustomer = data;
			customer c = new customer(data);
			
			db.insert(c);
			ArrayList <String[]> tmp = db.search(data[5], false, false);
			tmpCustomer = tmp.get(0);
		}catch(RuntimeException err) {
			ErrorMessage.invalidInput(this, 
			"Ikke gyldig format på innsetting. "+
			"Telefon nummer eller postnummer er feil");

		}

	}

	/**
	 * this method clear all fields.
	 */
	private void clearFields(){
		txtSearch.clear();
		txtFirstName.clear();
		txtLastName.clear();
		txtAdress.clear();
		txtCity.clear();
		txtZipCode.clear();
		txtPhone.clear();
		txtAdress.clear();

	}

	/**
	 * This method finds the customer in the database.
	 * And add the firsname, lastname and phonenumber
	 * to a wordlist witch is used to build a autocompleter.
	 * 
	 */
	private void findCustomer() {
		//Search after the given query and return information from kunde (table)
		try {
			ArrayList<String[]> list = db.search(txtSearch.text(), false, false);

			List<String> liste = new ArrayList<String>();
			String temp = "";
			//Save current customer
			if(list.size() ==1){
				String[] res = list.get(0);
				tmpCustomer = res;
				txtFirstName.setText(res[0]); 	//Fornavn
				txtLastName.setText(res[1]); 	//Etternavn
				txtAdress.setText(res[2]);   	//Adresse
				txtCity.setText(res[3]); 	//Sted
				txtZipCode.setText(res[4]);	//Postkode
				txtPhone.setText(res[5]);//Telefon

				tmpCustomer = res;
			}
			else{
				for (String[] string : list) {
					temp = string[0]+ " " + string[1] + " " + string[5];
					liste.add(temp);
				}
				words.setStringList(liste);
			}

		}catch(RuntimeException err) {
			//ErrorMessage.noSuchUser(this);
		}

	}
	/**
	 * This method is used when a search is done,
	 * to get the customer information in the different
	 * fields.
	 * 
	 * @param text
	 */
	private void insertInfo(String text){
	     StringTokenizer st = new StringTokenizer(text);
	     ArrayList<String> list = new ArrayList<String>();
	     while (st.hasMoreTokens()) {
	    	 list.add(st.nextToken());
	     }
		ArrayList<String[]> result = db.search(list.get(2), false, false);
		String[] res = result.get(0);
		tmpCustomer = res;

		txtFirstName.setText(res[0]); 	//Fornavn
		txtLastName.setText(res[1]); 	//Etternavn
		txtAdress.setText(res[2]);   	//Adresse
		txtCity.setText(res[3]); 	//Sted
		txtZipCode.setText(res[4]);	//Postkode
		txtPhone.setText(res[5]); //telefon

		tmpCustomer = res;
	}

	/**
	 * This method update user based on CustomerID
	 */
	private void updateUser(){
		try {
			String[] customer = getFields();

			db.update(customer, tmpCustomer[6]);
		}catch(RuntimeException err) {
			ErrorMessage.invalidInput(this, 
			"Oppdatering feilet grunnet feil format på input."+
			" Telefon nummer eller postnummer er feil");
		}
	}

	/**
	 * This method return information from the textboxes.
	 * Throws exception if either zipcode or phone number is typed wrong,
	 * i.e not integers
	 * @throws RuntimeException
	 * @return
	 */
	private String[] getFields(){
		try {
			Integer.parseInt(txtZipCode.text());
			Integer.parseInt(txtPhone.text());
			String[] data =  {
					txtFirstName.text(),
					txtLastName.text(),
					txtAdress.text(),
					txtCity.text(),
					txtZipCode.text(),
					txtPhone.text()	
			};

			return data;
			
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}
		
	}


	/**
	 *  This method sends a signal about changes in
	 *  a costumer at a given costumer id.
	 */
	private void sendCustomer() {
		if(tmpCustomer == null) {
			ErrorMessage.noSuchUser(this);
			return;
		}
		try {

			int customerID = Integer.parseInt(tmpCustomer[6]);
			signalCustomer.emit(customerID);
			changeTab.emit(true);

		}catch(RuntimeException err) {
			ErrorMessage.noSuchUser(this);
		}

		clearFields();
	}



}