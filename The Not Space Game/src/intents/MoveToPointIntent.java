package intents;

import java.io.Serializable;

import core.Intent;
import core.Unit;
import core.XYPair;

public class MoveToPointIntent extends Intent implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1476786569304137616L;
    XYPair target;

    public MoveToPointIntent(double x, double y) {
        target = new XYPair(x, y);
        priority = Intent.MOVEMENT;
        removesEqualOrLowerPrority = true;
    }

    public MoveToPointIntent(XYPair target) {
        this(target.x, target.y);
    }

    public void update(Unit u) {

        // TODO Path-finding algorithm
        XYPair difference = u.center().vectorTo(target);

        u.limitNextMove = true;
        u.moveLimit = difference;

        u.velocity = difference.unitVector().rescale(u.movespeed);

        if (difference.magnitude() < 1) {
            expired = true;
        }

    }

}
