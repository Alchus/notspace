package units;
import intents.MoveToUnitIntent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import core.Player;
import core.Unit;
import core.XYPair;


public class Shooter extends Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2845322846968039700L;

	public Shooter(XYPair location, Player controller) {
		
		owner = controller;
		
		moveTo(location.clone());
		
		mobile = true;
		
		canCollide = true;
		collidesWithEnemies = true;
		maxhealth = 100;
		health = 100;
		movespeed = 50;
		energyregen = 150;
		maxenergy = 50;

		healthregen = 0;

		height = 15;
		width = 15;	
	}
	
	public BufferedImage draw() {
		img = (BufferedImage) super.draw();
		Graphics2D g2 = img.createGraphics();

		g2.setColor(Color.pink);
		g2.fillPolygon(new int[] { 0, 15, 8 }, new int[] { 0, 0, 15 }, 3);
		return img;
	}
	
	public void refreshIntents() {
		issueCommand(new MoveToUnitIntent(Player.PLAYER1.heroUnit));
	}

	
	public void onCollide(Unit u) {
		if (u.owner == Player.PLAYER1) {
			u.damage(101, this);
			destroy(SELFDESTRUCT);
		}
	}
	
	
	

}
