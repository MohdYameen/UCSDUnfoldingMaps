package module1;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;


public class HelloWorld extends PApplet
{
	private static final long serialVersionUID = 1L;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";

	// IF YOU ARE WORKING OFFLINE: Change the value of this variable to true
	private static final boolean offline = false;

	/** The map we use to display our home town: Rampur, UP, INDIA */
	UnfoldingMap map1;

	/** The map you will use to display our University in Pnjab */
	UnfoldingMap map2;

	public void setup() {
		size(800, 600, P2D);
		this.background(0,255,0);

		// Select a map provider
		AbstractMapProvider provider = new Google.GoogleTerrainProvider();
		AbstractMapProvider provider2 = new Google.GoogleTerrainProvider();
		// Set a zoom level
		int zoomLevel = 10;

		if (offline) {
			provider = new MBTilesMapProvider(mbTilesString);
			zoomLevel = 3;
		}

		map1 = new UnfoldingMap(this, 50, 50, 350, 500, provider);

	
		//28.8108489,79.1836203  rampur long and lat
	    map1.zoomAndPanTo(9, new Location(28.8108, 79.1836f));

		MapUtils.createDefaultEventDispatcher(this, map1);

		map2 = new UnfoldingMap(this, 420, 50, 350, 500, provider2);
		
		//31.2499121,75.6983578  lpu long and lat
		map2.zoomAndPanTo(9, new Location(31.2499f, 75.6983f));

		MapUtils.createDefaultEventDispatcher(this, map2);

	}
	public void draw() {
		map1.draw();
		map2.draw();
		drawButtons();
	}

	private void drawButtons() {
		fill(255, 255, 255);
		rect(10, 50, 25, 25);

		fill(150, 150, 150);
		rect(10, 100, 25, 25);
	}

	public void mouseReleased() {
		if( mouseX > 10 && mouseX < 35
				&& mouseY > 50 && mouseY < 75) {
			background(255, 255, 255);
		}
		else if ( mouseX > 10 && mouseX < 35
				&& mouseY > 100 && mouseY < 125 ){
			background(150, 150, 150);
		}

	}


}
