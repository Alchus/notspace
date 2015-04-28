package core;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

public class LoadButton extends Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1542993818270959719L;

	public LoadButton() {
		owner = Player.INTERFACE;
		moveTo(new XYPair(50, 400));
		width = 40;
		height = 40;
		mobile = false;
		canCollide = false;

	}

	@Override
	public void destroy(int cause) {
		// TODO Auto-generated method stub

	}

	public void onClick() {
		System.out.println("Loading");
		game.running = false;
		if (Window.jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream inputFileStream = new FileInputStream(
						Window.jfc.getSelectedFile());
				ObjectInputStream objectInputStream = new ObjectInputStream(
						inputFileStream);
				Window.main.newGame = (Game) objectInputStream.readObject();
				objectInputStream.close();
				inputFileStream.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException i) {
				i.printStackTrace();
			}
			System.out.println("Loading game at "
					+ Window.jfc.getSelectedFile().getAbsolutePath());
		}
		game.running = true;
	}

	public BufferedImage draw() {

		img = (BufferedImage) super.draw();

		Graphics2D g2 = img.createGraphics();

		g2.setColor(controllerColor());
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(0, 0, height - 1, width - 1);
		// g2.fillOval(10, 10, 15, 15);
		g2.setFont(new Font("Arial", Font.BOLD, 14));
		g2.drawString("Load", 3, 25);

		return img;
	}

}
