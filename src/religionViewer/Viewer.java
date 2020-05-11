package religionViewer;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class Viewer {

	private static JFrame main;
	private static JPanel mainPanel;

	public static JLabel status;

	public static void makeAndDisplay() {
		main = new JFrame("Victoria 2 Religion Viewer");
		mainPanel = new JPanel(new BorderLayout());

		main.add(mainPanel);

		JPanel controls = new JPanel(new BorderLayout());
		mainPanel.add(controls, BorderLayout.BEFORE_FIRST_LINE);

		JPanel topPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		controls.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

		JTextField saveFile = new JTextField(40);
		c.gridx = 0;
		c.gridy = 0;
		topPanel.add(new JLabel("Save File:"), c);
		c.gridx = 1;
		topPanel.add(saveFile, c);
		JTextField mapDir = new JTextField(40);
		c.gridx = 0;
		c.gridy = 1;
		topPanel.add(new JLabel("Map Directory:"), c);
		c.gridx = 1;
		topPanel.add(mapDir, c);

		JPanel lowPanel = new JPanel();
		controls.add(lowPanel, BorderLayout.AFTER_LAST_LINE);
		JButton generate = new JButton("Generate Map!");
		lowPanel.add(generate);

		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				generate.setEnabled(false);
				String fs = File.separator;

				SwingWorker sw = new SwingWorker() {
					protected Object doInBackground() throws Exception {
						Driver.saveFile = new File(saveFile.getText());
						Driver.defines = new File(
								mapDir.getText() + (mapDir.getText().endsWith(fs) ? "" : fs) + "definition.csv");
						Driver.mapFile = new File(
								mapDir.getText() + (mapDir.getText().endsWith(fs) ? "" : fs) + "provinces.bmp");

						Religion.initColors();
						try {
							Driver.initMap();
							Driver.readSave();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						Driver.drawMap();
						status.setText("Done! Map saved in save game directory.");
						return null;
					}

				};

				sw.execute();

				generate.setEnabled(true);

			}
		});

		JPanel statusPanel = new JPanel();

		status = new JLabel(" ");
		statusPanel.add(status);
		mainPanel.add(statusPanel, BorderLayout.CENTER);

		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.pack();
		main.setVisible(true);
	}
}
