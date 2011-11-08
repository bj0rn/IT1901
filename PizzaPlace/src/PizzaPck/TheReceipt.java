package PizzaPck;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QPrintDialog;
import com.trolltech.qt.gui.QPrinter;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QVBoxLayout;

/**
 * This class sets up a QDialog that communicates
 * with the printer. This class has
 * ovverwritten the accept method so it can print
 * the acutal textfield we have edited.
 * 
 * @author Everyone
 *
 */
public class TheReceipt extends QDialog{
	protected QPrinter printer;
	protected QTextEdit textbox;
	
	//Må evt ta inn DB...
	
	/**
	 * This method is the constructor,
	 * and sets up the whole dialogbox
	 */
	public TheReceipt(){
		setup();
	}
	
	/**
	 * This method sets up the printview, with
	 * a dialogbox. Where the user can choose to
	 * print the receipt or cancel the action
	 */
	public void setup(){
		this.setFixedSize(400, 400);
		printer = new QPrinter();
		textbox = new QTextEdit();
		
		textbox.setReadOnly(true);
		
		//Buttons
		QPushButton cancel = new QPushButton("Avbryt");
		QPushButton print = new QPushButton("Skriv ut");
		
		print.clicked.connect(this,"accept()");
		cancel.clicked.connect(this,"reject()");
		
		QHBoxLayout box = new QHBoxLayout();
		QVBoxLayout vbox = new QVBoxLayout();
		
		vbox.addWidget(textbox);
		vbox.addLayout(box);
		
		box.addStretch(1);
		box.addWidget(print);
		
		box.addStretch(1);
		box.addWidget(cancel);
		
		box.addStretch(1);
		this.setLayout(vbox);
		
	}
	/**
	 * Ovverride method, that print the actual
	 * textbox/receipt.
	 */
	@Override
	public void accept(){
		QPrintDialog dialog = new QPrintDialog(printer,this);
		if(dialog.exec() == QDialog.DialogCode.Accepted.value()){
			textbox.print(printer);
			super.accept();
		}
	}
}
