package PizzaPck;

import java.awt.image.BufferedImage;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.webkit.QWebView;

/**
 * Klassen som for fram map og viser tag mellom to adresser
 * @author Linn
 *
 */
public class Map extends QWebView{
	private final QUrl url; 
	protected BufferedImage map;
	

	public Map(){
		url = new QUrl("http://maps.googleapis.com/maps/api/staticmap?center=Trondheim,NO&size=460x460&markers=color:blue|"+encodeUTF8("Elgseter Gate 1 7039 Trondheim")+"&path=color:red|weight:1&maptype=roadmap&sensor=false");
		loadMap(url);
		this.setFixedSize(460,460);
	}
	
	/**
	 * loader map
	 */
	public void loadMap(QUrl url){
		this.setUrl(url);
	}
	
	public BufferedImage getMap(){
		return map;
	}
	
	/**
	 * Henter et kart med adressene markert på 
	 * @param addressFrom
	 * @param addressTo
	 * @param zoom
	 * @return
	 */
	public QUrl getMap(String addressFrom, String addressTo, int zoom){
		return new QUrl("http://maps.googleapis.com/maps/api/staticmap?center=Trondheim,NO&size=460x460&markers=color:blue|"+encodeUTF8(addressFrom)+"|"+encodeUTF8(addressTo)+"&path=color:red|weight:1&maptype=roadmap&sensor=false");
	}
	
	/**
	 * Decoder adresser
	 * @param adress
	 * @return
	 */
	public String decodeUTF8(String adress) {
	    return URLDecoder.decode(adress);
	}
	
	/**
	 * Encoder adresser
	 * @param adress
	 * @return
	 */
	public String encodeUTF8(String adress) {
	    return URLEncoder.encode(adress);
	}
	
}
