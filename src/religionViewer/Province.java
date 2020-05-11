package religionViewer;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;

import java.util.Iterator;
import java.util.Map;

public class Province {
	private int id; // Province ID
	private String name; // Province name
	private int size = 0; // Total # of pops
	private HashMap<String, Integer> religions; // Map of Religions: "Religion Name" -> Reigion Size

	public Color color; // Majority color.
	public Color mColor; // Minority color.
	boolean showMinority;

	public Province(int id, String name) {
		this.id = id;
		this.name = name;
		religions = new HashMap<String, Integer>();
	}

	public void countPops(String religion, int size) {
		religions.put(religion, (religions.get(religion) != null) ? religions.get(religion) + size : size);
		this.size += size;
	}

	public void print() {
		System.out.println("-- " + id + " " + name);
		System.out.println("Show Minor? " + showMinority);
		Iterator<Entry<String, Integer>> i = religions.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<String, Integer> e = i.next();
			System.out.println(e.getKey() + " " + e.getValue());
		}
	}

	// Runs once to determine color of Province
	public void setColor() {

		if (religions.size() == 0) {
			color = Color.WHITE;
			return;
		}

		int currentTop = 0;
		int currentMinor = 0;
		String topRel = "";
		String minorRel = "";

		Iterator<Entry<String, Integer>> i = religions.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<String, Integer> e = i.next();
			// Ignore if the ""religion"" is empty. Idk why some provinces have empty
			// religions...
			if (e.getValue() > currentTop) {
				minorRel = topRel;
				currentMinor = currentTop;

				topRel = e.getKey();
				currentTop = e.getValue();
			}
		}

		if (topRel.equals(""))
			System.out.println("Prov. " + this.id + " (" + this.name + ") has an empty religion!");
		color = Religion.getReligionColor(topRel);
		if (!minorRel.equals(""))
			mColor = Religion.getReligionColor(minorRel);

		showMinority = (((double) currentMinor) / (double) size >= .34);

	}

}
