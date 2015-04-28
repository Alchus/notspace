package core;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame implements MouseListener,
		MouseMotionListener {

	public static Window main;

	static long minFrameHoldMillis = 1;// No need to push the frame rate too
										// high.
	int playerNumber = 1;
	public Game game = new Game();

	public Game newGame = null;
	public static JFileChooser jfc = new JFileChooser();
	
	


	public static void main(String[] args) {
		new Window();

	}

	
	
	public Window() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);
		requestFocus();
		requestFocusInWindow();
		main = this;
		game.init();
		// jfc.setFileFilter(new FileNameExtensionFilter("Save Files", "SGS"));
		try {
			Files.createDirectories(Paths.get(System.getProperty("user.home")
					+ "\\Saved Games\\NSG\\"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jfc.setCurrentDirectory(new File(System.getProperty("user.home")
				+ "\\Saved Games\\NSG\\"));

		this.setSize(1215, 837);// Magic numbers woo
		this.setResizable(false);
		setVisible(true);

		getGlassPane().addMouseListener(this);
		getGlassPane().addMouseMotionListener(this);
		getGlassPane().setVisible(true);
		
		getContentPane().add(new KeyBindingComponent());
		

		long lastmillis = System.currentTimeMillis();
		long millis;
		long delta;
		game.running = true;

		while (true) {

			millis = System.currentTimeMillis();
			delta = millis - lastmillis;
			lastmillis = millis;
			setTitle(Long.toString(delta));
			if (game.running == true) {
				game.tick((int) delta); // I promise this line doesn't do
										// anything.
				getContentPane().getGraphics().drawImage(game.render(),
						0 - game.viewx, 0 - game.viewy, this);
			} // TODO: PLayer perspective check
			setTitle(Long.toString(delta));

			if ((System.currentTimeMillis() - lastmillis) < minFrameHoldMillis) {
				// If the system finished rendering faster than the specified
				// minimum frame hold time,
				// then wait a bit before rendering again.
				try {
					Thread.sleep(minFrameHoldMillis
							- System.currentTimeMillis() + lastmillis);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}

			// loads a save
			if (newGame != null) {
				game = newGame;
				newGame = null;
				game.running = true;
			}
		}

	}



	@Override
	public void mouseDragged(MouseEvent arg0) {
		game.mouseDragged(arg0);

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		game.mouseMoved(arg0);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		game.mouseClicked(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		game.mouseEntered(arg0);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		game.mouseExited(arg0);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		game.mousePressed(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		game.mouseReleased(arg0);
	}

}
