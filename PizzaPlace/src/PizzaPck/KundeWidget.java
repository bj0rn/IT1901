package PizzaPck;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLayout.SizeConstraint;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QSizePolicy.Policy;
import com.trolltech.qt.gui.QWidget;



public class KundeWidget extends QWidget {

	private String fornavn, etternavn, adresse, poststed, postkode,telefonNr;

	private QPushButton btnSok, btnBestill, btnOppdater, btnLagre;
	private QLineEdit txtFornavn, txtEtternavn, txtAdresse, txtPoststed, txtPostkode, txtTelefonNr, txtSok;
	private QLabel labFornavn, labEtternavn, labAdresse, labPostSted, labtPostKode, labTelefon, labSok;
	private QGridLayout layout;

	/**
	 * 
	 * @see : setUp()
	 */
	public KundeWidget() {
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




}