/**
 * @author: Eliyas Tadesse
 */
package lindenmayer;

import javax.swing.SwingUtilities;

import controller.LSystemController;
import view.MainFrame;

/*
 * Entry point of the application
 */
public class Main {

	public Main() {
		MainFrame frame = new MainFrame();
		@SuppressWarnings("unused")
		LSystemController controller = new LSystemController(frame);
	}

	public static void main(String[] args) {
		// invokeLater() to run programme on Event Dispatch Thread (EDT) from Swing utilities - handling UI and button actions etc
		SwingUtilities.invokeLater(new Runnable() { // instantiates anon class implementing Runnable() interface and its run() method

			@Override
			public void run() {
				new Main();
			}
		});
	}
}