package module3;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;


public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = true;

	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";

	// The map
	private UnfoldingMap map;

	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";


	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			
		}

	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);

	    
	    List<Marker> markers = new ArrayList<Marker>();

	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);


	    int blue = color(0, 0, 255);
		int yellow = color(255, 255, 0);
		int red = color(255, 0, 0);


		if( earthquakes.size() > 0 ) {
			for( PointFeature feature : earthquakes ){
		    	SimplePointMarker simplePointMarker = createMarker(feature);

		    	Object magObj = simplePointMarker.getProperty("magnitude");
		    	float mag = Float.parseFloat(magObj.toString());

		    	if ( mag < 4.0 ){
					simplePointMarker.setColor(blue);
					simplePointMarker.setRadius(5);
				}
				else if( mag < 5.0 ){
					simplePointMarker.setColor(yellow);
					simplePointMarker.setRadius(8);
				}
				else {
					simplePointMarker.setColor(red);
					simplePointMarker.setRadius(10);
				}


		    	markers.add(simplePointMarker);
		    }
		}

	    map.addMarkers(markers);
	    MapUtils.createDefaultEventDispatcher(this, map);
	}

	
	private SimplePointMarker createMarker(PointFeature feature)
	{
		return new SimplePointMarker(feature.getLocation(), feature.getProperties());
	}

	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}

	private void addKey()
	{
		fill(235,221,218);
		rect(20, 50, 150, 250);

		fill(0,0,0);
		text("Earthquake Key", 45, 100);

		fill(255, 0, 0);
		ellipse(35, 137.5f, 10, 10);
		fill(0,0,0);
		text("5.0+ Magnitude", 55, 142);

		fill(255, 255, 0);
		ellipse(35, 175, 8, 8);
		fill(0,0,0);
		text("4.0+ Magnitude", 55, 178.5f);

		fill(0, 0, 255);
		ellipse(35, 212.5f, 5, 5);
		fill(0,0,0);
		text("Below 4.0", 55, 216f);

	}
}
