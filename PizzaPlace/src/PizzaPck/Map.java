package PizzaPck;

import java.awt.image.BufferedImage;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.webkit.QWebView;

/**
 * This class displays a map and shows a tag between two addresses.
 * @author Everyone
 */
public class Map extends QWebView{
	private final QUrl url; 
	protected BufferedImage map;
	
	/**
	 * Setting a default map over Trondheim in the beginning,
	 * and setting it to be 460x460 pixels i size
	 */
	public Map(){
		url = new QUrl("http://maps.googleapis.com/maps/api/staticmap?center=Trondheim,NO"+
	"&size=460x460&scale=3&markers=color:blue|"
	+encodeUTF8("Bispegata 5  7032 Trondheim")+"&maptype=roadmap&sensor=false");
		loadMap(url);
		this.setFixedSize(460,470);
	}
	
	/**
	 * Loading the new map
	 * @param url
	 */
	public void loadMap(QUrl url){
		this.setUrl(url);
	}
	
	public void setSize(QListWidget listWidget){
		this.setFixedSize(listWidget.width(),listWidget.height());
	}
	public BufferedImage getMap(){
		return map;
	}
	
	/**
	 * Getting a new map which have markers on the start address,
	 * and the delivery address
	 * 
	 * @param addressFrom
	 * @param addressTo
	 * @param zoom
	 * @return
	 */
	public QUrl getMap(String addressFrom, String addressTo){
		return new QUrl("http://maps.googleapis.com/maps/api/staticmap?center=Trondheim,NO"+
	"&size=460x460&scale=3&markers=color:blue|"+encodeUTF8(addressFrom)+"|"+encodeUTF8(addressTo)+
	"&path=color:red|weight:3|"+encodeUTF8(addressFrom)+"|"
	+encodeUTF8(addressTo)+"&maptype=roadmap&sensor=false");
	}
	
	/**
	 * Sets a default map if the costumer pick up the
	 * order by them selfs
	 * @return
	 */
	public QUrl getDefaultMap(){
		return new QUrl("http://maps.googleapis.com/maps/api/staticmap?center=Trondheim,NO"+
				"&size=460x460&scale=3&markers=color:blue|"
				+encodeUTF8("Bispegata 5  7032 Trondheim")+"&maptype=roadmap&sensor=false");
	}
	
	/**
	 * Decoding an address
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
	 * Encoding an address
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
