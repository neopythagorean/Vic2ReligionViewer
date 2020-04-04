package religionViewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class Driver {

	public static final int MAPW = 5616;
	public static final int MAPH = 2160;
	
	
	public static File saveFile;	// Save File Location
	public static File defines;	// Definitions File
	public static File mapFile;		// Map File
	
	public static File outMap;
	
	public static HashMap<Integer, Province> provinces = new HashMap<Integer, Province>();
	public static HashMap<Integer, Integer> mapColors;
	
	// ^\d+=$ -- Regex for 
	
	
	
	public static void main(String[] args) throws IOException {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				 Viewer.makeAndDisplay();
			}
		});
		
		
	}
	
	public static void initMap() throws IOException {
		Viewer.status.setText("Reading Definitions...");
		mapColors = new HashMap<Integer, Integer>();
		
		FileReader fr = new FileReader(defines);
		BufferedReader bf = new BufferedReader(fr);
		Scanner s = new Scanner(bf);
		
		s.nextLine(); // Skip title
		while(s.hasNext()) {
			String[] line = s.nextLine().split(";");
			
			if(line[0].equals(""))
				continue;
			
			mapColors.put(new Color( new Double(line[1]).intValue(), 
					new Double(line[2]).intValue(), 
					new Double(line[3]).intValue()).getRGB(), new Integer(line[0]));
		}
		
		System.out.println("Done. :)");
		s.close();
	}
	
	
	public static void drawMap() {
		System.out.println("Drawing Image...");
		Viewer.status.setText("Drawing Map...");
		outMap = new File(saveFile.getAbsolutePath() + "_religion.png");
		BufferedImage outimg = new BufferedImage(MAPW, MAPH, BufferedImage.TYPE_INT_ARGB);
		BufferedImage inimg = null;
		
		try {
			inimg = ImageIO.read(mapFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Graphics go = outimg.createGraphics();
		
		for(int y = 0; y < MAPH; y++) {
			for(int x = 0; x < MAPW; x++) {
				Province p = provinces.get(mapColors.get(inimg.getRGB(x, y)));
				if(p != null)
					if(p.showMinority && (y + x) % 5 == 0)
						outimg.setRGB(x, MAPH-y-1, p.mColor.getRGB());
					else
						outimg.setRGB(x, MAPH-y-1, p.color.getRGB());
					
				else {
					//System.out.println("No province found for: " + Integer.toHexString(inimg.getRGB(x, y)) + ", " + mapColors.get(inimg.getRGB(x, y)));
					outimg.setRGB(x, MAPH-y-1, 0xFFFFFFFF);
				}
			}
		}
		
		
		try {
			ImageIO.write(outimg, "png", outMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done. :)");
	}
	
	public static void readSave() throws IOException {
		FileReader fr = new FileReader(saveFile);
		BufferedReader bf = new BufferedReader(fr);
		Scanner s = new Scanner(bf);
		
		int num = 0;
		
		Province current = null;
		System.out.println("Reading Save File...");
		Viewer.status.setText("Reading Save File...");
		while(s.hasNext()) {
			String line = s.nextLine();
			// Check if this is a Province
			if(line.matches("\\d+=")) {
				int id = new Integer(line.substring(0, line.length()-1));
				s.nextLine(); // Skip a line
				String name = s.nextLine().substring(7).replace("\"", "");
				//System.out.println(id + " " + name);
				s.nextLine();
				if(!s.nextLine().equals("}")) {
					current = new Province(id, name);
					provinces.put(id, current);
				}
			}
			
			// Check if this is a POP
			if(line.matches("\t(" // WOW! this is an ugly if statement :) 
					+ "aristocrats|" 
					+ "artisans|"
					+ "bureaucrats|"
					+ "capitalists|"
					+ "clergymen|"
					+ "clerks|"
					+ "craftsmen|"
					+ "farmers|"
					+ "labourers|"
					+ "officers|"
					+ "serfs|"
					+ "slaves|"
					+ "soldiers"
					+ ")=")) {
				s.nextLine();
				s.nextLine();
				int size = new Integer(s.nextLine().substring(7));
				String temp = s.nextLine();
				String religion = temp.substring(temp.indexOf("=")+1);
				current.countPops(religion, size);
			}
			
			
		}
		System.out.println("Done. :)");
		s.close();
		
		Viewer.status.setText("Calculating Religion Sizes...");
		Iterator<Entry<Integer, Province>> i = provinces.entrySet().iterator();
		while(i.hasNext()) {
			i.next().getValue().setColor();
		}
		System.out.println("Done. :)");
	}
}
