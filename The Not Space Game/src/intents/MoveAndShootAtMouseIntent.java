package intents;
import java.awt.event.MouseEvent;

import core.Intent;
import core.Unit;
import core.XYPair;


public class MoveAndShootAtMouseIntent extends Intent {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9046286072102506039L;
	XYPair vector;
	MouseEvent e;
	
	public MoveAndShootAtMouseIntent(XYPair vector, MouseEvent lastMouseEvent) {
		e = lastMouseEvent;
		this.vector = vector;
		priority = MOVEMENT;
		removesEqualOrLowerPrority = true;
	}
	
	public void update(Unit u){
		u.basicAttack.sourceUnit = u;
		u.velocity = vector.unitVector().rescale(u.movespeed);
		u.basicAttack.targetPoint = new XYPair(e.getX(),e.getY());
		u.basicAttack.start();
	}

}
