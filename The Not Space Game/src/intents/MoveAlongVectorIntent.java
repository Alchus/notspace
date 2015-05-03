package intents;

import core.Intent;
import core.Unit;
import core.XYPair;

public class MoveAlongVectorIntent extends Intent {

    /**
     *
     */
    private static final long serialVersionUID = -2251544194715386166L;
    XYPair vector;

    public MoveAlongVectorIntent(XYPair vector) {
        this.vector = vector;
        priority = MOVEMENT;
        removesEqualOrLowerPrority = true;
    }

    public void update(Unit u) {
        u.velocity = vector.unitVector().rescale(u.movespeed);
    }

    public Intent clone() {
        MoveAlongVectorIntent copy = (MoveAlongVectorIntent) super.clone();
        copy.vector = vector.clone();
        return copy;

    }

}
