package intents;

import core.Intent;
import core.Unit;
import core.XYPair;

public class IdleIntent extends Intent {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1609908316340457743L;

	public void update(Unit u) {

		u.velocity = new XYPair(0, 0);

	}

	
}
