package PizzaPck;

import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QSizePolicy.Policy;
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
	 * Constructoren kaller setUp()
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


		//adds customer widget to main window
		KundeWidget kunde = new KundeWidget(db);
		tabMain.addTab(kunde, null);
		tabMain.addTab(kunde,"Kunde");


		//adds ordergui to main window
		OrderGUI tab2 = new OrderGUI(db);
		PizzaList liste= new PizzaList(db);
		tabMain.addTab(tab2, "Bestilling");

		//adds kitchen widget to main windows
		Kitchen ki = new Kitchen(db);
		tabMain.addTab(ki, "Kj�kken");
		
		//adds settings widget to main window
		SettingsWidget sw = new SettingsWidget(db);
		tabMain.addTab(sw, "Settings");
	
		//Connect Settingswidget to ordergui
		System.out.println("Signal connection complete");
		sw.test.connect(tab2, "hei()");
		
		/*
		 * Viser og setter størrelsen på widgeten 
		 */
		this.setSizePolicy(Policy.Fixed, Policy.Maximum);
		this.setWindowTitle("PizzaPlace");
		this.show();

	}



	/**
	 * Setter opp og kjører programmet
	 * @param args
	 */
    public static void main(String[] args) {
    	
        QApplication.initialize(args);
        new HovedVindu(); // oppretter seg selv og kjører setUp() gjennom constructoren;
        QApplication.exec();
       
    }
    
}
