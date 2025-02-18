/*
 *@author: Eliyas Tadesse
 */
package view;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenuItem saveMenuItem;

	public MenuBar() {
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		saveMenuItem = new JMenuItem("Save");
		// Add an action listener to the "Save" menu item
		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open a file chooser dialog
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save Drawing");
				fileChooser.setFileFilter(
						new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg"));

				int result = fileChooser.showSaveDialog(getParent());

				// If the user selects a file, save the drawing
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					// Ensure the file has a proper extension
					String fileName = file.getAbsolutePath();
					if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")) {
						file = new File(fileName + ".png");
					}

					try {
						// Save the drawing as an image
						BufferedImage image = new BufferedImage(getParent().getWidth(), getParent().getHeight(),
								BufferedImage.TYPE_INT_ARGB);
						Graphics2D g2d = image.createGraphics();
						getParent().paint(g2d);
						g2d.dispose();

						// Write the image to the file
						ImageIO.write(image, "png", file);

						// Show success message
						JOptionPane.showMessageDialog(getParent(), "File saved successfully!");
					} catch (IOException ex) {
						// Show error message
						JOptionPane.showMessageDialog(getParent(), "Error saving file: " + ex.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		fileMenu.add(saveMenuItem);
		this.add(fileMenu);
		this.add(helpMenu);
	}

	public JMenuItem getSaveMenuItem() {
		return saveMenuItem;
	}

	public JMenu getHelpManu() {
		return helpMenu;
	}
}
