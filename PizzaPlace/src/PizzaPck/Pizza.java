package PizzaPck;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

/**
 * 
 * This class sets information about one single
 * pizza together. Like pizzaname, ingridients,
 * the user can choose size and amount. The user 
 * can also make a comment, when ordering if the
 * costumer is alergic.
 * 
 * @author Everyone
 * 
 * */

public class Pizza extends QWidget{

	private QPixmap image;
	private QComboBox amount;
	private QComboBox size;
	private QLabel amount_label;
	private QLabel size_label;
	private QLabel price_label;
	private QLabel pizza_name;
	private QLabel ingridients;
	private QGridLayout grid;
	private DecimalFormat price_format;
	private double start_price;
	private List<String> amountList;
	private QGroupBox box;
	private QLabel image_label;
	private QLineEdit comments;
	private QLabel labComments;
	private QPushButton btnAdd;

	private QHBoxLayout size_layout;
	private QHBoxLayout amount_layout;

	//Send pizza information to pizzalist
	public Signal1<String[]> signalPizza = new Signal1<String[]>();

	/**
	 * The constructor sets up the widget,
	 * and fetches the information from the database.
	 * @param liste
	 */
	public Pizza(String[] liste){

		start_price = Double.parseDouble(liste[2]);
		pizza_name = new QLabel(liste[1]);
		ingridients = new QLabel(liste[3]);
		grid = new QGridLayout(this);

		setup();
		
	}


	/**
	 * This method sets up the graphical user
	 * interface, and formatting everything
	 */
	private void setup(){

		comments = new QLineEdit();
		comments.setFixedWidth(150);
		labComments = new QLabel("Kommentar:");
		labComments.setFixedWidth(100);

		price_format = new DecimalFormat("00");
		price_format.format(start_price);
		price_label = new QLabel("Pris: "+start_price);

		btnAdd = new QPushButton("Legg til");
		btnAdd.clicked.connect(this, "signalBtnAdd()");

		//Man kan valge mellom liten og stor pizza
		size = new QComboBox();
		size_label = new QLabel("Størrelse :");
		size.addItem("Liten");
		size.addItem("Stor");

		//
		amount = new QComboBox();
		amount_label = new QLabel("Antall :");
		amountList = new ArrayList<String>();

		//for å legge til nummer i antal pizza man kan velge
		for (int i = 1; i < 21; i++) {
			amountList.add(""+i);
		}
		
		amount.addItems(amountList);
		amount.setCurrentIndex(0);
		ingridients.setFont(new QFont("Verdana",11));
		ingridients.setWordWrap(true);
		labComments.setWordWrap(true);

		pizza_name.setFont(
				new QFont("Arial",13,QFont.Weight.Bold.value()));
		
		pizza_name.setWordWrap(true);
		price_label.setWordWrap(true);

		amount_layout = new QHBoxLayout();
		amount_layout.addWidget(amount_label);
		amount_layout.addWidget(amount);

		size_layout = new QHBoxLayout();
		size_layout.addWidget(size_label);
		size_layout.addWidget(size);
		
		this.setStyleSheet("QLineEdit { font-size: 11px; }");

		//legge til de forskjellige tingene til griden
		this.grid.addWidget(pizza_name,1,0);

		this.grid.addWidget(ingridients,2,0);
		this.grid.addLayout(amount_layout, 2, 5);
		this.grid.addLayout(size_layout, 3, 5);
		this.grid.addWidget(price_label,3,0);
		this.grid.addWidget(labComments, 4, 0);
		this.grid.addWidget(comments, 5, 0);
		this.grid.addWidget(btnAdd, 5, 5);
		this.grid.setWidgetSpacing(10);

	}


	/**
	 * This method is activated when the user push the add button,
	 * and adds a pizza order to the orderlist.
	 */
	private void signalBtnAdd() {
		
		String [] data = {
				pizza_name.text(),      	 //Pizza name
				size.currentText(),			//Size
				amount.currentText(),		//Amount
				Double.toString(start_price),//start_prize
				ingridients.text(), 		//ingridients
				comments.text() 			// comments
		};
		
		//Send data
		signalPizza.emit(data);

		comments.clear();
		size.setCurrentIndex(0);
		amount.setCurrentIndex(0);
		
	}

}
