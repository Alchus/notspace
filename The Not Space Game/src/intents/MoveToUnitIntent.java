package intents;

import core.Intent;
import core.Unit;
import core.XYPair;

public class MoveToUnitIntent extends Intent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1872603937274307708L;
	Unit target;

	public MoveToUnitIntent(Unit Target) {
		target = Target;
		priority = MOVEMENT;
	}

	public void update(Unit u) {


		// TODO Path-finding algorithm
		XYPair difference = u.center().vectorTo(target.center());
		
		u.limitNextMove = true;
		u.moveLimit = difference;
		u.velocity = difference.unitVector().rescale(u.movespeed);

		if (difference.magnitude() < 1)
			expired = true;

	}
}
