/*
 *@author: Eliyas Tadesse
 */
package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


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
