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
	
	private OrderGUI orderGui;
	private Kitchen kitchenWidget;
	private SettingsWidget settingWidget;
	private Delivery deliveryWidget;
	private KundeWidget customerWidget;
	
	public Signal1<Boolean> changeTab = new Signal1<Boolean>();
	
	
	/**
	 * Constructoren kaller setUp()
	 * @author Vegard 
	 */
	public MainFrame(){
		setUp();
	}	


	
	/**
	 * Creates the gui
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
		customerWidget = new KundeWidget(db);
		tabMain.addTab(customerWidget, null);
		tabMain.addTab(customerWidget,"Kunde");
		


		//adds ordergui to main window
		orderGui = new OrderGUI(db);
		tabMain.addTab(orderGui, "Bestilling");
		orderGui.setDisabled(true);
		
		//adds kitchen widget to main windows
		kitchenWidget = new Kitchen(db);
		tabMain.addTab(kitchenWidget, "Kj�kken");
		
		//adds map
		deliveryWidget = new Delivery(db);
		tabMain.addTab(deliveryWidget, "Kartet");
		
		//adds settings widget to main window
		settingWidget = new SettingsWidget(db);
		tabMain.addTab(settingWidget, "Settings");
	
		
		//Connect Settingswidget to ordergui
		
		settingWidget.singalInsertProduct.connect(orderGui, " updatePizzaList()");
		//kunde.customer.connect(tab2, "insertOrder(int)");
		customerWidget.signalCustomer.connect(orderGui, "displayCustomer(int)");
		customerWidget.changeTab.connect(this, "setCurrentTab()");
		
		orderGui.signalKitchen.connect(kitchenWidget, "getOrders()");
		orderGui.signalCancel.connect(this,"setCurrentTab()");
		kitchenWidget.signalDelivery.connect(deliveryWidget, "getDeliveries()");
		

		
		/*
		 * Viser og setter størrelsen på widgeten 
		 */
		this.setSizePolicy(Policy.Fixed, Policy.Maximum);
		this.setWindowTitle("PizzaPlace");
		this.show();

	}


	public void setCurrentTab (){
		int index = tabMain.currentIndex();
		System.out.println(index);
		if(index == 0){
			tabMain.setCurrentIndex(1);
			
			customerWidget.setDisabled(true);
			orderGui.setDisabled(false);
			kitchenWidget.setDisabled(true);
			deliveryWidget.setDisabled(true);
			settingWidget.setDisabled(true);
		}
		else if(index == 1) {
			tabMain.setCurrentIndex(0);
			customerWidget.setDisabled(false);
			orderGui.setDisabled(true);
			kitchenWidget.setDisabled(false);
			deliveryWidget.setDisabled(false);
			settingWidget.setDisabled(false);
		}
		
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
