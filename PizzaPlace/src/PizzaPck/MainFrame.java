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
public class MainFrame extends QWidget {

	private QGridLayout layout;
	private QTabWidget tabMain;
	
	private OrderGUI tab2;
	private PizzaList liste;
	private Kitchen ki;
	private SettingsWidget sw;
	private Delivery dl;
	
	public Signal1<Boolean> changeTab = new Signal1<Boolean>();
	/**
	 * Constructoren kaller setUp()
	 * @author Vegard 
	 */
	public MainFrame(){
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
		tab2 = new OrderGUI(db);
		liste= new PizzaList(db);
		tabMain.addTab(tab2, "Bestilling");
		tab2.setDisabled(true);
		
		//adds kitchen widget to main windows
		ki = new Kitchen(db);
		tabMain.addTab(ki, "Kj�kken");
		
		//adds map
		dl = new Delivery(db);
		tabMain.addTab(dl, "Kartet");
		
		//adds settings widget to main window
		sw = new SettingsWidget(db);
		tabMain.addTab(sw, "Settings");
	
		
		
		//Connect Settingswidget to ordergui
		System.out.println("Signal connection complete");
		sw.test.connect(tab2, "hei()");
		//kunde.customer.connect(tab2, "insertOrder(int)");
		kunde.signalCustomer.connect(tab2, "displayCustomer(int)");
		kunde.changeTab.connect(this, "setCurrentTab()");
		

		
		/*
		 * Viser og setter størrelsen på widgeten 
		 */
		this.setSizePolicy(Policy.Fixed, Policy.Maximum);
		this.setWindowTitle("PizzaPlace");
		this.show();

	}


	public void setCurrentTab (){
		tabMain.setCurrentIndex(1);
		tab2.setDisabled(false);
	}

	/**
	 * Setter opp og kjører programmet
	 * @param args
	 */
    public static void main(String[] args) {
    	
        QApplication.initialize(args);
        new MainFrame(); // oppretter seg selv og kjører setUp() gjennom constructoren;
        QApplication.exec();
       
    }
    
}