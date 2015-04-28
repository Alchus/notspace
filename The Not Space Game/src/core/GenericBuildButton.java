package core;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GenericBuildButton extends Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3270764480371972272L;

	public GenericBuildButton(Unit genUnit) {
		owner = Player.INTERFACE;
		moveTo(new XYPair(100, 50));
		width = 60;
		height = 80;
		mobile = false;
		canCollide = false;

		this.genUnit = genUnit;
		genUnit.energy = genUnit.maxenergy;

		children.add(genUnit);

	}

	Unit genUnit = null;

	@Override
	public void destroy(int cause) {
		// TODO Auto-generated method stub

	}

	public void onClick() {
		System.out.println("Build menu button pushed");
		Window.main.game.cursor = Game.BLUEPRINT;
	}

	public void targetSelected(int tarX, int tarY) {
		Unit outgoing = genUnit.clone();
		outgoing.moveTo(new XYPair(tarX, tarY));

		outgoing.generateBounds();

		Unit[] collisions = game.checkCollisions(outgoing);
		if (collisions.length == 0) {

			Window.main.game.babies.add(outgoing);
		} else {
			game.selection = collisions[0];
			selected = false;
			game.cursor = Game.SELECTION;
		}
	}

	public BufferedImage draw() {

		img = (BufferedImage) super.draw();

		Graphics2D g2 = img.createGraphics();

		g2.setColor(controllerColor());
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(0, 0, width - 1, height - 1);
		// g2.fillOval(10, 10, 15, 15);
		// g2.setFont(new Font("Arial", Font.BOLD, 20));
		// g2.drawString("+", 10, 25);

		return img;
	}

}
