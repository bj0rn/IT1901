package PizzaPck;

import com.trolltech.qt.gui.*;
/**
 * Her opprettes widgeten som holder på fanene man vil legge til i programmet
 * 
 * Main-metoden for å kjøre hele programmet ligger her
 * @author Vegard
 *
 */
public class HovedVindu extends QWidget {

	private QGridLayout layout;
	private QTabWidget tabMain;

	/**
	 * Constructoren kaller @link KundeWidget
	 * @author Vegard 
	 */
	public HovedVindu(){
		setUp();
	}	


	/**
	 * Intern metode som setter opp fanene
	 */
	private void setUp(){

		//Connect to database
		DB db = new DB();
		
		
		//oppretter layout og QtabWidget der vi kan plassere widgeter i hver fane
		layout = new QGridLayout(this); 
		tabMain = new QTabWidget();
		layout.addWidget(tabMain);



		/*
		 * Setter opp fanene, med navn og riktig widget
		 */


		//kunde
		KundeWidget kunde = new KundeWidget(db);
		tabMain.addTab(kunde, null);
		tabMain.addTab(kunde,"Kunde");


		//Bestillingstab
		OrderGUI tab2 = new OrderGUI(db);
		tabMain.addTab(tab2, "Bestilling");

		
		//Settings
		SettingsWidget sw = new SettingsWidget(db);
		tabMain.addTab(sw, "Settings");
		
		/*
		 * Viser og setter størrelsen på widgeten 
		 */
		this.setFixedSize(800, 600);
		this.show();

	}



	/**
	 * Setter opp og kjører programmet
	 * @param args
	 */
    public static void main(String[] args) {
    	
        QApplication.initialize(args);
        new HovedVindu(); // oppretter seg selv og kjører setup();
        QApplication.exec();
       
    }
    
}
