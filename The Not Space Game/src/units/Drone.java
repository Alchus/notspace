package units;
import intents.MoveToUnitIntent;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import core.Player;
import core.Unit;
import core.XYPair;
import java.awt.Color;

public class Drone extends Unit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7054355014761985339L;

	public Drone(double x, double y, Player controller) {
		owner = controller;

		moveTo(new XYPair(x, y));

		mobile = true;

		canCollide = true;
		collidesWithEnemies = true;
		maxhealth = 600;
		health = 600;
		movespeed = 50;
		energyregen = 150;
		maxenergy = 50;
		
		destroyOnOffMap = true;

		healthregen = 0;

		height = 30;
		width = 30;
		
		defaultIntents.add(new MoveToUnitIntent(Player.PLAYER1.heroUnit));

	}

	public BufferedImage draw() {
		img = (BufferedImage) super.draw();
		Graphics2D g2 = img.createGraphics();

		g2.setColor(Color.green);
		g2.fillPolygon(new int[] { 0, 30, 15 }, new int[] { 0, 0, 30 }, 3);
		return img;
	}

	

	public void onCollide(Unit u) {
		if (u.owner.isHostileTo(owner)) {
			u.damage(200, this);
			destroy(SELFDESTRUCT);
		}
	}

}
