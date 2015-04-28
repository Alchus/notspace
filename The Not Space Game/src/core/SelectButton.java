package core;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SelectButton extends Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5988388955184257021L;

	public SelectButton() {
		owner = Player.INTERFACE;
		moveTo(new XYPair(50, 200));
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
		System.out.println("Select button pushed");
		Window.main.game.cursor = Game.SELECTION;
	}

	public void targetSelected(Unit tar) {
		// This button itself doesn't do anything with or to a selected unit.
	}

	public BufferedImage draw() {

		img = (BufferedImage) super.draw();

		Graphics2D g2 = img.createGraphics();

		g2.setColor(controllerColor());
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(0, 0, height - 1, width - 1);
		// g2.fillOval(10, 10, 15, 15);
		g2.setFont(new Font("Arial", Font.BOLD, 20));
		g2.drawString(">", 10, 25);

		return img;
	}

}
