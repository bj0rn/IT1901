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
	
	/**
	 * User doesn't exist in database, messagebox is displayed.
	 * @param parent
	 */
	public static void noSuchUser(QWidget parent){
		QMessageBox.information(parent, "Info", "Brukeren finnes ikke i databasen");
	}
	
	/**
	 * Invalid input/No input, messagebox is displayed.
	 * @param parent
	 */
	public static void invalidInput(QWidget parent){
		QMessageBox.information(parent, "Info", "Alle feltene er ikke utfylt");
	}
	/**
	 * messagebox is displayed if database-error occurs.
	 * @param parent
	 * @param s
	 */
	
	public static void databaseError(QWidget parent, String s){
		QMessageBox.warning(parent, "Database error", s);
	}
	/**
	 * messagebox is displayed if the program fails to get 
	 * products from the database
	 * @param parent
	 * @param s
	 */
	
	public static void failMenu(QWidget parent, String s){
		
		QMessageBox.critical(parent, "Major problem",s);
	}

}
