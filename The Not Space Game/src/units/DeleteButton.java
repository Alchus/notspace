package units;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import core.Game;
import core.Player;
import core.Unit;
import core.Window;
import core.XYPair;

public class DeleteButton extends Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2900659596091411105L;

	public DeleteButton() {
		owner = Player.INTERFACE;
		moveTo(new XYPair(50, 120));
		width = 40;
		height = 40;
		mobile = false;
		canCollide = false;

	}

	@Override
	public void destroy(int cause) {

	}

	public void onClick() {
		System.out.println("Delete button pushed");
		Window.main.game.cursor = Game.UNITTARGET;
	}

	public void targetSelected(Unit tar) {
		System.out.println("Deleting");
		tar.owner = Player.MISSILE;
		tar.destroy(HEALTH);
	}

	public BufferedImage draw() {

		img = (BufferedImage) super.draw();

		Graphics2D g2 = img.createGraphics();

		g2.setColor(controllerColor());
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(0, 0, height - 1, width - 1);
		// g2.fillOval(10, 10, 15, 15);
		g2.setFont(new Font("Arial", Font.BOLD, 20));
		g2.drawString("X", 10, 25);

		return img;
	}

}
