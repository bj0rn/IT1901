package PizzaPck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.qt.gui.QScrollArea;
import com.trolltech.qt.gui.QSizePolicy.Policy;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;


/**
 * This class is used to contain several pizza dishes
 * in a list and shows them as a list.
 * 
 * @author Linn
 *
 */
public class PizzaList extends QScrollArea implements Iterable<Pizza>{

	protected QLayout lay;
	protected QVBoxLayout v_box;
	protected List<Pizza> pizza_list;
	protected QWidget main;
	protected QScrollArea scrollarea;
	private DB db;

	/**
	 * 
	 * @param db
	 * @see fillList
	 */
	public PizzaList(DB db){
		this.db = db;
		//Init 
		fillList();

	}


	public Signal1<String[]> signalPizza = new Signal1<String[]>();
	public Signal1<String[]> signalBridge = new Signal1<String[]>();

	/**
	 * This methode iterates over the list with pizza dishes
	 */
	@Override
	public Iterator<Pizza> iterator() {
		// TODO Auto-generated method stub
		return pizza_list.iterator();
	}

	/**
	 * This method fills the list with pizzas from the database.
	 * And sets up the layout and look of the list that will be shown
	 * in the OrderGUI.
	 */
	public void fillList() {
		pizza_list = new ArrayList<Pizza>();
		LinkedList<String[]> llProdukter;
		llProdukter = db.getMenu();
		Iterator<String[]> iter = llProdukter.iterator();
		System.out.println(llProdukter);
		
		while(iter.hasNext()){
			String[] a = iter.next();
			Pizza p = new Pizza(a);
			p.setFixedWidth(500);
			p.signalPizza.connect(this, "signalBridge(String[])");
			pizza_list.add(p);
		}
		

		main = new QWidget(this);
		v_box = new QVBoxLayout(main); 
		main.setLayout(v_box);
		v_box.setContentsMargins(2,2,2,2);
		
		this.setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOn);
		this.setContentsMargins(1, 1, 1, 2);
		this.setWidget(main);
		this.setWidgetResizable(true);
	
	
		//Update gui
		for (Pizza p: pizza_list) {
			
			v_box.addWidget(p);
		}
	}

	/**
	 * 
	 * @param data
	 */
	public void signalBridge(String [] data) {
		System.out.println("Signal forwared from pizza list");
		signalBridge.emit(data);
	}

}
