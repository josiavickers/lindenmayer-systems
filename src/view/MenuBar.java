/*
 *@author: Eliyas Tadesse
 */
package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem userGuideMenuItem;
	private JMenuItem aboutMenuItem;

	public MenuBar() {
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");

		// Add save menu item
		saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);

		// Add exit menu item
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);

		// Add User Guide item
		userGuideMenuItem = new JMenuItem("User Guide");
		helpMenu.add(userGuideMenuItem);

		// Add About item
		aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);

		this.add(fileMenu);
		this.add(helpMenu);
	}

	public JMenu getFileMenu() {
		return fileMenu;
	}

	public JMenu getHelpManu() {
		return helpMenu;
	}

	public JMenuItem getSaveMenuItem() {
		return saveMenuItem;
	}

	public JMenuItem getExitMenuItem() {
		return exitMenuItem;
	}

	public JMenuItem getUserGuideMenuItem() {
		return userGuideMenuItem;
	}

	public JMenuItem getAboutMenuItem() {
		return aboutMenuItem;
	}
}
