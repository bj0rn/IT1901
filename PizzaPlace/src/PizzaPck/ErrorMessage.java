package PizzaPck;

import com.trolltech.qt.gui.QDialogButtonBox.StandardButton;
import com.trolltech.qt.gui.QDialogButtonBox.StandardButtons;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QWidget;

/**
 * Collection of errormessages
 * @author Everyone
 *
 */

public class ErrorMessage {
	
	public static void noSuchUser(QWidget parent){
		QMessageBox.information(parent, "Info", "Brukeren finnes ikke i databasen");
	}
	public static void invalidInput(QWidget parent){
		QMessageBox.information(parent, "Info", "Alle feltene er ikke utfylt");
	}
	
	public static void databaseError(QWidget parent, String s){
		QMessageBox.warning(parent, "Database error", s);
	}
	
	public static void failMenu(QWidget parent, String s){
		
		QMessageBox.critical(parent, "Major problem",s);
	}

}
