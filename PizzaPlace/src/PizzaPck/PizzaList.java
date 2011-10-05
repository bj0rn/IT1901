package PizzaPck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.*;





public class PizzaList extends QWidget implements Iterable<Pizza>{
	
	protected QLayout lay;
	protected QVBoxLayout v_box;
	protected List<Pizza> pizza_list;
	protected QWidget main;
	protected QScrollArea scrollarea;
	
	
	public PizzaList(DB db){
		pizza_list = new ArrayList<Pizza>();
		LinkedList<String[]> llProdukter;
			llProdukter = db.getMenu();

		System.out.println(llProdukter);
		
		
		lay = new QGridLayout();
		v_box = new QVBoxLayout();
		
		for (Pizza p: pizza_list) {
			v_box.addWidget(p);
		}
		
		main = new QWidget();
		main.setLayout(v_box);
		//main.setBaseSize(pizza_list.get(0).width()+10, pizza_list.get(0).height()*6);
		
		scrollarea = new QScrollArea(this);
		scrollarea.setWidgetResizable(true);
		scrollarea.setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOn);
		scrollarea.setWidget(main);
		
	}
	
	@Override
	public Iterator<Pizza> iterator() {
		// TODO Auto-generated method stub
		return pizza_list.iterator();
	}
	
	
	public void addPizza(Pizza pizza){
		pizza_list.add(pizza);
	}
	
	
	
}
