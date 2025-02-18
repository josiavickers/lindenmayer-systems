/**
 * @author: Eliyas Tadesse
 */
package lindenmayer;

import javax.swing.SwingUtilities;

import controller.LSystemController;
import view.MainFrame;

public class Main {

	public Main() {
		MainFrame frame = new MainFrame();
		@SuppressWarnings("unused")
		LSystemController controller = new LSystemController(frame.getControlPanel(), frame.getCanvasPanel());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Main();

			}

		});

	}

}