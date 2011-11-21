package PizzaPck;

import java.awt.image.BufferedImage;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.webkit.QWebView;

/**
 * This class displays a map and shows a tag between two addresses.
 * And sets it up in a QWebView, witch it inherit from.
 * @author Everyone
 */
public class Map extends QWebView{
	private final QUrl url; 
	protected BufferedImage map;
	
	/**
	 * The constructor is setting a default map 
	 * over Trondheim in the beginning,
	 * and setting it to be 460x460 pixels i size.
	 */
	public Map(){
		url = getDefaultMap();
		loadMap(url);
		this.setFixedSize(460,470);
	}

	/**
	 * This method loads the new map on to the view.
	 * @param url
	 */
	public void loadMap(QUrl url){
		this.setUrl(url);
	}
	
	/**
	 * This methode sets the fixed size of the list
	 * of deliveries.
	 * 
	 * @param listWidget
	 */
	public void setSize(QListWidget listWidget){
		this.setFixedSize(listWidget.width(),listWidget.height());
	}

	/**
	 * Getting a new map which have markers on the start address,
	 * and the delivery address. And the path between the
	 * addresses.
	 * 
	 * @param addressFrom
	 * @param addressTo
	 * @return
	 */
	public QUrl getMap(String addressFrom, String addressTo){
		addressFrom = addressFrom.replace("æ","ae");
		addressFrom = addressFrom.replace("ø","oe");
		addressFrom = addressFrom.replace("å","aa");
		
		return new QUrl("http://folk.ntnu.no/vikre/show?toAddress="+addressTo);
	}
	/**
	 * This method sets a default map if the 
	 * costumer pick up the order by itself.
	 * @return
	 */
	public QUrl getDefaultMap(){
		return new QUrl("http://maps.googleapis.com/maps/api/staticmap?" +
				"center=Trondheim,NO"+
				"&size=460x460&scale=3&markers=color:blue|"
				+("Kongsgårdsgata+2+7013+Trondheim")+
				"&maptype=roadmap&sensor=false");
	}

	/**
	 * This method is decoding an address.
	 * @param adress
	 * @return
	 */
	public String decodeUTF8(String adress)  {
		try {
			return URLDecoder.decode(adress);
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}

	}

	/**
	 * This method is encoding an address.
	 * @param adress
	 * @return
	 */
	public String encodeUTF8(String adress) {
		try {
			return URLEncoder.encode(adress);
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}

	}

}
