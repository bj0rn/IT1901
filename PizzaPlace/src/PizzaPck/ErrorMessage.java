package PizzaPck;

import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QWidget;

public class ErrorMessage {
	
	public static void noSuchUser(QWidget parent){
		QMessageBox.information(parent, "Info", "Brukeren finnes ikke i databasen");
	}
	public static void invalidInput(QWidget parent){
		QMessageBox.information(parent, "Info", "Alle feltene er ikke utfylt");
	}

}
