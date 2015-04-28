package intents;
import java.io.Serializable;

import core.Intent;
import core.Unit;
import core.XYPair;


public class HoldPositionIntent extends Intent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5415051287755628092L;

	public HoldPositionIntent() {
		priority = Intent.MOVEMENT;
		removesEqualOrLowerPrority = true;
	}
	
	public void update (Unit u){
		u.velocity = new XYPair(0, 0);
	}

}
