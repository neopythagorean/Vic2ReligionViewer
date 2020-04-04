package religionViewer;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

public class Religion {
	// This is a map of all the default religions for vanilla + popular mods like HP/FM
	static HashMap<String, Color> colorMap;
	
	public static void initColors() {
		colorMap = new HashMap<String, Color>();
		// Christian
		colorMap.put("catholic", 	new Color(.8f, .8f, 0f));
		colorMap.put("protestant",	new Color(0f, 0f, .7f));
		colorMap.put("orthodox", 	new Color(0.7f, 0.5f, 0f));
		colorMap.put("coptic",		new Color(0f, 0.5f, 0.7f));
		// Muslim
		colorMap.put("sunni",		new Color(0f, 0.6f, 0f));
		colorMap.put("shiite",		new Color(0f, 0.8f, 0f));
		colorMap.put("jewish",		new Color(0.3f, 0.5f, 0.3f)); // """muslim"""
		// Eastern
		colorMap.put("mahayana",	new Color(0.8f, 0.3f, 0f));
		colorMap.put("gelugpa",		new Color(0f, 0.3f, 0.8f));
		colorMap.put("theravada",	new Color(0.8f, 0f, 0.8f));
		colorMap.put("hindu", 		new Color(0f, 0.8f, 0.8f));
		colorMap.put("shinto",		new Color(0.8f, 0f, 0f));
		colorMap.put("sikh", 		new Color(0.3f, 0.8f, 0f));
		// Pagan
		colorMap.put("animist", 	new Color(0.5f, 0f, 0f));
		
		// HPM/FM
		colorMap.put("mormon", 		new Color(0f, 0f, 0.5f));
		colorMap.put("ibadi", 		new Color(0.1f, 0.4f, 0.6f));
		colorMap.put("druze", 		new Color(0.7f, 0.8f, 0.6f));
		colorMap.put("yazidi", 		new Color(0.4f, 0f, 1f));
		
		// DOD
		colorMap.put("taiping", 	new Color(0.5f, 0.5f, 0f));
		colorMap.put("assyrian", 	new Color(0f, 0.5f, 0.7f));
		colorMap.put("zoroastrian", new Color(0.7f, 0.2f, 0.1f));
		colorMap.put("jain", 		new Color(0f, 0.1f, 0f));
		colorMap.put("inti",		new Color(0.6f, 0.7f, 0.6f));
		colorMap.put("fetishist", 	new Color(0.419f, 0.247f, 0.627f));
		
	}
	
	public static Color getReligionColor(String religion) {
		
		if(religion == "") System.out.println("Empty Religion!");
		
		Color c = colorMap.get(religion);
		// If the Map has that religion, return that color.
		if(c != null) return c;
		
		// Otherwise, add the religion to the map, and give it a random color
		// based off of the religion name.
		System.out.println("Adding new religion to hashmap: " + religion);
		Random r = new Random(religion.hashCode());
		c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
		colorMap.put(religion, c);
		return c;
	}
}
