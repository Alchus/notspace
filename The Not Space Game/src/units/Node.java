package units;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import core.Player;
import core.Unit;
import core.XYPair;

public class Node extends Unit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3239845369803596507L;

	public Node(int xpos, int ypos, Player controller) {
		owner = controller;
		moveTo(new XYPair(xpos, ypos));

		mobile = false;

		canCollide = true;
		maxhealth = 150;
		health = 150;

		energy = 0;
		energyregen = 0.01;
		maxenergy = 2;

		healthregen = 1;

		height = 10;
		width = 10;

	}

	@Override
	public void onTick(int delta) {

		redraw = true; // TODO fix on graphics fixes
		// redraw = (health != maxhealth || energy != maxenergy);

	}

	public void generateBounds() {
		bounds = new Area(new Ellipse2D.Double(position().x, position().y, width,
				height));
	}

	@Override
	public BufferedImage draw() {

		img = (BufferedImage) super.draw();

		Graphics2D g2 = img.createGraphics();

		g2.setColor(getEnergyColor());

		g2.setStroke(new BasicStroke(3));

		g2.fillOval(2, 2, width - 4, height - 4);
		g2.setColor(controllerColor());
		g2.drawOval(2, 2, width - 4, height - 4);
		g2.fillOval(1, 1, 8, 8);

		return img;
	}

}
