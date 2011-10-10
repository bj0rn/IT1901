package PizzaPck;

import java.awt.image.BufferedImage;

import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.webkit.QWebView;


public class Map extends QWebView{
	
	protected QUrl url;
	protected BufferedImage map;
	
	public Map(){
	}
	
	public void loadMap(){
		url = new QUrl("http://maps.googleapis.com/maps/api/staticmap?center=Trondheim,NO&zoom=14&size=400x400&maptype=roadmap&sensor=false");
		this.setUrl(url);
	}
	public String setAdressLocation(String from, String to){
		
		return null;
	}
	
	public BufferedImage getMap(){
		return map;
	}
	
	public String getMapUrl( double longitude,
			double latitude, int zoom){
		
		return"http://maps.google.com/staticmap?center="+latitude+","+longitude+"&format"
				+BufferedImage.TYPE_INT_RGB+"&zoom="+zoom+"&size="+512+"X"+512;
		
	}
	
	public String getGeocode(){
		return "http://maps.google.com/staticmap?geo?q=";
	}
	
}
