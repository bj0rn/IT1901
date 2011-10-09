package PizzaPck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QSizePolicy.Policy;



public class PizzaList extends QWidget implements Iterable<Pizza>{
	
	protected QLayout lay;
	protected QVBoxLayout v_box;
	protected List<Pizza> pizza_list;
	protected QWidget main;
	protected QScrollArea scrollarea;
	private DB db;
	
	public PizzaList(DB db){
		this.db = db;
		//Init 
		fillList();
		}
	
	
	@Override
	public Iterator<Pizza> iterator() {
		// TODO Auto-generated method stub
		return pizza_list.iterator();
	}
	
	public void fillList() {
		pizza_list = new ArrayList<Pizza>();
		LinkedList<String[]> llProdukter;
		llProdukter = db.getMenu();
		Iterator<String[]> iter = llProdukter.iterator();
		System.out.println(llProdukter);
		while(iter.hasNext()){
			String[] a = iter.next();
			pizza_list.add(new Pizza(a));
		}
		v_box = new QVBoxLayout(); 
		lay = new QGridLayout();
		
		
		
		
		main = new QWidget();
		main.setLayout(v_box);
		//main.setBaseSize(pizza_list.get(0).width()+10, pizza_list.get(0).height()*6);
		
		scrollarea = new QScrollArea(this);
		scrollarea.setWidgetResizable(true);
		scrollarea.setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOn);
		scrollarea.setWidget(main);
		scrollarea.setSizePolicy(Policy.Fixed, Policy.Fixed);
		//Update gui
		for (Pizza p: pizza_list) {
			v_box.addWidget(p);
		}
	}

}
